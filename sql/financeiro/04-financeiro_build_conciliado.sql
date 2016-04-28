USE pp_06895434000183;

DROP TABLE lancamento_conciliado;

CREATE TABLE `lancamento_conciliado` (
  `idLancamentoConciliado` INT NOT NULL AUTO_INCREMENT,
  `idConta`                INT NOT NULL,
  `idPlanoConta`           INT NULL,
  `tipo`                   INT NOT NULL,
  `competencia`            DATETIME NOT NULL,
  `dataEmissao`            DATETIME NOT NULL,
  `dataLancamento`         DATETIME NOT NULL,
  `valor`                  DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (`idLancamentoConciliado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_conta` (`idConta` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

