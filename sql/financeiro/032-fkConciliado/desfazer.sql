ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_lancamentoconciliado`
  FOREIGN KEY (`numeroLoteConciliado`)
  REFERENCES `lancamento_conciliado` (`numeroLote`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;