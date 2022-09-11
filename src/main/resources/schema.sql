CREATE DATABASE IF NOT EXISTS `calendarrest-db`;
USE `calendarrest-db`;

CREATE TABLE IF NOT EXISTS `Working` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `start` varchar(5) NOT NULL,
    `end` varchar(5) NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `Calendar` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `working_id` int NOT NULL,
    FOREIGN KEY(`working_id`) REFERENCES `Working`(`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `Meeting` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `start` varchar(5) NOT NULL,
    `end` varchar(5) NOT NULL,
    `calendar_id` int NOT NULL,
    FOREIGN KEY(`calendar_id`) REFERENCES `Calendar`(`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;