-- =======================================
-- MELHORIAS DO SISTEMA DE TESTE VOCACIONAL
-- Data: 03/10/2025
-- Descrição: Implementação completa do sistema de testes vocacionais
-- com áreas do ENEM, subáreas (cursos) e questões expandidas
-- =======================================

USE rotafuturo;

-- =======================================
-- 1) Inserir as áreas do ENEM (se ainda não existirem)
-- =======================================
INSERT IGNORE INTO AREA (AREA_DATACADASTRO, AREA_HORACADASTRO, AREA_DESCRICAO, AREA_ATIVO)
VALUES
(CURDATE(), CURTIME(), 'Ciências da Natureza', 1),
(CURDATE(), CURTIME(), 'Linguagens e Códigos', 1),
(CURDATE(), CURTIME(), 'Ciências Humanas', 1),
(CURDATE(), CURTIME(), 'Matemática', 1);

-- =======================================
-- 2) Criar subáreas (cursos) para cada área
-- =======================================

-- Linguagens e Códigos
INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Letras', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Jornalismo', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Artes Visuais', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Publicidade e Propaganda', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Comunicação Social', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

-- Matemática
INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Matemática', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Engenharia Civil', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Engenharia Mecânica', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Sistemas de Informação', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Ciência da Computação', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Estatística', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Matemática';

-- Ciências da Natureza
INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Medicina', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Enfermagem', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Biomedicina', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Nutrição', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Fisioterapia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Fonoaudiologia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Medicina Veterinária', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Farmácia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Biologia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

-- Ciências Humanas
INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Direito', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Psicologia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Gestão de Recursos Humanos', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Educação Física - Bacharelado', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Logística', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Gestão Ambiental', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Terapia Ocupacional', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Odontologia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Estética e Cosmética', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'História', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Geografia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Filosofia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO AREASUB (AREAS_DESCRICAO, AREA_ID, AREAS_ATIVO, AREAS_DATACADASTRO, AREAS_HORACADASTRO)
SELECT 'Sociologia', AREA_ID, 1, CURDATE(), CURTIME() FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

-- =======================================
-- 3) Criar testes (1 por área principal + 1 teste geral)
-- =======================================

-- Teste geral de afinidades
INSERT INTO TESTE (TES_DATACADASTRO, TES_HORACADASTRO, TES_DESCRICAO)
VALUES (CURDATE(), CURTIME(), 'Teste Vocacional Geral - Questionário de Afinidades');

-- Testes específicos por área
INSERT INTO TESTE (TES_DATACADASTRO, TES_HORACADASTRO, TES_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), CONCAT('Teste de afinidade - ', AREA_DESCRICAO), AREA_ID
FROM AREA WHERE AREA_ID IN (SELECT AREA_ID FROM AREA WHERE AREA_DESCRICAO IN ('Ciências da Natureza', 'Linguagens e Códigos', 'Ciências Humanas', 'Matemática'));

-- =======================================
-- 4) Inserir questões gerais (Parte 1)
-- =======================================

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO)
VALUES
(CURDATE(), CURTIME(), 'Gosto de aprender coisas novas no meu tempo livre.'),
(CURDATE(), CURTIME(), 'Prefiro resolver problemas práticos a estudar teorias abstratas.'),
(CURDATE(), CURTIME(), 'Consigo me concentrar facilmente quando o assunto me interessa.'),
(CURDATE(), CURTIME(), 'Tenho facilidade em me comunicar com outras pessoas.'),
(CURDATE(), CURTIME(), 'Prefiro atividades criativas a atividades repetitivas.'),
(CURDATE(), CURTIME(), 'Gosto de trabalhar em equipe.'),
(CURDATE(), CURTIME(), 'Tenho facilidade em organizar ideias de forma lógica.'),
(CURDATE(), CURTIME(), 'Me interesso em entender como funcionam as coisas ao meu redor.'),
(CURDATE(), CURTIME(), 'Prefiro tarefas estruturadas a tarefas abertas e criativas.'),
(CURDATE(), CURTIME(), 'Gosto de ajudar as pessoas a resolverem seus problemas.'),
(CURDATE(), CURTIME(), 'Sinto-me motivado por desafios intelectuais.'),
(CURDATE(), CURTIME(), 'Gosto de pesquisar e investigar temas em profundidade.'),
(CURDATE(), CURTIME(), 'Prefiro atividades práticas a leituras longas.'),
(CURDATE(), CURTIME(), 'Tenho facilidade em aprender por meio da observação.'),
(CURDATE(), CURTIME(), 'Consigo lidar bem com situações novas ou imprevistas.'),
(CURDATE(), CURTIME(), 'Prefiro trabalhar sozinho a depender de outras pessoas.'),
(CURDATE(), CURTIME(), 'Gosto de resolver situações que exigem criatividade.'),
(CURDATE(), CURTIME(), 'Me interesso em aplicar o que aprendo na vida real.'),
(CURDATE(), CURTIME(), 'Sinto-me confortável falando em público.'),
(CURDATE(), CURTIME(), 'Prefiro seguir regras claras e bem definidas.'),
(CURDATE(), CURTIME(), 'Tenho facilidade em identificar padrões em informações.'),
(CURDATE(), CURTIME(), 'Me interesso por tecnologia e inovações.'),
(CURDATE(), CURTIME(), 'Prefiro estudar por meio de vídeos, imagens e exemplos práticos.'),
(CURDATE(), CURTIME(), 'Gosto de refletir sobre questões sociais e culturais.'),
(CURDATE(), CURTIME(), 'Sinto-me motivado quando vejo o impacto do meu trabalho em outras pessoas.'),
(CURDATE(), CURTIME(), 'Prefiro estudar de forma independente a ter orientação constante.'),
(CURDATE(), CURTIME(), 'Gosto de testar diferentes soluções para um mesmo problema.'),
(CURDATE(), CURTIME(), 'Tenho curiosidade sobre como o mundo e a sociedade funcionam.'),
(CURDATE(), CURTIME(), 'Prefiro atividades que exigem concentração intensa.'),
(CURDATE(), CURTIME(), 'Sinto-me motivado por objetivos de longo prazo.');

-- =======================================
-- 5) Inserir questões específicas por área - LINGUAGENS E CÓDIGOS
-- =======================================

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de ler livros, artigos ou textos em geral.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade para escrever textos criativos ou dissertativos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso em aprender novos idiomas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de analisar filmes, músicas ou obras de arte.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade em interpretar textos complexos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de atividades artísticas, como música, teatro ou desenho.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por diferentes culturas e formas de expressão.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro atividades que envolvem comunicação e expressão.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de produzir conteúdos como redações, vídeos ou apresentações.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho boa memória para palavras e expressões.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Consigo explicar ideias de forma clara para outras pessoas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de debater temas culturais e sociais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em literatura e produção textual.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me sinto confortável em aprender por meio da leitura.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro atividades ligadas à criatividade e expressão pessoal.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

-- Questões adicionais para Linguagens e Códigos (mais abertas)
INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade em me expressar diante de um público.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de analisar como a comunicação pode influenciar pessoas e decisões.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em carreiras que envolvam ensinar, interpretar ou traduzir.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Sinto prazer em criar histórias, roteiros ou narrativas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de refletir sobre como a linguagem molda culturas e sociedades.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Linguagens e Códigos';

-- =======================================
-- 6) Inserir questões específicas por área - MATEMÁTICA
-- =======================================

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de resolver problemas de lógica.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade com cálculos e números.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de identificar padrões em tabelas ou gráficos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro respostas objetivas e exatas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso em aplicar a matemática em situações práticas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de desafios que exigem raciocínio rápido.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho paciência para resolver problemas longos e complexos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Consigo identificar erros em cálculos facilmente.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso em áreas como estatística ou finanças.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de jogos de raciocínio e estratégia.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro atividades que exigem lógica a atividades criativas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade em trabalhar com fórmulas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de analisar dados e informações quantitativas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me sinto motivado por problemas que exigem precisão.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro estudar conteúdos exatos a conteúdos subjetivos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

-- Questões adicionais para Matemática (mais abertas)
INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em usar a matemática para resolver problemas do mundo real.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de entender como a matemática se aplica em negócios e finanças.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho curiosidade em aprender sobre algoritmos e programação.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso em usar dados para tomar decisões ou prever resultados.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de pensar em como a matemática é usada em engenharias e tecnologias.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Matemática';

-- =======================================
-- 7) Inserir questões específicas por área - CIÊNCIAS DA NATUREZA
-- =======================================

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de entender como funcionam fenômenos naturais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho curiosidade por temas relacionados à saúde e ao corpo humano.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por meio ambiente e sustentabilidade.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de assistir a experimentos ou demonstrações científicas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro aprender na prática a apenas decorar teorias.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em tecnologia e suas aplicações.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de discutir questões ambientais e energéticas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por biologia e como os seres vivos funcionam.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de aprender sobre o espaço, o universo e a física.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro aulas práticas a aulas apenas teóricas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em carreiras ligadas à saúde ou ciência.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de relacionar ciência com situações do cotidiano.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me sinto motivado a resolver problemas ambientais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade em entender esquemas e representações visuais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de aprender por meio de experimentação.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

-- Questões adicionais para Ciências da Natureza (mais abertas)
INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de investigar como a ciência pode melhorar a saúde das pessoas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho curiosidade em entender como funcionam tecnologias inovadoras.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por pesquisas sobre espaço, universo e astrofísica.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de compreender reações químicas e transformações da matéria.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em profissões que envolvem laboratórios, hospitais ou clínicas.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências da Natureza';

-- =======================================
-- 8) Inserir questões específicas por área - CIÊNCIAS HUMANAS
-- =======================================

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de estudar história, geografia, filosofia ou sociologia.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por questões sociais e culturais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de debater política e sociedade.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade em analisar diferentes pontos de vista.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por entender como as sociedades evoluem.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de refletir sobre valores e ética.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro atividades que envolvem interpretação a cálculos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de analisar fatos históricos e suas consequências.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso em temas como economia, governo e cidadania.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em compreender culturas diferentes.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de discutir problemas sociais atuais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho facilidade em escrever textos sobre temas sociais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me interesso por profissões ligadas a pessoas e comunidades.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de interpretar mapas, gráficos e dados geográficos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Prefiro analisar contextos amplos a focar em detalhes técnicos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

-- Questões adicionais para Ciências Humanas (mais abertas)
INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho interesse em compreender como a política influencia o cotidiano.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de analisar notícias e relacioná-las com fatos históricos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Me sinto motivado a ajudar comunidades e grupos sociais.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Tenho curiosidade em entender como culturas diferentes se organizam.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
SELECT CURDATE(), CURTIME(), 'Gosto de refletir sobre valores éticos e direitos humanos.', AREA_ID 
FROM AREA WHERE AREA_DESCRICAO='Ciências Humanas';

-- =======================================
-- 9) Vincular questões aos testes apropriados
-- =======================================

-- Vincular questões gerais ao teste geral
INSERT INTO TESTEQUESTAOVINCULO (TES_ID, TESQ_ID)
SELECT t.TES_ID, q.TESQ_ID
FROM TESTE t
CROSS JOIN TESTEQUESTAO q
WHERE t.TES_DESCRICAO = 'Teste Vocacional Geral - Questionário de Afinidades'
AND q.AREA_ID IS NULL;

-- Vincular questões específicas aos testes de área
INSERT INTO TESTEQUESTAOVINCULO (TES_ID, TESQ_ID)
SELECT t.TES_ID, q.TESQ_ID
FROM TESTE t
JOIN TESTEQUESTAO q ON t.AREA_ID = q.AREA_ID
WHERE t.AREA_ID IS NOT NULL;

-- =======================================
-- 10) Inserir dados básicos para funcionalidade
-- =======================================

-- Inserir tipos de questionário
INSERT IGNORE INTO QUESTIONARIOTIPO (QUEST_DESCRICAO, QUEST_ATIVO)
VALUES
('Teste Vocacional', 1),
('Avaliação de Conhecimento', 1),
('Questionário de Afinidade', 1);

-- Inserir tipos de questão
INSERT IGNORE INTO QUESTAOTIPO (QUET_DESCRICAO, QUET_ATIVO)
VALUES
('Múltipla Escolha', 1),
('Escala Likert', 1),
('Verdadeiro/Falso', 1),
('Dissertativa', 1);

-- Inserir níveis de dificuldade
INSERT IGNORE INTO NIVEL (NIV_DESCRICAO)
VALUES
('Básico'),
('Intermediário'),
('Avançado');

-- Inserir grupos de acesso
INSERT IGNORE INTO GRUPOACESSO (GRUA_DESCRICAO)
VALUES
('Administrador'),
('Professor'),
('Aluno'),
('Visitante');

-- =======================================
-- CONSULTAS DE VERIFICAÇÃO
-- =======================================

-- Verificar áreas criadas
SELECT 'ÁREAS CRIADAS:' as Info;
SELECT AREA_ID, AREA_DESCRICAO FROM AREA ORDER BY AREA_ID;

-- Verificar subáreas por área
SELECT 'SUBÁREAS POR ÁREA:' as Info;
SELECT a.AREA_DESCRICAO as Area, s.AREAS_DESCRICAO as SubArea
FROM AREA a
JOIN AREASUB s ON a.AREA_ID = s.AREA_ID
ORDER BY a.AREA_DESCRICAO, s.AREAS_DESCRICAO;

-- Verificar testes criados
SELECT 'TESTES CRIADOS:' as Info;
SELECT TES_ID, TES_DESCRICAO, 
       CASE WHEN AREA_ID IS NULL THEN 'Geral' ELSE 
            (SELECT AREA_DESCRICAO FROM AREA WHERE AREA_ID = t.AREA_ID) 
       END as Area
FROM TESTE t
ORDER BY TES_ID;

-- Verificar total de questões por área
SELECT 'TOTAL DE QUESTÕES POR ÁREA:' as Info;
SELECT 
    CASE WHEN tq.AREA_ID IS NULL THEN 'Gerais' ELSE a.AREA_DESCRICAO END as Area,
    COUNT(*) as Total_Questoes
FROM TESTEQUESTAO tq
LEFT JOIN AREA a ON tq.AREA_ID = a.AREA_ID
GROUP BY tq.AREA_ID, a.AREA_DESCRICAO
ORDER BY tq.AREA_ID;

-- Verificar vínculos de questões com testes
SELECT 'VÍNCULOS TESTE-QUESTÃO:' as Info;
SELECT t.TES_DESCRICAO, COUNT(tv.TESQ_ID) as Total_Questoes_Vinculadas
FROM TESTE t
LEFT JOIN TESTEQUESTAOVINCULO tv ON t.TES_ID = tv.TES_ID
GROUP BY t.TES_ID, t.TES_DESCRICAO
ORDER BY t.TES_ID;

-- =======================================
-- FIM DO SCRIPT DE MELHORIAS
-- =======================================