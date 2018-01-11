-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: zerli
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `comments_survey`
--

DROP TABLE IF EXISTS `comments_survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments_survey` (
  `ID` varchar(45) NOT NULL,
  `Customer_ID` varchar(45) NOT NULL,
  `comment` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`,`Customer_ID`),
  KEY `CustomerID_idx` (`Customer_ID`),
  CONSTRAINT `Customer` FOREIGN KEY (`Customer_ID`) REFERENCES `person` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SurveyID` FOREIGN KEY (`ID`) REFERENCES `survey` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments_survey`
--

LOCK TABLES `comments_survey` WRITE;
/*!40000 ALTER TABLE `comments_survey` DISABLE KEYS */;
INSERT INTO `comments_survey` VALUES ('3','1','hadar\nnati\nstas\naviram\ncc\n\nhadar\nnati\nstas\naviram\nccdsdsds'),('3','2','ccccfffff\n\nccccfffffsssss');
/*!40000 ALTER TABLE `comments_survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `ID` int(11) NOT NULL,
  `Customer_ID` varchar(45) DEFAULT NULL,
  `Text` varchar(300) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Answer` varchar(45) DEFAULT NULL,
  `Compensation` varchar(45) DEFAULT NULL,
  `Date` varchar(45) DEFAULT NULL,
  `Hour` varchar(45) DEFAULT NULL,
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
INSERT INTO `complaint` VALUES (1928,'5','ddddd','Closed','sorry','12','05/01/2018','12:00'),(2385,'1','dsdsdsd','Closed','fdfgfgf','45','06/01/2018','10:21'),(2876,'666666666','The service in Tel-Aviv store was so bad','Closed','cdcdcdc','13','05/01/2018','17:00'),(2877,'1','Complain topic: Order an item\nhi i woild like to fdsjkcvjj  djsksa','Pending',NULL,NULL,'10/01/2018','11:54');
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
  `Price` float DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Image` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('1234','Kalanit','Red',10,'Flower',NULL),('1983','Yekinton','Red',6,'Flower',NULL),('2234','Turkish Vase','Brown',20,'Vase',NULL),('3490','Luli','Green',1.5,'Tape',NULL),('4567','Rose','Red',8,'Flower',NULL),('4896','Brazilien Vase','Red',30,'Vase',NULL),('6475','Kashmir','Pink',1.5,'Tape',NULL),('7589','Spain Vase','Green',25,'Vase',NULL),('7835','Narkis','Yellow',7,'Flower',NULL);
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
  `Name` varchar(45) DEFAULT NULL,
  `Price` float DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_catalog`
--

LOCK TABLES `item_in_catalog` WRITE;
/*!40000 ALTER TABLE `item_in_catalog` DISABLE KEYS */;
INSERT INTO `item_in_catalog` VALUES ('5555','Live',40,'If you want to suprise someone important, this is the correct gift!','src\\gui\\fxml\\buque.jpg'),('6666','Del Mondo',30,'A varient colors to make your friday more happines','src\\gui\\fxml\\planet.jpg'),('7777','Lover',25,'A beutiful gift for your lovers','src\\gui\\fxml\\rose.jpg');
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
  `Requested_Time` varchar(45) DEFAULT NULL,
  `Requested_Date` varchar(45) DEFAULT NULL,
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
INSERT INTO `order` VALUES ('3788','666666666','Yes','Active','Credit','50','2287','13:00','1/1/2018','20:00','2/1/2018'),('6578','1','Yes','Active','Credit','150','3671','18:00','10/01/2018','20:00','10/01/2018'),('6651','1','No','Active','Cash','20','3671','12:00','01/01/2018','19:59','10/01/2018'),('6655','1','No','Active','Cash','155','3671','12:00','01/01/2018','21:29','10/01/2018'),('6657','1','No','Canceled','Cash','20','3671','12:00','01/01/2018','22:55','10/01/2018');
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
  `Store_ID` varchar(45) DEFAULT NULL,
  `payment_accountcol` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`,`payment_accountcol`),
  KEY `StoreID_idx` (`Store_ID`),
  CONSTRAINT `ID` FOREIGN KEY (`ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `IDStore` FOREIGN KEY (`Store_ID`) REFERENCES `store` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_account`
--

LOCK TABLES `payment_account` WRITE;
/*!40000 ALTER TABLE `payment_account` DISABLE KEYS */;
INSERT INTO `payment_account` VALUES ('1','234112311354ee43','Active','Month','2287',''),('111111111','387566284667382','Active','Year','3671',''),('222222222','338746519037463','Block','Year','3671',''),('3','6666666666666666','Active','Year','2287',''),('305131179','458032764731163','Active','Year','3671',''),('317573137','548027469077463','Active','Year','3671',''),('444444444','223489057633572','Active','Year','3671',''),('555555555','647589003547593','Active','Year','2287',''),('666666666','374566788394639','Active','Month','2287',''),('777777777','478883976590372','Active','Per Order','2287',''),('888888888','336774899575666','Block','Month','3671',''),('999999999','663748599373662','Active','Year','2287','');
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
INSERT INTO `person` VALUES ('1','c','ghj','a','Store Manager','0','5555'),('111111111','Netanel','Azulay','Netanel','Chain Employee','0','8364'),('2','a','a','a','Customer Service Employee','0',NULL),('222222222','a','Kunio','a','Customer Service Employee','0','3759'),('3','d','dd','a','Store Employee','0','444'),('305131179','Hadar','Rahamim','Hadar','Chain Manager','0','3344'),('317573137','s','Fishman','s','Store Manager','0','2248'),('4','4','4','a','Service Expert','0','333'),('444444444','Roman','Koifman','Roman','Service Expert','0','8312'),('5','5','5','s','Customer','0','4444'),('555555555','Daniel','Elezra','Daniel','Store Employee','0','5224'),('666666666','Lior','Gal','Lior','Customer','0',NULL),('777777777','Marina','Minshin','Marina','Customer','0',NULL),('888888888','Eli','Bar-Yahalom','Eli','Customer','0',NULL),('999999999','Koko','Loko','Koko','Store Manager','0','7765');
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
  `Path` longblob,
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
  `Item_ID` varchar(45) NOT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Amount` varchar(45) DEFAULT NULL,
  `Sale_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`,`Item_ID`),
  KEY `Manager_ID_idx` (`Manager_ID`),
  KEY `IDSale_idx` (`Sale_ID`),
  CONSTRAINT `IDSale` FOREIGN KEY (`Sale_ID`) REFERENCES `sales` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Manager_ID` FOREIGN KEY (`Manager_ID`) REFERENCES `person` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('2287','Tel-Aviv','10:00-22:00','999999999','1234','Item','10',NULL),('2287','Tel-Aviv','10:00-22:00','999999999','1983','Item','20',NULL),('2287','Tel-Aviv','10:00-22:00','999999999','2234','Item','20',NULL),('2287','Tel-Aviv','10:00-22:00','999999999','5555','Catalog','10','5564'),('2287','Tel-Aviv','10:00-22:00','999999999','6666','Catalog','15',NULL),('2287','Tel-Aviv','10:00-22:00','999999999','7777','Catalog','20','5564'),('3671','Haifa','12:00-20:00','317573137','2234',NULL,'5',NULL),('6785','Carmiel','10:00-21:00','1','1234',NULL,'10',NULL);
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
  `Q1` varchar(200) DEFAULT NULL,
  `Q2` varchar(200) DEFAULT NULL,
  `Q3` varchar(200) DEFAULT NULL,
  `Q4` varchar(200) DEFAULT NULL,
  `Q5` varchar(200) DEFAULT NULL,
  `Q6` varchar(200) DEFAULT NULL,
  `A1` varchar(45) DEFAULT NULL,
  `A2` varchar(45) DEFAULT NULL,
  `A3` varchar(45) DEFAULT NULL,
  `A4` varchar(45) DEFAULT NULL,
  `A5` varchar(45) DEFAULT NULL,
  `A6` varchar(45) DEFAULT NULL,
  `Conclusion` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Num_Of_Participant` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
INSERT INTO `survey` VALUES ('0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'No Active',NULL),('1','30/12/2017','a','a','a','a','a','a','0','0','0','0','0','0','hadar\nstats\nnati\naviramdddd\nddddssss','No Active','0'),('2','02/01/2018','a','s','1','sdf','kh','hj','0','0','0','0','0','0','try','No Active','0'),('3','03/01/2018','c','c','c','c','c','c','5','5','5','5','6','5',NULL,'Active','2');
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

-- Dump completed on 2018-01-11 11:06:12
