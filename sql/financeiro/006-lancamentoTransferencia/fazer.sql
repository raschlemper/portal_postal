ALTER TABLE lancamento_transferencia ADD `numero` VARCHAR(254) NULL,;
ALTER TABLE lancamento_transferencia ADD `data` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia ADD `valor` DECIMAL(13,2) NOT NULL;
ALTER TABLE lancamento_transferencia ADD `historico` VARCHAR(254) NOT NULL;

ALTER TABLE `lancamento_transferencia` 
ADD INDEX `i_lancamentoprogramado_lancamentoorigem` (`idLancamentoOrigem` ASC);

ALTER TABLE `lancamento_transferencia` 
ADD INDEX `i_lancamentoprogramado_lancamentodestino` (`idLancamentoDestino` ASC);