USE pp_06895434000183;

DROP TABLE lancamento_rateio;

CREATE TABLE `lancamento_rateio` (
  `idLancamentoRateio` INT NOT NULL AUTO_INCREMENT,
  `idPlanoConta`       INT NULL,
  `idCentroCusto`      INT NULL,
  `idLancamento`       INT NOT NULL,
  `valor`              DECIMAL(13,2) NOT NULL,
  `observacao`         LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`idLancamentoRateio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_rateio` 
ADD INDEX `i_lancamentorateio_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_rateio` 
ADD INDEX `i_lancamentorateio_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_rateio` 
ADD INDEX `i_lancamentorateio_lancamento` (`idLancamento` ASC);

ALTER TABLE `lancamento_rateio` 
ADD CONSTRAINT `fk_lancamentorateio_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_rateio` 
ADD CONSTRAINT `fk_lancamentorateio_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_rateio` 
ADD CONSTRAINT `fk_lancamentorateio_lancamento`
  FOREIGN KEY (`idLancamento`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
