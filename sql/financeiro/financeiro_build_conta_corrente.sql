
USE pp_06895434000183;

DROP TABLE banco;
DROP TABLE conta_corrente;

CREATE TABLE `banco` (
  `idBanco` INT NOT NULL AUTO_INCREMENT,
  `nome`    VARCHAR(254) NOT NULL,
  `numero`  INT NOT NULL,
  `website` VARCHAR(254) DEFAULT NULL,
  PRIMARY KEY (`idbanco`),
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