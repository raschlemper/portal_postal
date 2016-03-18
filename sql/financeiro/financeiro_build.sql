USE pp_06895434000183;

DROP TABLE banco;
DROP TABLE tipo_conta;
DROP TABLE plano_conta;
DROP TABLE conta;
DROP TABLE conta_cartao_credito;
DROP TABLE lancamento;
DROP TABLE lancamento_transferencia;

CREATE TABLE `banco` (
  `idBanco` INT NOT NULL AUTO_INCREMENT,
  `nome`    VARCHAR(254) NOT NULL,
  `numero`  INT NOT NULL,
  `website` VARCHAR(254) DEFAULT NULL,
  PRIMARY KEY (`idbanco`) ,
  UNIQUE INDEX `numero_UNIQUE` (`numero` ASC)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `tipo_conta` (
  `idTipoConta` INT NOT NULL AUTO_INCREMENT,
  `categoria`   INT NOT NULL,
  `descricao`   VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idTipoConta`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `plano_conta` (
  `idPlanoConta` INT NOT NULL AUTO_INCREMENT,
  `tipo`         INT NOT NULL,
  `codigo`       INT NOT NULL,
  `nome`         VARCHAR(254) NOT NULL,
  `grupo`        INT DEFAULT NULL,
  PRIMARY KEY (`idPlanoConta`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `conta` (
  `idConta`               INT NOT NULL AUTO_INCREMENT,
  `tipo`                  INT NOT NULL,
  `banco`                 INT NOT NULL,
  `agencia`               INT NOT NULL,
  `contaCorrente`         INT NOT NULL,
  `carteira`              INT NULL,
  `valorLimiteCredito`    DECIMAL(13,2) NULL,
  `dataVencimentoCredito` DATETIME NULL,
  `status`                INT NOT NULL,
  `dataAbertura`          DATETIME NOT NULL,
  `valorSaldoAbertura`    DECIMAL(13,2) DEFAULT 0,
  PRIMARY KEY (`idConta`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `conta_cartao_credito` (
  `idContaCartaoCredito`  INT NOT NULL AUTO_INCREMENT,
  `idConta`               INT NOT NULL,
  `diaFechamento`         INT NOT NULL,
  `diaVencimento`         INT NOT NULL,
  `idContaPagamento`      INT NOT NULL,
  PRIMARY KEY (`idContaCartaoCredito`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `conta_cartao_credito` 
ADD INDEX `i_contacartaocredito_conta` (`idConta` ASC);

ALTER TABLE `conta_cartao_credito` 
ADD INDEX `i_contacartaocredito_contapagamento` (`idContaPagamento` ASC);

ALTER TABLE `conta_cartao_credito` 
ADD CONSTRAINT `fk_contacartaocredito_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `conta_cartao_credito` 
ADD CONSTRAINT `fk_contacartaocredito_contapagamento`
  FOREIGN KEY (`idContaPagamento`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `lancamento` (
  `idLancamento` INT NOT NULL AUTO_INCREMENT,
  `idConta`      INT NOT NULL,
  `idPlanoConta` INT NOT NULL,
  `tipo`         INT NOT NULL,
  `data`         DATETIME NOT NULL,
  `valor`        DECIMAL(13,2) DEFAULT 0,
  `historico`    VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamento`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_conta` (`idConta` ASC);

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `lancamento_transferencia` (
  `idLancamentoTransferencia` INT NOT NULL AUTO_INCREMENT,
  `idContaOrigem`             INT NOT NULL,
  `idContaDestino`            INT NOT NULL,
  `data`                      DATETIME NOT NULL,
  `valor`                     DECIMAL(13,2) DEFAULT 0,
  `historico`                 VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamentoTransferencia`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_transferencia` 
ADD CONSTRAINT `fk_lancamentotransferencia_contaorigem`
  FOREIGN KEY (`idContaOrigem`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_transferencia` 
ADD CONSTRAINT `fk_lancamentotransferencia_contadestino`
  FOREIGN KEY (`idContaDestino`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;