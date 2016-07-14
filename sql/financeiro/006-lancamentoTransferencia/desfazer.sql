ALTER TABLE lancamento_transferencia DROP `numero`;
ALTER TABLE lancamento_transferencia DROP `competencia`;
ALTER TABLE lancamento_transferencia DROP `dataEmissao`;
ALTER TABLE lancamento_transferencia DROP `dataLancamento`;
ALTER TABLE lancamento_transferencia DROP `valor`;
ALTER TABLE lancamento_transferencia DROP `historico`;

ALTER TABLE `lancamento_transferencia` 
DROP INDEX `i_lancamentoprogramado_lancamentoorigem`;

ALTER TABLE `lancamento_transferencia` 
DROP INDEX `i_lancamentoprogramado_lancamentodestino`;