-- MySQL dump 10.13  Distrib 5.5.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: studyplan_userdata
-- ------------------------------------------------------
-- Server version	5.5.54-0+deb8u1

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
-- Current Database: `studyplan_userdata`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `studyplan_userdata` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `studyplan_userdata`;

--
-- Table structure for table `authorization_context`
--

DROP TABLE IF EXISTS `authorization_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorization_context` (
  `context_id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(100) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `scope` enum('STUDENT') NOT NULL,
  `refresh_token` int(11) DEFAULT NULL,
  `client_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`context_id`),
  UNIQUE KEY `authorization_context_context_id_uindex` (`context_id`),
  UNIQUE KEY `authorization_context_acess_token_uindex` (`access_token`),
  KEY `authorization_context_user_user_id_fk` (`user_id`),
  KEY `authorization_context_rest_client_client_id_fk` (`client_id`),
  CONSTRAINT `authorization_context_rest_client_client_id_fk` FOREIGN KEY (`client_id`) REFERENCES `rest_client` (`client_id`),
  CONSTRAINT `authorization_context_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorization_context`
--

LOCK TABLES `authorization_context` WRITE;
/*!40000 ALTER TABLE `authorization_context` DISABLE KEYS */;
INSERT INTO `authorization_context` VALUES (34,'f32e0b0c-3d1f-486c-9843-a8cc560fa04a','2017-03-19 18:23:11','STUDENT',NULL,1,9),(35,'49df158f-fd86-4570-af20-62192a9397b8','2017-03-19 18:46:13','STUDENT',NULL,1,9),(36,'39f43c30-8bec-4003-9551-7c52fa333c54','2017-03-20 12:19:26','STUDENT',NULL,1,9);
/*!40000 ALTER TABLE `authorization_context` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_scope_assignment`
--

DROP TABLE IF EXISTS `client_scope_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_scope_assignment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scope` enum('STUDENT') NOT NULL,
  `client_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_scope_assignment_id_uindex` (`id`),
  KEY `client_scope_assignment_rest_client_client_id_fk` (`client_id`),
  CONSTRAINT `client_scope_assignment_rest_client_client_id_fk` FOREIGN KEY (`client_id`) REFERENCES `rest_client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_scope_assignment`
--

LOCK TABLES `client_scope_assignment` WRITE;
/*!40000 ALTER TABLE `client_scope_assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_scope_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_entry`
--

DROP TABLE IF EXISTS `module_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_entry` (
  `entry_id` int(11) NOT NULL AUTO_INCREMENT,
  `semester` int(11) NOT NULL,
  `module_id` varchar(100) NOT NULL,
  PRIMARY KEY (`entry_id`),
  UNIQUE KEY `module_entry_entry_id_uindex` (`entry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=580 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_entry`
--

LOCK TABLES `module_entry` WRITE;
/*!40000 ALTER TABLE `module_entry` DISABLE KEYS */;
INSERT INTO `module_entry` VALUES (462,6,'M-INFO-101721'),(463,3,'M-MATH-101308-1'),(464,3,'M-INFO-101180-2'),(465,2,'M-INFO-100030'),(466,2,'M-INFO-101175'),(467,2,'M-MATH-101305-2'),(468,2,'M-MATH-101307-2'),(469,1,'M-INFO-101170'),(470,1,'M-INFO-101174'),(471,1,'M-MATH-101305-1'),(472,1,'M-MATH-101307-1'),(473,3,'M-INFO-101176'),(474,3,'M-INFO-101177'),(475,4,'M-INFO-101178'),(476,5,'M-INFO-101179'),(477,4,'M-MATH-101308-2'),(478,2,'M-INFO-101180-1'),(479,4,'M-KEY-42-1'),(480,5,'M-KEY-42-2'),(481,4,'M-INFO-100834'),(482,5,'M-INFO-101173'),(483,4,'M-MATH-101313'),(484,5,'M-INFO-100799'),(485,3,'M-INFO-101172'),(486,4,'M-INFO-101181-4'),(487,6,'M-INFO-101721'),(488,5,'M-INFO-101179'),(489,5,'M-INFO-100799'),(490,5,'M-MATH-101318'),(491,5,'M-MATH-101315'),(492,5,'M-KEY-42-2'),(493,4,'M-MATH-101313'),(494,4,'M-INFO-101178'),(495,4,'M-MATH-101308-2'),(496,4,'M-INFO-101180-1'),(497,4,'M-INFO-100834'),(498,4,'M-INFO-100818'),(499,4,'M-KEY-42-1'),(500,4,'M-INFO-101181-4'),(501,3,'M-INFO-101177'),(502,3,'M-INFO-101172'),(503,3,'M-INFO-101173'),(504,3,'M-INFO-101220'),(505,3,'M-INFO-101176'),(506,3,'M-INFO-101225'),(507,6,'M-INFO-101247'),(508,6,'M-INFO-101865'),(509,3,'M-INFO-101225'),(510,6,'M-INFO-101257'),(511,5,'M-INFO-101220'),(512,2,'M-INFO-101180-1'),(513,6,'M-INFO-101184'),(514,3,'M-INFO-101176'),(536,3,'M-INFO-101225'),(537,3,'T-WIWI-102708');
/*!40000 ALTER TABLE `module_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_preference`
--

DROP TABLE IF EXISTS `module_preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_preference` (
  `preference_id` int(11) NOT NULL AUTO_INCREMENT,
  `preference_type` enum('POSITIVE','NEGATIVE') NOT NULL,
  `module_id` varchar(100) NOT NULL,
  `plan_identifier` varchar(100) NOT NULL,
  PRIMARY KEY (`preference_id`),
  UNIQUE KEY `module_preference_preference_id_uindex` (`preference_id`),
  KEY `module_preference_plan_identifier_fk` (`plan_identifier`),
  CONSTRAINT `module_preference_plan_identifier_fk` FOREIGN KEY (`plan_identifier`) REFERENCES `plan` (`identifier`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_preference`
--

LOCK TABLES `module_preference` WRITE;
/*!40000 ALTER TABLE `module_preference` DISABLE KEYS */;
INSERT INTO `module_preference` VALUES (2,'POSITIVE','M-KEY-42-2','a262c5ce-cc43-41c8-9301-e803529a8941'),(3,'POSITIVE','M-INFO-100834','a262c5ce-cc43-41c8-9301-e803529a8941'),(4,'NEGATIVE','M-INFO-100818','a262c5ce-cc43-41c8-9301-e803529a8941'),(5,'POSITIVE','M-INFO-100799','a262c5ce-cc43-41c8-9301-e803529a8941'),(9,'POSITIVE','M-INFO-101173','a262c5ce-cc43-41c8-9301-e803529a8941'),(11,'POSITIVE','M-INFO-101220','a262c5ce-cc43-41c8-9301-e803529a8941'),(13,'POSITIVE','M-INFO-101865','a262c5ce-cc43-41c8-9301-e803529a8941'),(14,'NEGATIVE','M-INFO-101219','a262c5ce-cc43-41c8-9301-e803529a8941');
/*!40000 ALTER TABLE `module_preference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passed_modules`
--

DROP TABLE IF EXISTS `passed_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passed_modules` (
  `user_id` int(11) NOT NULL,
  `entry_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`entry_id`),
  KEY `passed_modules_module_entry_entry_id_fk` (`entry_id`),
  CONSTRAINT `passed_modules_module_entry_entry_id_fk` FOREIGN KEY (`entry_id`) REFERENCES `module_entry` (`entry_id`),
  CONSTRAINT `passed_modules_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passed_modules`
--

LOCK TABLES `passed_modules` WRITE;
/*!40000 ALTER TABLE `passed_modules` DISABLE KEYS */;
INSERT INTO `passed_modules` VALUES (9,463),(9,464),(9,465),(9,466),(9,467),(9,468),(9,469),(9,470),(9,471),(9,472);
/*!40000 ALTER TABLE `passed_modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `state` enum('NOT_VERIFIED','VALID','INVALID') NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`plan_id`),
  UNIQUE KEY `plan_plan_id_uindex` (`plan_id`),
  UNIQUE KEY `plan_identifier_uindex` (`identifier`),
  KEY `plan_user_user_id_fk` (`user_id`),
  CONSTRAINT `plan_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (13,'e745e4e7-119a-4f14-b7ce-14079143907e','Mein Plan','NOT_VERIFIED',9),(14,'a262c5ce-cc43-41c8-9301-e803529a8941','Plan 2','NOT_VERIFIED',9);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_entries`
--

DROP TABLE IF EXISTS `plan_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_entries` (
  `plan_identifier` varchar(100) NOT NULL,
  `entry_id` int(11) NOT NULL,
  PRIMARY KEY (`plan_identifier`,`entry_id`),
  KEY `plan_entries_module_entry_entry_id_fk` (`entry_id`),
  CONSTRAINT `plan_entries_module_entry_entry_id_fk` FOREIGN KEY (`entry_id`) REFERENCES `module_entry` (`entry_id`) ON DELETE CASCADE,
  CONSTRAINT `plan_entries_plan_identifier_fk` FOREIGN KEY (`plan_identifier`) REFERENCES `plan` (`identifier`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_entries`
--

LOCK TABLES `plan_entries` WRITE;
/*!40000 ALTER TABLE `plan_entries` DISABLE KEYS */;
INSERT INTO `plan_entries` VALUES ('e745e4e7-119a-4f14-b7ce-14079143907e',487),('e745e4e7-119a-4f14-b7ce-14079143907e',488),('e745e4e7-119a-4f14-b7ce-14079143907e',489),('e745e4e7-119a-4f14-b7ce-14079143907e',490),('e745e4e7-119a-4f14-b7ce-14079143907e',491),('e745e4e7-119a-4f14-b7ce-14079143907e',492),('e745e4e7-119a-4f14-b7ce-14079143907e',493),('e745e4e7-119a-4f14-b7ce-14079143907e',494),('e745e4e7-119a-4f14-b7ce-14079143907e',495),('e745e4e7-119a-4f14-b7ce-14079143907e',499),('e745e4e7-119a-4f14-b7ce-14079143907e',500),('e745e4e7-119a-4f14-b7ce-14079143907e',501),('e745e4e7-119a-4f14-b7ce-14079143907e',502),('e745e4e7-119a-4f14-b7ce-14079143907e',505),('e745e4e7-119a-4f14-b7ce-14079143907e',508),('e745e4e7-119a-4f14-b7ce-14079143907e',510),('e745e4e7-119a-4f14-b7ce-14079143907e',511),('e745e4e7-119a-4f14-b7ce-14079143907e',512),('e745e4e7-119a-4f14-b7ce-14079143907e',513),('a262c5ce-cc43-41c8-9301-e803529a8941',537);
/*!40000 ALTER TABLE `plan_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rest_client`
--

DROP TABLE IF EXISTS `rest_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `api_key` varchar(100) DEFAULT NULL,
  `api_secret` varchar(100) DEFAULT NULL,
  `origin` varchar(200) DEFAULT NULL,
  `redirect_url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `rest_client_client_id_uindex` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rest_client`
--

LOCK TABLES `rest_client` WRITE;
/*!40000 ALTER TABLE `rest_client` DISABLE KEYS */;
INSERT INTO `rest_client` VALUES (1,'key-26hg02lsa','secret-jg921tjg0','.*','http://localhost/processLogin');
/*!40000 ALTER TABLE `rest_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `discipline_id` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `semester_type` enum('WINTER_TERM','SUMMER_TERM') DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_user_id_uindex` (`user_id`),
  UNIQUE KEY `user_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'niklas',1,2015,'WINTER_TERM');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-20 11:53:57
