ALTER TABLE `veiculo_manutencao` CHANGE COLUMN `quilometragem` `quilometragem` INT(11) NOT NULL;
ALTER TABLE `veiculo_manutencao` CHANGE COLUMN `valor` `valor` DECIMAL(13,2) NOT NULL;
ALTER TABLE `veiculo_manutencao` CHANGE COLUMN `dataManutencao` `dataManutencao` datetime NOT NULL;
