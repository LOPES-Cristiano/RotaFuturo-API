
-- 1) Inserir as áreas do ENEM (somente se ainda não tiver feito)
INSERT INTO AREA (AREA_DATACADASTRO, AREA_HORACADASTRO, AREA_DESCRICAO, AREA_ATIVO)
VALUES
(CURDATE(), CURTIME(), 'Ciências da Natureza', 1),
(CURDATE(), CURTIME(), 'Linguagens e Códigos', 1),
(CURDATE(), CURTIME(), 'Ciências Humanas', 1),
(CURDATE(), CURTIME(), 'Matemática', 1);

-- Conferir IDs das áreas
SELECT * FROM AREA;

-- 2) Inserir o teste principal
INSERT INTO TESTE (TES_DATACADASTRO, TES_HORACADASTRO, TES_DESCRICAO)
VALUES (CURDATE(), CURTIME(), 'Teste Vocacional Áreas ENEM');

-- 3) Capturar o TES_ID gerado
SET @TES_ID = LAST_INSERT_ID();

-- 4) Inserir 30 questões já categorizadas
INSERT INTO TESTEQUESTAO (TESQ_DATACADASTRO, TESQ_HORACADASTRO, TESQ_DESCRICAO, AREA_ID)
VALUES
-- Ciências da Natureza (AREA_ID = 1)
(CURDATE(), CURTIME(), 'Você gosta de resolver problemas de lógica e matemática aplicados à física?', 1),
(CURDATE(), CURTIME(), 'Você se interessa em como funcionam os fenômenos da natureza?', 1),
(CURDATE(), CURTIME(), 'Você gosta de realizar experimentos em laboratório?', 1),
(CURDATE(), CURTIME(), 'Você sente curiosidade sobre o funcionamento do corpo humano?', 1),
(CURDATE(), CURTIME(), 'Você gosta de assistir documentários sobre biologia, química ou física?', 1),
(CURDATE(), CURTIME(), 'Você se interessa por pesquisas científicas?', 1),
(CURDATE(), CURTIME(), 'Você gosta de entender as causas das doenças e suas curas?', 1),
(CURDATE(), CURTIME(), 'Você tem facilidade em compreender conceitos de energia e matéria?', 1),

-- Linguagens e Códigos (AREA_ID = 2)
(CURDATE(), CURTIME(), 'Você gosta de ler livros, revistas ou artigos?', 2),
(CURDATE(), CURTIME(), 'Você tem facilidade em aprender novas línguas?', 2),
(CURDATE(), CURTIME(), 'Você gosta de escrever redações ou histórias?', 2),
(CURDATE(), CURTIME(), 'Você aprecia obras literárias, teatro ou poesia?', 2),
(CURDATE(), CURTIME(), 'Você tem interesse em comunicação social, como jornalismo ou publicidade?', 2),
(CURDATE(), CURTIME(), 'Você gosta de analisar textos e identificar seus significados?', 2),
(CURDATE(), CURTIME(), 'Você se interessa por artes visuais, música ou cinema?', 2),
(CURDATE(), CURTIME(), 'Você sente facilidade em interpretar gráficos, charges ou propagandas?', 2),

-- Ciências Humanas (AREA_ID = 3)
(CURDATE(), CURTIME(), 'Você gosta de estudar história e compreender acontecimentos do passado?', 3),
(CURDATE(), CURTIME(), 'Você se interessa em entender como funciona a sociedade?', 3),
(CURDATE(), CURTIME(), 'Você gosta de geografia e de analisar mapas?', 3),
(CURDATE(), CURTIME(), 'Você tem curiosidade em filosofia ou sociologia?', 3),
(CURDATE(), CURTIME(), 'Você se interessa por política e economia?', 3),
(CURDATE(), CURTIME(), 'Você gosta de discutir sobre ética, valores e justiça?', 3),
(CURDATE(), CURTIME(), 'Você tem interesse em culturas diferentes e antropologia?', 3),

-- Matemática (AREA_ID = 4)
(CURDATE(), CURTIME(), 'Você gosta de resolver problemas matemáticos complexos?', 4),
(CURDATE(), CURTIME(), 'Você sente prazer em trabalhar com números e cálculos?', 4),
(CURDATE(), CURTIME(), 'Você gosta de estatística e probabilidade?', 4),
(CURDATE(), CURTIME(), 'Você se interessa em modelar situações do cotidiano usando matemática?', 4),
(CURDATE(), CURTIME(), 'Você gosta de desafios de raciocínio lógico?', 4),
(CURDATE(), CURTIME(), 'Você tem curiosidade sobre aplicações da matemática na tecnologia?', 4),
(CURDATE(), CURTIME(), 'Você sente facilidade em interpretar gráficos e tabelas?', 4);

-- 5) Vincular automaticamente as questões criadas ao teste recém-inserido
INSERT INTO TESTEQUESTAOVINCULO (TES_ID, TESQ_ID)
SELECT @TES_ID, TESQ_ID
FROM TESTEQUESTAO
WHERE DATE(TESQ_DATACADASTRO) = CURDATE();

