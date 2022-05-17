-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2022 at 07:51 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `janjijiwa`
--

-- --------------------------------------------------------

--
-- Table structure for table `baverages`
--

CREATE TABLE `baverages` (
  `BaveragesId` char(5) NOT NULL,
  `BaverageName` varchar(30) NOT NULL,
  `BaverageType` varchar(30) NOT NULL,
  `BaveragePrice` varchar(30) NOT NULL,
  `BaverageStock` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `baverages`
--

INSERT INTO `baverages` (`BaveragesId`, `BaverageName`, `BaverageType`, `BaveragePrice`, `BaverageStock`) VALUES
('BE001', 'ramen', 'mie', '20000', 16),
('BE002', 'pop Ice', 'Smoothies', '20000', 22),
('BE003', 'MieSosis', 'Boba', '20000', 13);

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserId` char(5) NOT NULL,
  `BaverageId` char(5) NOT NULL,
  `quantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaction`
--

CREATE TABLE `detailtransaction` (
  `TransactionId` char(5) NOT NULL,
  `BaverageId` char(5) NOT NULL,
  `quantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailtransaction`
--

INSERT INTO `detailtransaction` (`TransactionId`, `BaverageId`, `quantity`) VALUES
('TR003', 'BE002', 2),
('TR003', 'BE003', 1),
('TR004', 'BE001', 4),
('TR004', 'BE002', 4);

-- --------------------------------------------------------

--
-- Table structure for table `headertransaction`
--

CREATE TABLE `headertransaction` (
  `TransactionId` char(5) NOT NULL,
  `UserId` char(5) NOT NULL,
  `TransationDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `headertransaction`
--

INSERT INTO `headertransaction` (`TransactionId`, `UserId`, `TransationDate`) VALUES
('TR003', 'US002', '2022-01-18'),
('TR004', 'US002', '2022-01-18');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserId` char(5) NOT NULL,
  `UserName` varchar(30) NOT NULL,
  `UserEmail` varchar(50) NOT NULL,
  `UserPassword` varchar(30) NOT NULL,
  `UserGender` varchar(10) NOT NULL,
  `UserAddress` varchar(255) NOT NULL,
  `UserPhone` varchar(255) NOT NULL,
  `UserRole` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserId`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserAddress`, `UserPhone`, `UserRole`) VALUES
('US001', 'Admin', 'admin', 'admin2', 'Male', 'Jakarta Street', '085655478354', 'Admin'),
('US002', 'Customer', 'customer', 'customer', 'Male', 'jakarta', '085647895456', 'Customer'),
('US003', 'darule', 'buid@gmail.com', '12345', 'Female', 'adsfs Street', '092345667788', 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `baverages`
--
ALTER TABLE `baverages`
  ADD PRIMARY KEY (`BaveragesId`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`UserId`,`BaverageId`);

--
-- Indexes for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD PRIMARY KEY (`TransactionId`,`BaverageId`);

--
-- Indexes for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD PRIMARY KEY (`TransactionId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
