ALTER TABLE lancamento_transferencia CHANGE `dataLancamento` `dataLancamentoOrigem` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia CHANGE `dataCompetencia` `dataCompetenciaOrigem` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia ADD `dataLancamentoDestino` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia ADD `dataCompetenciaDestino` DATETIME NOT NULL;

ALTER TABLE lancamento_programado_transferencia CHANGE `dataVencimento` `dataVencimentoOrigem` DATETIME NOT NULL;
ALTER TABLE lancamento_programado_transferencia CHANGE `dataCompetencia` `dataCompetenciaOrigem` DATETIME NOT NULL;
ALTER TABLE lancamento_programado_transferencia ADD `dataVencimentoDestino` DATETIME NOT NULL;
ALTER TABLE lancamento_programado_transferencia ADD `dataCompetenciaDestino` DATETIME NOT NULL;
