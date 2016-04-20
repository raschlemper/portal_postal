ALTER TABLE lancamento RENAME `dataEmissao` TO `data`;
ALTER TABLE lancamento DROP `dataVencimento`;
ALTER TABLE lancamento DROP `dataPagamento`;
ALTER TABLE lancamento DROP `dataCompensacao`;
ALTER TABLE lancamento DROP `modelo`;
ALTER TABLE lancamento DROP `idLancamentoProgramado`;