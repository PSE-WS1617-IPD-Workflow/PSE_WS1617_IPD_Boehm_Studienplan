-- MySQL dump 10.13  Distrib 5.7.16, for osx10.11 (x86_64)
--
-- Host: localhost    Database: studyplan_userdata
-- ------------------------------------------------------
-- Server version	5.7.16

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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-14  0:21:57
