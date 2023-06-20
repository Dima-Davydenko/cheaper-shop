CREATE DATABASE `cheaper_shop` /*!40100 DEFAULT CHARACTER SET cp1251 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product` varchar(255) DEFAULT NULL,
  `volume` varchar(255) DEFAULT NULL,
  `article_fora` varchar(255) DEFAULT NULL,
  `article_atb` varchar(255) DEFAULT NULL,
  `article_novus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=cp1251;

CREATE TABLE `cart` (
  `id` int NOT NULL,
  `product` varchar(255) DEFAULT NULL,
  `volume` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `old_price` decimal(10,2) DEFAULT NULL,
  `article` varchar(255) DEFAULT NULL,
  `shop` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;
