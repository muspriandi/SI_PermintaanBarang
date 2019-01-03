# Host: localhost  (Version 5.5.5-10.1.36-MariaDB)
# Date: 2019-01-03 11:02:27
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "barang"
#

DROP TABLE IF EXISTS `barang`;
CREATE TABLE `barang` (
  `KdBrg` int(5) NOT NULL AUTO_INCREMENT,
  `NmBrg` varchar(30) DEFAULT NULL,
  `Satuan` varchar(10) DEFAULT NULL,
  `Harga` int(9) DEFAULT NULL,
  PRIMARY KEY (`KdBrg`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

#
# Data for table "barang"
#

INSERT INTO `barang` VALUES (1,'Komputer','Unit',3000000),(2,'Mouse','Buah',50000),(3,'Keyboard','Unit',65000);

#
# Structure for table "fpb"
#

DROP TABLE IF EXISTS `fpb`;
CREATE TABLE `fpb` (
  `NoFPB` varchar(7) NOT NULL DEFAULT '',
  `TglFPB` date DEFAULT NULL,
  `KdKar` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`NoFPB`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "fpb"
#

INSERT INTO `fpb` VALUES ('FPB0001','2018-12-09','1'),('FPB0002','2018-12-15','2'),('FPB0003','2018-12-24','1'),('FPB0004','2018-12-24','1'),('FPB0005','2018-12-24','1'),('FPB0006','2018-12-24','2'),('FPB0007','2018-12-24','2'),('FPB0008','2019-01-03','2');

#
# Structure for table "karyawan"
#

DROP TABLE IF EXISTS `karyawan`;
CREATE TABLE `karyawan` (
  `KdKar` int(5) NOT NULL AUTO_INCREMENT,
  `NmKar` varchar(25) DEFAULT NULL,
  `Dept` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`KdKar`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

#
# Data for table "karyawan"
#

INSERT INTO `karyawan` VALUES (1,'Mus','SDK'),(2,'Test','Test'),(3,'coba','cobacoba');

#
# Structure for table "minta"
#

DROP TABLE IF EXISTS `minta`;
CREATE TABLE `minta` (
  `NoFPB` varchar(7) NOT NULL DEFAULT '',
  `KdBrg` int(5) DEFAULT NULL,
  `HargaFPB` int(9) DEFAULT NULL,
  `JmlMinta` int(3) DEFAULT NULL,
  KEY `KdBrg` (`KdBrg`),
  KEY `NoFPB` (`NoFPB`),
  CONSTRAINT `minta_ibfk_1` FOREIGN KEY (`KdBrg`) REFERENCES `barang` (`KdBrg`),
  CONSTRAINT `minta_ibfk_2` FOREIGN KEY (`NoFPB`) REFERENCES `fpb` (`NoFPB`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "minta"
#

INSERT INTO `minta` VALUES ('FPB0001',3,65000,3),('FPB0001',2,50000,3),('FPB0002',1,3000000,1),('FPB0002',3,65000,3),('FPB0003',2,50000,10),('FPB0003',1,3000000,10),('FPB0004',1,3000000,4),('FPB0004',2,50000,5),('FPB0005',2,50000,9),('FPB0006',1,3000000,8),('FPB0007',1,3000000,1),('FPB0007',2,50000,2),('FPB0007',3,65000,3),('FPB0008',1,3000000,5);

#
# Structure for table "supplier"
#

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `KdSpl` int(5) NOT NULL AUTO_INCREMENT,
  `NmSpl` varchar(25) DEFAULT NULL,
  `Alamat` varchar(50) DEFAULT NULL,
  `Telp` varchar(12) DEFAULT NULL,
  `Email` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`KdSpl`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

#
# Data for table "supplier"
#

INSERT INTO `supplier` VALUES (1,'Mus P','Ciledug, Tangerang','0821','1711501559@student.com'),(2,'Test','Test','123','Test@test.com');

#
# Structure for table "suratpesan"
#

DROP TABLE IF EXISTS `suratpesan`;
CREATE TABLE `suratpesan` (
  `NoSP` varchar(8) NOT NULL DEFAULT '',
  `TglSP` date DEFAULT NULL,
  `NoFPB` varchar(7) DEFAULT NULL,
  `KdSpl` int(5) DEFAULT NULL,
  PRIMARY KEY (`NoSP`),
  KEY `KdSpl` (`KdSpl`),
  KEY `NoFPB` (`NoFPB`),
  CONSTRAINT `suratpesan_ibfk_2` FOREIGN KEY (`KdSpl`) REFERENCES `karyawan` (`KdKar`),
  CONSTRAINT `suratpesan_ibfk_3` FOREIGN KEY (`NoFPB`) REFERENCES `fpb` (`NoFPB`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "suratpesan"
#

INSERT INTO `suratpesan` VALUES ('SP0001','2018-12-24','FPB0001',2),('SP00010','2019-01-03','FPB0001',2),('SP0002','2018-12-24','FPB0001',2),('SP0003','2018-12-24','FPB0001',2),('SP0004','2018-12-27','FPB0001',1),('SP0005','2018-12-27','FPB0001',1),('SP0006','2018-12-27','FPB0002',1),('SP0007','2018-12-27','FPB0001',2),('SP0008','2018-12-27','FPB0003',2),('SP0009','2018-12-29','FPB0005',1);

#
# Structure for table "pesan"
#

DROP TABLE IF EXISTS `pesan`;
CREATE TABLE `pesan` (
  `NoSP` varchar(8) NOT NULL DEFAULT '',
  `KdBrg` int(5) DEFAULT NULL,
  `HargaPesan` int(9) DEFAULT NULL,
  `JmlPesan` int(3) DEFAULT NULL,
  KEY `KdBrg` (`KdBrg`),
  KEY `NoSP` (`NoSP`),
  CONSTRAINT `pesan_ibfk_2` FOREIGN KEY (`KdBrg`) REFERENCES `barang` (`KdBrg`),
  CONSTRAINT `pesan_ibfk_3` FOREIGN KEY (`NoSP`) REFERENCES `suratpesan` (`NoSP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "pesan"
#

INSERT INTO `pesan` VALUES ('SP0002',1,3000000,1),('SP0003',1,3000000,1),('SP0003',1,3000000,2),('SP0004',2,50000,3),('SP0005',1,3000000,2),('SP0006',1,3000000,1),('SP0007',1,3000000,2),('SP0007',2,50000,4),('SP0008',1,3000000,3),('SP0009',1,3000000,9),('SP00010',2,50000,4);
