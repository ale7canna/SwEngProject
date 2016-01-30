CREATE DATABASE  IF NOT EXISTS `swengproj` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `swengproj`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: swengproj
-- ------------------------------------------------------
-- Server version	5.7.10-log

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
-- Table structure for table `amicizia`
--

DROP TABLE IF EXISTS `amicizia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amicizia` (
  `idUtente1` int(11) NOT NULL,
  `idUtente2` int(11) NOT NULL,
  PRIMARY KEY (`idUtente1`,`idUtente2`),
  KEY `idUtente2_idx` (`idUtente2`),
  CONSTRAINT `idUtente1` FOREIGN KEY (`idUtente1`) REFERENCES `utente` (`idUtente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idUtente2` FOREIGN KEY (`idUtente2`) REFERENCES `utente` (`idUtente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attivita`
--

DROP TABLE IF EXISTS `attivita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attivita` (
  `idAttivita` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Luogo` varchar(45) DEFAULT NULL,
  `Ora` datetime DEFAULT NULL,
  `idProgetto` int(11) NOT NULL,
  `Completata` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idAttivita`),
  KEY `idProgetto_idx` (`idProgetto`),
  CONSTRAINT `idProgetto` FOREIGN KEY (`idProgetto`) REFERENCES `progetto` (`idProgetto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notifica`
--

DROP TABLE IF EXISTS `notifica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifica` blob NOT NULL,
  `notifica_class` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notifica_utente`
--

DROP TABLE IF EXISTS `notifica_utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifica_utente` (
  `id_utente` int(11) NOT NULL,
  `id_notifica` int(11) NOT NULL,
  PRIMARY KEY (`id_utente`,`id_notifica`),
  KEY `notifica_idx` (`id_notifica`),
  CONSTRAINT `notifica` FOREIGN KEY (`id_notifica`) REFERENCES `notifica` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`idUtente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partecipante`
--

DROP TABLE IF EXISTS `partecipante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partecipante` (
  `idUtente` int(11) NOT NULL,
  `idProgetto` int(11) NOT NULL,
  PRIMARY KEY (`idUtente`,`idProgetto`),
  KEY `idProgetto_idx` (`idProgetto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `progetto`
--

DROP TABLE IF EXISTS `progetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `progetto` (
  `idProgetto` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `idAdmin` int(11) NOT NULL,
  `Attivo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`idProgetto`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='			';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `responsabile_attivita`
--

DROP TABLE IF EXISTS `responsabile_attivita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `responsabile_attivita` (
  `idUtente` int(11) NOT NULL,
  `idAttivita` int(11) NOT NULL,
  PRIMARY KEY (`idUtente`,`idAttivita`),
  KEY `idAttivita_idx` (`idAttivita`),
  CONSTRAINT `idAttivita` FOREIGN KEY (`idAttivita`) REFERENCES `attivita` (`idAttivita`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idUtente` FOREIGN KEY (`idUtente`) REFERENCES `utente` (`idUtente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `idUtente` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `UserName` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Cognome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUtente`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente_connesso`
--

DROP TABLE IF EXISTS `utente_connesso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente_connesso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utente` blob NOT NULL,
  `connessione` datetime DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `id_utente` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-30 17:44:41
