package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoDesafiosResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoResultadoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.DesafioBean;
import br.com.rotafuturo.carreiras.model.DesafioQuestionarioBean;
import br.com.rotafuturo.carreiras.model.NivelBean;
import br.com.rotafuturo.carreiras.model.QuestionarioBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.DesafioQuestionarioRepository;
import br.com.rotafuturo.carreiras.repository.DesafioRepository;
import br.com.rotafuturo.carreiras.repository.NivelRepository;
import br.com.rotafuturo.carreiras.repository.QuestionarioRepository;

@Service
public class ImportacaoDesafiosService {

    @Autowired
    private DesafioRepository desafioRepository;

    @Autowired
    private DesafioQuestionarioRepository desafioQuestionarioRepository;

    @Autowired
    private QuestionarioRepository questionarioRepository;

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaSubRepository areaSubRepository;

    @Transactional
    public ImportacaoDesafiosResultadoDTO importarDesafios(MultipartFile arquivo) {
        ImportacaoDesafiosResultadoDTO resultado = new ImportacaoDesafiosResultadoDTO();

        try (Workbook workbook = WorkbookFactory.create(arquivo.getInputStream())) {
            resultado.setDesafios(importarDesafio(workbook));
            resultado.setDesafiosQuestionario(importarDesafioQuestionario(workbook));

            return resultado;
        } catch (Exception e) {
            ImportacaoDesafiosResultadoDTO erroGeral = new ImportacaoDesafiosResultadoDTO();
            ImportacaoResultadoDTO erro = new ImportacaoResultadoDTO();
            erro.setPlanilha("04-desafios");
            erro.setSucesso(false);
            erro.setMensagem("Erro ao processar arquivo: " + e.getMessage());
            erro.adicionarErro(0, "Erro geral: " + e.getMessage());
            erroGeral.setDesafios(erro);
            return erroGeral;
        }
    }

    private ImportacaoResultadoDTO importarDesafio(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO();
        resultado.setPlanilha("04-desafios");
        resultado.setAba("DESAFIO");

        try {
            Sheet sheet = workbook.getSheet("DESAFIO");
            if (sheet == null) {
                resultado.setSucesso(false);
                resultado.setMensagem("Aba DESAFIO não encontrada");
                return resultado;
            }

            int totalLinhas = sheet.getLastRowNum();
            resultado.setTotalLinhas(totalLinhas);

            // Cache para melhor performance
            Map<Integer, NivelBean> cacheNiveisPorId = new HashMap<>();
            Map<String, AreaBean> cacheAreas = new HashMap<>();
            Map<String, AreaSubBean> cacheAreaSubs = new HashMap<>();

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    // Coluna A: DES_TITULO
                    String desTitulo = getCellValue(row.getCell(0));
                    // Coluna B: DES_DESCRICAO
                    String desDescricao = getCellValue(row.getCell(1));
                    // Coluna C: NIV_ID (número)
                    String nivelIdStr = getCellValue(row.getCell(2));
                    // Coluna D: AREA_REF (descrição da área)
                    String areaDescricao = getCellValue(row.getCell(3));
                    // Coluna E: AREASUB_REF (descrição da subárea)
                    String areaSubDescricao = getCellValue(row.getCell(4));

                    if (desTitulo == null || desTitulo.trim().isEmpty()) {
                        resultado.adicionarErro(i, "DESAFIO - Título vazio");
                        continue;
                    }

                    // Verificar se já existe
                    Optional<DesafioBean> existente = desafioRepository.findByDesTitulo(desTitulo);

                    DesafioBean desafio;
                    boolean isUpdate = existente.isPresent();
                    
                    if (isUpdate) {
                        desafio = existente.get();
                    } else {
                        desafio = new DesafioBean();
                        desafio.setDesTitulo(desTitulo);
                    }
                    
                    // Atualiza campos básicos
                    desafio.setDesDescricao(desDescricao);
                    desafio.setDesDatacadastro(LocalDate.now());
                    desafio.setDesHoracadastro(LocalTime.now());

                    // Buscar e associar Nível por ID (se informado)
                    if (nivelIdStr != null && !nivelIdStr.trim().isEmpty()) {
                        try {
                            Integer nivelId = Integer.parseInt(nivelIdStr);
                            NivelBean nivel = cacheNiveisPorId.computeIfAbsent(nivelId, id -> {
                                Optional<NivelBean> opt = nivelRepository.findById(id);
                                return opt.orElse(null);
                            });
                            
                            if (nivel != null) {
                                desafio.setNivel(nivel);
                            } else {
                                resultado.adicionarErro(i, "DESAFIO - Nível com ID " + nivelId + " não encontrado");
                            }
                        } catch (NumberFormatException e) {
                            resultado.adicionarErro(i, "DESAFIO - NIV_ID inválido: " + nivelIdStr);
                        }
                    }

                    // Buscar e associar Área (se informado)
                    if (areaDescricao != null && !areaDescricao.trim().isEmpty()) {
                        AreaBean area = cacheAreas.computeIfAbsent(areaDescricao, desc -> {
                            Optional<AreaBean> opt = areaRepository.findByAreaDescricao(desc);
                            return opt.orElse(null);
                        });
                        
                        if (area != null) {
                            desafio.setArea(area);
                        } else {
                            resultado.adicionarErro(i, "DESAFIO - Área não encontrada: " + areaDescricao);
                        }
                    }

                    // Buscar e associar SubÁrea (se informado)
                    if (areaSubDescricao != null && !areaSubDescricao.trim().isEmpty()) {
                        AreaSubBean areaSub = cacheAreaSubs.computeIfAbsent(areaSubDescricao, desc -> {
                            Optional<AreaSubBean> opt = areaSubRepository.findByAreasDescricao(desc);
                            return opt.orElse(null);
                        });
                        
                        if (areaSub != null) {
                            desafio.setAreaSub(areaSub);
                        } else {
                            resultado.adicionarErro(i, "DESAFIO - Subárea não encontrada: " + areaSubDescricao);
                        }
                    }

                    desafioRepository.save(desafio);
                    
                    if (isUpdate) {
                        resultado.incrementarAtualizados();
                    } else {
                        resultado.incrementarInseridos();
                    }

                } catch (Exception e) {
                    resultado.adicionarErro(i, "DESAFIO - " + e.getMessage());
                }
            }

            resultado.setSucesso(true);
            resultado.setMensagem("Importação de DESAFIO concluída");

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagem("Erro ao importar DESAFIO: " + e.getMessage());
        }

        return resultado;
    }

    private ImportacaoResultadoDTO importarDesafioQuestionario(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO();
        resultado.setPlanilha("04-desafios");
        resultado.setAba("DESAFIOQUESTIONARIO");

        try {
            Sheet sheet = workbook.getSheet("DESAFIOQUESTIONARIO");
            if (sheet == null) {
                resultado.setSucesso(false);
                resultado.setMensagem("Aba DESAFIOQUESTIONARIO não encontrada");
                return resultado;
            }

            int totalLinhas = sheet.getLastRowNum();
            resultado.setTotalLinhas(totalLinhas);

            // Cache para Desafio e Questionario
            Map<String, DesafioBean> cacheDesafios = new HashMap<>();
            Map<String, QuestionarioBean> cacheQuestionarios = new HashMap<>();

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String desTitulo = getCellValue(row.getCell(0));
                    String quesDescricao = getCellValue(row.getCell(1));

                    if (desTitulo == null || desTitulo.trim().isEmpty()) {
                        resultado.adicionarErro(i, "DESAFIOQUESTIONARIO - Título do desafio vazio");
                        continue;
                    }

                    if (quesDescricao == null || quesDescricao.trim().isEmpty()) {
                        resultado.adicionarErro(i, "DESAFIOQUESTIONARIO - Descrição do questionário vazia");
                        continue;
                    }

                    // Buscar ou criar cache do Desafio
                    DesafioBean desafio = cacheDesafios.computeIfAbsent(desTitulo, titulo -> {
                        Optional<DesafioBean> opt = desafioRepository.findByDesTitulo(titulo);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("Desafio não encontrado: " + titulo);
                        }
                        return opt.get();
                    });

                    // Buscar ou criar cache do Questionario
                    QuestionarioBean questionario = cacheQuestionarios.computeIfAbsent(quesDescricao, desc -> {
                        Optional<QuestionarioBean> opt = questionarioRepository.findByQuesDescricao(desc);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("Questionário não encontrado: " + desc);
                        }
                        return opt.get();
                    });

                    DesafioQuestionarioBean vinculo = new DesafioQuestionarioBean();
                    vinculo.setDesafio(desafio);
                    vinculo.setQuestionario(questionario);

                    desafioQuestionarioRepository.save(vinculo);
                    resultado.incrementarInseridos();

                } catch (Exception e) {
                    resultado.adicionarErro(i, "DESAFIOQUESTIONARIO - " + e.getMessage());
                }
            }

            resultado.setSucesso(true);
            resultado.setMensagem("Importação de DESAFIOQUESTIONARIO concluída");

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagem("Erro ao importar DESAFIOQUESTIONARIO: " + e.getMessage());
        }

        return resultado;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}
