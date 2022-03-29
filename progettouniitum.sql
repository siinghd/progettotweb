-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 29, 2022 at 06:47 PM
-- Server version: 8.0.28-0ubuntu0.20.04.3
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `progettouniitum`
--

-- --------------------------------------------------------

--
-- Table structure for table `corso`
--

CREATE TABLE `corso` (
  `id` int NOT NULL,
  `titolo` varchar(255) NOT NULL,
  `softdelete` tinyint NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `corso`
--

INSERT INTO `corso` (`id`, `titolo`, `softdelete`) VALUES
(3, 'Matemtica', 1),
(4, 'Informatica 1', 0),
(5, 'Informatica 2', 0),
(6, 'Informatica 3', 0),
(11, 'Matematica', 0);

-- --------------------------------------------------------

--
-- Table structure for table `corsodocente`
--

CREATE TABLE `corsodocente` (
  `idcorso` int NOT NULL,
  `iddocente` int NOT NULL,
  `softdelete` tinyint NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `corsodocente`
--

INSERT INTO `corsodocente` (`idcorso`, `iddocente`, `softdelete`) VALUES
(3, 6, 1),
(3, 7, 1),
(4, 6, 0),
(5, 6, 0),
(6, 6, 0),
(6, 7, 0);

-- --------------------------------------------------------

--
-- Table structure for table `docente`
--

CREATE TABLE `docente` (
  `id` int NOT NULL,
  `idutente` int NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `softdelete` tinyint NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `docente`
--

INSERT INTO `docente` (`id`, `idutente`, `nome`, `cognome`, `softdelete`) VALUES
(6, 19, 'Simone', 'Multari', 0),
(7, 42, 'Singh ', 'Billy', 0),
(8, 45, 'sdg', 'sdg', 0),
(9, 46, 'BillyWinka', '', 1),
(10, 47, 'asfsaf', 'Wonka', 1),
(11, 48, 'asf', 'asf', 1),
(12, 50, 'asfasf', 'asfasf', 1),
(13, 52, 'asfsaf', 'asfasf', 1),
(14, 54, 'saf', 'asf', 1),
(15, 56, 'asfasgasg', 'asfasg', 1),
(16, 58, 'asfsagas', 'asfgasg', 1),
(17, 59, 'asfsgasgsa', 'asfastgag', 1),
(18, 60, 'sdgew', 'gwet', 1),
(19, 61, 'asfsaf', 'sdhsdh', 0),
(20, 62, 'asfsafsdg', 'sdhsdhsdg', 0),
(21, 63, 'safegdsg', 'sdgegsg', 1),
(22, 64, 'sdg', 'sdh', 1),
(23, 65, 'sdgdsh', 'sdhsdhew', 1),
(24, 66, 'WowJon', 'dsgsd', 1);

-- --------------------------------------------------------

--
-- Table structure for table `ripetizione`
--

CREATE TABLE `ripetizione` (
  `id` int NOT NULL,
  `idcorso` int NOT NULL,
  `idutente` int NOT NULL,
  `data` date NOT NULL,
  `iddocente` int NOT NULL,
  `status` tinyint UNSIGNED NOT NULL DEFAULT '1',
  `ora` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `ripetizione`
--

INSERT INTO `ripetizione` (`id`, `idcorso`, `idutente`, `data`, `iddocente`, `status`, `ora`) VALUES
(9, 3, 19, '2022-02-03', 6, 1, '15:00'),
(10, 3, 20, '2022-02-07', 6, 1, '15:00'),
(11, 3, 19, '2022-02-07', 7, 1, '16:00');

-- --------------------------------------------------------

--
-- Table structure for table `utente`
--

CREATE TABLE `utente` (
  `id` int NOT NULL,
  `accountname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ruolo` tinyint(1) NOT NULL
) ;

--
-- Dumping data for table `utente`
--

INSERT INTO `utente` (`id`, `accountname`, `password`, `ruolo`) VALUES
(19, 'MultariSimone@unito.it', '$2a$10$L/nH2M19wADYBGNeJ7DLx.K/KimCRg/lrwx8SDis6mp0IdbReS0Zm', 2),
(20, 'test123@gmail.com', '$2a$10$mSjHzZd.wCqfDDdzTlRe/ep5HGNpo9nm13x.77rUfgzB1PaAPx.eW', 1),
(21, 'singh@gmail.com', '$2a$10$BcLV/WHFyM//4rXf5rQCF.11NrY3VFQKzkDez3kzNBX0ol2BAziQu', 1),
(23, 'pippo@gmail.com', '$2a$10$5LNKUiNMRssXDeglExfw0O25u9wPVSKJhq5mNPZ9Y6Ifzb3poICf2', 1),
(24, 'pipp@gmail.com', '$2a$10$90Sx/BKaPGszZ5QJ1IvluOe6RCKdLQMs.RIQj8C/LnhO0jYlajRyy', 1),
(25, 'owow@gmail.com', '$2a$10$FGAiWI0gAey1a6ojAoaJ5.5EBXmTxT1OMAW8JSKJL8Ls2j0VMh/wi', 1),
(29, 'oUwow@gmail.com', '$2a$10$jOqUmxPHsgk9.2zD8aIGzeSj5EK4CH0T3NX9otUNAXAw0i38L6sQy', 1),
(30, 'oU54wow@gmail.com', '$2a$10$a3VGFycH5GFRg4lNslrCKOEME2x5mbJEOh77CZFyYKASKKHW8u4Y2', 1),
(32, 'asfasf@gmail.com', '$2a$10$qs9MjlMhwtO.LBzAnUI.R.LvDZPXSj0D/xNMZREnIUFlhu4UT7W6i', 1),
(36, 'singhtest@gmail.com', '$2a$10$5Wnpkm445FQl2SaJxAkvruzIcSZquqMoRsZPOTBg1Wn3v2stzQSbC', 1),
(42, 'BillySingh @unito.it', '$2a$10$jJY8FummW1zT4tXpYeNsPeZD5eG41wsvgari1defQEpt3QukzZNAG', 2),
(43, 'mondino@mondino.com', '$2a$10$HnzvyMV.G729hzSv.eTGEepqaU9jX7H2OtjR.u81/jipxS3eo.UfO', 2),
(44, 'test@tester.com', '$2a$10$K1.pN/GrBXB.d31EjAGiSeOMQgSRVxgUbGBi2Q102isI7fkoMRcqm', 2),
(45, 'sdgsdg@unito.it', '$2a$10$KyqBNE5eihps.LeM31NI8OQQarMAATe/U9A1ZbrfS.Mg8IBcPW4JS', 2),
(46, 'BillyWinka@unito.it', '$2a$10$ryV4GWhJ0AegcMG3ds45Z.WLlFMB2e60JtgbSc1t9ds1WFcq5LBHq', 2),
(47, 'Wonkaasfsaf@unito.it', '$2a$10$3uafFTwmD1O8L1gCiObbled8gRdeMC5zq.nJ3wHhjin774c0GcjgS', 2),
(48, 'asfasf@unito.it', '$2a$10$Se6uXh7SJ17jX2szdYbI5ucPFaGihRI3Ks4J93C0EJW7PeOoiWSce', 2),
(50, 'asfasfasfasf@unito.it', '$2a$10$gDSz7fWPATHgvYVOwPSGXeGWD6t3wtPmJM.sEgN1U/n1dMkQUDmMa', 2),
(52, 'asfasfasfsaf@unito.it', '$2a$10$KhzqWbI0BAnk30HcVvGNauDdLvaIrfSMU6GyISBTjxlFYGlatV8q6', 2),
(54, 'asfsaf@unito.it', '$2a$10$x8l2ac2pb0Ed45QopWSOFu.JyKrLSRL2ghVtZjGSQDl/VTW6nNp8S', 2),
(56, 'asfasgasfasgasg@unito.it', '$2a$10$enbd8WqRZLiiM52nUD8RRuFs9VxSu/YD9DfMZXJlH.yd1d6Esna42', 2),
(58, 'asfgasgasfsagas@unito.it', '$2a$10$2YbPNG.3192G9GRFaE/65uA2H7TTz50Pgkgx16lwqhQf2K2qhjM1m', 2),
(59, 'asfastgagasfsgasgsa@unito.it', '$2a$10$dBaQ6bokB7i5.F/TQPe1KuoVyNsEl/mIaRBLX3nargXezVvOsqGiu', 2),
(60, 'gwetsdgew@unito.it', '$2a$10$IpdF36HozDkLN5D0I5pN..uBBehA3fXEiIzM.6AN3J4sp37SOouBm', 2),
(61, 'sdhsdhasfsaf@unito.it', '$2a$10$i11zckxiZ87K3ixYDCCGZePZpay3pdIqUIbirHnB8KRMpXeIEbQiW', 2),
(62, 'sdhsdhsdgasfsafsdg@unito.it', '$2a$10$F9pvvi29OosJ/99ZXusRaOBESjJES/72tcScVAzjhvzpLyoXKq7aO', 2),
(63, 'sdgegsgsafegdsg@unito.it', '$2a$10$gQKip6uso6H2weuJbbWBK.lM5PZ4zT0l9crsBIQvHO6j99KdKjCjK', 2),
(64, 'sdhsdg@unito.it', '$2a$10$7nY1tFL5ou.7Y1r5dQPg/OUQ214bI4uRXReJUsaeogrq3j3QqFc36', 2),
(65, 'sdhsdhewsdgdsh@unito.it', '$2a$10$jZmoKSqtSHcyPG0NlJSAfeETlQC2Guw2qMZNyhu7Y1bRN5YkQkHke', 2),
(66, 'dsgsdWowJon@unito.it', '$2a$10$SuhzksfgKNvTa0Pqz7B0ouWDO8fyb1K0FhH0vOGlWdX4hdlm99/nK', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `corso`
--
ALTER TABLE `corso`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `corsodocente`
--
ALTER TABLE `corsodocente`
  ADD PRIMARY KEY (`idcorso`,`iddocente`),
  ADD KEY `fk_iddocente` (`iddocente`);

--
-- Indexes for table `docente`
--
ALTER TABLE `docente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_docenteut` (`idutente`);

--
-- Indexes for table `ripetizione`
--
ALTER TABLE `ripetizione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idutente` (`idutente`),
  ADD KEY `fk_iddocenterip` (`iddocente`),
  ADD KEY `fk_idcorsorip` (`idcorso`);

--
-- Indexes for table `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `accountname` (`accountname`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `corso`
--
ALTER TABLE `corso`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `docente`
--
ALTER TABLE `docente`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `ripetizione`
--
ALTER TABLE `ripetizione`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `corsodocente`
--
ALTER TABLE `corsodocente`
  ADD CONSTRAINT `fk_idcorso` FOREIGN KEY (`idcorso`) REFERENCES `corso` (`id`),
  ADD CONSTRAINT `fk_iddocente` FOREIGN KEY (`iddocente`) REFERENCES `docente` (`id`);

--
-- Constraints for table `docente`
--
ALTER TABLE `docente`
  ADD CONSTRAINT `fk_docenteut` FOREIGN KEY (`idutente`) REFERENCES `utente` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `ripetizione`
--
ALTER TABLE `ripetizione`
  ADD CONSTRAINT `fk_idcorsorip` FOREIGN KEY (`idcorso`) REFERENCES `corsodocente` (`idcorso`),
  ADD CONSTRAINT `fk_iddocenterip` FOREIGN KEY (`iddocente`) REFERENCES `corsodocente` (`iddocente`),
  ADD CONSTRAINT `fk_idutente` FOREIGN KEY (`idutente`) REFERENCES `utente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
