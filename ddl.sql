-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: recruit
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `host_check`
--

DROP TABLE IF EXISTS `host_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `host_check` (
  `check_no` int(11) NOT NULL AUTO_INCREMENT,
  `host_no` int(11) NOT NULL,
  `status` enum('Y','N') NOT NULL,
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `registered` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`check_no`),
  KEY `FK_host_check_host_no_idx` (`host_no`),
  CONSTRAINT `FK_host_check_host_no` FOREIGN KEY (`host_no`) REFERENCES `host_list` (`host_no`)
) ENGINE=InnoDB AUTO_INCREMENT=831 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `host_check`
--

LOCK TABLES `host_check` WRITE;
/*!40000 ALTER TABLE `host_check` DISABLE KEYS */;
/*!40000 ALTER TABLE `host_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `host_list`
--

DROP TABLE IF EXISTS `host_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `host_list` (
  `host_no` int(11) NOT NULL AUTO_INCREMENT,
  `member_no` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `active_status` enum('ACTIVE','DEAD') NOT NULL DEFAULT 'ACTIVE',
  `registered` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`host_no`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `ip_UNIQUE` (`ip`),
  KEY `host_list_member_no_idx` (`member_no`),
  CONSTRAINT `host_list_member_no` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `host_list`
--

LOCK TABLES `host_list` WRITE;
/*!40000 ALTER TABLE `host_list` DISABLE KEYS */;
INSERT INTO `host_list` VALUES (1,2,'test','1.1.1.1','DEAD','2024-02-23 03:42:38','2024-02-23 03:42:38'),(2,2,'test2222','61.109.61.157','ACTIVE','2024-02-23 03:43:31','2024-02-23 19:45:54'),(3,2,'test3','127.0.0.1','ACTIVE','2024-02-23 03:47:34','2024-02-23 03:47:34');
/*!40000 ALTER TABLE `host_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_no` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(20) NOT NULL,
  `password` varchar(128) NOT NULL,
  `role` enum('ROLE_MEMBER','ROLE_ADMIN') NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `member_status` enum('ALIVE','DEAD') NOT NULL DEFAULT 'ALIVE',
  `registered` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_no`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'adminid','$2a$10$kOmJM0ULuNUgSLRm6D7aduCFhOk8.W93MBJXR9HzGQ3OWfbFvgcM2','ROLE_ADMIN',NULL,'ALIVE','2024-02-23 02:19:01'),(2,'testid2','$2a$10$7g5nnxugeZzyV3RA2xQ/e.HrSqxZXewUCJKLemnDd/SjqEgXdTAc2','ROLE_ADMIN',NULL,'ALIVE','2024-02-23 02:19:50'),(3,'testid3','$2a$10$dPjAaYGq/SslsOJyWFnar.mJzS7PshB4KcM9NsqsOV2/YC5YWWUA.','ROLE_ADMIN',NULL,'ALIVE','2024-02-23 02:20:35'),(4,'normalid','$2a$10$0x8kANu5bpKc7epjjRwGCeaZdSZRs7/CCFFCr40615yuE1ulfaEYS','ROLE_MEMBER',NULL,'ALIVE','2024-02-23 02:24:04'),(5,'testid5','$2a$10$Lv2M2sYlt/d/PzrtRpLE2uh1QQKDDxyDOGoWS9PzwWt5TAIsic.WW','ROLE_MEMBER',NULL,'ALIVE','2024-02-23 02:27:59');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_log`
--

DROP TABLE IF EXISTS `member_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_log` (
  `log_no` int(11) NOT NULL AUTO_INCREMENT,
  `member_no` int(11) NOT NULL,
  `type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `result` enum('Y','N') NOT NULL,
  `registered` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_no`),
  KEY `member_log_member_no_idx` (`member_no`),
  CONSTRAINT `member_log_member_no` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_log`
--

LOCK TABLES `member_log` WRITE;
/*!40000 ALTER TABLE `member_log` DISABLE KEYS */;
INSERT INTO `member_log` VALUES (37,5,'GET_HOST_MONITOR','Y','2024-02-23 19:45:34'),(38,5,'GET_HOST_STATUS','Y','2024-02-23 19:45:48'),(39,2,'PUT_HOST','Y','2024-02-23 19:45:54'),(40,2,'GET_LOG','Y','2024-02-23 20:07:41'),(41,5,'GET_HOST_STATUS','Y','2024-02-23 20:07:52'),(42,2,'GET_LOG','Y','2024-02-23 20:07:55'),(43,2,'GET_LOG','Y','2024-02-23 21:44:31'),(44,2,'GET_HOST_MONITOR','Y','2024-02-23 21:44:52');
/*!40000 ALTER TABLE `member_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'recruit'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-23 22:15:01
