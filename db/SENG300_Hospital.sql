-- MySQL dump 10.13  Distrib 8.0.15, for Linux (x86_64)
--
-- Host: localhost    Database: SENG300_Hospital
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `SENG300_Hospital`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `SENG300_Hospital` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `SENG300_Hospital`;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrator` (
  `role` text,
  `cred_id` int(11) DEFAULT NULL,
  KEY `cred_id` (`cred_id`),
  CONSTRAINT `administrator_ibfk_1` FOREIGN KEY (`cred_id`) REFERENCES `credential` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES ('Primary Admin',8);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointment` (
  `time` datetime NOT NULL,
  `duration` int(11) DEFAULT NULL,
  `desc` text,
  `pat_id` int(11) DEFAULT NULL,
  `doc_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  UNIQUE KEY `multiValPair` (`time`,`doc_id`,`pat_id`),
  KEY `fk_pat` (`pat_id`),
  KEY `doc_id` (`doc_id`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `credential` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_pat` FOREIGN KEY (`pat_id`) REFERENCES `patient` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('2019-02-26 09:00:00',45,'Jane Doe\'s regular check-in for her heart condition',1,1,'Regular Cardio Check-Up'),('2019-02-27 14:00:00',45,'John Doe is getting his cast removed from a particularly private bone. Male Physician sadly unavailable',2,7,'Cast Removal'),('2019-02-27 09:00:00',45,'Monthly organ harvest required to prevent the end of humanity. Use cadavers if at all possible',3,4,'Ritual Organ Harvest'),('2019-02-28 00:00:00',NULL,'Standard protocol day-of-rest for the mediator of the sacrifice.',NULL,4,'Day of Rest #803');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credential`
--

DROP TABLE IF EXISTS `credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `credential` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credential`
--

LOCK TABLES `credential` WRITE;
/*!40000 ALTER TABLE `credential` DISABLE KEYS */;
INSERT INTO `credential` VALUES (1,'AndyPants','pass1','111-222-3344','Andy A. Anderson'),(2,'Alphyboi','pass2','123-123-1234','Alpha A. Alphonzo'),(3,'bill_hilly','password','987-654-3210','Bill B. Billsner'),(4,'bobbin','pass_word','291-657-2534','Bob B. Bobbin'),(5,'grassybryar','pass_wordy','834-526-7462','Bryan B. Briar'),(6,'no_one_cathy','im_good_cat','111-234-0987','Cathy C. Catherine'),(7,'no_one_cathy_really','im_better_cat','222-432-5432','Catherine C. Cathy'),(8,'admin_01','a_good_password','403-434-5678','Urist AdminPants');
/*!40000 ALTER TABLE `credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `desc` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Cardiology','Disorders of the heart and circulatory system'),(2,'Surgery','Surgery scheduling and operations, of all types'),(3,'Orthopedics','Disorders of the bones and/or muscles');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `doctor` (
  `is_surgeon` tinyint(1) NOT NULL DEFAULT '0',
  `dep_id` int(11) NOT NULL,
  `cred_id` int(11) NOT NULL,
  KEY `cred_id` (`cred_id`),
  KEY `dep_id` (`dep_id`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`cred_id`) REFERENCES `credential` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `doctor_ibfk_2` FOREIGN KEY (`dep_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (0,1,1),(1,1,2),(1,2,3),(1,2,4),(1,2,5),(1,3,6),(0,3,7);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'Doe',
  `sex` tinyint(1) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'Jane Doe',0,'123-456-7890'),(2,'John Doe',1,'758-324-0645'),(3,'Xthulum Zuian',NULL,'[REDACTED]');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receptionist`
--

DROP TABLE IF EXISTS `receptionist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `receptionist` (
  `role` text,
  `cred_id` int(11) DEFAULT NULL,
  KEY `cred_id` (`cred_id`),
  CONSTRAINT `receptionist_ibfk_1` FOREIGN KEY (`cred_id`) REFERENCES `credential` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receptionist`
--

LOCK TABLES `receptionist` WRITE;
/*!40000 ALTER TABLE `receptionist` DISABLE KEYS */;
INSERT INTO `receptionist` VALUES ('Head Receptionist',8);
/*!40000 ALTER TABLE `receptionist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'SENG300_Hospital'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-17 22:01:06
