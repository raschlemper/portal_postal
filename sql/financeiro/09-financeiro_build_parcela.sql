USE pp_06895434000183;

DROP TABLE lancamento_programado_parcela;

CREATE TABLE `lancamento_programado_parcela` (
  `idLancamentoProgramadoParcela` INT NOT NULL AUTO_INCREMENT,
  `idPlanoConta`                  INT NULL,
  `idCentroCusto`                 INT NULL,
  `idLancamentoProgramado`        INT NOT NULL,
  `numero`                        INTEGER NOT NULL,
  `dataVencimento`                DATETIME NOT NULL,
  `valor`                         DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (`idLancamentoProgramadoParcela`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_programado_parcela` 
ADD INDEX `i_lancamentoprogramadoparcela_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_programado_parcela` 
ADD INDEX `i_lancamentoprogramadoparcela_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_programado_parcela` 
ADD INDEX `i_lancamentoprogramadoparcela_lancamentoprogramado` (`idLancamentoProgramado` ASC);

ALTER TABLE `lancamento_programado_parcela` 
ADD CONSTRAINT `fk_lancamentoprogramadoparcela_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_parcela` 
ADD CONSTRAINT `fk_lancamentoprogramadoparcela_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_parcela` 
ADD CONSTRAINT `fk_lancamentoprogramadoparcela_lancamentoprogramado`
  FOREIGN KEY (`idLancamentoProgramado`)
  REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
