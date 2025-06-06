-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: QUANLYTHUVIEN
-- ------------------------------------------------------
-- Server version	8.0.40-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `OrderBook`
--

DROP TABLE IF EXISTS `OrderBook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderBook` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `Studentname` varchar(100) NOT NULL,
  `StudentID` varchar(50) NOT NULL,
  `Bookname` varchar(255) NOT NULL,
  `Status` varchar(50) DEFAULT 'Pending',
  `OrderedDate` date DEFAULT NULL,
  `ReturnedDate` date DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `StudentID` (`StudentID`),
  KEY `Bookname` (`Bookname`),
  CONSTRAINT `OrderBook_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `students` (`StudentID`),
  CONSTRAINT `OrderBook_ibfk_2` FOREIGN KEY (`Bookname`) REFERENCES `book` (`Bookname`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderBook`
--

LOCK TABLES `OrderBook` WRITE;
/*!40000 ALTER TABLE `OrderBook` DISABLE KEYS */;
INSERT INTO `OrderBook` VALUES (1,'trysnguyen','nguyen dinh tri','24AI061','asd','Returned',NULL,NULL),(2,'trysnguyen','nguyen dinh tri','24AI061','qwe','Returned',NULL,NULL),(3,'trysnguyen','nguyen dinh tri','24AI061','asd','Returned',NULL,NULL),(4,'trysnguyen','nguyen dinh tri','24AI061','qwe','Returned',NULL,NULL),(5,'trysnguyen','nguyen dinh tri','24AI061','asd','Returned',NULL,NULL),(6,'trysnguyen','nguyen dinh tri','24AI061','qwe','Returned',NULL,NULL),(7,'trysnguyen','nguyen dinh tri','24AI061','Data Structure and Algorithm','Returned',NULL,NULL),(8,'trysnguyen','nguyen dinh tri','24AI061','Data Structure and Algorithm','Returned',NULL,NULL),(9,'trysnguyen','nguyen dinh tri','24AI061','How to become an AI Researcher','Returned',NULL,NULL),(10,'trysnguyen','nguyen dinh tri','24AI061','A good Computer Science','Returned',NULL,NULL),(11,'trysnguyen','nguyen dinh tri','24AI061','qwe','Returned',NULL,NULL),(12,'trysnguyen','nguyen dinh tri','24AI061','Data Structure and Algorithm','Returned',NULL,NULL),(13,'trysnguyen','nguyen dinh tri','24AI061','How to become an AI Researcher','Returned',NULL,NULL),(14,'trysnguyen','nguyen dinh tri','24AI061','Data Structure and Algorithm','Pending',NULL,NULL),(15,'trysnguyen','nguyen dinh tri','24AI061','How to become an AI Researcher','Returned',NULL,NULL),(16,'trysnguyen','nguyen dinh tri','24AI061','How to become an AI Researcher','Returned','2025-01-15','2025-01-18'),(17,'trysnguyen','nguyen dinh tri','24AI061','asd','Refuse','2025-01-15','2025-01-18'),(18,'trysnguyen','nguyen dinh tri','24AI061','A good Computer Science','Accept','2025-01-15','2025-01-18'),(19,'nguyendinhtri','nguyen tri','061','A good Computer Science','Refuse','2025-01-15','2025-01-18');
/*!40000 ALTER TABLE `OrderBook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `BookID` int NOT NULL AUTO_INCREMENT,
  `Bookname` varchar(255) NOT NULL,
  `Author` varchar(100) NOT NULL,
  `Category` varchar(50) NOT NULL,
  `Quantity` int DEFAULT '0',
  PRIMARY KEY (`BookID`),
  UNIQUE KEY `Bookname` (`Bookname`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'asd','asd','asd',2),(2,'qwe','qwe','qwe',2),(3,'Data Structure and Algorithm','Unknow dev','Learning',7),(4,'How to become an AI Researcher','Donald Trump','Self-help',5),(5,'A good Computer Science','Osama bin Laden','Chill',0),(6,'Java','idk','Chill',0);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `StudentID` varchar(50) NOT NULL,
  `Studentname` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `Class` varchar(50) NOT NULL,
  PRIMARY KEY (`StudentID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('061','nguyen tri','nguyendinhtri','123','24AI'),('24AI061','nguyen dinh tri','trysnguyen','123','24AI'),('S001','John Doe','john_doe','password123','Class A'),('S002','Jane Smith','jane_smith','password456','Class B');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-15  2:10:59
