DELETE FROM conta WHERE codigoIntegracao IN(1,2);
ALTER TABLE `conta` DROP codigoIntegracao;