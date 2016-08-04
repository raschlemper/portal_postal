USE pp_06895434000183;

DROP TABLE lancamento_programado_transferencia;

CREATE TABLE `lancamento_programado_transferencia` (
   `idLancamentoProgramadoTransferencia` INT NOT NULL AUTO_INCREMENT,
   `idLancamentoProgramadoOrigem`        INT NOT NULL,
   `idLancamentoProgramadoDestino`       INT NOT NULL,
   `numero`                              VARCHAR(254) NULL,
   `idTipoDocumento`                     INT NULL,
   `idTipoFormaPagamento`                INT NULL,
   `frequencia`                          INT NOT NULL,
   `dataCompetencia`                     DATETIME NOT NULL,
   `dataEmissao`                         DATETIME NOT NULL,
   `dataVencimento`                      DATETIME NOT NULL,
   `valor`                               DECIMAL(13,2) NOT NULL,
   `historico`                           VARCHAR(254) NOT NULL,
   `observacao`                          LONGTEXT DEFAULT NULL,
   `usuario`                             VARCHAR(100) DEFAULT NULL,
   PRIMARY KEY (`idLancamentoProgramadoTransferencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 ALTER TABLE `lancamento_programado_transferencia` 
 ADD INDEX `i_lancamentoprogramadotransferencia_lancamentoprogramadoorigem` (`idLancamentoProgramadoOrigem` ASC);

 ALTER TABLE `lancamento_programado_transferencia` 
 ADD INDEX `i_lancamentoprogramadotransferencia_lancamentoprogramadodestino` (`idLancamentoProgramadoDestino` ASC);

 ALTER TABLE `lancamento_programado_transferencia` 
 ADD CONSTRAINT `fk_lancamentoprogramadotransferencia_lancamentoprogramadoorigem`
   FOREIGN KEY (`idLancamentoProgramadoOrigem`)
   REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_transferencia` 
ADD CONSTRAINT `fk_lancamentoprogramadotransferencia_lancamentoprogramadodestino`
   FOREIGN KEY (`idLancamentoProgramadoDestino`)
   REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION;

