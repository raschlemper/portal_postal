ALTER TABLE lancamento CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;
ALTER TABLE lancamento_programado CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;
ALTER TABLE lancamento_conciliado CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;