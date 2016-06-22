ALTER TABLE lancamento ADD `favorecido` VARCHAR(254) NULL;
ALTER TABLE lancamento_programado ADD `favorecido` VARCHAR(254) NULL;

ALTER TABLE lancamento DROP `idFavorecido`;
ALTER TABLE lancamento_programado DROP `idFavorecido`;