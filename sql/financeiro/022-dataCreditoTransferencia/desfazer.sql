ALTER TABLE lancamento_transferencia DROP `dataLancamentoDestino`;
ALTER TABLE lancamento_transferencia DROP `dataCompetenciaDestino`;
ALTER TABLE lancamento_transferencia CHANGE `dataLancamentoOrigem` `dataLancamento` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia CHANGE `dataCompetenciaOrigem` `dataCompetencia` DATETIME NOT NULL;

ALTER TABLE lancamento_programado_transferencia DROP `dataVencimentoDestino`;
ALTER TABLE lancamento_programado_transferencia DROP `dataCompetenciaDestino`;
ALTER TABLE lancamento_programado_transferencia CHANGE `dataVencimentoOrigem` `dataVencimento` DATETIME NOT NULL;
ALTER TABLE lancamento_programado_transferencia CHANGE `dataCompetenciaOrigem` `dataCompetencia` DATETIME NOT NULL;
