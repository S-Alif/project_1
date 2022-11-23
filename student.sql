-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 23, 2022 at 05:41 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `student`
--

-- --------------------------------------------------------

--
-- Table structure for table `batch_cse27`
--

CREATE TABLE `batch_cse27` (
  `id` int(100) NOT NULL,
  `firstName` char(50) NOT NULL,
  `lastName` char(50) NOT NULL,
  `DOB` char(20) NOT NULL,
  `department` char(10) NOT NULL,
  `batch` int(5) NOT NULL,
  `shift` char(15) NOT NULL,
  `trimester` char(15) NOT NULL,
  `contact` char(60) NOT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `batch_cse27`
--

INSERT INTO `batch_cse27` (`id`, `firstName`, `lastName`, `DOB`, `department`, `batch`, `shift`, `trimester`, `contact`, `password`) VALUES
(27004, 'MD. Kamrul', 'Islam', '2022-11-03', 'CSE', 27, 'DAY', 'SPRING-22', 'Kamrul@new.mail.com', '12345678'),
(27005, 'Osman', 'Goni', '2022-11-30', 'CSE', 27, 'DAY', 'SPRING-22', 'new_mail@osman.com', '1234hello'),
(27006, 'Hasin Bin', 'Alam', '2022-10-04', 'CSE', 27, 'DAY', 'SPRING-22', 'hasin_bin@mail.com', '123456'),
(27007, 'MD. Shahariar', 'Alif', '2022-11-01', 'CSE', 27, 'DAY', 'SPRING-22', 'mail@mail.com', '1234567'),
(27009, 'Taswar', 'Sakib', '2022-11-01', 'CSE', 27, 'DAY', 'SPRING-22', 'mail@mail.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `batch_cse28`
--

CREATE TABLE `batch_cse28` (
  `id` int(15) NOT NULL,
  `firstName` char(50) NOT NULL,
  `lastName` char(50) NOT NULL,
  `DOB` char(20) NOT NULL,
  `department` char(10) NOT NULL,
  `batch` int(5) NOT NULL,
  `shift` char(15) NOT NULL,
  `trimester` char(15) NOT NULL,
  `contact` char(60) NOT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `batch_cse28`
--

INSERT INTO `batch_cse28` (`id`, `firstName`, `lastName`, `DOB`, `department`, `batch`, `shift`, `trimester`, `contact`, `password`) VALUES
(28001, 'Sirajul', 'Moni', '2000-11-01', 'CSE', 28, 'DAY', 'SUMMER-22', 'new@mail.com', 'iammoni'),
(28002, 'Md. Parvez', 'Hasan', '2022-11-08', 'CSE', 28, 'DAY', 'SUMMER-22', 'parvez@mail.com', 'heroguy'),
(28003, 'Abdullah Mohammad', 'Hosain', '2022-11-01', 'CSE', 28, 'EVENING', 'SUMMER-22', 'abdullah@mail.com', '1234567'),
(28004, 'Saiful Islam', 'Sakib', '2022-11-03', 'CSE', 28, 'DAY', 'SUMMER-22', 'mail@mail.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `batch_eee27`
--

CREATE TABLE `batch_eee27` (
  `id` int(100) NOT NULL,
  `firstName` char(50) NOT NULL,
  `lastName` char(50) NOT NULL,
  `DOB` char(20) NOT NULL,
  `department` char(10) NOT NULL,
  `batch` int(5) NOT NULL,
  `shift` char(15) NOT NULL,
  `trimester` char(15) NOT NULL,
  `contact` char(60) NOT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `batch_eee27`
--

INSERT INTO `batch_eee27` (`id`, `firstName`, `lastName`, `DOB`, `department`, `batch`, `shift`, `trimester`, `contact`, `password`) VALUES
(27001, 'Sadman', 'Bahar', '2022-11-13', 'EEE', 27, 'EVENING', 'SPRING-22', 'mai@mail.com', 'gilpahar200');

-- --------------------------------------------------------

--
-- Table structure for table `cse27_result`
--

CREATE TABLE `cse27_result` (
  `id` int(15) NOT NULL,
  `department` char(50) NOT NULL,
  `trimester` char(50) NOT NULL,
  `sub1` int(5) DEFAULT NULL,
  `sub2` int(5) DEFAULT NULL,
  `sub3` int(5) DEFAULT NULL,
  `sub4` int(5) DEFAULT NULL,
  `sub5` int(5) DEFAULT NULL,
  `sub6` int(5) DEFAULT NULL,
  `totalLetterGrade` char(5) DEFAULT NULL,
  `totalGrade` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cse27_result`
--

INSERT INTO `cse27_result` (`id`, `department`, `trimester`, `sub1`, `sub2`, `sub3`, `sub4`, `sub5`, `sub6`, `totalLetterGrade`, `totalGrade`) VALUES
(27003, 'CSE', 'SPRING-22', 90, 89, 69, 80, 77, 90, 'A', 3.8333333333333335),
(27004, 'CSE', 'SPRING-22', 90, 67, 89, 55, 98, 94, 'A-', 3.6666666666666665),
(27005, 'CSE', 'SPRING-22', 90, 89, 70, 89, 69, 96, 'A', 3.7916666666666665),
(27006, 'CSE', 'SPRING-22', 89, 90, 78, 69, 96, 78, 'A', 3.7916666666666665),
(27007, 'CSE', 'SPRING-22', 98, 90, 87, 88, 72, 77, 'A', 3.875),
(27008, 'CSE', 'SPRING-22', 78, 90, 89, 90, 89, 79, 'A', 3.9166666666666665),
(27009, 'CSE', 'SPRING-22', 78, 86, 90, 77, 88, 97, 'A', 3.9166666666666665),
(27006, 'CSE', 'SUMMER-22', 90, 86, 90, 92, 77, 78, 'A', 3.9166666666666665),
(27004, 'CSE', 'SUMMER-22', 89, 74, 98, 90, 78, 67, 'A', 3.75);

-- --------------------------------------------------------

--
-- Table structure for table `cse28_result`
--

CREATE TABLE `cse28_result` (
  `id` int(15) NOT NULL,
  `department` char(50) NOT NULL,
  `trimester` char(50) NOT NULL,
  `sub1` int(5) DEFAULT NULL,
  `sub2` int(5) DEFAULT NULL,
  `sub3` int(5) DEFAULT NULL,
  `sub4` int(5) DEFAULT NULL,
  `sub5` int(5) DEFAULT NULL,
  `sub6` int(5) DEFAULT NULL,
  `totalLetterGrade` char(5) DEFAULT NULL,
  `totalGrade` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cse28_result`
--

INSERT INTO `cse28_result` (`id`, `department`, `trimester`, `sub1`, `sub2`, `sub3`, `sub4`, `sub5`, `sub6`, `totalLetterGrade`, `totalGrade`) VALUES
(28001, 'CSE', 'SUMMER-22', 90, 89, 97, 87, 69, 99, 'A', 3.875),
(28002, 'CSE', 'SUMMER-22', 80, 81, 90, 91, 69, 70, 'A', 3.7916666666666665),
(28003, 'CSE', 'SUMMER-22', 89, 67, 78, 89, 90, 97, 'A', 3.8333333333333335),
(28004, 'CSE', 'SUMMER-22', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `eee27_result`
--

CREATE TABLE `eee27_result` (
  `id` int(15) NOT NULL,
  `department` char(50) NOT NULL,
  `trimester` char(50) NOT NULL,
  `sub1` int(5) DEFAULT NULL,
  `sub2` int(5) DEFAULT NULL,
  `sub3` int(5) DEFAULT NULL,
  `sub4` int(5) DEFAULT NULL,
  `sub5` int(5) DEFAULT NULL,
  `sub6` int(5) DEFAULT NULL,
  `totalLetterGrade` char(5) DEFAULT NULL,
  `totalGrade` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `eee27_result`
--

INSERT INTO `eee27_result` (`id`, `department`, `trimester`, `sub1`, `sub2`, `sub3`, `sub4`, `sub5`, `sub6`, `totalLetterGrade`, `totalGrade`) VALUES
(27001, 'EEE', 'SPRING-22', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payment_cse27`
--

CREATE TABLE `payment_cse27` (
  `id` int(100) UNSIGNED NOT NULL,
  `department` char(100) NOT NULL,
  `trimester` char(100) DEFAULT NULL,
  `midTerm` int(100) DEFAULT 0,
  `finalTerm` int(100) DEFAULT 0,
  `paidFee` int(100) NOT NULL DEFAULT 0,
  `dueFee` int(100) NOT NULL DEFAULT 30000
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment_cse27`
--

INSERT INTO `payment_cse27` (`id`, `department`, `trimester`, `midTerm`, `finalTerm`, `paidFee`, `dueFee`) VALUES
(27003, 'CSE', 'SPRING-22', 15000, 0, 15000, 15000),
(27004, 'CSE', 'SPRING-22', 15000, 15000, 30000, 0),
(27005, 'CSE', 'SPRING-22', 15000, 0, 15000, 15000),
(27006, 'CSE', 'SPRING-22', 15000, 0, 15000, 15000),
(27007, 'CSE', 'SPRING-22', 15000, 0, 15000, 15000),
(27008, 'CSE', 'SPRING-22', 15000, 0, 15000, 15000),
(27009, 'CSE', 'SPRING-22', 0, 0, 0, 30000),
(27006, 'CSE', 'SUMMER-22', 0, 0, 0, 30000),
(27004, 'CSE', 'SUMMER-22', 15000, 0, 15000, 15000);

-- --------------------------------------------------------

--
-- Table structure for table `payment_cse28`
--

CREATE TABLE `payment_cse28` (
  `id` int(100) NOT NULL,
  `department` char(100) NOT NULL,
  `trimester` char(100) DEFAULT NULL,
  `midTerm` int(100) DEFAULT 0,
  `finalTerm` int(100) DEFAULT 0,
  `paidFee` int(100) NOT NULL DEFAULT 0,
  `dueFee` int(100) NOT NULL DEFAULT 30000
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment_cse28`
--

INSERT INTO `payment_cse28` (`id`, `department`, `trimester`, `midTerm`, `finalTerm`, `paidFee`, `dueFee`) VALUES
(28001, 'CSE', 'SUMMER-22', 15000, 15000, 30000, 0),
(28002, 'CSE', 'SUMMER-22', 15000, 15000, 30000, 0),
(28003, 'CSE', 'SUMMER-22', 15000, 15000, 30000, 0),
(28004, 'CSE', 'SUMMER-22', 15000, 15000, 30000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `payment_eee27`
--

CREATE TABLE `payment_eee27` (
  `id` int(100) UNSIGNED NOT NULL,
  `department` char(15) NOT NULL,
  `trimester` char(15) NOT NULL,
  `midTerm` int(50) DEFAULT 0,
  `finalTerm` int(50) DEFAULT 0,
  `paidFee` int(50) NOT NULL DEFAULT 0,
  `dueFee` int(50) NOT NULL DEFAULT 30000
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment_eee27`
--

INSERT INTO `payment_eee27` (`id`, `department`, `trimester`, `midTerm`, `finalTerm`, `paidFee`, `dueFee`) VALUES
(27001, 'EEE', 'SPRING-22', 0, 0, 0, 30000);

-- --------------------------------------------------------

--
-- Table structure for table `re_registration27`
--

CREATE TABLE `re_registration27` (
  `id` int(15) NOT NULL,
  `department` char(15) NOT NULL,
  `trimester` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `re_registration27`
--

INSERT INTO `re_registration27` (`id`, `department`, `trimester`) VALUES
(27006, 'CSE', 'SUMMER-22'),
(27004, 'CSE', 'SUMMER-22');

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `department` char(5) NOT NULL,
  `batch` int(5) NOT NULL,
  `trimester` char(15) NOT NULL,
  `sub1` char(100) NOT NULL,
  `sub2` char(100) NOT NULL,
  `sub3` char(100) NOT NULL,
  `sub4` char(100) NOT NULL,
  `sub5` char(100) NOT NULL,
  `sub6` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`department`, `batch`, `trimester`, `sub1`, `sub2`, `sub3`, `sub4`, `sub5`, `sub6`) VALUES
('CSE', 27, 'SUMMER-22', 'Electrical Engineering', 'Electrical Engineering Lab', 'Structured Programming', 'Structured Programming Lab', 'Differential Calculus', 'Bangladesh Studies'),
('CSE', 27, 'SPRING-22', 'Physics-1', 'Physics-1 Lab', 'Computer Fundamentals', 'Computer Fundamentals Lab', 'Composition', 'History of BD'),
('CSE', 27, 'FALL-22', 'Electronics', 'Electronics Lab', 'Discrete Math', 'OOP', 'OOP Lab', 'Integral Calculus & Vector'),
('CSE', 28, 'SUMMER-22', 'Physics-1', 'Physics-1 Lab', 'Computer Fundamentals', 'Computer Fundamentals Lab', 'Composition', 'History of BD'),
('CSE', 28, 'FALL-22', 'Electrical Engineering', 'Electrical Engineering Lab', 'Structured Programming', 'Structured Programming Lab', 'Differential Calculus', 'Bangladesh Studies');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `batch_cse27`
--
ALTER TABLE `batch_cse27`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `batch_cse28`
--
ALTER TABLE `batch_cse28`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `batch_eee27`
--
ALTER TABLE `batch_eee27`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `batch_cse27`
--
ALTER TABLE `batch_cse27`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27010;

--
-- AUTO_INCREMENT for table `batch_cse28`
--
ALTER TABLE `batch_cse28`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28005;

--
-- AUTO_INCREMENT for table `batch_eee27`
--
ALTER TABLE `batch_eee27`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27002;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
