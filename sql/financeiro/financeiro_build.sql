
USE pp_06895434000183;

-- DROP TABLE tipo_conta;
DROP TABLE cartao_credito;
DROP TABLE conta;
DROP TABLE plano_conta;
DROP TABLE lancamento;
DROP TABLE lancamento_transferencia;

-- CREATE TABLE `tipo_conta` (
--   `idTipoConta` INT NOT NULL AUTO_INCREMENT,
--   `categoria`   INT NOT NULL,
--   `descricao`   VARCHAR(254) NOT NULL,
--   PRIMARY KEY (`idTipoConta`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cartao_credito` (
  `idCartaoCredito`    INT NOT NULL AUTO_INCREMENT,
  `idContaCorrente`    INT DEFAULT NULL,
  `nome`               VARCHAR(254) NOT NULL,
  `bandeira`           VARCHAR(254) NOT NULL,
  `diaFechamento`      INT NOT NULL,
  `diaVencimento`      INT NOT NULL,
  `valorLimiteCredito` DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (`idCartaoCredito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `cartao_credito` 
ADD INDEX `i_cartaocredito_contacorrente` (`idContaCorrente` ASC);

ALTER TABLE `cartao_credito` 
ADD CONSTRAINT `fk_cartaocredito_contacorrente`
  FOREIGN KEY (`idContaCorrente`)
  REFERENCES `conta_corrente` (`idContaCorrente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `conta` (
  `idConta`            INT NOT NULL AUTO_INCREMENT,
--   `idTipoConta`       INT NOT NULL,
  `idContaCorrente`    INT DEFAULT NULL,
  `idCartaoCredito`    INT DEFAULT NULL,
  `nome`               VARCHAR(254) NOT NULL,
  `tipo`               INT NOT NULL,
  `status`             INT NOT NULL,
  `dataAbertura`       DATETIME NOT NULL,
  `valorSaldoAbertura` DECIMAL(13,2) DEFAULT 0,
  PRIMARY KEY (`idConta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `conta` 
ADD INDEX `i_conta_contacorrente` (`idContaCorrente` ASC);

ALTER TABLE `conta` 
ADD CONSTRAINT `fk_conta_contacorrente`
  FOREIGN KEY (`idContaCorrente`)
  REFERENCES `conta_corrente` (`idContaCorrente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `conta` 
ADD INDEX `i_conta_cartaocredito` (`idCartaoCredito` ASC);

ALTER TABLE `conta` 
ADD CONSTRAINT `fk_conta_cartaocredito`
  FOREIGN KEY (`idCartaoCredito`)
  REFERENCES `cartao_credito` (`idCartaoCredito`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `plano_conta` (
  `idPlanoConta` INT NOT NULL AUTO_INCREMENT,
  `tipo`         INT NOT NULL,
  `codigo`       INT NOT NULL,
  `nome`         VARCHAR(254) NOT NULL,
  `grupo`        INT DEFAULT NULL,
  PRIMARY KEY (`idPlanoConta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `plano_conta` 
ADD UNIQUE INDEX `u_planoconta` (`tipo`, `codigo`, `grupo`);

-- ALTER TABLE `conta` 
-- ADD INDEX `i_conta_tipoconta` (`idTipoConta` ASC);

-- ALTER TABLE `conta` 
-- ADD CONSTRAINT `fk_conta_tipoconta`
--   FOREIGN KEY (`idTipoConta`)
--   REFERENCES `tipo_conta` (`idTipoConta`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

CREATE TABLE `lancamento` (
  `idLancamento` INT NOT NULL AUTO_INCREMENT,
  `idConta`      INT NOT NULL,
  `idPlanoConta` INT NULL,
  `tipo`         INT NOT NULL,
  `favorecido`   VARCHAR(254) NULL,
  `numero`       VARCHAR(254) NULL,
  `data`         DATETIME NOT NULL,
  `valor`        DECIMAL(13,2) NOT NULL,
  `situacao`     INT(11) NOT NULL,
  `historico`    VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`idLancamentoTransferencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_transferencia` 
ADD CONSTRAINT `fk_lancamentotransferencia_lancamentoorigem`
  FOREIGN KEY (`idLancamentoOrigem`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_transferencia` 
ADD CONSTRAINT `fk_lancamentotransferencia_lancamentodestino`
  FOREIGN KEY (`idLancamentoDestino`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;