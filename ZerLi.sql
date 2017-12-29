-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: zer-li
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `Item_ID` varchar(45) NOT NULL,
  `Order_ID` varchar(45) NOT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Price` varchar(45) DEFAULT NULL,
  `Text` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Item_ID`,`Order_ID`),
  KEY `Order_ID_idx` (`Order_ID`),
  CONSTRAINT `ItemID` FOREIGN KEY (`Item_ID`) REFERENCES `item_in_order` (`Item_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `OrderID` FOREIGN KEY (`Order_ID`) REFERENCES `item_in_order` (`Order_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES ('9361','3788','Birthday','5','Happy birthday Dad!');
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `ID` varchar(45) NOT NULL,
  `Customer_ID` varchar(45) DEFAULT NULL,
  `Text` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Answer` varchar(45) DEFAULT NULL,
  `Compensation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CustomerID_idx` (`Customer_ID`),
  CONSTRAINT `CustomerID` FOREIGN KEY (`Customer_ID`) REFERENCES `person` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` VALUES ('2876','666666666','The service in Tel-Aviv store was so bad','Pending',NULL,NULL);
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delivery` (
  `Order_ID` varchar(45) NOT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `RecieverName` varchar(45) DEFAULT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Order_ID`),
  CONSTRAINT `Order` FOREIGN KEY (`Order_ID`) REFERENCES `order` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES ('3788','Tel-Aviv, Herzel 78','David','0526477824');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `ID` varchar(45) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Color` varchar(45) DEFAULT NULL,
  `Price` varchar(45) DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Amount` varchar(45) DEFAULT NULL,
  `Sale_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Sale_ID_idx` (`Sale_ID`),
  CONSTRAINT `Sale_ID` FOREIGN KEY (`Sale_ID`) REFERENCES `sales` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('1234','Kalanit','Red','10','flower','60','5564'),('1983','Yekinton','Yellow','3','flowe','40','5564'),('2234','Turkish Vase','Brown','20','vase','15',NULL),('3490','Luli','Green','1.5','tape','100',NULL),('4567','Rose','Red','5','flower','50','5564'),('4896','Brazilien Vase','Red','30','vase','30',NULL),('6475','Kashmir','Pink','1.5','tape','100','5564'),('7589','Spain Vase','Green','25','vase','20',NULL),('7835','Narkis','Yellow','7','flower','30',NULL);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_catalog`
--

DROP TABLE IF EXISTS `item_in_catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_in_catalog` (
  `ID` varchar(45) NOT NULL,
  `Item_ID` varchar(45) NOT NULL,
  `Amount` varchar(45) DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Price` varchar(45) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`,`Item_ID`),
  KEY `Item_ID_idx` (`Item_ID`),
  CONSTRAINT `Item_ID` FOREIGN KEY (`Item_ID`) REFERENCES `item` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_catalog`
--

LOCK TABLES `item_in_catalog` WRITE;
/*!40000 ALTER TABLE `item_in_catalog` DISABLE KEYS */;
INSERT INTO `item_in_catalog` VALUES ('1176','6475','1','Simple Love','25','If you want to suprise someone important, this is the correct gift!'),('1176','7835','15','Simple Love','25','If you want to suprise someone important, this is the correct gift!'),('8361','1983','10','Del Mondo','45','A varient colors to make your friday more happines'),('8361','2234','1','Del Mondo','45','A varient colors to make your friday more happines'),('8361','4567','5','Del Mondo','45','A varient colors to make your friday more happines'),('9476','1234','5','Heaven','50','A beutiful gift for your lovers'),('9476','4896','1','Heaven','50','A beutiful gift for your lovers');
/*!40000 ALTER TABLE `item_in_catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_order`
--

DROP TABLE IF EXISTS `item_in_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_in_order` (
  `Order_ID` varchar(45) NOT NULL,
  `Item_ID` varchar(45) NOT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Order_ID`,`Item_ID`),
  KEY `Order_ID_idx` (`Order_ID`),
  KEY `ItemID_idx` (`Item_ID`),
  KEY `ItemCat_idx` (`Type`),
  CONSTRAINT `ID_Order` FOREIGN KEY (`Order_ID`) REFERENCES `order` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_order`
--

LOCK TABLES `item_in_order` WRITE;
/*!40000 ALTER TABLE `item_in_order` DISABLE KEYS */;
INSERT INTO `item_in_order` VALUES ('3788','9361','Catalog','1'),('6657','1234','Self','2');
/*!40000 ALTER TABLE `item_in_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `ID` varchar(45) NOT NULL,
  `Person_ID` varchar(45) DEFAULT NULL,
  `Delivery` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Payment_Type` varchar(45) DEFAULT NULL,
  `Price` varchar(45) DEFAULT NULL,
  `Store_ID` varchar(45) DEFAULT NULL,
  `Time` varchar(45) DEFAULT NULL,
  `Date` varchar(45) DEFAULT NULL,
  `Requested Time` varchar(45) DEFAULT NULL,
  `Requested Date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_idx` (`Person_ID`),
  KEY `Store_ID_idx` (`Store_ID`),
  CONSTRAINT `Person_ID` FOREIGN KEY (`Person_ID`) REFERENCES `payment_account` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Store_ID` FOREIGN KEY (`Store_ID`) REFERENCES `store` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('3788','666666666','Yes','Active','Credit','50','2287','13:00','1/1/2018','20:00','2/1/2018'),('6657','777777777','No','Active','Cash','20','3671','12:00','1/1/2018','17:00','1/1/2018');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_account`
--

DROP TABLE IF EXISTS `payment_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_account` (
  `ID` varchar(45) NOT NULL,
  `CreditCard` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Subscription` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ID` FOREIGN KEY (`ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_account`
--

LOCK TABLES `payment_account` WRITE;
/*!40000 ALTER TABLE `payment_account` DISABLE KEYS */;
INSERT INTO `payment_account` VALUES ('111111111','387566284667382','Active','Year'),('222222222','338746519037463','Active','Year'),('305131179','458032764731163','Active','Year'),('317573137','548027469077463','Active','Year'),('444444444','223489057633572','Active','Year'),('555555555','647589003547593','Active','Year'),('666666666','374566788394639','Active','Month'),('777777777','478883976590372','Active','Per Order'),('888888888','336774899575666','Block','Month'),('999999999','663748599373662','Active','Year');
/*!40000 ALTER TABLE `payment_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `ID` varchar(45) NOT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Privilege` varchar(45) DEFAULT NULL,
  `Online` varchar(45) DEFAULT NULL,
  `WWID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('111111111','Netanel','Azulay','Netanel','Chain Employee','no','8364'),('222222222','Aviram','Kunio','Aviram','Customer Service Employee','no','3759'),('305131179','Hadar','Rahamim','Hadar','Chain Manager','no','3344'),('317573137','Stas','Fishman','Stas','Store Manager','no','2248'),('444444444','Roman','Koifman','Roman','Service Expert','no','8312'),('555555555','Daniel','Elezra','Daniel','Store Employee','no','5224'),('666666666','Lior','Gal','Lior','Customer','no',NULL),('777777777','Marina','Minshin','Marina','Customer','no',NULL),('888888888','Eli','Bar-Yahalom','Eli','Customer','no',NULL),('999999999','Koko','Loko','Koko','Store Manager','no','7765');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `Date` varchar(45) NOT NULL,
  `Type` varchar(45) NOT NULL,
  `Path` varchar(200) DEFAULT NULL,
  `Store_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Date`,`Type`),
  KEY `StoreID_idx` (`Store_ID`),
  CONSTRAINT `StoreID` FOREIGN KEY (`Store_ID`) REFERENCES `store` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales` (
  `ID` varchar(45) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Discount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES ('5564','Christmas Sale','20');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `ID` varchar(45) NOT NULL,
  `Location` varchar(45) DEFAULT NULL,
  `Open_Hours` varchar(45) DEFAULT NULL,
  `Manager_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Manager_ID_idx` (`Manager_ID`),
  CONSTRAINT `Manager_ID` FOREIGN KEY (`Manager_ID`) REFERENCES `person` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('2287','Tel-Aviv','10:00-22:00','999999999'),('3671','Haifa','12:00-20:00','317573137');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey` (
  `ID` varchar(45) NOT NULL,
  `Date` varchar(45) DEFAULT NULL,
  `Q1` varchar(45) DEFAULT NULL,
  `Q2` varchar(45) DEFAULT NULL,
  `Q3` varchar(45) DEFAULT NULL,
  `Q4` varchar(45) DEFAULT NULL,
  `Q5` varchar(45) DEFAULT NULL,
  `Q6` varchar(45) DEFAULT NULL,
  `A1` varchar(45) DEFAULT NULL,
  `A2` varchar(45) DEFAULT NULL,
  `A3` varchar(45) DEFAULT NULL,
  `A4` varchar(45) DEFAULT NULL,
  `A5` varchar(45) DEFAULT NULL,
  `A6` varchar(45) DEFAULT NULL,
  `Conclusion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-27 12:31:51
