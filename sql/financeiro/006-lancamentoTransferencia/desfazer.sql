ALTER TABLE lancamento_transferencia DROP `numero`;
ALTER TABLE lancamento_transferencia DROP `data`;
ALTER TABLE lancamento_transferencia DROP `valor`;
ALTER TABLE lancamento_transferencia DROP `historico`;

ALTER TABLE `lancamento_transferencia` 
DROP INDEX `i_lancamentoprogramado_lancamentoorigem`;

ALTER TABLE `lancamento_transferencia` 
DROP INDEX `i_lancamentoprogramado_lancamentodestino`;