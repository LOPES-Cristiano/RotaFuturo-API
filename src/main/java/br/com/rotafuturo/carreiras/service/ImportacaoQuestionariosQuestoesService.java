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

import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoQuestionariosQuestoesResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoResultadoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.NivelBean;
import br.com.rotafuturo.carreiras.model.QuestaoAlternativaBean;
import br.com.rotafuturo.carreiras.model.QuestaoBean;
import br.com.rotafuturo.carreiras.model.QuestaoTipoBean;
import br.com.rotafuturo.carreiras.model.QuestionarioBean;
import br.com.rotafuturo.carreiras.model.QuestionarioQuestaoBean;
import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.NivelRepository;
import br.com.rotafuturo.carreiras.repository.QuestaoAlternativaRepository;
import br.com.rotafuturo.carreiras.repository.QuestaoRepository;
import br.com.rotafuturo.carreiras.repository.QuestaoTipoRepository;
import br.com.rotafuturo.carreiras.repository.QuestionarioQuestaoRepository;
import br.com.rotafuturo.carreiras.repository.QuestionarioRepository;
import br.com.rotafuturo.carreiras.repository.QuestionarioTipoRepository;

@Service
public class ImportacaoQuestionariosQuestoesService {

    @Autowired
    private QuestionarioRepository questionarioRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private QuestaoAlternativaRepository questaoAlternativaRepository;

    @Autowired
    private QuestionarioQuestaoRepository questionarioQuestaoRepository;

    @Autowired
    private QuestionarioTipoRepository questionarioTipoRepository;

    @Autowired
    private QuestaoTipoRepository questaoTipoRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaSubRepository areaSubRepository;

    @Autowired
    private NivelRepository nivelRepository;

    private static final int BATCH_SIZE = 100;

    @Transactional
    public ImportacaoQuestionariosQuestoesResultadoDTO importarQuestionariosQuestoes(MultipartFile arquivo) {
        ImportacaoQuestionariosQuestoesResultadoDTO resultado = new ImportacaoQuestionariosQuestoesResultadoDTO();

        try (Workbook workbook = WorkbookFactory.create(arquivo.getInputStream())) {
            resultado.setQuestionarios(importarQuestionarios(workbook));
            resultado.setQuestoes(importarQuestoes(workbook));
            resultado.setQuestoesAlternativa(importarQuestoesAlternativa(workbook));
            resultado.setQuestionariosQuestao(importarQuestionariosQuestao(workbook));

            return resultado;
        } catch (Exception e) {
            ImportacaoQuestionariosQuestoesResultadoDTO erroGeral = new ImportacaoQuestionariosQuestoesResultadoDTO();
            ImportacaoResultadoDTO erro = new ImportacaoResultadoDTO();
            erro.setPlanilha("03-questionarios-questoes");
            erro.setSucesso(false);
            erro.setMensagem("Erro ao processar arquivo: " + e.getMessage());
            erro.adicionarErro(0, "Erro geral: " + e.getMessage());
            erroGeral.setQuestionarios(erro);
            return erroGeral;
        }
    }

    private ImportacaoResultadoDTO importarQuestionarios(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO();
        resultado.setPlanilha("03-questionarios-questoes");
        resultado.setAba("QUESTIONARIO");

        try {
            Sheet sheet = workbook.getSheet("QUESTIONARIO");
            if (sheet == null) {
                resultado.setSucesso(false);
                resultado.setMensagem("Aba QUESTIONARIO não encontrada");
                return resultado;
            }

            int totalLinhas = sheet.getLastRowNum();
            resultado.setTotalLinhas(totalLinhas);

            // Cache para QuestionarioTipo e Nivel
            Map<String, QuestionarioTipoBean> cacheQuestipos = new HashMap<>();
            Map<Integer, NivelBean> cacheNiveis = new HashMap<>();

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    // Coluna A: QUES_DESCRICAO
                    String quesDescricao = getCellValue(row.getCell(0));
                    // Coluna B: NIV_ID
                    String nivIdStr = getCellValue(row.getCell(1));
                    // Coluna C: QUEST_TIPO_REF
                    String questipoDescricao = getCellValue(row.getCell(2));

                    if (quesDescricao == null || quesDescricao.trim().isEmpty()) {
                        resultado.adicionarErro(i, "QUESTIONARIO - Descrição do questionário vazia");
                        continue;
                    }

                    // Buscar ou criar cache do QuestionarioTipo
                    QuestionarioTipoBean questipo = cacheQuestipos.computeIfAbsent(questipoDescricao, desc -> {
                        Optional<QuestionarioTipoBean> opt = questionarioTipoRepository.findByQuestDescricao(desc);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("QuestionarioTipo não encontrado: " + desc);
                        }
                        return opt.get();
                    });

                    // Buscar Nível (opcional)
                    NivelBean nivel = null;
                    if (nivIdStr != null && !nivIdStr.trim().isEmpty()) {
                        try {
                            Integer nivId = Integer.parseInt(nivIdStr);
                            nivel = cacheNiveis.computeIfAbsent(nivId, id -> {
                                Optional<NivelBean> opt = nivelRepository.findById(id);
                                if (opt.isEmpty()) {
                                    throw new RuntimeException("Nível não encontrado: " + id);
                                }
                                return opt.get();
                            });
                        } catch (NumberFormatException e) {
                            resultado.adicionarErro(i, "QUESTIONARIO - NIV_ID inválido: " + nivIdStr);
                            continue;
                        }
                    }

                    // Verificar se já existe
                    Optional<QuestionarioBean> existente = questionarioRepository.findByQuesDescricao(quesDescricao);

                    QuestionarioBean questionario;
                    if (existente.isPresent()) {
                        questionario = existente.get();
                        questionario.setQuestionarioTipo(questipo);
                        questionario.setNivel(nivel);
                        questionario.setQuesDatacadastro(LocalDate.now());
                        questionario.setQuesHoracadastro(LocalTime.now());
                        questionarioRepository.save(questionario);
                        resultado.incrementarAtualizados();
                    } else {
                        questionario = new QuestionarioBean();
                        questionario.setQuesDescricao(quesDescricao);
                        questionario.setQuestionarioTipo(questipo);
                        questionario.setNivel(nivel);
                        questionario.setQuesDatacadastro(LocalDate.now());
                        questionario.setQuesHoracadastro(LocalTime.now());
                        questionarioRepository.save(questionario);
                        resultado.incrementarInseridos();
                    }

                } catch (Exception e) {
                    resultado.adicionarErro(i, "QUESTIONARIO - " + e.getMessage());
                }
            }

            resultado.setSucesso(true);
            resultado.setMensagem("Importação de QUESTIONARIO concluída");

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagem("Erro ao importar QUESTIONARIO: " + e.getMessage());
        }

        return resultado;
    }

    private ImportacaoResultadoDTO importarQuestoes(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO();
        resultado.setPlanilha("03-questionarios-questoes");
        resultado.setAba("QUESTAO");

        try {
            Sheet sheet = workbook.getSheet("QUESTAO");
            if (sheet == null) {
                resultado.setSucesso(false);
                resultado.setMensagem("Aba QUESTAO não encontrada");
                return resultado;
            }

            int totalLinhas = sheet.getLastRowNum();
            resultado.setTotalLinhas(totalLinhas);

            // Cache para QuestaoTipo, Area, AreaSub e Nivel
            Map<String, QuestaoTipoBean> cacheQuesttipos = new HashMap<>();
            Map<String, AreaBean> cacheAreas = new HashMap<>();
            Map<String, AreaSubBean> cacheAreasSub = new HashMap<>();
            Map<Integer, NivelBean> cacheNiveis = new HashMap<>();

            int contador = 0;

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    // Coluna A: QUESTAO_CODIGO
                    String questCodigo = getCellValue(row.getCell(0));
                    // Coluna B: QUESTAO_DESCRICAO
                    String questDescricao = getCellValue(row.getCell(1));
                    // Coluna C: QUESTAO_EXPERIENCIA
                    String questExperienciaStr = getCellValue(row.getCell(2));
                    // Coluna D: NIV_ID
                    String nivIdStr = getCellValue(row.getCell(3));
                    // Coluna E: QUET_TIPO_REF
                    String questtipoDescricao = getCellValue(row.getCell(4));
                    // Coluna F: AREA_REF
                    String areaDescricao = getCellValue(row.getCell(5));
                    // Coluna G: AREASUB_REF
                    String areasubDescricao = getCellValue(row.getCell(6));

                    if (questCodigo == null || questCodigo.trim().isEmpty()) {
                        resultado.adicionarErro(i, "QUESTAO - Código da questão vazio");
                        continue;
                    }

                    // Buscar ou criar cache do QuestaoTipo
                    QuestaoTipoBean questtipo = cacheQuesttipos.computeIfAbsent(questtipoDescricao, desc -> {
                        Optional<QuestaoTipoBean> opt = questaoTipoRepository.findByQuetDescricao(desc);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("QuestaoTipo não encontrado: " + desc);
                        }
                        return opt.get();
                    });

                    // Processar QUESTAO_EXPERIENCIA (opcional)
                    Integer questExperiencia = null;
                    if (questExperienciaStr != null && !questExperienciaStr.trim().isEmpty()) {
                        try {
                            questExperiencia = Integer.parseInt(questExperienciaStr);
                        } catch (NumberFormatException e) {
                            resultado.adicionarErro(i, "QUESTAO - QUESTAO_EXPERIENCIA inválida: " + questExperienciaStr);
                            continue;
                        }
                    }

                    // Buscar Nível (opcional)
                    NivelBean nivel = null;
                    if (nivIdStr != null && !nivIdStr.trim().isEmpty()) {
                        try {
                            Integer nivId = Integer.parseInt(nivIdStr);
                            nivel = cacheNiveis.computeIfAbsent(nivId, id -> {
                                Optional<NivelBean> opt = nivelRepository.findById(id);
                                if (opt.isEmpty()) {
                                    throw new RuntimeException("Nível não encontrado: " + id);
                                }
                                return opt.get();
                            });
                        } catch (NumberFormatException e) {
                            resultado.adicionarErro(i, "QUESTAO - NIV_ID inválido: " + nivIdStr);
                            continue;
                        }
                    }

                    // Buscar ou criar cache da Area (opcional)
                    AreaBean area = null;
                    if (areaDescricao != null && !areaDescricao.trim().isEmpty()) {
                        area = cacheAreas.computeIfAbsent(areaDescricao, desc -> {
                            Optional<AreaBean> opt = areaRepository.findByAreaDescricao(desc);
                            return opt.orElse(null);
                        });
                    }

                    // Buscar ou criar cache da AreaSub (opcional)
                    AreaSubBean areasub = null;
                    if (areasubDescricao != null && !areasubDescricao.trim().isEmpty()) {
                        areasub = cacheAreasSub.computeIfAbsent(areasubDescricao, desc -> {
                            Optional<AreaSubBean> opt = areaSubRepository.findByAreasDescricao(desc);
                            return opt.orElse(null);
                        });
                    }

                    // Verificar se já existe
                    Optional<QuestaoBean> existente = questaoRepository.findByQuestaoCodigo(questCodigo);

                    QuestaoBean questao;
                    if (existente.isPresent()) {
                        questao = existente.get();
                        questao.setQuestaoDescricao(questDescricao);
                        questao.setQuestaoExperiencia(questExperiencia);
                        questao.setNivel(nivel);
                        questao.setQuestaoTipo(questtipo);
                        questao.setArea(area);
                        questao.setAreaSub(areasub);
                        questao.setQuestaoDatacadastro(LocalDate.now());
                        questao.setQuestaoHoracadastro(LocalTime.now());
                        questaoRepository.save(questao);
                        resultado.incrementarAtualizados();
                    } else {
                        questao = new QuestaoBean();
                        questao.setQuestaoCodigo(questCodigo);
                        questao.setQuestaoDescricao(questDescricao);
                        questao.setQuestaoExperiencia(questExperiencia);
                        questao.setNivel(nivel);
                        questao.setQuestaoTipo(questtipo);
                        questao.setArea(area);
                        questao.setAreaSub(areasub);
                        questao.setQuestaoDatacadastro(LocalDate.now());
                        questao.setQuestaoHoracadastro(LocalTime.now());
                        questaoRepository.save(questao);
                        resultado.incrementarInseridos();
                    }

                    // Batch processing para +1000 registros
                    contador++;
                    if (contador % BATCH_SIZE == 0) {
                        questaoRepository.flush();
                    }

                } catch (Exception e) {
                    resultado.adicionarErro(i, "QUESTAO - " + e.getMessage());
                }
            }

            // Flush final
            questaoRepository.flush();

            resultado.setSucesso(true);
            resultado.setMensagem("Importação de QUESTAO concluída com processamento em lote");

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagem("Erro ao importar QUESTAO: " + e.getMessage());
        }

        return resultado;
    }

    private ImportacaoResultadoDTO importarQuestoesAlternativa(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO();
        resultado.setPlanilha("03-questionarios-questoes");
        resultado.setAba("QUESTAOALTERNATIVA");

        try {
            Sheet sheet = workbook.getSheet("QUESTAOALTERNATIVA");
            if (sheet == null) {
                resultado.setSucesso(false);
                resultado.setMensagem("Aba QUESTAOALTERNATIVA não encontrada");
                return resultado;
            }

            int totalLinhas = sheet.getLastRowNum();
            resultado.setTotalLinhas(totalLinhas);

            // Cache para Questao
            Map<String, QuestaoBean> cacheQuestoes = new HashMap<>();

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String questCodigo = getCellValue(row.getCell(0));
                    String questaltDescricao = getCellValue(row.getCell(1));
                    String questaltPontuacaoStr = getCellValue(row.getCell(2));

                    if (questCodigo == null || questCodigo.trim().isEmpty()) {
                        resultado.adicionarErro(i, "QUESTAOALTERNATIVA - Código da questão vazio");
                        continue;
                    }

                    // Buscar ou criar cache da Questao
                    QuestaoBean questao = cacheQuestoes.computeIfAbsent(questCodigo, codigo -> {
                        Optional<QuestaoBean> opt = questaoRepository.findByQuestaoCodigo(codigo);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("Questão não encontrada: " + codigo);
                        }
                        return opt.get();
                    });

                    Integer questaltPontuacao = null;
                    if (questaltPontuacaoStr != null && !questaltPontuacaoStr.trim().isEmpty()) {
                        try {
                            questaltPontuacao = Integer.parseInt(questaltPontuacaoStr);
                        } catch (NumberFormatException e) {
                            resultado.adicionarErro(i, "QUESTAOALTERNATIVA - Pontuação inválida: " + questaltPontuacaoStr);
                            continue;
                        }
                    }

                    QuestaoAlternativaBean alternativa = new QuestaoAlternativaBean();
                    alternativa.setQuestao(questao);
                    alternativa.setQuesaDescricao(questaltDescricao);
                    alternativa.setQuesaCorreta(questaltPontuacao);

                    questaoAlternativaRepository.save(alternativa);
                    resultado.incrementarInseridos();

                } catch (Exception e) {
                    resultado.adicionarErro(i, "QUESTAOALTERNATIVA - " + e.getMessage());
                }
            }

            resultado.setSucesso(true);
            resultado.setMensagem("Importação de QUESTAOALTERNATIVA concluída");

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagem("Erro ao importar QUESTAOALTERNATIVA: " + e.getMessage());
        }

        return resultado;
    }

    private ImportacaoResultadoDTO importarQuestionariosQuestao(Workbook workbook) {
        ImportacaoResultadoDTO resultado = new ImportacaoResultadoDTO();
        resultado.setPlanilha("03-questionarios-questoes");
        resultado.setAba("QUESTIONARIOQUESTAO");

        try {
            Sheet sheet = workbook.getSheet("QUESTIONARIOQUESTAO");
            if (sheet == null) {
                resultado.setSucesso(false);
                resultado.setMensagem("Aba QUESTIONARIOQUESTAO não encontrada");
                return resultado;
            }

            int totalLinhas = sheet.getLastRowNum();
            resultado.setTotalLinhas(totalLinhas);

            // Cache para Questionario e Questao
            Map<String, QuestionarioBean> cacheQuestionarios = new HashMap<>();
            Map<String, QuestaoBean> cacheQuestoes = new HashMap<>();

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String quesDescricao = getCellValue(row.getCell(0));
                    String questCodigo = getCellValue(row.getCell(1));

                    if (quesDescricao == null || quesDescricao.trim().isEmpty()) {
                        resultado.adicionarErro(i, "QUESTIONARIOQUESTAO - Descrição do questionário vazia");
                        continue;
                    }

                    if (questCodigo == null || questCodigo.trim().isEmpty()) {
                        resultado.adicionarErro(i, "QUESTIONARIOQUESTAO - Código da questão vazio");
                        continue;
                    }

                    // Buscar ou criar cache do Questionario
                    QuestionarioBean questionario = cacheQuestionarios.computeIfAbsent(quesDescricao, desc -> {
                        Optional<QuestionarioBean> opt = questionarioRepository.findByQuesDescricao(desc);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("Questionário não encontrado: " + desc);
                        }
                        return opt.get();
                    });

                    // Buscar ou criar cache da Questao
                    QuestaoBean questao = cacheQuestoes.computeIfAbsent(questCodigo, codigo -> {
                        Optional<QuestaoBean> opt = questaoRepository.findByQuestaoCodigo(codigo);
                        if (opt.isEmpty()) {
                            throw new RuntimeException("Questão não encontrada: " + codigo);
                        }
                        return opt.get();
                    });

                    QuestionarioQuestaoBean vinculo = new QuestionarioQuestaoBean();
                    vinculo.setQuestionario(questionario);
                    vinculo.setQuestao(questao);

                    questionarioQuestaoRepository.save(vinculo);
                    resultado.incrementarInseridos();

                } catch (Exception e) {
                    resultado.adicionarErro(i, "QUESTIONARIOQUESTAO - " + e.getMessage());
                }
            }

            resultado.setSucesso(true);
            resultado.setMensagem("Importação de QUESTIONARIOQUESTAO concluída");

        } catch (Exception e) {
            resultado.setSucesso(false);
            resultado.setMensagem("Erro ao importar QUESTIONARIOQUESTAO: " + e.getMessage());
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
