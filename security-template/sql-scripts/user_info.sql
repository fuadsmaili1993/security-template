create database user_auth;
use user_auth;


CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(150) NOT NULL,
  `enabled` int DEFAULT 0,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `password_reset_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(120) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `expiry_date` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
