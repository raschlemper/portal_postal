USE pp_06895434000183;

DROP TABLE banco;
DROP TABLE conta_corrente;

CREATE TABLE `banco` (
  `idBanco` INT NOT NULL AUTO_INCREMENT,
  `nome`    VARCHAR(254) NOT NULL,
  `numero`  INT NOT NULL,
  `website` VARCHAR(254) DEFAULT NULL,
  PRIMARY KEY (`idbanco`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `banco` 
ADD UNIQUE INDEX `u_numero` (`numero`);

CREATE TABLE `conta_corrente` (
  `idContaCorrente` INT NOT NULL AUTO_INCREMENT,
  `nome`            VARCHAR(254) NOT NULL,
  `idBanco`         INT NOT NULL,
  `agencia`         INT NOT NULL,
  `contaCorrente`   INT NOT NULL,
  `carteira`        INT DEFAULT NULL,
  `poupanca`        TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idContaCorrente`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `conta_corrente` 
ADD UNIQUE INDEX `u_contacorrente` (`idBanco`, `agencia`, `contaCorrente`);

CREATE TABLE `carteira_cobranca` (
  `idCarteira`              INT (11) NOT NULL AUTO_INCREMENT,
  `idContaCorrente`         INT (11),
  `nome`                    VARCHAR (50),
  `cod_beneficiario`        INT (11) DEFAULT 0,
  `cod_beneficiario_dv`     TINYINT (2) DEFAULT 0,
  `cod_convenio`            INT (11) DEFAULT 0,
  `cod_carteira`            INT (11) DEFAULT 0,
  `aceite`                  TINYINT (2) DEFAULT 0,
  `baixa`                   TINYINT (2) DEFAULT 0,
  `especie_doc`             VARCHAR (2) DEFAULT 'DV',
  `local_pagamento`         VARCHAR (90) DEFAULT '',
  `instrucao01`             VARCHAR (90) DEFAULT '',
  `instrucao02`             VARCHAR (90) DEFAULT '',
  `instrucao03`             VARCHAR (90) DEFAULT '',
  `instrucao04`             VARCHAR (90) DEFAULT '',
  `instrucao05`             VARCHAR (90) DEFAULT '',
  `beneficiario_nome`       VARCHAR (90) DEFAULT '',
  `beneficiario_doc`        VARCHAR (20) DEFAULT '',
  `beneficiario_logradouro` VARCHAR (100) DEFAULT '',
  `beneficiario_bairro`     VARCHAR (50) DEFAULT '',
  `beneficiario_cidade`     VARCHAR (50) DEFAULT '',
  `beneficiario_uf`         VARCHAR (2) DEFAULT '',
  `beneficiario_cep`        VARCHAR (10) DEFAULT '',
  PRIMARY KEY (`idCarteira`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;