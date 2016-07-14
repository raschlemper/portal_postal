
USE pp_06895434000183;

DROP TABLE centro_custo;

CREATE TABLE `centro_custo` (
  `idCentroCusto` INT NOT NULL AUTO_INCREMENT,
  `codigo`       INT NOT NULL,
  `nome`         VARCHAR(254) NOT NULL,
  `grupo`        INT DEFAULT NULL,
  PRIMARY KEY (`idCentroCusto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `centro_custo` 
ADD UNIQUE INDEX `u_centrocusto` (`codigo`, `grupo`);

ALTER TABLE `lancamento` ADD `idCentroCusto` INT NULL;
ALTER TABLE `lancamento_programado` ADD `idCentroCusto` INT NULL;

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;