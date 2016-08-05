ALTER TABLE lancamento_programado ADD `modelo` INT(11) NOT NULL;
UPDATE lancamento_programado SET modelo = 0 WHERE modelo is null;