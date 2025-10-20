package br.com.rotafuturo.carreiras.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoEstruturaBaseResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoResultadoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.QuestaoTipoBean;
import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.NivelRepository;
import br.com.rotafuturo.carreiras.repository.QuestaoTipoRepository;
import br.com.rotafuturo.carreiras.repository.QuestionarioTipoRepository;

@Service
public class ImportacaoEstruturaBaseService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaSubRepository areaSubRepository;

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private QuestionarioTipoRepository questionarioTipoRepository;

    @Autowired
    private QuestaoTipoRepository questaoTipoRepository;

    @Transactional
    public ImportacaoEstruturaBaseResultadoDTO importarEstruturaBase(MultipartFile arquivo) {
        ImportacaoEstruturaBaseResultadoDTO resultado = new ImportacaoEstruturaBaseResultadoDTO();

        try (InputStream is = arquivo.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {

            System.out.println("=== INICIANDO IMPORTAÇÃO ESTRUTURA BASE ===");
            
            // Importar cada aba na ordem de dependência
            System.out.println("Importando AREAS...");
            resultado.setAreas(importarAreas(workbook));
            
            System.out.println("Importando AREAS SUB...");
            resultado.setAreasSub(importarAreasSub(workbook));
            
            System.out.println("Importando NIVEIS...");
            resultado.setNiveis(importarNiveis(workbook));
            
            System.out.println("Importando QUESTIONARIO TIPOS...");
            resultado.setQuestionarioTipos(importarQuestionarioTipos(workbook));
            
            System.out.println("Importando QUESTAO TIPOS...");
            resultado.setQuestaoTipos(importarQuestaoTipos(workbook));

            // Verificar sucesso geral
            boolean sucesso = resultado.getTotalErros() == 0;
            resultado.setSucesso(sucesso);

            if (sucesso) {
                resultado.setMensagemGeral(
                        "Importação concluída com sucesso! " + resultado.getTotalInseridos() + " registros inseridos.");
            } else {
                resultado.setMensagemGeral("Importação concluída com erros. " + resultado.getTotalErros()
                        + " erros encontrados. " + resultado.getTotalInseridos() + " registros inseridos.");
            }

        } catch (IOException e) {
            System.err.println("ERRO DE IO: " + e.getMessage());
            e.printStackTrace();
            resultado.setSucesso(false);
            resultado.setMensagemGeral("Erro ao processar arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRO GENÉRICO: " + e.getMessage());
            e.printStackTrace();
            resultado.setSucesso(false);
            resultado.setMensagemGeral("Erro ao processar importação: " + e.getMessage());
        }

        System.out.println("=== FIM IMPORTAÇÃO - SUCESSO: " + resultado.isSucesso() + " ===");
        return resultado;
    }

    private ImportacaoResultadoDTO importarAreas(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("01-estrutura-base.xlsx", "AREA");
        Sheet sheet = workbook.getSheet("AREA");

        if (sheet == null) {
            System.err.println("❌ Aba AREA não encontrada!");
            resultado.setErros(1);
            resultado.setMensagem("Aba AREA não encontrada");
            resultado.setSucesso(false);
            return resultado;
        }

        System.out.println("✓ Aba AREA encontrada. Total de linhas: " + sheet.getLastRowNum());
        
        int inseridos = 0;
        int erros = 0;
        int totalLinhas = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            Cell cellDescricao = row.getCell(0);
            if (cellDescricao == null || cellDescricao.toString().trim().isEmpty()) {
                continue; // Pula linha vazia
            }

            totalLinhas++;

            try {
                String descricao = cellDescricao.toString().trim();

                // Verifica se já existe
                if (areaRepository.findByAreaDescricao(descricao).isEmpty()) {
                    AreaBean area = new AreaBean();
                    area.setAreaDescricao(descricao);
                    area.setAreaDatacadastro(LocalDate.now());
                    area.setAreaHoracadastro(LocalTime.now());
                    area.setAreaAtivo(true);
                    areaRepository.save(area);
                    inseridos++;
                }

            } catch (Exception e) {
                erros++;
                String msgErro = "Erro ao processar linha: " + e.getMessage();
                System.err.println("❌ AREA linha " + (i + 1) + ": " + msgErro);
                e.printStackTrace();
                resultado.adicionarErro(i + 1, msgErro);
            }
        }

        resultado.setTotalLinhas(totalLinhas);
        resultado.setInseridos(inseridos);
        resultado.setErros(erros);
        resultado.setSucesso(erros == 0);
        resultado.setMensagem(inseridos + " áreas importadas");

        System.out.println("✓ AREA: " + inseridos + " inseridos, " + erros + " erros");
        return resultado;
    }

    private ImportacaoResultadoDTO importarAreasSub(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("01-estrutura-base.xlsx", "AREASUB");
        Sheet sheet = workbook.getSheet("AREASUB");

        if (sheet == null) {
            System.err.println("❌ Aba AREASUB não encontrada!");
            resultado.setErros(1);
            resultado.setMensagem("Aba AREASUB não encontrada");
            resultado.setSucesso(false);
            return resultado;
        }

        System.out.println("✓ Aba AREASUB encontrada. Total de linhas: " + sheet.getLastRowNum());
        
        int inseridos = 0;
        int erros = 0;
        int totalLinhas = 0;

        // Cache de áreas para performance
        Map<String, AreaBean> areaCache = new HashMap<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            Cell cellDescricao = row.getCell(0);
            Cell cellAreaRef = row.getCell(1);

            if (cellDescricao == null || cellDescricao.toString().trim().isEmpty()) {
                continue; // Pula linha vazia
            }

            totalLinhas++;

            try {
                String descricao = cellDescricao.toString().trim();
                String areaDescricaoRef = cellAreaRef.toString().trim();

                // Busca área referenciada (com cache)
                AreaBean area = areaCache.get(areaDescricaoRef);
                if (area == null) {
                    area = areaRepository.findByAreaDescricao(areaDescricaoRef)
                            .orElseThrow(() -> new RuntimeException("Área não encontrada: " + areaDescricaoRef));
                    areaCache.put(areaDescricaoRef, area);
                }

                // Verifica se já existe
                if (areaSubRepository.findByAreasDescricao(descricao).isEmpty()) {
                    AreaSubBean areaSub = new AreaSubBean();
                    areaSub.setAreasDescricao(descricao);
                    areaSub.setArea(area);
                    areaSub.setAreasDatacadastro(LocalDate.now());
                    areaSub.setAreasHoracadastro(LocalTime.now());
                    areaSub.setAreasAtivo(true);
                    areaSubRepository.save(areaSub);
                    inseridos++;
                }

            } catch (Exception e) {
                erros++;
                String msgErro = "Erro ao processar linha: " + e.getMessage();
                System.err.println("❌ AREASUB linha " + (i + 1) + ": " + msgErro);
                e.printStackTrace();
                resultado.adicionarErro(i + 1, msgErro);
            }
        }

        resultado.setTotalLinhas(totalLinhas);
        resultado.setInseridos(inseridos);
        resultado.setErros(erros);
        resultado.setSucesso(erros == 0);
        resultado.setMensagem(inseridos + " cursos importados");

        System.out.println("✓ AREASUB: " + inseridos + " inseridos, " + erros + " erros");
        return resultado;
    }

    private ImportacaoResultadoDTO importarNiveis(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("01-estrutura-base.xlsx", "NIVEL");
        Sheet sheet = workbook.getSheet("NIVEL");

        if (sheet == null) {
            System.err.println("❌ Aba NIVEL não encontrada!");
            resultado.setErros(1);
            resultado.setMensagem("Aba NIVEL não encontrada");
            resultado.setSucesso(false);
            return resultado;
        }

        System.out.println("✓ Aba NIVEL encontrada. Total de linhas: " + sheet.getLastRowNum());
        
        int inseridos = 0;
        int erros = 0;
        int totalLinhas = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            Cell cellId = row.getCell(0);
            Cell cellDescricao = row.getCell(1);

            if (cellId == null || cellDescricao == null || cellDescricao.toString().trim().isEmpty()) {
                continue;
            }

            totalLinhas++;

            try {
                Integer id = (int) cellId.getNumericCellValue();
                String descricao = cellDescricao.toString().trim();

                // Usa INSERT IGNORE para evitar erro se já existe
                // MySQL vai simplesmente ignorar se o ID já existir
                nivelRepository.insertIfNotExists(id, descricao);
                inseridos++;
                System.out.println("  → Nível " + id + " processado: " + descricao);

            } catch (Exception e) {
                erros++;
                String msgErro = "Erro ao processar linha: " + e.getMessage();
                System.err.println("❌ NIVEL linha " + (i + 1) + ": " + msgErro);
                e.printStackTrace();
                resultado.adicionarErro(i + 1, msgErro);
            }
        }

        resultado.setTotalLinhas(totalLinhas);
        resultado.setInseridos(inseridos);
        resultado.setErros(erros);
        resultado.setSucesso(erros == 0);
        resultado.setMensagem(inseridos + " níveis importados");

        System.out.println("✓ NIVEL: " + inseridos + " inseridos, " + erros + " erros");
        return resultado;
    }

    private ImportacaoResultadoDTO importarQuestionarioTipos(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("01-estrutura-base.xlsx", "QUESTIONARIOTIPO");
        Sheet sheet = workbook.getSheet("QUESTIONARIOTIPO");

        if (sheet == null) {
            System.err.println("❌ Aba QUESTIONARIOTIPO não encontrada!");
            resultado.setErros(1);
            resultado.setMensagem("Aba QUESTIONARIOTIPO não encontrada");
            resultado.setSucesso(false);
            return resultado;
        }

        System.out.println("✓ Aba QUESTIONARIOTIPO encontrada. Total de linhas: " + sheet.getLastRowNum());
        
        int inseridos = 0;
        int erros = 0;
        int totalLinhas = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            Cell cellDescricao = row.getCell(0);

            if (cellDescricao == null || cellDescricao.toString().trim().isEmpty()) {
                continue;
            }

            totalLinhas++;

            try {
                String descricao = cellDescricao.toString().trim();

                // Verifica se já existe
                if (questionarioTipoRepository.findByQuestDescricao(descricao).isEmpty()) {
                    QuestionarioTipoBean tipo = new QuestionarioTipoBean();
                    tipo.setQuestDescricao(descricao);
                    tipo.setQuestAtivo(true);
                    questionarioTipoRepository.save(tipo);
                    inseridos++;
                }

            } catch (Exception e) {
                erros++;
                String msgErro = "Erro ao processar linha: " + e.getMessage();
                System.err.println("❌ QUESTIONARIOTIPO linha " + (i + 1) + ": " + msgErro);
                e.printStackTrace();
                resultado.adicionarErro(i + 1, msgErro);
            }
        }

        resultado.setTotalLinhas(totalLinhas);
        resultado.setInseridos(inseridos);
        resultado.setErros(erros);
        resultado.setSucesso(erros == 0);
        resultado.setMensagem(inseridos + " tipos de questionário importados");

        System.out.println("✓ QUESTIONARIOTIPO: " + inseridos + " inseridos, " + erros + " erros");
        return resultado;
    }

    private ImportacaoResultadoDTO importarQuestaoTipos(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO("01-estrutura-base.xlsx", "QUESTAOTIPO");
        Sheet sheet = workbook.getSheet("QUESTAOTIPO");

        if (sheet == null) {
            System.err.println("❌ Aba QUESTAOTIPO não encontrada!");
            resultado.setErros(1);
            resultado.setMensagem("Aba QUESTAOTIPO não encontrada");
            resultado.setSucesso(false);
            return resultado;
        }

        System.out.println("✓ Aba QUESTAOTIPO encontrada. Total de linhas: " + sheet.getLastRowNum());
        
        int inseridos = 0;
        int erros = 0;
        int totalLinhas = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            Cell cellDescricao = row.getCell(0);

            if (cellDescricao == null || cellDescricao.toString().trim().isEmpty()) {
                continue;
            }

            totalLinhas++;

            try {
                String descricao = cellDescricao.toString().trim();

                // Verifica se já existe
                if (questaoTipoRepository.findByQuetDescricao(descricao).isEmpty()) {
                    QuestaoTipoBean tipo = new QuestaoTipoBean();
                    tipo.setQuetDescricao(descricao);
                    tipo.setQuetAtivo(true);
                    questaoTipoRepository.save(tipo);
                    inseridos++;
                }

            } catch (Exception e) {
                erros++;
                String msgErro = "Erro ao processar linha: " + e.getMessage();
                System.err.println("❌ QUESTAOTIPO linha " + (i + 1) + ": " + msgErro);
                e.printStackTrace();
                resultado.adicionarErro(i + 1, msgErro);
            }
        }

        resultado.setTotalLinhas(totalLinhas);
        resultado.setInseridos(inseridos);
        resultado.setErros(erros);
        resultado.setSucesso(erros == 0);
        resultado.setMensagem(inseridos + " tipos de questão importados");

        System.out.println("✓ QUESTAOTIPO: " + inseridos + " inseridos, " + erros + " erros");
        return resultado;
    }
}
