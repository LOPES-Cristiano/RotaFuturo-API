--SCRIPTS BANCO DE DADOS  @cristiano

CREATE TABLE `usuario` (
  `usu_id` INT NOT NULL AUTO_INCREMENT,
  `usu_email` VARCHAR(200) DEFAULT NULL,
  `usu_senha` VARCHAR(250) DEFAULT NULL,
  `usu_datacadastro` DATE DEFAULT NULL,
  `usu_horacadastro` TIME DEFAULT NULL,
  `usu_emailvalidado` TINYINT(1) DEFAULT '0',
  `usu_ativo` TINYINT(1) DEFAULT '1',
  `usu_trocasenha` TINYINT(1) DEFAULT '0',
  `usu_senhaantiga1` VARCHAR(250) DEFAULT NULL,
  `usu_senhaantiga2` VARCHAR(250) DEFAULT NULL,
  PRIMARY KEY (`usu_id`)
);


CREATE TABLE pessoa (
  pes_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  pes_nome VARCHAR(255),
  pes_apelido VARCHAR(255),
  pes_datacadastro DATE,
  pes_horacadastro TIME,
  pes_datanascimento DATE,
  pes_telefone1 VARCHAR(20),
  pes_telefone2 VARCHAR(20),
  pes_imagemperfil VARCHAR(1000),
  pes_imagemcurriculo VARCHAR(1000),
  pes_nivel INT,
  pes_xp INT,
  usu_id INT
);

ALTER TABLE pessoa ADD CONSTRAINT fk_pessoa_usuario FOREIGN KEY (usu_id) REFERENCES usuario(usu_id);



CREATE TABLE arquivotipo (
    arqt_id INT PRIMARY KEY AUTO_INCREMENT,
    arqt_descricao VARCHAR(255) NOT NULL
);


CREATE TABLE arquivo (
    arq_id INT PRIMARY KEY AUTO_INCREMENT,
    arq_datacadastro DATE NOT NULL,
    arq_horacadastro TIME NOT NULL,
    arq_tamanho INT,
    arq_descricao VARCHAR(255),
    arq_extensao VARCHAR(10),
    arq_endereco VARCHAR(1000),
    arqt_id INT,
    usu_id INT,
    FOREIGN KEY (arqt_id) REFERENCES arquivotipo(arqt_id),
    FOREIGN KEY (usu_id) REFERENCES usuario(usu_id)
);