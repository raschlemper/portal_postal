USE pp_06895434000183;

DROP TABLE lancamento_transferencia_programado;

CREATE TABLE `lancamento_transferencia_programado` (
   `idLancamentoTransferenciaProgramado` INT NOT NULL AUTO_INCREMENT,
   `idLancamentoProgramadoOrigem`        INT NOT NULL,
   `idLancamentoProgramadoDestino`       INT NOT NULL,
   `numero`                              VARCHAR(254) NULL,
   `idTipoDocumento`                     INT NULL,
   `idTipoFormaPagamento`                INT NULL,
   `frequencia`                          INT NOT NULL,
   `dataEmissao`                         DATETIME NOT NULL,
   `valor`                               DECIMAL(13,2) NOT NULL,
   `historico`                           VARCHAR(254) NOT NULL,
   `usuario`                             VARCHAR(100) DEFAULT NULL,
   PRIMARY KEY (`idLancamentoTransferenciaProgramado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 ALTER TABLE `lancamento_transferencia_programado` 
 ADD INDEX `i_lancamentoprogramadoprogramado_lancamentoprogramadoorigem` (`idLancamentoProgramadoOrigem` ASC);

 ALTER TABLE `lancamento_transferencia_programado` 
 ADD INDEX `i_lancamentoprogramadoprogramado_lancamentoprogramadodestino` (`idLancamentoProgramadoDestino` ASC);

 ALTER TABLE `lancamento_transferencia_programado` 
 ADD CONSTRAINT `fk_lancamentotransferenciaprogramado_lancamentoprogramadoorigem`
   FOREIGN KEY (`idLancamentoProgramadoOrigem`)
   REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION;

ALTER TABLE `lancamento_transferencia_programado` 
ADD CONSTRAINT `fk_lancamentotransferenciaprogramado_lancamentoprogramadodestino`
   FOREIGN KEY (`idLancamentoProgramadoDestino`)
   REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION;

