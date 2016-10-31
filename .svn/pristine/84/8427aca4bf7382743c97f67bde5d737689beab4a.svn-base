-- MySQL dump 10.13  Distrib 5.1.73, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: pp_06895434000183
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `plano_conta_padrao`
--

--DROP TABLE IF EXISTS `plano_conta_padrao`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plano_conta_padrao` (
  `idPlanoConta` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL,
  `codigo` int(11) NOT NULL,
  `nome` varchar(254) NOT NULL,
  `grupo` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPlanoConta`),
  UNIQUE KEY `u_planocontapadrao` (`tipo`,`codigo`,`grupo`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plano_conta_padrao`
--

LOCK TABLES `plano_conta_padrao` WRITE;
/*!40000 ALTER TABLE `plano_conta_padrao` DISABLE KEYS */;
INSERT INTO `plano_conta_padrao` VALUES (1,0,1,'Receita',NULL),(2,1,2,'Despesa',NULL),(3,1,1,'Despesa Operacional',2),(4,1,2,'Despesa Não Operacional',2),(5,0,1,'Receita Operacional',1),(6,0,2,'Receita Não Operacional',1),(7,1,1,'Despesa Administrativa',3),(8,1,2,'Despesa com Pessoal',3),(9,1,3,'Despesa com Coleta',3),(10,1,4,'Despesa Financeira',3),(11,1,5,'Despesa com Impostos',3),(12,1,1,'Despesa com Motos',9),(13,1,2,'Despesa com Carros',9),(14,1,1,'Financiamento',12),(15,1,2,'Licenciamento',12),(16,1,3,'Manutenção',12),(17,1,4,'Multa',12),(18,1,5,'Rastreamento',12),(19,1,1,'Financiamento',13),(20,1,2,'Licenciamento',13),(21,1,3,'Manutenção',13),(22,1,4,'Multa',13),(23,1,5,'Rastreamento',13),(24,1,6,'Seguro',13),(25,1,7,'Estacionamento',13),(26,1,8,'Locação',13),(27,1,9,'Combustíveis e Lubrificantes',13),(28,1,6,'Combustíveis e Lubrificantes',12),(29,1,1,'Tarifa Bancária',10),(30,1,2,'Juros',10),(31,1,1,'Simples Nacional',11),(32,1,2,'Outros Impostos',11),(33,1,1,'Salário',8),(34,1,2,'13 Salário',8),(35,1,3,'FGTS',8),(36,1,4,'V.A.',8),(37,1,5,'V.T.',8),(38,1,6,'Uniforme',8),(39,1,1,'Aluguel',7),(40,1,2,'Contabilidade',7);
/*!40000 ALTER TABLE `plano_conta_padrao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-29 10:49:08
