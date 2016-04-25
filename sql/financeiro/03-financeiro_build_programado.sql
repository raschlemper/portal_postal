USE pp_06895434000183;

-- DROP TABLE lancamento_parcelado;
-- DROP TABLE lancamento_transferencia_programado;
DROP TABLE lancamento_programado;
DROP TABLE tipo_documento;
DROP TABLE tipo_forma_pagamento;

CREATE TABLE `tipo_documento` (
  `idTipoDocumento` INT NOT NULL AUTO_INCREMENT,
  `descricao`       VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idTipoDocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tipo_forma_pagamento` (
  `idTipoFormaPagamento` INT NOT NULL AUTO_INCREMENT,
  `descricao`       VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idTipoFormaPagamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- CREATE TABLE `lancamento_parcelado` (
--   `idLancamentoParcelado`  INT NOT NULL AUTO_INCREMENT,
--   `idConta`                INT NOT NULL,
--   `idPlanoConta`           INT NULL,
--   `tipo`                   INT NOT NULL,
--   `favorecido`             VARCHAR(254) NULL,
--   `numero`                 VARCHAR(254) NULL,
--   `idTipoDocumento`        INT NULL,
--   `idTipoFormaPagamento`   INT NULL,
--   `frequencia`             INT NOT NULL,
--   `quantidadeParcela`      INT NULL,
--   `dataEmissao`            DATETIME NOT NULL,
--   `valorTotal`             DECIMAL(13,2) NOT NULL,
--   `historico`              VARCHAR(254) NOT NULL,
--   PRIMARY KEY (`idLancamentoParcelado`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ALTER TABLE `lancamento_parcelado` 
-- ADD INDEX `i_lancamentoparcelado_conta` (`idConta` ASC);

-- ALTER TABLE `lancamento_parcelado` 
-- ADD INDEX `i_lancamentoparcelado_planoconta` (`idPlanoConta` ASC);

-- ALTER TABLE `lancamento_parcelado` 
-- ADD INDEX `i_lancamentoparcelado_tipodocumento` (`idTipoDocumento` ASC);

-- ALTER TABLE `lancamento_parcelado` 
-- ADD INDEX `i_lancamentoparcelado_tipoformapagamento` (`idTipoFormaPagamento` ASC);

-- ALTER TABLE `lancamento_parcelado` 
-- ADD CONSTRAINT `fk_lancamentoparcelado_conta`
--   FOREIGN KEY (`idConta`)
--   REFERENCES `conta` (`idConta`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

-- ALTER TABLE `lancamento_parcelado` 
-- ADD CONSTRAINT `fk_lancamentoparcelado_planoconta`
--   FOREIGN KEY (`idPlanoConta`)
--   REFERENCES `plano_conta` (`idPlanoConta`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

-- ALTER TABLE `lancamento_parcelado` 
-- ADD CONSTRAINT `fk_lancamentoparcelado_tipodocumento`
--   FOREIGN KEY (`idTipoDocumento`)
--   REFERENCES `tipo_documento` (`idTipoDocumento`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

-- ALTER TABLE `lancamento_parcelado` 
-- ADD CONSTRAINT `fk_lancamentoparcelado_tipoformapagamento`
--   FOREIGN KEY (`idTipoFormaPagamento`)
--   REFERENCES `tipo_forma_pagamento` (`idTipoFormaPagamento`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

CREATE TABLE `lancamento_programado` (
  `idLancamentoProgramado` INT NOT NULL AUTO_INCREMENT,
  `idConta`                INT NOT NULL,
  `idPlanoConta`           INT NULL,
--   `idLancamentoParcelado`  INT NULL,
  `tipo`                   INT NOT NULL,
  `favorecido`             VARCHAR(254) NULL,
  `numero`                 VARCHAR(254) NULL,
  `idTipoDocumento`        INT NULL,
  `idTipoFormaPagamento`   INT NULL,
  `frequencia`             INT NOT NULL,
  `quantidadeParcela`      INT NULL,
  `numeroParcela`          INT NULL,
  `competencia`            DATETIME NOT NULL,
  `dataEmissao`            DATETIME NOT NULL,
  `dataVencimento`         DATETIME NOT NULL,
  `valor`                  DECIMAL(13,2) NOT NULL,
  `situacao`               INT(11) NOT NULL,
  `historico`              VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamentoProgramado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_conta` (`idConta` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_planoconta` (`idPlanoConta` ASC);

-- ALTER TABLE `lancamento_programado` 
-- ADD INDEX `i_lancamentoprogramado_lancamentoparcelado` (`idLancamentoParcelado` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_tipodocumento` (`idTipoDocumento` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_tipoformapagamento` (`idTipoFormaPagamento` ASC);

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- ALTER TABLE `lancamento_programado` 
-- ADD CONSTRAINT `fk_lancamentoprogramado_lancamentoparcelado`
--   FOREIGN KEY (`idLancamentoParcelado`)
--   REFERENCES `lancamento_parcelado` (`idLancamentoParcelado`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_tipodocumento`
  FOREIGN KEY (`idTipoDocumento`)
  REFERENCES `tipo_documento` (`idTipoDocumento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_tipoformapagamento`
  FOREIGN KEY (`idTipoFormaPagamento`)
  REFERENCES `tipo_forma_pagamento` (`idTipoFormaPagamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- CREATE TABLE `lancamento_transferencia_programado` (
--   `idLancamentoTransferenciaProgramado` INT NOT NULL AUTO_INCREMENT,
--   `idLancamentoProgramadoOrigem`        INT NOT NULL,
--   `idLancamentoProgramadoDestino`       INT NOT NULL,
--   `numero`                              VARCHAR(254) NULL,
--   `idTipoDocumento`                     INT NULL,
--   `idTipoFormaPagamento`                INT NULL,
--   `frequencia`                          INT NOT NULL,
--   `dataEmissao`                         DATETIME NOT NULL,
--   `valor`                               DECIMAL(13,2) NOT NULL,
--   `historico`                           VARCHAR(254) NOT NULL,
--   PRIMARY KEY (`idLancamentoTransferenciaProgramado`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ALTER TABLE `lancamento_transferencia_programado` 
-- ADD INDEX `i_lancamentoprogramadoprogramado_lancamentoprogramadoorigem` (`idLancamentoProgramadoOrigem` ASC);

-- ALTER TABLE `lancamento_transferencia_programado` 
-- ADD INDEX `i_lancamentoprogramadoprogramado_lancamentoprogramadodestino` (`idLancamentoProgramadoDestino` ASC);

-- ALTER TABLE `lancamento_transferencia_programado` 
-- ADD CONSTRAINT `fk_lancamentotransferenciaprogramado_lancamentoprogramadoorigem`
--   FOREIGN KEY (`idLancamentoProgramadoOrigem`)
--   REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

-- ALTER TABLE `lancamento_transferencia_programado` 
-- ADD CONSTRAINT `fk_lancamentotransferenciaprogramado_lancamentoprogramadodestino`
--   FOREIGN KEY (`idLancamentoProgramadoDestino`)
--   REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;

