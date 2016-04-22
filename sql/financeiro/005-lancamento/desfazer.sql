ALTER TABLE lancamento CHANGE `dataEmissao` `data` DATETIME NOT NULL;
ALTER TABLE lancamento DROP `dataVencimento`;
ALTER TABLE lancamento DROP `dataPagamento`;
ALTER TABLE lancamento DROP `dataCompensacao`;
ALTER TABLE lancamento DROP `modelo`;
ALTER TABLE lancamento DROP `idLancamentoProgramado`;
ALTER TABLE lancamento DROP `competencia`;
ALTER TABLE lancamento DROP `observacao`;