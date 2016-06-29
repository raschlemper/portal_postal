/***** 01-financeiro_build_conta_corrente *****/

CREATE TABLE `banco` (
  `idBanco` INT NOT NULL AUTO_INCREMENT,
  `nome`    VARCHAR(254) NOT NULL,
  `numero`  INT NOT NULL,
  `website` VARCHAR(254) DEFAULT NULL,
  PRIMARY KEY (`idbanco`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `banco` 
ADD UNIQUE INDEX `u_numero` (`numero`);

CREATE TABLE `conta_corrente` (
  `idContaCorrente` INT NOT NULL AUTO_INCREMENT,
  `nome`            VARCHAR(254) NOT NULL,
  `idBanco`         INT NOT NULL,
  `agencia`         INT NOT NULL,
  `contaCorrente`   INT NOT NULL,
  `carteira`        INT DEFAULT NULL,
  `poupanca`        TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idContaCorrente`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

ALTER TABLE `conta_corrente` 
ADD UNIQUE INDEX `u_contacorrente` (`idBanco`, `agencia`, `contaCorrente`);

CREATE TABLE `carteira_cobranca` (
  `idCarteira`              INT (11) NOT NULL AUTO_INCREMENT,
  `idContaCorrente`         INT (11),
  `nome`                    VARCHAR (50),
  `cod_beneficiario`        INT (11) DEFAULT 0,
  `cod_beneficiario_dv`     TINYINT (2) DEFAULT 0,
  `cod_convenio`            INT (11) DEFAULT 0,
  `cod_carteira`            INT (11) DEFAULT 0,
  `aceite`                  TINYINT (2) DEFAULT 0,
  `baixa`                   TINYINT (2) DEFAULT 0,
  `especie_doc`             VARCHAR (2) DEFAULT 'DV',
  `local_pagamento`         VARCHAR (90) DEFAULT '',
  `instrucao01`             VARCHAR (90) DEFAULT '',
  `instrucao02`             VARCHAR (90) DEFAULT '',
  `instrucao03`             VARCHAR (90) DEFAULT '',
  `instrucao04`             VARCHAR (90) DEFAULT '',
  `instrucao05`             VARCHAR (90) DEFAULT '',
  `beneficiario_nome`       VARCHAR (90) DEFAULT '',
  `beneficiario_doc`        VARCHAR (20) DEFAULT '',
  `beneficiario_logradouro` VARCHAR (100) DEFAULT '',
  `beneficiario_bairro`     VARCHAR (50) DEFAULT '',
  `beneficiario_cidade`     VARCHAR (50) DEFAULT '',
  `beneficiario_uf`         VARCHAR (2) DEFAULT '',
  `beneficiario_cep`        VARCHAR (10) DEFAULT '',
  PRIMARY KEY (`idCarteira`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***** 02-financeiro_build *****/

CREATE TABLE `cartao_credito` (
  `idCartaoCredito`    INT NOT NULL AUTO_INCREMENT,
  `idContaCorrente`    INT DEFAULT NULL,
  `nome`               VARCHAR(254) NOT NULL,
  `bandeira`           VARCHAR(254) NOT NULL,
  `diaFechamento`      INT NOT NULL,
  `diaVencimento`      INT NOT NULL,
  `valorLimiteCredito` DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (`idCartaoCredito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `cartao_credito` 
ADD INDEX `i_cartaocredito_contacorrente` (`idContaCorrente` ASC);

ALTER TABLE `cartao_credito` 
ADD CONSTRAINT `fk_cartaocredito_contacorrente`
  FOREIGN KEY (`idContaCorrente`)
  REFERENCES `conta_corrente` (`idContaCorrente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `conta` (
  `idConta`            INT NOT NULL AUTO_INCREMENT,
--   `idTipoConta`       INT NOT NULL,
  `idContaCorrente`    INT DEFAULT NULL,
  `idCartaoCredito`    INT DEFAULT NULL,
  `nome`               VARCHAR(254) NOT NULL,
  `tipo`               INT NOT NULL,
  `status`             INT NOT NULL,
  `dataAbertura`       DATETIME NOT NULL,
  `valorSaldoAbertura` DECIMAL(13,2) DEFAULT 0,
  PRIMARY KEY (`idConta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `conta` 
ADD INDEX `i_conta_contacorrente` (`idContaCorrente` ASC);

ALTER TABLE `conta` 
ADD CONSTRAINT `fk_conta_contacorrente`
  FOREIGN KEY (`idContaCorrente`)
  REFERENCES `conta_corrente` (`idContaCorrente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `conta` 
ADD INDEX `i_conta_cartaocredito` (`idCartaoCredito` ASC);

ALTER TABLE `conta` 
ADD CONSTRAINT `fk_conta_cartaocredito`
  FOREIGN KEY (`idCartaoCredito`)
  REFERENCES `cartao_credito` (`idCartaoCredito`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `plano_conta` (
  `idPlanoConta` INT NOT NULL AUTO_INCREMENT,
  `tipo`         INT NOT NULL,
  `codigo`       INT NOT NULL,
  `nome`         VARCHAR(254) NOT NULL,
  `grupo`        INT DEFAULT NULL,
  PRIMARY KEY (`idPlanoConta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `plano_conta` 
ADD UNIQUE INDEX `u_planoconta` (`tipo`, `codigo`, `grupo`);

CREATE TABLE `lancamento` (
  `idLancamento` INT NOT NULL AUTO_INCREMENT,
  `idConta`      INT NOT NULL,
  `idPlanoConta` INT NULL,
  `tipo`         INT NOT NULL,
  `favorecido`   VARCHAR(254) NULL,
  `numero`       VARCHAR(254) NULL,
  `data`         DATETIME NOT NULL,
  `valor`        DECIMAL(13,2) NOT NULL,
  `situacao`     INT(11) NOT NULL,
  `historico`    VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_conta` (`idConta` ASC);

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `lancamento_transferencia` (
  `idLancamentoTransferencia` INT NOT NULL AUTO_INCREMENT,
  `idLancamentoOrigem`        INT NOT NULL,
  `idLancamentoDestino`       INT NOT NULL,
  PRIMARY KEY (`idLancamentoTransferencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_transferencia` 
ADD CONSTRAINT `fk_lancamentotransferencia_lancamentoorigem`
  FOREIGN KEY (`idLancamentoOrigem`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_transferencia` 
ADD CONSTRAINT `fk_lancamentotransferencia_lancamentodestino`
  FOREIGN KEY (`idLancamentoDestino`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 03-financeiro_build_programado *****/

CREATE TABLE `tipo_documento` (
  `idTipoDocumento` INT NOT NULL AUTO_INCREMENT,
  `descricao`       VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idTipoDocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tipo_forma_pagamento` (
  `idTipoFormaPagamento` INT NOT NULL AUTO_INCREMENT,
  `descricao`       VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idTipoFormaPagamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lancamento_programado` (
  `idLancamentoProgramado` INT NOT NULL AUTO_INCREMENT,
  `idConta`                INT NOT NULL,
  `idPlanoConta`           INT NOT NULL,
  `tipo`                   INT NOT NULL,
  `favorecido`             VARCHAR(254) NULL,
  `numero`                 VARCHAR(254) NULL,
  `idTipoDocumento`        INT NULL,
  `idTipoFormaPagamento`   INT NULL,
  `frequencia`             INT NOT NULL,
  `quantidadeParcela`      INT NULL,
  `numeroParcela`          INT NULL,
  `competencia`            DATETIME NOT NULL,
  `dataEmissao`            DATETIME NOT NULL,
  `dataVencimento`         DATETIME NOT NULL,
  `valor`                  DECIMAL(13,2) NOT NULL,
  `situacao`               INT(11) NOT NULL,
  `historico`              VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamentoProgramado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_conta` (`idConta` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_tipodocumento` (`idTipoDocumento` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_tipoformapagamento` (`idTipoFormaPagamento` ASC);

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_tipodocumento`
  FOREIGN KEY (`idTipoDocumento`)
  REFERENCES `tipo_documento` (`idTipoDocumento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_tipoformapagamento`
  FOREIGN KEY (`idTipoFormaPagamento`)
  REFERENCES `tipo_forma_pagamento` (`idTipoFormaPagamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 04-financeiro_build_conciliado *****/

CREATE TABLE `lancamento_conciliado` (
  `idLancamentoConciliado` INT NOT NULL AUTO_INCREMENT,
  `idConta`                INT NOT NULL,
  `idPlanoConta`           INT NOT NULL,
  `idLancamento`           INT NOT NULL,
  `tipo`                   INT NOT NULL,
  `numeroLote`             INT NOT NULL,
  `competencia`            DATETIME NOT NULL,
  `dataEmissao`            DATETIME NOT NULL,
  `dataLancamento`         DATETIME NOT NULL,
  `valor`                  DECIMAL(13,2) NOT NULL,
  `historico`              VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idLancamentoConciliado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_conta` (`idConta` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_lancamento` (`idLancamento` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_conta`
  FOREIGN KEY (`idConta`)
  REFERENCES `conta` (`idConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_lancamento`
  FOREIGN KEY (`idLancamento`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 05-financeiro_build_centro_custo *****/

CREATE TABLE `centro_custo` (
  `idCentroCusto` INT NOT NULL AUTO_INCREMENT,
  `codigo`       INT NOT NULL,
  `nome`         VARCHAR(254) NOT NULL,
  `grupo`        INT DEFAULT NULL,
  PRIMARY KEY (`idCentroCusto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `centro_custo` 
ADD UNIQUE INDEX `u_centrocusto` (`codigo`, `grupo`);

ALTER TABLE `lancamento` ADD `idCentroCusto` INT NULL;
ALTER TABLE `lancamento_programado` ADD `idCentroCusto` INT NULL;

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_programado` 
ADD INDEX `i_lancamentoprogramado_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado` 
ADD CONSTRAINT `fk_lancamentoprogramado_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `lancamento_rateio` (
  `idLancamentoRateio` INT NOT NULL AUTO_INCREMENT,
  `idPlanoConta`       INT NULL,
  `idCentroCusto`      INT NULL,
  `idLancamento`       INT NOT NULL,
  `valor`              DECIMAL(13,2) NOT NULL,
  `observacao`         LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`idLancamentoRateio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***** 06-financeiro_build_rateio *****/

ALTER TABLE `lancamento_rateio` 
ADD INDEX `i_lancamentorateio_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_rateio` 
ADD INDEX `i_lancamentorateio_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_rateio` 
ADD INDEX `i_lancamentorateio_lancamento` (`idLancamento` ASC);

ALTER TABLE `lancamento_rateio` 
ADD CONSTRAINT `fk_lancamentorateio_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_rateio` 
ADD CONSTRAINT `fk_lancamentorateio_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_rateio` 
ADD CONSTRAINT `fk_lancamentorateio_lancamento`
  FOREIGN KEY (`idLancamento`)
  REFERENCES `lancamento` (`idLancamento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 07-financeiro_build_rateio_programado *****/

CREATE TABLE `lancamento_programado_rateio` (
  `idLancamentoProgramadoRateio` INT NOT NULL AUTO_INCREMENT,
  `idPlanoConta`                 INT NULL,
  `idCentroCusto`                INT NULL,
  `idLancamentoProgramado`       INT NOT NULL,
  `valor`                        DECIMAL(13,2) NOT NULL,
  `observacao`                   LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`idLancamentoProgramadoRateio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `lancamento_programado_rateio` 
ADD INDEX `i_lancamentoprogramadorateio_planoconta` (`idPlanoConta` ASC);

ALTER TABLE `lancamento_programado_rateio` 
ADD INDEX `i_lancamentoprogramadorateio_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_programado_rateio` 
ADD INDEX `i_lancamentoprogramadorateio_lancamentoprogramado` (`idLancamentoProgramado` ASC);

ALTER TABLE `lancamento_programado_rateio` 
ADD CONSTRAINT `fk_lancamentoprogramadorateio_planoconta`
  FOREIGN KEY (`idPlanoConta`)
  REFERENCES `plano_conta` (`idPlanoConta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_rateio` 
ADD CONSTRAINT `fk_lancamentoprogramadorateio_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lancamento_programado_rateio` 
ADD CONSTRAINT `fk_lancamentoprogramadorateio_lancamentoprogramado`
  FOREIGN KEY (`idLancamentoProgramado`)
  REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


/***** 001-banco *****/

INSERT INTO banco (numero, nome) VALUES(1,'BANCO DO BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(3,'BANCO DA AMAZONIA S.A.');
INSERT INTO banco (numero, nome) VALUES(4,'BANCO DO NORDESTE DO BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(21,'BANESTES S.A BANCO DO ESTADO DO ESPIRITO SANTO');
INSERT INTO banco (numero, nome) VALUES(24,'BANCO DE PERNAMBUCO S.A.-BANDEPE');
INSERT INTO banco (numero, nome) VALUES(25,'BANCO ALFA S/A');
INSERT INTO banco (numero, nome) VALUES(27,'BANCO DO ESTADO DE SANTA CATARINA S.A.');
INSERT INTO banco (numero, nome) VALUES(29,'BANCO BANERJ S.A.');
INSERT INTO banco (numero, nome) VALUES(31,'BANCO BEG S.A.');
INSERT INTO banco (numero, nome) VALUES(33,'BANCO SANTANDER S.A.');
INSERT INTO banco (numero, nome) VALUES(34,'BANCO DO ESTADO DO AMAZONAS S.A.');
INSERT INTO banco (numero, nome) VALUES(36,'BANCO BRADESCO BBI S.A.');
INSERT INTO banco (numero, nome) VALUES(37,'BANCO DO ESTADO DO PARA S.A.');
INSERT INTO banco (numero, nome) VALUES(38,'BANCO BANESTADO S.A.');
INSERT INTO banco (numero, nome) VALUES(39,'BANCO DO ESTADO DO PIAUI S.A. - BEP');
INSERT INTO banco (numero, nome) VALUES(40,'BANCO CARGILL S.A');
INSERT INTO banco (numero, nome) VALUES(41,'BANCO DO ESTADO DO RIO GRANDE DO SUL S.A.');
INSERT INTO banco (numero, nome) VALUES(44,'BANCO BVA SA');
INSERT INTO banco (numero, nome) VALUES(45,'BANCO OPPORTUNITY S.A.');
INSERT INTO banco (numero, nome) VALUES(47,'BANCO DO ESTADO DE SERGIPE S.A.');
INSERT INTO banco (numero, nome) VALUES(62,'HIPERCARD BANCO MÚLTIPLO S.A');
INSERT INTO banco (numero, nome) VALUES(63,'BANCO IBI S.A - BANCO MULTIPLO');
INSERT INTO banco (numero, nome) VALUES(65,'LEMON BANK BANCO MÚLTIPLO S..A');
INSERT INTO banco (numero, nome) VALUES(66,'BANCO MORGAN STANLEY S.A');
INSERT INTO banco (numero, nome) VALUES(69,'BPN BRASIL BANCO MÚLTIPLO S.A.');
INSERT INTO banco (numero, nome) VALUES(70,'BRB - BANCO DE BRASILIA S.A.');
INSERT INTO banco (numero, nome) VALUES(72,'BANCO RURAL MAIS S.A.');
INSERT INTO banco (numero, nome) VALUES(73,'BB BANCO POPULAR DO BRASL S.A.');
INSERT INTO banco (numero, nome) VALUES(74,'BANCO J.SAFRA S.A.');
INSERT INTO banco (numero, nome) VALUES(75,'BANCO CR2 S.A.');
INSERT INTO banco (numero, nome) VALUES(76,'BANCO KDB DO BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(96,'BANCO BM&F DE SERVIÇOS DE LIQUIDAÇÃO E CUSTÓDIA S.A.');
INSERT INTO banco (numero, nome) VALUES(116,'BANCO ÚNICO S.A.');
INSERT INTO banco (numero, nome) VALUES(151,'BANCO NOSSA CAIXA S.A');
INSERT INTO banco (numero, nome) VALUES(175,'BANCO FINASA S.A.');
INSERT INTO banco (numero, nome) VALUES(184,'BANCO ITAÚ - BBA S.A.');
INSERT INTO banco (numero, nome) VALUES(204,'BANCO BRADESCO CARTÕES S.A');
INSERT INTO banco (numero, nome) VALUES(208,'BANCO UBS PACTUAL S.A.');
INSERT INTO banco (numero, nome) VALUES(212,'BANCO MATONE S.A.');
INSERT INTO banco (numero, nome) VALUES(213,'BANCO ARBI S.A.');
INSERT INTO banco (numero, nome) VALUES(214,'BANCO DIBENS S.A.');
INSERT INTO banco (numero, nome) VALUES(215,'BANCO ACOMERCIAL E DE INVESTIMENTO SUDAMERIS S.A.');
INSERT INTO banco (numero, nome) VALUES(217,'BANCO JOHN DEERE S.A.');
INSERT INTO banco (numero, nome) VALUES(218,'BANCO BONSUCESSO S.A.');
INSERT INTO banco (numero, nome) VALUES(222,'BANCO CLAYON BRASIL S/A');
INSERT INTO banco (numero, nome) VALUES(224,'BANCO FIBRA S.A.');
INSERT INTO banco (numero, nome) VALUES(225,'BANCO BRASCAN S.A.');
INSERT INTO banco (numero, nome) VALUES(229,'BANCO CRUZEIRO DO SUL S.A.');
INSERT INTO banco (numero, nome) VALUES(230,'UNICARD BANCO MÚLTIPLO S.A.');
INSERT INTO banco (numero, nome) VALUES(233,'BANCO GE CAPITAL S.A');
INSERT INTO banco (numero, nome) VALUES(237,'BANCO BRADESCO S.A.');
INSERT INTO banco (numero, nome) VALUES(241,'BANCO CLASSICO S.A.');
INSERT INTO banco (numero, nome) VALUES(243,'BANCO MAXIMA S.A.');
INSERT INTO banco (numero, nome) VALUES(246,'BANCO ABC-BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(248,'BANCO BOAVISTA INTERATLANTICO S.A.');
INSERT INTO banco (numero, nome) VALUES(249,'BANCO INVESTCRED UNIBANCO S.A.');
INSERT INTO banco (numero, nome) VALUES(250,'BANCO SCHAHIN');
INSERT INTO banco (numero, nome) VALUES(252,'BANCO FININVEST S.A.');
INSERT INTO banco (numero, nome) VALUES(254,'PARANÁ BANCO S.A.');
INSERT INTO banco (numero, nome) VALUES(263,'BANCO CACIQUE S.A.');
INSERT INTO banco (numero, nome) VALUES(265,'BANCO FATOR S.A.');
INSERT INTO banco (numero, nome) VALUES(266,'BANCO CEDULA S.A.');
INSERT INTO banco (numero, nome) VALUES(300,'BANCO DE LA NACION ARGENTINA');
INSERT INTO banco (numero, nome) VALUES(318,'BANCO BMG S.A.');
INSERT INTO banco (numero, nome) VALUES(320,'BANCO INDUSTRIAL E COMERCIAL S.A.');
INSERT INTO banco (numero, nome) VALUES(341,'BANCO ITAU S.A.');
INSERT INTO banco (numero, nome) VALUES(356,'BANCO ABN AMRO REAL S.A.');
INSERT INTO banco (numero, nome) VALUES(366,'BANCO SOCIETE GENERALE BRASIL S.A');
INSERT INTO banco (numero, nome) VALUES(370,'BANCO WESTLB DO BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(376,'BANCO J.P. MORGAN S.A.');
INSERT INTO banco (numero, nome) VALUES(389,'BANCO MERCANTIL DO BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(394,'BANCO BMC S.A.');
INSERT INTO banco (numero, nome) VALUES(399,'HSBC BANK BRASIL S.A.-BANCO MULTIPLO');
INSERT INTO banco (numero, nome) VALUES(409,'UNIBANCO - UNIAO DE BANCOS BRASILEIROS S.A.');
INSERT INTO banco (numero, nome) VALUES(412,'BANCO CAPITAL S.A.');
INSERT INTO banco (numero, nome) VALUES(422,'BANCO SAFRA S.A.');
INSERT INTO banco (numero, nome) VALUES(453,'BANCO RURAL S.A.');
INSERT INTO banco (numero, nome) VALUES(456,'BANCO DE TOKYO-MITSUBISHI UFJ BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(464,'BANCO SUMITOMO MITSUI BRASILEIRO S.A.');
INSERT INTO banco (numero, nome) VALUES(477,'CITIBANK N.A.');
INSERT INTO banco (numero, nome) VALUES(479,'BANCO ITAUBANK S.A.');
INSERT INTO banco (numero, nome) VALUES(487,'DEUTSCHE BANK S. A. - BANCO ALEMAO');
INSERT INTO banco (numero, nome) VALUES(488,'"JPMORGAN CHASE BANK, NATIONAL ASSOCIATION"');
INSERT INTO banco (numero, nome) VALUES(492,'ING BANK N.V.');
INSERT INTO banco (numero, nome) VALUES(494,'BANCO DE LA REPUBLICA ORIENTAL DEL URUGUAY');
INSERT INTO banco (numero, nome) VALUES(495,'BANCO DE LA PROVINCIA DE BUENOS AIRES');
INSERT INTO banco (numero, nome) VALUES(505,'BANCO CREDIT SUISSE (BRASIL) S.A.');
INSERT INTO banco (numero, nome) VALUES(600,'BANCO LUSO BRASILEIRO S.A.');
INSERT INTO banco (numero, nome) VALUES(604,'BANCO INDUSTRIAL DO BRASIL S. A.');
INSERT INTO banco (numero, nome) VALUES(610,'BANCO VR S.A.');
INSERT INTO banco (numero, nome) VALUES(611,'BANCO PAULISTA S.A.');
INSERT INTO banco (numero, nome) VALUES(612,'BANCO GUANABARA S.A.');
INSERT INTO banco (numero, nome) VALUES(613,'BANCO PECUNIA S.A.');
INSERT INTO banco (numero, nome) VALUES(623,'BANCO PANAMERICANO S.A.');
INSERT INTO banco (numero, nome) VALUES(626,'BANCO FICSA S.A.');
INSERT INTO banco (numero, nome) VALUES(630,'BANCO INTERCAP S.A.');
INSERT INTO banco (numero, nome) VALUES(633,'BANCO RENDIMENTO S.A.');
INSERT INTO banco (numero, nome) VALUES(634,'BANCO TRIANGULO S.A.');
INSERT INTO banco (numero, nome) VALUES(637,'BANCO SOFISA S.A.');
INSERT INTO banco (numero, nome) VALUES(638,'BANCO PROSPER S.A.');
INSERT INTO banco (numero, nome) VALUES(641,'BANCO ALVORADA S.A');
INSERT INTO banco (numero, nome) VALUES(643,'BANCO PINE S.A.');
INSERT INTO banco (numero, nome) VALUES(652,'BANCO ITAÚ HOLDING FINANCEIRA S.A.');
INSERT INTO banco (numero, nome) VALUES(653,'BANCO INDUSVAL S.A.');
INSERT INTO banco (numero, nome) VALUES(654,'BANCO A.J. RENNER S.A.');
INSERT INTO banco (numero, nome) VALUES(655,'BANCO VOTORANTIM S.A.');
INSERT INTO banco (numero, nome) VALUES(707,'BANCO DAYCOVAL S.A.');
INSERT INTO banco (numero, nome) VALUES(719,'"BANIF - BANCO INTERNACIONAL DO FUNCHAL (BRASIL), S.A."');
INSERT INTO banco (numero, nome) VALUES(721,'BANCO CREDIBEL S.A.');
INSERT INTO banco (numero, nome) VALUES(734,'BANCO GERDAU S.A.');
INSERT INTO banco (numero, nome) VALUES(735,'BANCO POTTENCIAL S.A.');
INSERT INTO banco (numero, nome) VALUES(738,'BANCO MORADA S.A.');
INSERT INTO banco (numero, nome) VALUES(739,'BANCO BGN S.A.');
INSERT INTO banco (numero, nome) VALUES(740,'BANCO BARCLAYS S.A.');
INSERT INTO banco (numero, nome) VALUES(741,'BANCO RIBEIRAO PRETO S.A.');
INSERT INTO banco (numero, nome) VALUES(743,'BANCO SEMEAR S.A.');
INSERT INTO banco (numero, nome) VALUES(744,'BANKBOSTON N.A.');
INSERT INTO banco (numero, nome) VALUES(745,'BANCO CITIBANK S.A.');
INSERT INTO banco (numero, nome) VALUES(746,'BANCO MODAL S.A.');
INSERT INTO banco (numero, nome) VALUES(747,'BANCO RABOBANK INTERNATIONAL BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(748,'BANCO COOPERATIVO SICREDI S.A.');
INSERT INTO banco (numero, nome) VALUES(749,'BANCO SIMPLES S.A.');
INSERT INTO banco (numero, nome) VALUES(751,'DRESDNER BANK BRASIL S.A. BANCO MULTIPLO.');
INSERT INTO banco (numero, nome) VALUES(752,'BANCO BNP PARIBAS BRASIL S.A.');
INSERT INTO banco (numero, nome) VALUES(753,'BANCO COMERCIAL URUGUAI S.A.');
INSERT INTO banco (numero, nome) VALUES(756,'BANCO COOPERATIVO DO BRASIL S.A. - BANCOOB');
INSERT INTO banco (numero, nome) VALUES(757,'BANCO KEB DO BRASIL S.A.');

/***** 002-planoConta *****/

INSERT INTO plano_conta (idPlanoConta, tipo, codigo, nome, grupo)
values(1, 0, 1, 'Receita', null);

INSERT INTO plano_conta (idPlanoConta, tipo, codigo, nome, grupo)
values(2, 1, 2, 'Despesa', null);

/***** 003-documento *****/

INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(1,'Outros');
INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(2,'Boleto');
INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(3,'Carnê');
INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(4,'Cheque');
INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(5,'DARF');
INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(6,'Fatura / Duplicata');
INSERT INTO tipo_documento (idTipoDocumento, descricao) VALUES(7,'Nota Promissória');

/***** 004-formaPagamento *****/

INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(1,'Outros');
INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(2,'Dinheiro');
INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(3,'Cheque');
INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(4,'Cartão Crédito');
INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(5,'Cartão Débito');
INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(6,'Depósito Bancário');
INSERT INTO tipo_forma_pagamento (idTipoFormaPagamento, descricao) VALUES(7,'Débito Conta');

/***** 005-lancamento *****/

ALTER TABLE lancamento CHANGE `data` `dataEmissao` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `dataVencimento` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `dataLancamento` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `dataCompensacao` DATETIME NULL;
ALTER TABLE lancamento ADD `modelo` INT(11) NOT NULL;
ALTER TABLE lancamento ADD `idLancamentoProgramado` INT NULL;
ALTER TABLE lancamento ADD `competencia` DATETIME NOT NULL;
ALTER TABLE lancamento ADD `observacao` LONGTEXT DEFAULT NULL;
ALTER TABLE lancamento ADD `valorDesconto` DECIMAL(13,2) NOT NULL;
ALTER TABLE lancamento ADD `valorJuros` DECIMAL(13,2) NOT NULL;
ALTER TABLE lancamento ADD `valorMulta` DECIMAL(13,2) NOT NULL;
ALTER TABLE lancamento ADD `numeroParcela` INT NULL;

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_lancamentoprogramado` (`idLancamentoProgramado` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_lancamentoprogramado`
  FOREIGN KEY (`idLancamentoProgramado`)
  REFERENCES `lancamento_programado` (`idLancamentoProgramado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 006-lancamentoTransferencia *****/

ALTER TABLE lancamento_transferencia ADD `numero` VARCHAR(254) NULL;
ALTER TABLE lancamento_transferencia ADD `competencia` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia ADD `dataEmissao` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia ADD `dataLancamento` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia ADD `valor` DECIMAL(13,2) NOT NULL;
ALTER TABLE lancamento_transferencia ADD `historico` VARCHAR(254) NOT NULL;

ALTER TABLE `lancamento_transferencia` 
ADD INDEX `i_lancamentotransferencia_lancamentoorigem` (`idLancamentoOrigem` ASC);

ALTER TABLE `lancamento_transferencia` 
ADD INDEX `i_lancamentotransferencia_lancamentodestino` (`idLancamentoDestino` ASC);

/***** 007-lancamentoConciliado *****/

ALTER TABLE lancamento ADD `numeroLoteConciliado` INT NULL;
ALTER TABLE lancamento ADD `autenticacao` VARCHAR(254) DEFAULT NULL;
ALTER TABLE lancamento_programado CHANGE `idPlanoConta` `idPlanoConta` INT NOT NULL;

/***** 008-limiteContaCorrente *****/

ALTER TABLE conta_corrente ADD `limite` DECIMAL(13,2) NOT NULL DEFAULT 0;
ALTER TABLE conta_corrente ADD `agencia_dv` TINYINT (2) DEFAULT 0;
ALTER TABLE conta_corrente ADD `contaCorrente_dv` TINYINT (2) DEFAULT 0;

/***** 009-competencia *****/

ALTER TABLE lancamento CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;
ALTER TABLE lancamento_programado CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;
ALTER TABLE lancamento_transferencia CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;
ALTER TABLE lancamento_conciliado CHANGE `competencia` `dataCompetencia` DATETIME NOT NULL;

/***** 010-observacaoTransferencia *****/

ALTER TABLE lancamento_transferencia ADD `observacao` LONGTEXT DEFAULT NULL;

/***** 011-lancamentoConciliadoNumeroLote *****/

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_numerolote` (`numeroLote` ASC);

ALTER TABLE `lancamento` 
ADD INDEX `i_lancamento_lancamentoconciliado` (`numeroLoteConciliado` ASC);

ALTER TABLE `lancamento` 
ADD CONSTRAINT `fk_lancamento_lancamentoconciliado`
  FOREIGN KEY (`numeroLoteConciliado`)
  REFERENCES `lancamento_conciliado` (`numeroLote`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 012-usuarioLancamento *****/

ALTER TABLE lancamento ADD `usuario` VARCHAR(100) DEFAULT NULL;
ALTER TABLE lancamento_conciliado ADD `usuario` VARCHAR(100) DEFAULT NULL;
ALTER TABLE lancamento_programado ADD `usuario` VARCHAR(100) DEFAULT NULL;
ALTER TABLE lancamento_transferencia ADD `usuario` VARCHAR(100) DEFAULT NULL;

/***** 013-centroCustoConciliacao *****/

ALTER TABLE `lancamento_conciliado` ADD `idCentroCusto` INT NULL;

ALTER TABLE `lancamento_conciliado` 
ADD INDEX `i_lancamentoconciliado_centrocusto` (`idCentroCusto` ASC);

ALTER TABLE `lancamento_conciliado` 
ADD CONSTRAINT `fk_lancamentoconciliado_centrocusto`
  FOREIGN KEY (`idCentroCusto`)
  REFERENCES `centro_custo` (`idCentroCusto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/***** 014-planoContaConciliacao *****/

ALTER TABLE lancamento_conciliado CHANGE `idPlanoConta` `idPlanoConta` INT NULL;
ALTER TABLE lancamento_programado CHANGE `idPlanoConta` `idPlanoConta` INT NULL;
ALTER TABLE lancamento CHANGE `valorDesconto` `valorDesconto` DECIMAL(13,2) NULL;
ALTER TABLE lancamento CHANGE `valorJuros` `valorJuros` DECIMAL(13,2) NULL;
ALTER TABLE lancamento CHANGE `valorMulta` `valorMulta` DECIMAL(13,2) NULL;

/***** 015-lancamentoConciliadoLancamento *****/

ALTER TABLE lancamento_conciliado CHANGE `idLancamento` `idLancamento` INT NULL;
ALTER TABLE lancamento_conciliado CHANGE `tipo` `tipo` INT NULL;

/***** 016-CartaoCredito *****/

ALTER TABLE cartao_credito ADD `numero` VARCHAR(16) DEFAULT NULL;
ALTER TABLE cartao_credito ADD `codigoSeguranca` VARCHAR(4) DEFAULT NULL;
ALTER TABLE cartao_credito ADD `nomeTitular` VARCHAR(254) DEFAULT NULL;

/***** 017-numeroLoteConciliado *****/

ALTER TABLE `lancamento` 
DROP FOREIGN KEY `fk_lancamento_lancamentoconciliado`;

/***** 018-lancamentoProgramadoObservacao *****/

ALTER TABLE lancamento_programado ADD `observacao` LONGTEXT DEFAULT NULL;
