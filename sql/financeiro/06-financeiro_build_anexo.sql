USE pp_06895434000183;

DROP TABLE lancamento_anexo;

CREATE TABLE `lancamento_anexo` (
  `idLancamentoAnexo` INT NOT NULL AUTO_INCREMENT,
  `idLancamento`      INT NOT NULL,
  `nome`              VARCHAR(100) NOT NULL,
  `anexo`             BLOB NOT NULL,
  `usuario`           VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`idLancamentoAnexo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_anexo` 
ADD INDEX `i_lancamentoanexo_lancamento` (`idLancamentoAnexo` ASC);

ALTER TABLE `lancamento_anexo` 
ADD CONSTRAINT `fk_lancamentoanexo_lancamento`
  FOREIGN KEY (`idLancamento`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
