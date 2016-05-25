ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_numerolote` (`numeroLote` ASC);

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_lancamentoconciliado` (`numeroLoteConciliado` ASC)  COMMENT '';

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_lancamentoconciliado`
  FOREIGN KEY (`numeroLoteConciliado`)
  REFERENCES `lancamento_conciliado` (`numeroLote`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

