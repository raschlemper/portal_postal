USE pp_06895434000183;

DROP TABLE veiculo_sinistro;
DROP TABLE veiculo_seguro;
DROP TABLE veiculo_multa;
DROP TABLE veiculo_manutencao;
DROP TABLE veiculo_combustivel;
DROP TABLE veiculo;

CREATE TABLE `veiculo` (
  `idVeiculo` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL,
  `idMarca` int(11) NOT NULL,
  `marca` varchar(254) NOT NULL,
  `idModelo` int(11) NOT NULL,
  `modelo` varchar(254) NOT NULL,
  `idVersao` varchar(100) NOT NULL,
  `versao` varchar(254) NOT NULL,
  `placa` varchar(7) NOT NULL,
  `anoModelo` int(11) DEFAULT NULL,
  `chassis` varchar(17) DEFAULT NULL,
  `renavam` varchar(11) NOT NULL,
  `quilometragem` int(11) NOT NULL,
  `combustivel` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `situacao` int(11) NOT NULL,
  `dataCadastro` datetime NOT NULL,
  PRIMARY KEY (`idVeiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `veiculo_combustivel` (
  `idVeiculoCombustivel` int(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo` int(11) NOT NULL,
  -- `idMotorista` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `valorUnitario` decimal(13,2) NOT NULL,
  `data` datetime NOT NULL,
  -- `media` int(11) NOT NULL,
  `valorTotal` decimal(13,2) NOT NULL,
  `quilometragem` int(11) NOT NULL,
  PRIMARY KEY (`idVeiculoCombustivel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pp_06895434000183`.`veiculo_combustivel` 
ADD INDEX `i_veiculocombustivel_veiculo` (`idVeiculo` ASC);

ALTER TABLE `pp_06895434000183`.`veiculo_combustivel` 
ADD CONSTRAINT `fk_veiculocombustivel_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `pp_06895434000183`.`veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- ALTER TABLE `pp_06895434000183`.`veiculo_combustivel` 
-- ADD INDEX `i_veiculocombustivel_coletacoletador` (`idMotorista` ASC);

-- ALTER TABLE `pp_06895434000183`.`veiculo_combustivel` 
-- ADD CONSTRAINT `fk_veiculocombustivel_coletacoletador`
--   FOREIGN KEY (`idMotorista`)
--   REFERENCES `pp_06895434000183`.`coleta_coletador` (`idColetador`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;  

CREATE TABLE `pp_06895434000183`.`veiculo_manutencao` (
  `idVeiculoManutencao` int(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `quilometragem` int(11) NOT NULL,
  `valor` decimal(13,2) NOT NULL,
  `dataManutencao` datetime NOT NULL,
  `dataAgendamento` datetime DEFAULT NULL,
  `descricao` longtext DEFAULT NULL,
  PRIMARY KEY (`idVeiculoManutencao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pp_06895434000183`.`veiculo_manutencao` 
ADD INDEX `i_veiculomanutencao_veiculo` (`idVeiculo` ASC);

ALTER TABLE `pp_06895434000183`.`veiculo_manutencao` 
ADD CONSTRAINT `fk_veiculomanutencao_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `pp_06895434000183`.`veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `veiculo_multa` (
  `idVeiculoMulta` int(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo` int(11) NOT NULL,
  -- `idMotorista` int(11) NOT NULL,
  `condutor` varchar(254) NOT NULL,
  `numero` int(15) NOT NULL,
  `valor` decimal(13,2) NOT NULL,
  `data` datetime NOT NULL,
  `descontada` TINYINT(1) NOT NULL,
  `local` longtext DEFAULT NULL,
  `descricao` longtext DEFAULT NULL,
  PRIMARY KEY (`idVeiculoMulta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pp_06895434000183`.`veiculo_multa` 
ADD INDEX `i_veiculomulta_veiculo` (`idVeiculo` ASC);

ALTER TABLE `pp_06895434000183`.`veiculo_multa` 
ADD CONSTRAINT `fk_veiculomulta_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `pp_06895434000183`.`veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- ALTER TABLE `pp_06895434000183`.`veiculo_multa` 
-- ADD INDEX `i_veiculomulta_coletacoletador` (`idMotorista` ASC);

-- ALTER TABLE `pp_06895434000183`.`veiculo_multa` 
-- ADD CONSTRAINT `fk_veiculomulta_coletacoletador`
--   FOREIGN KEY (`idMotorista`)
--   REFERENCES `pp_06895434000183`.`coleta_coletador` (`idColetador`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;   

CREATE TABLE `veiculo_seguro` (
  `idVeiculoSeguro` int(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo` int(11) NOT NULL,
  `numeroApolice` int(11) NOT NULL,
  `corretora` varchar(254) NOT NULL,
  `assegurado` varchar(150) NOT NULL,
  `valorFranquia` decimal(13,2) NOT NULL,
  `indenizacao` varchar(45) NOT NULL,
  `dataInicioVigencia` datetime NOT NULL,
  `dataFimVigencia` datetime NOT NULL,
  PRIMARY KEY (`idVeiculoSeguro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pp_06895434000183`.`veiculo_seguro` 
ADD INDEX `i_veiculoseguro_veiculo` (`idVeiculo` ASC);

ALTER TABLE `pp_06895434000183`.`veiculo_seguro` 
ADD CONSTRAINT `fk_veiculoseguro_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `pp_06895434000183`.`veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `veiculo_sinistro` (
  `idVeiculoSinistro` int(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo` int(11) NOT NULL,
  -- `idMotorista` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `boletimOcorrencia` int(11) NOT NULL,
  `data` datetime NOT NULL,
  `responsavel` varchar(45) NOT NULL,
  `local` varchar(255) DEFAULT NULL,
  `descricao` longtext DEFAULT NULL,
  PRIMARY KEY (`idVeiculoSinistro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pp_06895434000183`.`veiculo_sinistro` 
ADD INDEX `i_veiculosinistro_veiculo` (`idVeiculo` ASC);

ALTER TABLE `pp_06895434000183`.`veiculo_sinistro` 
ADD CONSTRAINT `fk_veiculosinistro_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `pp_06895434000183`.`veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- ALTER TABLE `pp_06895434000183`.`veiculo_sinistro` 
-- ADD INDEX `i_veiculosinistro_coletacoletador` (`idMotorista` ASC);

-- ALTER TABLE `pp_06895434000183`.`veiculo_sinistro` 
-- ADD CONSTRAINT `fk_veiculosinistro_coletacoletador`
--   FOREIGN KEY (`idMotorista`)
--   REFERENCES `pp_06895434000183`.`coleta_coletador` (`idColetador`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION; 











