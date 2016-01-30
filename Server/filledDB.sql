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
-- Dumping data for table `amicizia`
--

LOCK TABLES `amicizia` WRITE;
/*!40000 ALTER TABLE `amicizia` DISABLE KEYS */;
INSERT INTO `amicizia` VALUES (9,8),(8,9),(8,10),(9,10),(8,11),(9,11),(8,12),(9,12),(8,13),(9,13);
/*!40000 ALTER TABLE `amicizia` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attivita`
--

LOCK TABLES `attivita` WRITE;
/*!40000 ALTER TABLE `attivita` DISABLE KEYS */;
INSERT INTO `attivita` VALUES (26,'Spesa','Esselunga','2016-02-03 13:12:22',42,'\0'),(27,'DJ set','','2016-02-03 22:13:18',42,'\0'),(28,'Spesa alcolica','','2016-01-30 20:14:00',42,'\0'),(29,'Spesa droghe','','2016-01-30 18:14:24',42,'\0'),(30,'Spesa griglia','SLunga','2016-01-30 18:42:27',43,'\0');
/*!40000 ALTER TABLE `attivita` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifica`
--

LOCK TABLES `notifica` WRITE;
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` VALUES (7,'¨\Ì\0sr\0.com.sweng.common.notice.UnlockedActivityNotice\0\0\0\0\0\0\0\0L\0activityt\0!Lcom/sweng/common/beans/Activity;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rìáwâxt\0ELa tua attivit√† √® stata sbloccata ed √® pronta per essere eseguita.t\0Attivit√† sbloccatasr\0com.sweng.common.beans.Activity\‘Û<ôôHô\«\0I\0\nidActivityI\0	idProjectZ\0isDoneZ\0isFinishableL\0hourq\0~\0L\0nameq\0~\0L\0placeq\0~\0xp\0\0\0\Z\0\0\0*\0\0sq\0~\0w\0\0Rß8pxt\0Spesat\0	Esselunga','com.sweng.common.notice.UnlockedActivityNotice'),(8,'¨\Ì\0sr\0.com.sweng.common.notice.UnlockedActivityNotice\0\0\0\0\0\0\0\0L\0activityt\0!Lcom/sweng/common/beans/Activity;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rìáwâxt\0ELa tua attivit√† √® stata sbloccata ed √® pronta per essere eseguita.t\0Attivit√† sbloccatasr\0com.sweng.common.beans.Activity\‘Û<ôôHô\«\0I\0\nidActivityI\0	idProjectZ\0isDoneZ\0isFinishableL\0hourq\0~\0L\0nameq\0~\0L\0placeq\0~\0xp\0\0\0\Z\0\0\0*\0\0sq\0~\0w\0\0Rß8pxt\0Spesat\0	Esselunga','com.sweng.common.notice.UnlockedActivityNotice'),(9,'¨\Ì\0sr\0)com.sweng.common.notice.StartedProjNotice\0\0\0\0\0\0\0\0L\0projectInfot\0$Lcom/sweng/common/beans/ProjectInfo;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rì°Cxt\0JSei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli.t\0Aggiunta ad un progettosr\0\"com.sweng.common.beans.ProjectInfoFôF\›\Ê\0F\0completetionPercentageI\0	idProjectZ\0isActiveL\0activitiesInResponsiblet\0Ljava/util/HashMap;L\0admint\0Lcom/sweng/common/beans/User;L\0nameq\0~\0L\0participantst\0Ljava/util/ArrayList;xp¿\0\0\0\0\0+\0sr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0com.sweng.common.beans.UserÅP|cXYp\Ã\0I\0idUserL\0nameq\0~\0L\0passwordq\0~\0L\0surnameq\0~\0L\0usernameq\0~\0xp\0\0\0t\0\nAlessandropt\0	Canicattit\0	ale7cannasq\0~\0\0\0\0	t\0Giorgiopt\0	Marzoratit\0	johnmarzosq\0~\0\0\0\0\nt\0Andreapt\0Vaghit\0wegosq\0~\0\0\0\0t\0Anielpt\0Rossit\0syndromesq\0~\0\0\0\0t\0Lorenzopt\0Randazzot\0randasq\0~\0\0\0\0\rt\0Andreapt\0Spezialet\0spezzax','com.sweng.common.notice.StartedProjNotice'),(10,'¨\Ì\0sr\0)com.sweng.common.notice.StartedProjNotice\0\0\0\0\0\0\0\0L\0projectInfot\0$Lcom/sweng/common/beans/ProjectInfo;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rì°Cxt\0JSei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli.t\0Aggiunta ad un progettosr\0\"com.sweng.common.beans.ProjectInfoFôF\›\Ê\0F\0completetionPercentageI\0	idProjectZ\0isActiveL\0activitiesInResponsiblet\0Ljava/util/HashMap;L\0admint\0Lcom/sweng/common/beans/User;L\0nameq\0~\0L\0participantst\0Ljava/util/ArrayList;xp¿\0\0\0\0\0+\0sr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0com.sweng.common.beans.UserÅP|cXYp\Ã\0I\0idUserL\0nameq\0~\0L\0passwordq\0~\0L\0surnameq\0~\0L\0usernameq\0~\0xp\0\0\0t\0\nAlessandropt\0	Canicattit\0	ale7cannasq\0~\0\0\0\0	t\0Giorgiopt\0	Marzoratit\0	johnmarzosq\0~\0\0\0\0\nt\0Andreapt\0Vaghit\0wegosq\0~\0\0\0\0t\0Anielpt\0Rossit\0syndromesq\0~\0\0\0\0t\0Lorenzopt\0Randazzot\0randasq\0~\0\0\0\0\rt\0Andreapt\0Spezialet\0spezzax','com.sweng.common.notice.StartedProjNotice'),(11,'¨\Ì\0sr\0)com.sweng.common.notice.StartedProjNotice\0\0\0\0\0\0\0\0L\0projectInfot\0$Lcom/sweng/common/beans/ProjectInfo;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rì°Cxt\0JSei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli.t\0Aggiunta ad un progettosr\0\"com.sweng.common.beans.ProjectInfoFôF\›\Ê\0F\0completetionPercentageI\0	idProjectZ\0isActiveL\0activitiesInResponsiblet\0Ljava/util/HashMap;L\0admint\0Lcom/sweng/common/beans/User;L\0nameq\0~\0L\0participantst\0Ljava/util/ArrayList;xp¿\0\0\0\0\0+\0sr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0com.sweng.common.beans.UserÅP|cXYp\Ã\0I\0idUserL\0nameq\0~\0L\0passwordq\0~\0L\0surnameq\0~\0L\0usernameq\0~\0xp\0\0\0t\0\nAlessandropt\0	Canicattit\0	ale7cannasq\0~\0\0\0\0	t\0Giorgiopt\0	Marzoratit\0	johnmarzosq\0~\0\0\0\0\nt\0Andreapt\0Vaghit\0wegosq\0~\0\0\0\0t\0Anielpt\0Rossit\0syndromesq\0~\0\0\0\0t\0Lorenzopt\0Randazzot\0randasq\0~\0\0\0\0\rt\0Andreapt\0Spezialet\0spezzax','com.sweng.common.notice.StartedProjNotice'),(12,'¨\Ì\0sr\0)com.sweng.common.notice.StartedProjNotice\0\0\0\0\0\0\0\0L\0projectInfot\0$Lcom/sweng/common/beans/ProjectInfo;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rì°Cxt\0JSei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli.t\0Aggiunta ad un progettosr\0\"com.sweng.common.beans.ProjectInfoFôF\›\Ê\0F\0completetionPercentageI\0	idProjectZ\0isActiveL\0activitiesInResponsiblet\0Ljava/util/HashMap;L\0admint\0Lcom/sweng/common/beans/User;L\0nameq\0~\0L\0participantst\0Ljava/util/ArrayList;xp¿\0\0\0\0\0+\0sr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0com.sweng.common.beans.UserÅP|cXYp\Ã\0I\0idUserL\0nameq\0~\0L\0passwordq\0~\0L\0surnameq\0~\0L\0usernameq\0~\0xp\0\0\0t\0\nAlessandropt\0	Canicattit\0	ale7cannasq\0~\0\0\0\0	t\0Giorgiopt\0	Marzoratit\0	johnmarzosq\0~\0\0\0\0\nt\0Andreapt\0Vaghit\0wegosq\0~\0\0\0\0t\0Anielpt\0Rossit\0syndromesq\0~\0\0\0\0t\0Lorenzopt\0Randazzot\0randasq\0~\0\0\0\0\rt\0Andreapt\0Spezialet\0spezzax','com.sweng.common.notice.StartedProjNotice'),(13,'¨\Ì\0sr\0)com.sweng.common.notice.StartedProjNotice\0\0\0\0\0\0\0\0L\0projectInfot\0$Lcom/sweng/common/beans/ProjectInfo;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rì°Cxt\0JSei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli.t\0Aggiunta ad un progettosr\0\"com.sweng.common.beans.ProjectInfoFôF\›\Ê\0F\0completetionPercentageI\0	idProjectZ\0isActiveL\0activitiesInResponsiblet\0Ljava/util/HashMap;L\0admint\0Lcom/sweng/common/beans/User;L\0nameq\0~\0L\0participantst\0Ljava/util/ArrayList;xp¿\0\0\0\0\0+\0sr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0com.sweng.common.beans.UserÅP|cXYp\Ã\0I\0idUserL\0nameq\0~\0L\0passwordq\0~\0L\0surnameq\0~\0L\0usernameq\0~\0xp\0\0\0t\0\nAlessandropt\0	Canicattit\0	ale7cannasq\0~\0\0\0\0	t\0Giorgiopt\0	Marzoratit\0	johnmarzosq\0~\0\0\0\0\nt\0Andreapt\0Vaghit\0wegosq\0~\0\0\0\0t\0Anielpt\0Rossit\0syndromesq\0~\0\0\0\0t\0Lorenzopt\0Randazzot\0randasq\0~\0\0\0\0\rt\0Andreapt\0Spezialet\0spezzax','com.sweng.common.notice.StartedProjNotice'),(14,'¨\Ì\0sr\0)com.sweng.common.notice.StartedProjNotice\0\0\0\0\0\0\0\0L\0projectInfot\0$Lcom/sweng/common/beans/ProjectInfo;xr\0com.sweng.common.notice.Notice\0\0\0\0\0\0\0\0L\0datet\0Ljava/util/Date;L\0messaget\0Ljava/lang/String;L\0titleq\0~\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0Rì°Cxt\0JSei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli.t\0Aggiunta ad un progettosr\0\"com.sweng.common.beans.ProjectInfoFôF\›\Ê\0F\0completetionPercentageI\0	idProjectZ\0isActiveL\0activitiesInResponsiblet\0Ljava/util/HashMap;L\0admint\0Lcom/sweng/common/beans/User;L\0nameq\0~\0L\0participantst\0Ljava/util/ArrayList;xp¿\0\0\0\0\0+\0sr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0com.sweng.common.beans.UserÅP|cXYp\Ã\0I\0idUserL\0nameq\0~\0L\0passwordq\0~\0L\0surnameq\0~\0L\0usernameq\0~\0xp\0\0\0t\0\nAlessandropt\0	Canicattit\0	ale7cannasq\0~\0\0\0\0	t\0Giorgiopt\0	Marzoratit\0	johnmarzosq\0~\0\0\0\0\nt\0Andreapt\0Vaghit\0wegosq\0~\0\0\0\0t\0Anielpt\0Rossit\0syndromesq\0~\0\0\0\0t\0Lorenzopt\0Randazzot\0randasq\0~\0\0\0\0\rt\0Andreapt\0Spezialet\0spezzax','com.sweng.common.notice.StartedProjNotice');
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `notifica_utente`
--

LOCK TABLES `notifica_utente` WRITE;
/*!40000 ALTER TABLE `notifica_utente` DISABLE KEYS */;
INSERT INTO `notifica_utente` VALUES (9,7),(12,8),(8,9),(9,10),(10,11),(11,12),(12,13),(13,14);
/*!40000 ALTER TABLE `notifica_utente` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `partecipante`
--

LOCK TABLES `partecipante` WRITE;
/*!40000 ALTER TABLE `partecipante` DISABLE KEYS */;
INSERT INTO `partecipante` VALUES (8,42),(9,42),(10,42),(11,42),(12,42),(13,42),(8,43),(9,43),(10,43),(11,43),(12,43),(13,43);
/*!40000 ALTER TABLE `partecipante` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='			';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progetto`
--

LOCK TABLES `progetto` WRITE;
/*!40000 ALTER TABLE `progetto` DISABLE KEYS */;
INSERT INTO `progetto` VALUES (42,'Grigliata',8,''),(43,'Giornata al lago',9,'');
/*!40000 ALTER TABLE `progetto` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `responsabile_attivita`
--

LOCK TABLES `responsabile_attivita` WRITE;
/*!40000 ALTER TABLE `responsabile_attivita` DISABLE KEYS */;
INSERT INTO `responsabile_attivita` VALUES (9,26),(12,26),(10,27),(11,27),(9,28),(13,29),(11,30),(12,30);
/*!40000 ALTER TABLE `responsabile_attivita` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (8,'Alessandro','ale7canna','123','Canicatti'),(9,'Giorgio','johnmarzo','123','Marzorati'),(10,'Andrea','wego','123','Vaghi'),(11,'Aniel','syndrome','123','Rossi'),(12,'Lorenzo','randa','123','Randazzo'),(13,'Andrea','spezza','123','Speziale');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente_connesso`
--

LOCK TABLES `utente_connesso` WRITE;
/*!40000 ALTER TABLE `utente_connesso` DISABLE KEYS */;
INSERT INTO `utente_connesso` VALUES (61,'¨\Ì\0s}\0\0\0\0java.rmi.Remote\0com.sweng.common.IClientxr\0java.lang.reflect.Proxy\·\'\⁄ \ÃC\À\0L\0ht\0%Ljava/lang/reflect/InvocationHandler;xpsr\0-java.rmi.server.RemoteObjectInvocationHandler\0\0\0\0\0\0\0\0\0xr\0java.rmi.server.RemoteObject\”a¥ëa3\0\0xpw4\0\nUnicastRef\0192.168.0.7\0\0\„ï˚Ω¢j≤),\”\‡É\0\0Rì®\nÄ\0x','2016-01-30 18:50:15','randa',12);
/*!40000 ALTER TABLE `utente_connesso` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-30 19:07:59
