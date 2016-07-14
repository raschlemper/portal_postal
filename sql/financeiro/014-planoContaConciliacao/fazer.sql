ALTER TABLE lancamento_conciliado CHANGE `idPlanoConta` `idPlanoConta` INT NULL;
ALTER TABLE lancamento_programado CHANGE `idPlanoConta` `idPlanoConta` INT NULL;
ALTER TABLE lancamento CHANGE `valorDesconto` `valorDesconto` DECIMAL(13,2) NULL;
ALTER TABLE lancamento CHANGE `valorJuros` `valorJuros` DECIMAL(13,2) NULL;
ALTER TABLE lancamento CHANGE `valorMulta` `valorMulta` DECIMAL(13,2) NULL;

