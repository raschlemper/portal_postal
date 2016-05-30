ALTER TABLE `lancamento_conciliado` ADD `idCentroCusto` INT NULL;

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

