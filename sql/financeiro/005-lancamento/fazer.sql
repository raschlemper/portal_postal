ALTER TABLE lancamento CHANGE `data` `dataEmissao` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `dataVencimento` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `dataLancamento` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `dataCompensacao` DATETIME NULL;
ALTER TABLE lancamento ADD `modelo` INT(11) NOT NULL;
ALTER TABLE lancamento ADD `idLancamentoProgramado` INT NULL;
ALTER TABLE lancamento ADD `competencia` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `observacao` LONGTEXT DEFAULT NULL;

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_lancamentoprogramado` (`idLancamentoProgramado` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_lancamentoprogramado`
  FOREIGN KEY (`idLancamentoProgramado`)
  REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;