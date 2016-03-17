USE pp_06895434000183;

DROP TABLE veiculo_sinistro;
DROP TABLE veiculo_seguro;
DROP TABLE veiculo_multa;
DROP TABLE veiculo_manutencao;
DROP TABLE veiculo_combustivel;
DROP TABLE veiculo;

CREATE TABLE `veiculo` (
  `idVeiculo`       INT(11) NOT NULL AUTO_INCREMENT,
  `tipo`            INT(11) NOT NULL,
  `idMarca`         INT(11) NOT NULL,
  `marca`           VARCHAR(254) NOT NULL,
  `idModelo`        INT(11) NOT NULL,
  `modelo`          VARCHAR(254) NOT NULL,
  `idVersao`        VARCHAR(100) NOT NULL,
  `versao`          VARCHAR(254) NOT NULL,
  `placa`           VARCHAR(7) NOT NULL,
  `anoModelo`       INT(11) DEFAULT NULL,
  `chassis`         VARCHAR(17) DEFAULT NULL,
  `renavam`         VARCHAR(11) NOT NULL,
  `quilometragem`   INT(11) NOT NULL,
  `combustivel`     INT(11) NOT NULL,
  `status`          INT(11) NOT NULL,
  `situacao`        INT(11) NOT NULL,
  `dataCadastro`    DATETIME NOT NULL,
  PRIMARY KEY (`idVeiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `veiculo_combustivel` (
  `idVeiculoCombustivel` INT(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo`            INT(11) NOT NULL,
  `tipo`                 VARCHAR(45) NOT NULL,
  `quantidade`           INT(11) NOT NULL,
  `valorUnitario`        DECIMAL(13,2) NOT NULL,
  `data` DATETIME        NOT NULL,
  `valorTotal`           DECIMAL(13,2) NOT NULL,
  `quilometragem`        INT(11) NOT NULL,
  PRIMARY KEY (`idVeiculoCombustivel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `veiculo_combustivel` 
ADD INDEX `i_veiculocombustivel_veiculo` (`idVeiculo` ASC);

ALTER TABLE `veiculo_combustivel` 
ADD CONSTRAINT `fk_veiculocombustivel_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `veiculo_manutencao` (
  `idVeiculoManutencao` INT(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo`           INT(11) NOT NULL,
  `tipo`                INT(11) NOT NULL,
  `quilometragem`       INT(11) NOT NULL,
  `valor`               DECIMAL(13,2) NOT NULL,
  `dataManutencao`      DATETIME NOT NULL,
  `dataAgendamento`     DATETIME DEFAULT NULL,
  `descricao`           LONGTEXT  DEFAULT NULL,
  PRIMARY KEY (`idVeiculoManutencao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `veiculo_manutencao` 
ADD INDEX `i_veiculomanutencao_veiculo` (`idVeiculo` ASC);

ALTER TABLE `veiculo_manutencao` 
ADD CONSTRAINT `fk_veiculomanutencao_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `veiculo_multa` (
  `idVeiculoMulta`  INT(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo`       INT(11) NOT NULL,
  `condutor`        VARCHAR(254) NOT NULL,
  `numero`          INT(15) NOT NULL,
  `valor`           DECIMAL(13,2) NOT NULL,
  `data`            DATETIME NOT NULL,
  `descontada`      TINYINT(1) NOT NULL,
  `local`           LONGTEXT DEFAULT NULL,
  `descricao`       LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`idVeiculoMulta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `veiculo_multa` 
ADD INDEX `i_veiculomulta_veiculo` (`idVeiculo` ASC);

ALTER TABLE `veiculo_multa` 
ADD CONSTRAINT `fk_veiculomulta_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `veiculo_seguro` (
  `idVeiculoSeguro`     INT(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo`           INT(11) NOT NULL,
  `numeroApolice`       INT(11) NOT NULL,
  `corretora`           VARCHAR(254) NOT NULL,
  `assegurado`          VARCHAR(150) NOT NULL,
  `valorFranquia`       DECIMAL(13,2) NOT NULL,
  `indenizacao`         VARCHAR(45) NOT NULL,
  `dataInicioVigencia`  DATETIME NOT NULL,
  `dataFimVigencia`     DATETIME NOT NULL,
  PRIMARY KEY (`idVeiculoSeguro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `veiculo_seguro` 
ADD INDEX `i_veiculoseguro_veiculo` (`idVeiculo` ASC);

ALTER TABLE `veiculo_seguro` 
ADD CONSTRAINT `fk_veiculoseguro_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `veiculo_sinistro` (
  `idVeiculoSinistro` INT(11) NOT NULL AUTO_INCREMENT,
  `idVeiculo`         INT(11) NOT NULL,
  `tipo`              VARCHAR(45) NOT NULL,
  `boletimOcorrencia` INT(11) NOT NULL,
  `data` DATETIME     NOT NULL,
  `responsavel`       VARCHAR(45) NOT NULL,
  `local`             VARCHAR(255) DEFAULT NULL,
  `descricao`         LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`idVeiculoSinistro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `veiculo_sinistro` 
ADD INDEX `i_veiculosinistro_veiculo` (`idVeiculo` ASC);

ALTER TABLE `veiculo_sinistro` 
ADD CONSTRAINT `fk_veiculosinistro_veiculo`
  FOREIGN KEY (`idVeiculo`)
  REFERENCES `veiculo` (`idVeiculo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;