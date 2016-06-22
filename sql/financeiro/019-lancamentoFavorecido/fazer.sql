ALTER TABLE lancamento ADD `idFavorecido` INT NULL;
ALTER TABLE lancamento_programado ADD `idFavorecido` INT NULL;

ALTER TABLE lancamento DROP `favorecido`;
ALTER TABLE lancamento_programado DROP `favorecido`;