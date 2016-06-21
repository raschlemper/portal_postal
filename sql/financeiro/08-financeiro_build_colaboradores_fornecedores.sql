USE pp_06895434000183;

DROP TABLE colaborador_endereco;
DROP TABLE colaborador;
DROP TABLE endereco;
DROP TABLE informacao_profissional;
DROP TABLE informacao_bancaria;

CREATE TABLE `colaborador` (
  `idColaborador`        INT NOT NULL AUTO_INCREMENT,
  `nome`                 VARCHAR(254) NOT NULL,
  `status`               INT NOT NULL,
  `cpf`                  VARCHAR(14) NULL,
  `rg`                   VARCHAR(50) NULL,
  `sexo`                 INT DEFAULT NULL,
  `dataNascimento`       DATETIME NULL,
  `dddTelefone`          INT DEFAULT NULL,
  `telefone`             INT DEFAULT NULL,
  `dddCelular`           INT DEFAULT NULL,
  `celular`              INT DEFAULT NULL,
  `email`                VARCHAR(254) NULL,
  `conjuge`              VARCHAR(254) NULL,
  `estadoCivil`          INT DEFAULT NULL,
  `naturalidade`         VARCHAR(254) NULL,
  `nacionalidade`        VARCHAR(254) NULL,
  `quantidadeDependente` INT DEFAULT NULL,
  `nomePai`              VARCHAR(254) NULL,
  `nomeMae`              VARCHAR(254) NULL,
  `observacao`           VARCHAR(254) NULL,
  PRIMARY KEY (`idColaborador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `endereco` (
  `idEndereco`  INT NOT NULL AUTO_INCREMENT,
  `logradouro`  VARCHAR(254) NOT NULL,
  `cep`         INT DEFAULT NULL,
  `complemento` VARCHAR(254) NOT NULL,
  `numero`      INT NOT NULL,
  `bairro`      VARCHAR(254) NOT NULL,
  `cidade`      VARCHAR(254) NOT NULL,
  `estado`      VARCHAR(2) NOT NULL,
  PRIMARY KEY (`idEndereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `informacao_profissional` (
  `idInformacaoProfissional` INT NOT NULL AUTO_INCREMENT,
  `idColaborador`            INT NOT NULL,
  `cargoFuncao`              VARCHAR(254) NOT NULL,
  `salario`                  DECIMAL(13,2) NOT NULL DEFAULT 0,
  `dataAdmissao`             DATETIME NULL,
  `dataDemissao`             DATETIME NULL,
  `pisPasep`                 VARCHAR(11) NULL,
  `tituloEleitoral`          VARCHAR(50) NULL,
  `certificadoReservista`    VARCHAR(50) NULL,
  `ctps`                     VARCHAR(50) NULL,
  `horarioEntrada`           TIME NULL,
  `horarioSaida`             TIME NULL,
  `intervaloDe`              TIME NULL,
  `intervaloAte`             TIME NULL,
  `observacao`               VARCHAR(254) NULL,
  PRIMARY KEY (`idInformacaoProfissional`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `informacao_profissional` 
ADD INDEX `i_informacaoprofissional_colaborador` (`idColaborador` ASC);

ALTER TABLE `informacao_profissional` 
ADD CONSTRAINT `fk_informacaoprofissional_colaborador`
  FOREIGN KEY (`idColaborador`)
  REFERENCES `colaborador` (`idColaborador`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `informacao_bancaria` (
  `idInformacaoBancaria` INT NOT NULL AUTO_INCREMENT,
  `idColaborador`        INT NOT NULL,
  `tipoConta`            INT NOT NULL,
  `idBanco`              INT NOT NULL,
  `agencia`              INT NOT NULL,
  `agencia_dv`           TINYINT (2) DEFAULT 0,
  `contaCorrente`        INT NOT NULL,
  `contaCorrente_dv`     TINYINT (2) DEFAULT 0,
  PRIMARY KEY (`idInformacaoBancaria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `informacao_bancaria` 
ADD INDEX `i_informacaobancaria_colaborador` (`idColaborador` ASC);

ALTER TABLE `informacao_bancaria` 
ADD CONSTRAINT `fk_informacaobancaria_colaborador`
  FOREIGN KEY (`idColaborador`)
  REFERENCES `colaborador` (`idColaborador`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `colaborador_endereco` (
  `idColaboradorEndereco` INT NOT NULL AUTO_INCREMENT,
  `idColaborador`         INT NOT NULL,
  `idEndereco`            INT NOT NULL,
  PRIMARY KEY (`idColaboradorEndereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `colaborador_endereco` 
ADD INDEX `i_colaboradorendereco_colaborador` (`idColaborador` ASC);

ALTER TABLE `colaborador_endereco` 
ADD INDEX `i_colaboradorendereco_endereco` (`idEndereco` ASC);

ALTER TABLE `colaborador_endereco` 
ADD CONSTRAINT `fk_colaboradorendereco_colaborador`
  FOREIGN KEY (`idColaborador`)
  REFERENCES `colaborador` (`idColaborador`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `colaborador_endereco` 
ADD CONSTRAINT `fk_colaboradorendereco_endereco`
  FOREIGN KEY (`idEndereco`)
  REFERENCES `endereco` (`idEndereco`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `favorecido` (
  `idFavorecido`  INT NOT NULL AUTO_INCREMENT,
  `tipo`          INT NOT NULL,
  `idColaborador` INT NULL,
  `idFornecedor`  INT NULL,
  `idCliente`     INT NULL,
  PRIMARY KEY (`idFavorecido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `favorecido` 
ADD INDEX `i_favorecido_colaborador` (`idColaborador` ASC);

ALTER TABLE `favorecido` 
ADD CONSTRAINT `fk_favorecido_colaborador`
  FOREIGN KEY (`idColaborador`)
  REFERENCES `colaborador` (`idColaborador`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;