CREATE TABLE `plano_conta_padrao` (
  `idPlanoConta` INT NOT NULL AUTO_INCREMENT,
  `tipo`         INT NOT NULL,
  `codigo`       INT NOT NULL,
  `nome`         VARCHAR(254) NOT NULL,
  `grupo`        INT DEFAULT NULL,
  PRIMARY KEY (`idPlanoConta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `plano_conta_padrao` 
ADD UNIQUE INDEX `u_planocontapadrao` (`tipo`, `codigo`, `grupo`);

INSERT INTO `plano_conta_padrao` VALUES 
(1,0,1,'Receita',NULL),
(2,1,2,'Despesa',NULL);

INSERT INTO `plano_conta_padrao` VALUES 
(1,0,1,'Receita',NULL),
(2,1,2,'Despesa',NULL),
(3,1,1,'Despesa Operacional',2),
(4,1,2,'Despesa Não Operacional',2),
(5,0,1,'Receita Operacional',1),
(6,0,2,'Receita Não Operacional',1),
(7,1,1,'Despesa Administrativa',3),
(8,1,2,'Despesa com Pessoal',3),
(9,1,3,'Despesa com Coleta',3),
(10,1,4,'Despesa Financeira',3),
(11,1,5,'Despesa com Impostos',3),
(12,1,1,'Despesa com Motos',9),
(13,1,2,'Despesa com Carros',9),
(14,1,1,'Financiamento',12),
(15,1,2,'Licenciamento',12),
(16,1,3,'Manutenção',12),
(17,1,4,'Multa',12),
(18,1,5,'Rastreamento',12),
(19,1,1,'Financiamento',13),
(20,1,2,'Licenciamento',13),
(21,1,3,'Manutenção',13),
(22,1,4,'Multa',13),
(23,1,5,'Rastreamento',13),
(24,1,6,'Seguro',13),
(25,1,7,'Estacionamento',13),
(26,1,8,'Locação',13),
(27,1,9,'Combustíveis e Lubrificantes',13),
(28,1,6,'Combustíveis e Lubrificantes',12),
(29,1,1,'Tarifa Bancária',10),
(30,1,2,'Juros',10),
(31,1,1,'Simples Nacional',11),
(32,1,2,'Outros Impostos',11),
(33,1,1,'Salário',8),
(34,1,2,'13 Salário',8),
(35,1,3,'FGTS',8),
(36,1,4,'V.A.',8),
(37,1,5,'V.T.',8),
(38,1,6,'Uniforme',8),
(39,1,1,'Aluguel',7),
(40,1,2,'Contabilidade',7);