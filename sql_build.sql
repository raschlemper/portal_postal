USE pp_06895434000183;

CREATE TABLE `veiculos` (
  `idVeiculo` int(11) NOT NULL AUTO_INCREMENT,
  `marca` varchar(45) NOT NULL,
  `modelo` varchar(45) NOT NULL,
  `placa` varchar(45) NOT NULL,
  `anoFabricacao` int(11) DEFAULT NULL,
  `anoModelo` int(11) DEFAULT NULL,
  `chassis` varchar(45) DEFAULT NULL,
  `renavam` varchar(45) NOT NULL,
  `quilometragem` int(11) NOT NULL,
  `combustivel` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `situacao` varchar(45) NOT NULL,
  PRIMARY KEY (`idVeiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

