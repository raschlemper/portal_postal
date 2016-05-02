ALTER TABLE lancamento ADD `numeroLoteConciliado` INT NULL;
ALTER TABLE lancamento ADD `autenticacao` VARCHAR(254) DEFAULT NULL;
ALTER TABLE lancamento_programado CHANGE `idPlanoConta` `idPlanoConta` INT NOT NULL;