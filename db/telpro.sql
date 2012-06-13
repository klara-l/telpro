-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 01. Jan 2000 um 03:10
-- Server Version: 5.5.16
-- PHP-Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `telpro`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Action`
--

DROP TABLE IF EXISTS `Action`;
CREATE TABLE IF NOT EXISTS `Action` (
  `idAction` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Description` text,
  PRIMARY KEY (`idAction`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Devices`
--

DROP TABLE IF EXISTS `Devices`;
CREATE TABLE IF NOT EXISTS `Devices` (
  `idDevices` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idDevices`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Event`
--

DROP TABLE IF EXISTS `Event`;
CREATE TABLE IF NOT EXISTS `Event` (
  `idEvent` int(11) NOT NULL AUTO_INCREMENT,
  `EventType_idEventType` int(11) NOT NULL,
  `Timestamp` timestamp NULL DEFAULT NULL,
  `Sensor_idSensor` varchar(16) DEFAULT NULL,
  `Trigger_idTrigger` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEvent`,`EventType_idEventType`),
  KEY `fk_Event_Sensor1` (`Sensor_idSensor`),
  KEY `fk_Event_Trigger1` (`Trigger_idTrigger`),
  KEY `fk_Event_EventType1` (`EventType_idEventType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `Event`
--

INSERT INTO `Event` (`idEvent`, `EventType_idEventType`, `Timestamp`, `Sensor_idSensor`, `Trigger_idTrigger`) VALUES
(2, 1, '2000-01-01 01:22:33', 'testnode', NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `EventType`
--

DROP TABLE IF EXISTS `EventType`;
CREATE TABLE IF NOT EXISTS `EventType` (
  `idEventType` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Description` text,
  PRIMARY KEY (`idEventType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `EventType`
--

INSERT INTO `EventType` (`idEventType`, `Name`, `Description`) VALUES
(1, 'Testevent', 'asdfghj');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `EventType_has_Action`
--

DROP TABLE IF EXISTS `EventType_has_Action`;
CREATE TABLE IF NOT EXISTS `EventType_has_Action` (
  `EventType_idEventType` int(11) NOT NULL,
  `Action_idAction` int(11) NOT NULL,
  PRIMARY KEY (`EventType_idEventType`,`Action_idAction`),
  KEY `fk_EventType_has_Action_Action1` (`Action_idAction`),
  KEY `fk_EventType_has_Action_EventType1` (`EventType_idEventType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Property`
--

DROP TABLE IF EXISTS `Property`;
CREATE TABLE IF NOT EXISTS `Property` (
  `idProperty` int(11) NOT NULL AUTO_INCREMENT,
  `PropertyType_idPropertyType` int(11) NOT NULL,
  `Sensor_idSensor` varchar(16) NOT NULL,
  `Value` double DEFAULT NULL,
  `Timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idProperty`,`PropertyType_idPropertyType`,`Sensor_idSensor`),
  KEY `fk_Property_PropertyType1` (`PropertyType_idPropertyType`),
  KEY `fk_Property_Sensor1` (`Sensor_idSensor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=56 ;

--
-- Daten für Tabelle `Property`
--

INSERT INTO `Property` (`idProperty`, `PropertyType_idPropertyType`, `Sensor_idSensor`, `Value`, `Timestamp`) VALUES
(2, 1, 'testnode', 21.1, '2012-06-05 19:43:26'),
(3, 1, 'testnode', 23, '2012-06-05 19:50:51'),
(4, 1, 'testnode', 23.7, '2012-06-05 19:51:19'),
(5, 1, 'testnode', 21.2, '2012-06-05 20:13:27'),
(6, 1, 'testnode', 21.6, '2012-06-05 20:16:49'),
(7, 1, 'testnode', 21.8, '2012-06-05 20:19:05'),
(8, 1, 'testnode', 21.9, '2012-06-05 20:21:15'),
(9, 1, 'testnode', 22.1, '2012-06-05 20:23:22'),
(10, 1, 'testnode', 22.2, '2012-06-05 20:27:09'),
(11, 1, 'testnode', 22.2, '2012-06-05 20:27:33'),
(12, 1, 'testnode', 22.2, '2012-06-05 20:27:48'),
(13, 1, 'testnode', 22.2, '2012-06-05 20:28:03'),
(14, 1, 'testnode', 22.2, '2012-06-05 20:28:17'),
(15, 1, 'testnode', 22.2, '2012-06-05 20:28:32'),
(16, 1, 'testnode', 22.2, '2012-06-05 20:28:47'),
(17, 1, 'testnode', 22.1, '2012-06-05 20:29:02'),
(18, 1, 'testnode', 22.1, '2012-06-05 20:29:16'),
(19, 1, 'testnode', 22, '2012-06-05 20:29:31'),
(20, 1, 'testnode', 22, '2012-06-05 20:29:46'),
(21, 1, 'testnode', 22, '2012-06-05 20:30:01'),
(22, 1, 'testnode', 22, '2012-06-05 20:30:16'),
(23, 1, 'testnode', 21.9, '2012-06-05 20:30:30'),
(24, 1, 'testnode', 21.9, '2012-06-05 20:30:45'),
(25, 1, 'testnode', 21.9, '2012-06-05 20:31:00'),
(26, 1, 'testnode', 21.9, '2012-06-05 20:31:15'),
(27, 1, 'testnode', 21.8, '2012-06-05 20:31:29'),
(28, 1, 'testnode', 21.8, '2012-06-05 20:31:44'),
(29, 1, 'testnode', 21.8, '2012-06-05 20:31:59'),
(30, 1, 'testnode', 21.8, '2012-06-05 20:32:14'),
(31, 1, 'testnode', 21.8, '2012-06-05 20:32:28'),
(32, 1, 'testnode', 21.8, '2012-06-05 20:32:43'),
(33, 1, 'testnode', 21.7, '2012-06-05 20:32:58'),
(34, 1, 'testnode', 21.7, '2012-06-05 20:33:13'),
(35, 1, 'testnode', 21.7, '2012-06-05 20:33:27'),
(36, 1, 'testnode', 21.7, '2012-06-05 20:33:42'),
(37, 1, 'testnode', 21.7, '2012-06-05 20:33:57'),
(38, 1, 'testnode', 21.7, '2012-06-05 20:34:12'),
(39, 1, 'testnode', 21.7, '2012-06-05 20:34:26'),
(40, 1, 'testnode', 21.7, '2012-06-05 20:34:41'),
(41, 1, 'testnode', 21.7, '2012-06-05 20:34:56'),
(42, 1, 'testnode', 21.7, '2012-06-05 20:35:11'),
(43, 1, 'testnode', 21.6, '2012-06-05 20:35:25'),
(44, 1, 'testnode', 21.6, '2012-06-05 20:35:40'),
(45, 1, 'testnode', 21.6, '2012-06-05 20:35:55'),
(46, 1, 'testnode', 21.6, '2012-06-05 20:36:10'),
(47, 1, 'testnode', 21.6, '2012-06-05 20:36:24'),
(48, 1, 'testnode', 21.6, '2012-06-05 20:36:39'),
(49, 1, 'testnode', 21.6, '2012-06-05 20:36:54'),
(50, 1, 'testnode', 21.6, '2012-06-05 20:37:09'),
(51, 1, 'testnode', 21.5, '2012-06-05 20:37:23'),
(52, 1, 'testnode', 21.5, '2012-06-05 20:37:38'),
(53, 1, 'testnode', 21.5, '2012-06-05 20:37:53'),
(54, 1, 'testnode', 21.5, '2012-06-05 20:38:08'),
(55, 1, 'testnode', 21.5, '2012-06-05 20:38:23');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `PropertyType`
--

DROP TABLE IF EXISTS `PropertyType`;
CREATE TABLE IF NOT EXISTS `PropertyType` (
  `idPropertyType` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPropertyType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `PropertyType`
--

INSERT INTO `PropertyType` (`idPropertyType`, `Name`) VALUES
(1, 'temperature'),
(2, 'humidity');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Sensor`
--

DROP TABLE IF EXISTS `Sensor`;
CREATE TABLE IF NOT EXISTS `Sensor` (
  `idSensor` varchar(16) NOT NULL,
  `IP` int(11) DEFAULT NULL,
  `Location` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSensor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `Sensor`
--

INSERT INTO `Sensor` (`idSensor`, `IP`, `Location`) VALUES
('testnode', 9, 'home');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Sensor_has_PropertyType`
--

DROP TABLE IF EXISTS `Sensor_has_PropertyType`;
CREATE TABLE IF NOT EXISTS `Sensor_has_PropertyType` (
  `Sensor_idSensor` varchar(16) NOT NULL,
  `PropertyType_idPropertyType` int(11) NOT NULL,
  PRIMARY KEY (`Sensor_idSensor`,`PropertyType_idPropertyType`),
  KEY `fk_Sensor_has_PropertyType_PropertyType1` (`PropertyType_idPropertyType`),
  KEY `fk_Sensor_has_PropertyType_Sensor1` (`Sensor_idSensor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Sensor_has_Trigger`
--

DROP TABLE IF EXISTS `Sensor_has_Trigger`;
CREATE TABLE IF NOT EXISTS `Sensor_has_Trigger` (
  `Sensor_idSensor` varchar(16) NOT NULL,
  `Trigger_idTrigger` int(11) NOT NULL,
  PRIMARY KEY (`Sensor_idSensor`,`Trigger_idTrigger`),
  KEY `fk_Sensor_has_Trigger_Trigger1` (`Trigger_idTrigger`),
  KEY `fk_Sensor_has_Trigger_Sensor1` (`Sensor_idSensor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Trigger`
--

DROP TABLE IF EXISTS `Trigger`;
CREATE TABLE IF NOT EXISTS `Trigger` (
  `idTrigger` int(11) NOT NULL AUTO_INCREMENT,
  `PropertyType_idPropertyType` int(11) NOT NULL,
  `EventType_idEventType` int(11) NOT NULL,
  PRIMARY KEY (`idTrigger`),
  KEY `fk_Trigger_PropertyType1` (`PropertyType_idPropertyType`),
  KEY `fk_Trigger_EventType1` (`EventType_idEventType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `Event`
--
ALTER TABLE `Event`
  ADD CONSTRAINT `fk_Event_EventType1` FOREIGN KEY (`EventType_idEventType`) REFERENCES `EventType` (`idEventType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Event_Sensor1` FOREIGN KEY (`Sensor_idSensor`) REFERENCES `Sensor` (`idSensor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Event_Trigger1` FOREIGN KEY (`Trigger_idTrigger`) REFERENCES `Trigger` (`idTrigger`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `EventType_has_Action`
--
ALTER TABLE `EventType_has_Action`
  ADD CONSTRAINT `fk_EventType_has_Action_Action1` FOREIGN KEY (`Action_idAction`) REFERENCES `Action` (`idAction`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_EventType_has_Action_EventType1` FOREIGN KEY (`EventType_idEventType`) REFERENCES `EventType` (`idEventType`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `Property`
--
ALTER TABLE `Property`
  ADD CONSTRAINT `fk_Property_PropertyType1` FOREIGN KEY (`PropertyType_idPropertyType`) REFERENCES `PropertyType` (`idPropertyType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Property_Sensor1` FOREIGN KEY (`Sensor_idSensor`) REFERENCES `Sensor` (`idSensor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `Sensor_has_PropertyType`
--
ALTER TABLE `Sensor_has_PropertyType`
  ADD CONSTRAINT `fk_Sensor_has_PropertyType_PropertyType1` FOREIGN KEY (`PropertyType_idPropertyType`) REFERENCES `PropertyType` (`idPropertyType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Sensor_has_PropertyType_Sensor1` FOREIGN KEY (`Sensor_idSensor`) REFERENCES `Sensor` (`idSensor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `Sensor_has_Trigger`
--
ALTER TABLE `Sensor_has_Trigger`
  ADD CONSTRAINT `fk_Sensor_has_Trigger_Sensor1` FOREIGN KEY (`Sensor_idSensor`) REFERENCES `Sensor` (`idSensor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Sensor_has_Trigger_Trigger1` FOREIGN KEY (`Trigger_idTrigger`) REFERENCES `Trigger` (`idTrigger`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `Trigger`
--
ALTER TABLE `Trigger`
  ADD CONSTRAINT `fk_Trigger_EventType1` FOREIGN KEY (`EventType_idEventType`) REFERENCES `EventType` (`idEventType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Trigger_PropertyType1` FOREIGN KEY (`PropertyType_idPropertyType`) REFERENCES `PropertyType` (`idPropertyType`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
