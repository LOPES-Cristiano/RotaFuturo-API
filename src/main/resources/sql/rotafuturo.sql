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

