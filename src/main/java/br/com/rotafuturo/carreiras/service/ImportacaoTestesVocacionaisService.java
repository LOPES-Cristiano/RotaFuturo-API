package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoTestesVocacionaisResultadoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoVinculoRepository;
import br.com.rotafuturo.carreiras.repository.TesteRepository;

@Service
public class ImportacaoTestesVocacionaisService {

    @Autowired
    private TesteRepository testeRepository;

    @Autowired
    private TesteQuestaoRepository testeQuestaoRepository;

    @Autowired
    private TesteQuestaoVinculoRepository testeQuestaoVinculoRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaSubRepository areaSubRepository;

    @Transactional
    public ImportacaoTestesVocacionaisResultadoDTO importarTestesVocacionais(MultipartFile arquivo) {
        ImportacaoTestesVocacionaisResultadoDTO resultado = new ImportacaoTestesVocacionaisResultadoDTO();

        try (Workbook workbook = WorkbookFactory.create(arquivo.getInputStream())) {
            
            // Importar abas em ordem
            resultado.setTestes(importarTestes(workbook));
            resultado.setTestesQuestao(importarTestesQuestao(workbook));
            resultado.setTestesQuestaoVinculo(importarTestesQuestaoVinculo(workbook));

            // Verificar sucesso geral
            int totalErros = resultado.getTotalErros();
            resultado.setSucesso(totalErros == 0);
            resultado.setMensagemGeral(
                totalErros == 0 
                    ? "Importação concluída com sucesso! Total de registros inseridos: " + resultado.getTotalInseridos()
                    : "Importação concluída com " + totalErros + " erro(s). Verifique os detalhes."
            );

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagemGeral("Erro ao processar arquivo: " + e.getMessage());
        }

        return resultado;
    }

    private ImportacaoResultadoDTO importarTestes(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("02-testes-vocacionais.xlsx", "TESTE");
        
        Sheet sheet = workbook.getSheet("TESTE");
        if (sheet == null) {
            resultado.adicionarErro(0, "Aba TESTE não encontrada");
            return resultado;
        }

        Map<String, AreaBean> cacheAreas = new HashMap<>();
        Map<String, AreaSubBean> cacheAreasSub = new HashMap<>();

        int totalLinhas = sheet.getLastRowNum();
        resultado.setTotalLinhas(totalLinhas);

        for (int i = 1; i <= totalLinhas; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String tesDescricao = row.getCell(0) != null ? row.getCell(0).getStringCellValue().trim() : null;
                String areaDescricaoRef = row.getCell(1) != null ? row.getCell(1).getStringCellValue().trim() : null;
                String areasDescricaoRef = row.getCell(2) != null ? row.getCell(2).getStringCellValue().trim() : null;

                if (tesDescricao == null || tesDescricao.isEmpty()) {
                    continue; // Linha vazia
                }

                // Verificar se já existe
                if (testeRepository.findByTesDescricao(tesDescricao).isPresent()) {
                    continue; // Já existe
                }

                TesteBean teste = new TesteBean();
                teste.setTesDescricao(tesDescricao);
                teste.setTesDatacadastro(LocalDate.now());
                teste.setTesHoracadastro(LocalTime.now());

                // Resolver FK AREA
                if (areaDescricaoRef != null && !areaDescricaoRef.equals("NULL")) {
                    AreaBean area = cacheAreas.computeIfAbsent(areaDescricaoRef, key ->
                        areaRepository.findByAreaDescricao(key).orElse(null)
                    );
                    if (area != null) {
                        teste.setArea(area);
                    } else {
                        resultado.adicionarErro(i + 1, "Área não encontrada: " + areaDescricaoRef);
                        continue;
                    }
                }

                // Resolver FK AREASUB
                if (areasDescricaoRef != null && !areasDescricaoRef.equals("NULL")) {
                    AreaSubBean areaSub = cacheAreasSub.computeIfAbsent(areasDescricaoRef, key ->
                        areaSubRepository.findByAreasDescricao(key).orElse(null)
                    );
                    if (areaSub != null) {
                        teste.setAreaSub(areaSub);
                    } else {
                        resultado.adicionarErro(i + 1, "Curso não encontrado: " + areasDescricaoRef);
                        continue;
                    }
                }

                testeRepository.save(teste);
                resultado.incrementarInseridos();

            } catch (Exception e) {
                resultado.adicionarErro(i + 1, "Erro: " + e.getMessage());
            }
        }

        resultado.setMensagem("Processados " + resultado.getInseridos() + " testes de " + totalLinhas + " linhas");
        resultado.setSucesso(resultado.getErros() == 0);
        return resultado;
    }

    private ImportacaoResultadoDTO importarTestesQuestao(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("02-testes-vocacionais.xlsx", "TESTEQUESTAO");
        
        Sheet sheet = workbook.getSheet("TESTEQUESTAO");
        if (sheet == null) {
            resultado.adicionarErro(0, "Aba TESTEQUESTAO não encontrada");
            return resultado;
        }

        Map<String, AreaBean> cacheAreas = new HashMap<>();
        Map<String, AreaSubBean> cacheAreasSub = new HashMap<>();

        int totalLinhas = sheet.getLastRowNum();
        resultado.setTotalLinhas(totalLinhas);

        for (int i = 1; i <= totalLinhas; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String tesqDescricao = row.getCell(0) != null ? row.getCell(0).getStringCellValue().trim() : null;
                String areaDescricaoRef = row.getCell(1) != null ? row.getCell(1).getStringCellValue().trim() : null;
                String areasDescricaoRef = row.getCell(2) != null ? row.getCell(2).getStringCellValue().trim() : null;

                if (tesqDescricao == null || tesqDescricao.isEmpty()) {
                    continue;
                }

                if (testeQuestaoRepository.findByTesqDescricao(tesqDescricao).isPresent()) {
                    continue;
                }

                TesteQuestaoBean questao = new TesteQuestaoBean();
                questao.setTesqDescricao(tesqDescricao);
                questao.setTesqDatacadastro(LocalDate.now());
                questao.setTesqHoracadastro(LocalTime.now());

                // Resolver FKs
                if (areaDescricaoRef != null && !areaDescricaoRef.equals("NULL")) {
                    AreaBean area = cacheAreas.computeIfAbsent(areaDescricaoRef, key ->
                        areaRepository.findByAreaDescricao(key).orElse(null)
                    );
                    if (area != null) {
                        questao.setArea(area);
                    }
                }

                if (areasDescricaoRef != null && !areasDescricaoRef.equals("NULL")) {
                    AreaSubBean areaSub = cacheAreasSub.computeIfAbsent(areasDescricaoRef, key ->
                        areaSubRepository.findByAreasDescricao(key).orElse(null)
                    );
                    if (areaSub != null) {
                        questao.setAreaSub(areaSub);
                    }
                }

                testeQuestaoRepository.save(questao);
                resultado.incrementarInseridos();

            } catch (Exception e) {
                resultado.adicionarErro(i + 1, "Erro: " + e.getMessage());
            }
        }

        resultado.setMensagem("Processadas " + resultado.getInseridos() + " questões de " + totalLinhas + " linhas");
        resultado.setSucesso(resultado.getErros() == 0);
        return resultado;
    }

    private ImportacaoResultadoDTO importarTestesQuestaoVinculo(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("02-testes-vocacionais.xlsx", "TESTEQUESTAOVINCULO");
        
        Sheet sheet = workbook.getSheet("TESTEQUESTAOVINCULO");
        if (sheet == null) {
            resultado.adicionarErro(0, "Aba TESTEQUESTAOVINCULO não encontrada");
            return resultado;
        }

        Map<String, TesteBean> cacheTestes = new HashMap<>();
        Map<String, TesteQuestaoBean> cacheQuestoes = new HashMap<>();

        int totalLinhas = sheet.getLastRowNum();
        resultado.setTotalLinhas(totalLinhas);

        for (int i = 1; i <= totalLinhas; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String tesDescricaoRef = row.getCell(0) != null ? row.getCell(0).getStringCellValue().trim() : null;
                String tesqDescricaoRef = row.getCell(1) != null ? row.getCell(1).getStringCellValue().trim() : null;

                if (tesDescricaoRef == null || tesqDescricaoRef == null) {
                    continue;
                }

                TesteBean teste = cacheTestes.computeIfAbsent(tesDescricaoRef, key ->
                    testeRepository.findByTesDescricao(key).orElse(null)
                );

                TesteQuestaoBean questao = cacheQuestoes.computeIfAbsent(tesqDescricaoRef, key ->
                    testeQuestaoRepository.findByTesqDescricao(key).orElse(null)
                );

                if (teste == null) {
                    resultado.adicionarErro(i + 1, "Teste não encontrado: " + tesDescricaoRef);
                    continue;
                }

                if (questao == null) {
                    resultado.adicionarErro(i + 1, "Questão não encontrada: " + tesqDescricaoRef);
                    continue;
                }

                TesteQuestaoVinculoBean vinculo = new TesteQuestaoVinculoBean();
                vinculo.setTeste(teste);
                vinculo.setTesteQuestao(questao);

                testeQuestaoVinculoRepository.save(vinculo);
                resultado.incrementarInseridos();

            } catch (Exception e) {
                resultado.adicionarErro(i + 1, "Erro: " + e.getMessage());
            }
        }

        resultado.setMensagem("Processados " + resultado.getInseridos() + " vínculos de " + totalLinhas + " linhas");
        resultado.setSucesso(resultado.getErros() == 0);
        return resultado;
    }
}
