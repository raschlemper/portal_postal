USE pp_06895434000183;

DROP TABLE lancamento_programado_rateio;

CREATE TABLE `lancamento_programado_rateio` (
  `idLancamentoProgramadoRateio` INT NOT NULL AUTO_INCREMENT,
  `idPlanoConta`                 INT NULL,
  `idCentroCusto`                INT NULL,
  `idLancamentoProgramado`       INT NOT NULL,
  `valor`                        DECIMAL(13,2) NOT NULL,
  `observacao`                   LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`idLancamentoProgramadoRateio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_programado_rateio` 
ADD INDEX `i_lancamentoprogramadorateio_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_programado_rateio` 
ADD INDEX `i_lancamentoprogramadorateio_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_programado_rateio` 
ADD INDEX `i_lancamentoprogramadorateio_lancamentoprogramado` (`idLancamentoProgramado` ASC);

ALTER TABLE `lancamento_programado_rateio` 
ADD CONSTRAINT `fk_lancamentoprogramadorateio_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_rateio` 
ADD CONSTRAINT `fk_lancamentoprogramadorateio_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_rateio` 
ADD CONSTRAINT `fk_lancamentoprogramadorateio_lancamentoprogramado`
  FOREIGN KEY (`idLancamentoProgramado`)
  REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
