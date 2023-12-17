-- Create the database
CREATE DATABASE furnitured;
USE furnitured;

-- Create table account
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `acc_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `acc_type` enum('admin','seller','buyer') NOT NULL,
  PRIMARY KEY (`acc_id`),
  UNIQUE KEY `email` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create table admin
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `email` varchar(200) DEFAULT NULL,
  `acc_id` int DEFAULT NULL,
  PRIMARY KEY (`aid`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_admin_account_id` (`acc_id`),
  CONSTRAINT `fk_admin_account_id` FOREIGN KEY (`acc_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create table buyer
DROP TABLE IF EXISTS `buyer`;
CREATE TABLE `buyer` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `uemail` varchar(200) DEFAULT NULL,
  `uaddress` text,
  `acc_id` int DEFAULT NULL,
  `uphone` varchar(15) DEFAULT NULL,
  `bname` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `uemail` (`uemail`),
  KEY `fk_buyer_account_id` (`acc_id`),
  CONSTRAINT `fk_buyer_account_id` FOREIGN KEY (`acc_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create table category
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `cname` (`cname`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create table product
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pname` varchar(200) DEFAULT NULL,
  `cid` int DEFAULT NULL,
  `pqty` int DEFAULT NULL,
  `pprice` double DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `cname` varchar(200) DEFAULT NULL,
  `image` text,
  `statusProduct` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `cid` (`cid`),
  KEY `fk_seller_id` (`sid`),
  CONSTRAINT `fk_seller_id` FOREIGN KEY (`sid`) REFERENCES `seller` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create table purchase
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` int DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `p_date` varchar(20) DEFAULT NULL,
  `baddress` text,
  `receive_date` varchar(20) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `method` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`bid`),
  KEY `pid` (`pid`),
  KEY `fk_seller_id_purchase` (`sid`),
  CONSTRAINT `fk_seller_id_purchase` FOREIGN KEY (`sid`) REFERENCES `seller` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`bid`) REFERENCES `buyer` (`bid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create table seller
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `semail` varchar(100) DEFAULT NULL,
  `sphone` varchar(15) DEFAULT NULL,
  `saddress` text,
  `acc_id` int DEFAULT NULL,
  `sname` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `semail` (`semail`),
  KEY `fk_seller_account_id` (`acc_id`),
  CONSTRAINT `fk_seller_account_id` FOREIGN KEY (`acc_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
