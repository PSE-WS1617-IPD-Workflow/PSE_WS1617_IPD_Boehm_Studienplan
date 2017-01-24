-- MySQL dump 10.13  Distrib 5.7.16, for osx10.11 (x86_64)
--
-- Host: localhost    Database: studyplan_moduledata
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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `is_subject` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_category_id_uindex` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (54,'Theoretische Informatik',1);
INSERT INTO `category` VALUES (55,'Praktische Informatik',1);
INSERT INTO `category` VALUES (56,'Mathematik',1);
INSERT INTO `category` VALUES (57,'Technische Informatik',1);
INSERT INTO `category` VALUES (59,'Robotik und Automation',1);
INSERT INTO `category` VALUES (60,'Telematik',1);
INSERT INTO `category` VALUES (61,'Kognitive Systeme',1);
INSERT INTO `category` VALUES (62,'Theoretische Grundlagen',1);
INSERT INTO `category` VALUES (63,'Kryptographie und Sicherheit',1);
INSERT INTO `category` VALUES (64,'Eingebettete Systeme',1);
INSERT INTO `category` VALUES (65,'Informationssysteme',1);
INSERT INTO `category` VALUES (66,'Betriebssysteme',1);
INSERT INTO `category` VALUES (67,'BWL',1);
INSERT INTO `category` VALUES (68,'Bachelor-Thesis',0);
INSERT INTO `category` VALUES (69,'VWL',1);
INSERT INTO `category` VALUES (70,'Elektrotechnik',1);
INSERT INTO `category` VALUES (85,'Stammmodul',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `constraint_type`
--

DROP TABLE IF EXISTS `constraint_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `constraint_type` (
  `type_id` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `formal_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `Typ` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `constraint_type`
--

LOCK TABLES `constraint_type` WRITE;
/*!40000 ALTER TABLE `constraint_type` DISABLE KEYS */;
INSERT INTO `constraint_type` VALUES (1,'prerequisite',NULL);
INSERT INTO `constraint_type` VALUES (2,'overlapping',NULL);
INSERT INTO `constraint_type` VALUES (3,'semester_link',NULL);
INSERT INTO `constraint_type` VALUES (4,'plan_link',NULL);
/*!40000 ALTER TABLE `constraint_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discipline`
--

DROP TABLE IF EXISTS `discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discipline` (
  `discipline_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(20) NOT NULL,
  PRIMARY KEY (`discipline_id`),
  UNIQUE KEY `discipline_discipline_id_uindex` (`discipline_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discipline`
--

LOCK TABLES `discipline` WRITE;
/*!40000 ALTER TABLE `discipline` DISABLE KEYS */;
INSERT INTO `discipline` VALUES (1,'Bachelor Informatik');
/*!40000 ALTER TABLE `discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `field` (
  `field_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `min_ects` double DEFAULT NULL,
  `discipline_id` int(11) DEFAULT NULL,
  `is_choosable` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`field_id`),
  UNIQUE KEY `field_field_id_uindex` (`field_id`),
  KEY `field_discipline_discipline_id_fk` (`discipline_id`),
  CONSTRAINT `field_discipline_discipline_id_fk` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`discipline_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field`
--

LOCK TABLES `field` WRITE;
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
INSERT INTO `field` VALUES (1,'Ergänzungsfach',21,1,1);
INSERT INTO `field` VALUES (2,'Abschlussarbeit',15,1,0);
INSERT INTO `field` VALUES (3,'Grundlagenstudium',106,1,0);
INSERT INTO `field` VALUES (4,'Schlüsselqualifikation',6,1,0);
INSERT INTO `field` VALUES (5,'Wahlbereich',32,1,1);
/*!40000 ALTER TABLE `field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `module_id` int(11) NOT NULL,
  `identifier` varchar(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `credit_points` double NOT NULL,
  `cycle_type` enum('WINTER_TERM','SUMMER_TERM','BOTH') NOT NULL,
  `is_compulsory` tinyint(1) DEFAULT NULL,
  `discipline_id` int(11) NOT NULL,
  `description_id` int(11) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`module_id`),
  UNIQUE KEY `ID` (`module_id`),
  UNIQUE KEY `module_identifier_uindex` (`identifier`),
  KEY `module_discipline_discipline_id_fk` (`discipline_id`),
  KEY `module_module_description_description_id_fk` (`description_id`),
  KEY `module_field_field_id_fk` (`field_id`),
  CONSTRAINT `module_discipline_discipline_id_fk` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`discipline_id`),
  CONSTRAINT `module_field_field_id_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`),
  CONSTRAINT `module_module_description_description_id_fk` FOREIGN KEY (`description_id`) REFERENCES `module_description` (`description_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,'M-INFO-101170','Grundbegriffe der Informatik',6,'WINTER_TERM',1,1,1,3);
INSERT INTO `module` VALUES (2,'M-INFO-100030','Algorithmen I',6,'SUMMER_TERM',1,1,2,3);
INSERT INTO `module` VALUES (3,'M-INFO-101172','Theoretische Grundlagen der Informatik',6,'WINTER_TERM',1,1,3,3);
INSERT INTO `module` VALUES (4,'M-INFO-101174','Programmieren',5,'WINTER_TERM',1,1,4,3);
INSERT INTO `module` VALUES (5,'M-INFO-101175','Softwaretechnik I',6,'SUMMER_TERM',1,1,5,3);
INSERT INTO `module` VALUES (6,'M-INFO-101176','PSE',7,'BOTH',1,1,6,3);
INSERT INTO `module` VALUES (7,'M-INFO-101177','Betriebssysteme',6,'WINTER_TERM',1,1,7,3);
INSERT INTO `module` VALUES (8,'M-INFO-101178','Kommunikation und Datenhaltung',8,'SUMMER_TERM',1,1,8,3);
INSERT INTO `module` VALUES (9,'M-INFO-101179','Programmierparadigmen',6,'WINTER_TERM',1,1,9,3);
INSERT INTO `module` VALUES (10,'M-MATH-101305-1','HM I',9,'WINTER_TERM',1,1,10,3);
INSERT INTO `module` VALUES (11,'M-MATH-101305-2','HM II',6,'SUMMER_TERM',1,1,10,3);
INSERT INTO `module` VALUES (12,'M-MATH-101307-1','LA I',9,'WINTER_TERM',1,1,12,3);
INSERT INTO `module` VALUES (13,'M-MATH-101307-2','LA II',5,'SUMMER_TERM',1,1,12,3);
INSERT INTO `module` VALUES (14,'M-MATH-101308-1','Wahrscheinlichkeitstheorie und Statistik',4.5,'WINTER_TERM',1,1,14,3);
INSERT INTO `module` VALUES (15,'M-MATH-101308-2','Numerik',4.5,'SUMMER_TERM',1,1,15,3);
INSERT INTO `module` VALUES (16,'M-INFO-101180-1','Rechnerorganisation',6,'SUMMER_TERM',1,1,16,3);
INSERT INTO `module` VALUES (17,'M-INFO-101180-2','Digitaltechnik und Entwurfsverfahren',6,'WINTER_TERM',1,1,17,3);
INSERT INTO `module` VALUES (18,'M-INFO-101225','TSE',2,'BOTH',1,1,18,4);
INSERT INTO `module` VALUES (19,'M-KEY-42-1','Sprachkurs Spanisch I',2,'BOTH',0,1,19,4);
INSERT INTO `module` VALUES (20,'M-KEY-42-2','Sprachkurs Spanisch II',2,'BOTH',0,1,19,4);
INSERT INTO `module` VALUES (21,'M-INFO-100803','Echtzeitsysteme',6,'SUMMER_TERM',0,1,21,5);
INSERT INTO `module` VALUES (22,'M-INFO-100801','Telematik',6,'WINTER_TERM',0,1,22,5);
INSERT INTO `module` VALUES (23,'M-INFO-100819','Kognitive Systeme',6,'SUMMER_TERM',0,1,23,5);
INSERT INTO `module` VALUES (24,'M-INFO-100799','Formale Systeme',6,'WINTER_TERM',0,1,24,5);
INSERT INTO `module` VALUES (25,'M-INFO-100834','Sicherheit',6,'SUMMER_TERM',0,1,25,5);
INSERT INTO `module` VALUES (26,'M-INFO-100818','Rechnerstrukturen',6,'SUMMER_TERM',0,1,26,5);
INSERT INTO `module` VALUES (27,'M-INFO-101181-1','Proseminar Informatik in der Medizin',3,'BOTH',0,1,27,5);
INSERT INTO `module` VALUES (28,'M-INFO-101181-2','Proseminar Moderne Kommunikationssysteme',3,'BOTH',0,1,28,5);
INSERT INTO `module` VALUES (29,'M-INFO-101181-3','Ausgewählte Kapitel der Rechnerarchitektur',3,'BOTH',0,1,29,5);
INSERT INTO `module` VALUES (30,'M-INFO-101181-4','Special Issues in Data Analysis',3,'BOTH',0,1,30,5);
INSERT INTO `module` VALUES (31,'M-INFO-101181-5','Proseminar Anthropomatik',3,'BOTH',0,1,31,5);
INSERT INTO `module` VALUES (32,'M-INFO-101173','Algorithmen II',6,'WINTER_TERM',0,1,32,5);
INSERT INTO `module` VALUES (33,'M-INFO-101220','Algorithmen für planare Graphen',5,'WINTER_TERM',0,1,33,5);
INSERT INTO `module` VALUES (34,'M-INFO-101257','Basispraktikum Betriebssystementwicklung',4,'BOTH',0,1,34,5);
INSERT INTO `module` VALUES (35,'M-INFO-101184','Basispraktikum Mobile Roboter',4,'BOTH',0,1,35,5);
INSERT INTO `module` VALUES (36,'M-INFO-101247','Basispraktikum Protocol Engineering',4,'BOTH',0,1,36,5);
INSERT INTO `module` VALUES (37,'M-INFO-101219','Basispraktikum TI: Hardwarenaher Systementwurf',4,'WINTER_TERM',0,1,37,5);
INSERT INTO `module` VALUES (38,'M-INFO-101865','Basispraktikum: Arbeiten mit Datenbanksystemen',4,'BOTH',0,1,38,5);
INSERT INTO `module` VALUES (39,'M-INFO-100757','Mechano-Informatik in der Robotik',4,'WINTER_TERM',0,1,39,5);
INSERT INTO `module` VALUES (40,'M-INFO-101183','Mikroprozessoren I',3,'SUMMER_TERM',0,1,40,5);
INSERT INTO `module` VALUES (41,'M-INFO-101249','Mobile Computing und Internet der Dinge',5,'WINTER_TERM',0,1,41,5);
INSERT INTO `module` VALUES (42,'T-WIWI-102816','Rechnungswesen',4,'WINTER_TERM',0,1,42,1);
INSERT INTO `module` VALUES (43,'T-WIWI-102818','Produktionswirtschaft und Marketing',4,'SUMMER_TERM',0,1,43,1);
INSERT INTO `module` VALUES (44,'T-WIWI-102819','Finanzwirtschaft und Rechnungswesen',4,'WINTER_TERM',0,1,44,1);
INSERT INTO `module` VALUES (45,'T-WIWI-102606','Grundlagen der Produktionswirtschaft',5.5,'SUMMER_TERM',0,1,45,1);
INSERT INTO `module` VALUES (46,'T-WIWI-102820','Produktion und Nachhaltigkeit',3.5,'WINTER_TERM',0,1,46,1);
INSERT INTO `module` VALUES (47,'M-MATH-101313','Proseminar Mathematik',3,'BOTH',0,1,47,1);
INSERT INTO `module` VALUES (48,'M-MATH-101315','Algebra',9,'WINTER_TERM',0,1,48,1);
INSERT INTO `module` VALUES (49,'M-MATH-101318','Analysis III',9,'WINTER_TERM',0,1,49,1);
INSERT INTO `module` VALUES (50,'M-INFO-101721','Bachelor-Thesis',15,'BOTH',1,1,50,2);
INSERT INTO `module` VALUES (51,'T-WIWI-102708','Volkswirtschaftslehre I: Mikroökonomie',6,'WINTER_TERM',0,1,51,1);
INSERT INTO `module` VALUES (52,'T-WIWI-102709','Volkswirtschaftslehre II: Makroökonomie',6,'SUMMER_TERM',0,1,51,1);
INSERT INTO `module` VALUES (53,'T-WIWI-102850','Einführung in die Spieltheorie',4.5,'WINTER_TERM',0,1,53,1);
INSERT INTO `module` VALUES (54,'T-WIWI-102609','Advanced Topics in Economic Theory',4.5,'SUMMER_TERM',0,1,54,1);
INSERT INTO `module` VALUES (55,'T-ETIT-101930','Bildgebende Verfahren in der Medizin I',3,'WINTER_TERM',0,1,55,1);
INSERT INTO `module` VALUES (56,'T-ETIT-101931','Bildgebende Verfahren in der Medizin II',3,'SUMMER_TERM',0,1,55,1);
INSERT INTO `module` VALUES (57,'T-ETIT-101956','Bioelektrische Signale',3,'WINTER_TERM',0,1,57,1);
INSERT INTO `module` VALUES (58,'T-ETIT-101928','Biomedizinische Messtechnik I',3,'SUMMER_TERM',0,1,58,1);
INSERT INTO `module` VALUES (59,'T-ETIT-101929','Biomedizinische Messtechnik II',3,'WINTER_TERM',0,1,58,1);
INSERT INTO `module` VALUES (60,'T-ETIT-101936','Nachrichtentechnik I',6,'SUMMER_TERM',0,1,60,1);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_category_assignment`
--

DROP TABLE IF EXISTS `module_category_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_category_assignment` (
  `category_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  PRIMARY KEY (`category_id`,`module_id`),
  KEY `module_category_assignment_module_module_id_fk` (`module_id`),
  CONSTRAINT `module_category_assignment_category_category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `module_category_assignment_module_module_id_fk` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_category_assignment`
--

LOCK TABLES `module_category_assignment` WRITE;
/*!40000 ALTER TABLE `module_category_assignment` DISABLE KEYS */;
INSERT INTO `module_category_assignment` VALUES (54,1);
INSERT INTO `module_category_assignment` VALUES (54,2);
INSERT INTO `module_category_assignment` VALUES (54,3);
INSERT INTO `module_category_assignment` VALUES (55,4);
INSERT INTO `module_category_assignment` VALUES (55,5);
INSERT INTO `module_category_assignment` VALUES (55,6);
INSERT INTO `module_category_assignment` VALUES (55,7);
INSERT INTO `module_category_assignment` VALUES (55,8);
INSERT INTO `module_category_assignment` VALUES (55,9);
INSERT INTO `module_category_assignment` VALUES (56,10);
INSERT INTO `module_category_assignment` VALUES (56,11);
INSERT INTO `module_category_assignment` VALUES (56,12);
INSERT INTO `module_category_assignment` VALUES (56,13);
INSERT INTO `module_category_assignment` VALUES (56,14);
INSERT INTO `module_category_assignment` VALUES (56,15);
INSERT INTO `module_category_assignment` VALUES (57,16);
INSERT INTO `module_category_assignment` VALUES (57,17);
INSERT INTO `module_category_assignment` VALUES (59,21);
INSERT INTO `module_category_assignment` VALUES (85,21);
INSERT INTO `module_category_assignment` VALUES (60,22);
INSERT INTO `module_category_assignment` VALUES (85,22);
INSERT INTO `module_category_assignment` VALUES (61,23);
INSERT INTO `module_category_assignment` VALUES (85,23);
INSERT INTO `module_category_assignment` VALUES (62,24);
INSERT INTO `module_category_assignment` VALUES (85,24);
INSERT INTO `module_category_assignment` VALUES (63,25);
INSERT INTO `module_category_assignment` VALUES (85,25);
INSERT INTO `module_category_assignment` VALUES (64,26);
INSERT INTO `module_category_assignment` VALUES (85,26);
INSERT INTO `module_category_assignment` VALUES (59,27);
INSERT INTO `module_category_assignment` VALUES (60,28);
INSERT INTO `module_category_assignment` VALUES (64,29);
INSERT INTO `module_category_assignment` VALUES (65,30);
INSERT INTO `module_category_assignment` VALUES (61,31);
INSERT INTO `module_category_assignment` VALUES (62,32);
INSERT INTO `module_category_assignment` VALUES (62,33);
INSERT INTO `module_category_assignment` VALUES (66,34);
INSERT INTO `module_category_assignment` VALUES (61,35);
INSERT INTO `module_category_assignment` VALUES (60,36);
INSERT INTO `module_category_assignment` VALUES (64,37);
INSERT INTO `module_category_assignment` VALUES (65,38);
INSERT INTO `module_category_assignment` VALUES (59,39);
INSERT INTO `module_category_assignment` VALUES (64,40);
INSERT INTO `module_category_assignment` VALUES (66,41);
INSERT INTO `module_category_assignment` VALUES (67,42);
INSERT INTO `module_category_assignment` VALUES (67,43);
INSERT INTO `module_category_assignment` VALUES (67,44);
INSERT INTO `module_category_assignment` VALUES (67,45);
INSERT INTO `module_category_assignment` VALUES (67,46);
INSERT INTO `module_category_assignment` VALUES (56,47);
INSERT INTO `module_category_assignment` VALUES (56,48);
INSERT INTO `module_category_assignment` VALUES (56,49);
INSERT INTO `module_category_assignment` VALUES (68,50);
INSERT INTO `module_category_assignment` VALUES (69,51);
INSERT INTO `module_category_assignment` VALUES (69,52);
INSERT INTO `module_category_assignment` VALUES (69,53);
INSERT INTO `module_category_assignment` VALUES (69,54);
INSERT INTO `module_category_assignment` VALUES (70,55);
INSERT INTO `module_category_assignment` VALUES (70,56);
INSERT INTO `module_category_assignment` VALUES (70,57);
INSERT INTO `module_category_assignment` VALUES (70,58);
INSERT INTO `module_category_assignment` VALUES (70,59);
INSERT INTO `module_category_assignment` VALUES (70,60);
/*!40000 ALTER TABLE `module_category_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_constraint`
--

DROP TABLE IF EXISTS `module_constraint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_constraint` (
  `constraint_id` int(11) NOT NULL,
  `module1` int(50) NOT NULL,
  `module2` int(50) NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`constraint_id`),
  UNIQUE KEY `ID` (`constraint_id`),
  KEY `module_constraint_constraint_type_type_id_fk` (`type_id`),
  KEY `module_constraint_module2_module_id_fk` (`module2`),
  KEY `module_constraint_module1_module_id_fk` (`module1`),
  CONSTRAINT `module_constraint_constraint_type_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `constraint_type` (`type_id`),
  CONSTRAINT `module_constraint_module1_module_id_fk` FOREIGN KEY (`module1`) REFERENCES `module` (`module_id`),
  CONSTRAINT `module_constraint_module2_module_id_fk` FOREIGN KEY (`module2`) REFERENCES `module` (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_constraint`
--

LOCK TABLES `module_constraint` WRITE;
/*!40000 ALTER TABLE `module_constraint` DISABLE KEYS */;
INSERT INTO `module_constraint` VALUES (1,6,18,3);
INSERT INTO `module_constraint` VALUES (2,10,11,1);
INSERT INTO `module_constraint` VALUES (3,12,13,1);
INSERT INTO `module_constraint` VALUES (4,42,43,1);
INSERT INTO `module_constraint` VALUES (5,43,44,1);
INSERT INTO `module_constraint` VALUES (6,42,43,4);
INSERT INTO `module_constraint` VALUES (7,43,44,4);
INSERT INTO `module_constraint` VALUES (8,44,45,1);
INSERT INTO `module_constraint` VALUES (9,45,46,4);
INSERT INTO `module_constraint` VALUES (10,47,48,1);
INSERT INTO `module_constraint` VALUES (11,47,49,1);
INSERT INTO `module_constraint` VALUES (12,51,52,4);
INSERT INTO `module_constraint` VALUES (13,53,54,4);
INSERT INTO `module_constraint` VALUES (14,52,53,1);
INSERT INTO `module_constraint` VALUES (15,58,59,4);
/*!40000 ALTER TABLE `module_constraint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_description`
--

DROP TABLE IF EXISTS `module_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_description` (
  `description_id` int(11) NOT NULL AUTO_INCREMENT,
  `description_text` varchar(200) DEFAULT NULL,
  `lecturer` varchar(100) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`description_id`),
  UNIQUE KEY `module_description_description_id_uindex` (`description_id`),
  KEY `module_description_module_type_type_id_fk` (`type_id`),
  CONSTRAINT `module_description_module_type_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `module_type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_description`
--

LOCK TABLES `module_description` WRITE;
/*!40000 ALTER TABLE `module_description` DISABLE KEYS */;
INSERT INTO `module_description` VALUES (1,NULL,NULL,2);
INSERT INTO `module_description` VALUES (2,NULL,NULL,2);
INSERT INTO `module_description` VALUES (3,NULL,NULL,2);
INSERT INTO `module_description` VALUES (4,NULL,NULL,2);
INSERT INTO `module_description` VALUES (5,NULL,NULL,2);
INSERT INTO `module_description` VALUES (6,NULL,NULL,2);
INSERT INTO `module_description` VALUES (7,NULL,NULL,2);
INSERT INTO `module_description` VALUES (8,NULL,NULL,2);
INSERT INTO `module_description` VALUES (9,NULL,NULL,2);
INSERT INTO `module_description` VALUES (10,NULL,NULL,2);
INSERT INTO `module_description` VALUES (12,NULL,NULL,2);
INSERT INTO `module_description` VALUES (14,NULL,NULL,2);
INSERT INTO `module_description` VALUES (15,NULL,NULL,2);
INSERT INTO `module_description` VALUES (16,NULL,NULL,2);
INSERT INTO `module_description` VALUES (17,NULL,NULL,2);
INSERT INTO `module_description` VALUES (18,NULL,NULL,5);
INSERT INTO `module_description` VALUES (19,NULL,NULL,5);
INSERT INTO `module_description` VALUES (21,NULL,NULL,2);
INSERT INTO `module_description` VALUES (22,NULL,NULL,2);
INSERT INTO `module_description` VALUES (23,NULL,NULL,2);
INSERT INTO `module_description` VALUES (24,NULL,NULL,2);
INSERT INTO `module_description` VALUES (25,NULL,NULL,2);
INSERT INTO `module_description` VALUES (26,NULL,NULL,2);
INSERT INTO `module_description` VALUES (27,NULL,NULL,1);
INSERT INTO `module_description` VALUES (28,NULL,NULL,1);
INSERT INTO `module_description` VALUES (29,NULL,NULL,1);
INSERT INTO `module_description` VALUES (30,NULL,NULL,1);
INSERT INTO `module_description` VALUES (31,NULL,NULL,1);
INSERT INTO `module_description` VALUES (32,NULL,NULL,2);
INSERT INTO `module_description` VALUES (33,NULL,NULL,2);
INSERT INTO `module_description` VALUES (34,NULL,NULL,3);
INSERT INTO `module_description` VALUES (35,NULL,NULL,3);
INSERT INTO `module_description` VALUES (36,NULL,NULL,3);
INSERT INTO `module_description` VALUES (37,NULL,NULL,3);
INSERT INTO `module_description` VALUES (38,NULL,NULL,3);
INSERT INTO `module_description` VALUES (39,NULL,NULL,2);
INSERT INTO `module_description` VALUES (40,NULL,NULL,2);
INSERT INTO `module_description` VALUES (41,NULL,NULL,2);
INSERT INTO `module_description` VALUES (42,NULL,NULL,2);
INSERT INTO `module_description` VALUES (43,NULL,NULL,2);
INSERT INTO `module_description` VALUES (44,NULL,NULL,2);
INSERT INTO `module_description` VALUES (45,NULL,NULL,2);
INSERT INTO `module_description` VALUES (46,NULL,NULL,2);
INSERT INTO `module_description` VALUES (47,NULL,NULL,2);
INSERT INTO `module_description` VALUES (48,NULL,NULL,2);
INSERT INTO `module_description` VALUES (49,NULL,NULL,2);
INSERT INTO `module_description` VALUES (50,NULL,NULL,4);
INSERT INTO `module_description` VALUES (51,NULL,NULL,2);
INSERT INTO `module_description` VALUES (53,NULL,NULL,2);
INSERT INTO `module_description` VALUES (54,NULL,NULL,2);
INSERT INTO `module_description` VALUES (55,NULL,NULL,2);
INSERT INTO `module_description` VALUES (57,NULL,NULL,2);
INSERT INTO `module_description` VALUES (58,NULL,NULL,2);
INSERT INTO `module_description` VALUES (60,NULL,NULL,2);
/*!40000 ALTER TABLE `module_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_type`
--

DROP TABLE IF EXISTS `module_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `module_type_type_id_uindex` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_type`
--

LOCK TABLES `module_type` WRITE;
/*!40000 ALTER TABLE `module_type` DISABLE KEYS */;
INSERT INTO `module_type` VALUES (1,'Proseminar');
INSERT INTO `module_type` VALUES (2,'Vorlesung');
INSERT INTO `module_type` VALUES (3,'Praktikum');
INSERT INTO `module_type` VALUES (4,'Bachelorarbeit');
INSERT INTO `module_type` VALUES (5,'Schlüsselqualifikation');
/*!40000 ALTER TABLE `module_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule_group`
--

DROP TABLE IF EXISTS `rule_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule_group` (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `min_num` int(11) DEFAULT NULL,
  `max_num` int(11) DEFAULT NULL,
  `discipline_id` int(11) NOT NULL,
  PRIMARY KEY (`rule_id`),
  UNIQUE KEY `group_rule_rule_id_uindex` (`rule_id`),
  KEY `group_rule_discipline_discipline_id_fk` (`discipline_id`),
  CONSTRAINT `group_rule_discipline_discipline_id_fk` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`discipline_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule_group`
--

LOCK TABLES `rule_group` WRITE;
/*!40000 ALTER TABLE `rule_group` DISABLE KEYS */;
INSERT INTO `rule_group` VALUES (1,'Stammmodule',2,NULL,1);
INSERT INTO `rule_group` VALUES (2,'Proseminar',1,NULL,1);
/*!40000 ALTER TABLE `rule_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule_module_assignment`
--

DROP TABLE IF EXISTS `rule_module_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule_module_assignment` (
  `rule_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  PRIMARY KEY (`module_id`,`rule_id`),
  KEY `rule_module_assignment_rule_group_rule_id_fk` (`rule_id`),
  CONSTRAINT `rule_module_assignment_module_module_id_fk` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`),
  CONSTRAINT `rule_module_assignment_rule_group_rule_id_fk` FOREIGN KEY (`rule_id`) REFERENCES `rule_group` (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule_module_assignment`
--

LOCK TABLES `rule_module_assignment` WRITE;
/*!40000 ALTER TABLE `rule_module_assignment` DISABLE KEYS */;
INSERT INTO `rule_module_assignment` VALUES (1,21);
INSERT INTO `rule_module_assignment` VALUES (1,22);
INSERT INTO `rule_module_assignment` VALUES (1,23);
INSERT INTO `rule_module_assignment` VALUES (1,24);
INSERT INTO `rule_module_assignment` VALUES (1,25);
INSERT INTO `rule_module_assignment` VALUES (1,26);
INSERT INTO `rule_module_assignment` VALUES (2,27);
INSERT INTO `rule_module_assignment` VALUES (2,28);
INSERT INTO `rule_module_assignment` VALUES (2,29);
INSERT INTO `rule_module_assignment` VALUES (2,30);
INSERT INTO `rule_module_assignment` VALUES (2,31);
/*!40000 ALTER TABLE `rule_module_assignment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-24 16:16:10
