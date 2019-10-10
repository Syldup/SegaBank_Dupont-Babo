-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 10 oct. 2019 à 08:20
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bdsegabank`
--
CREATE DATABASE IF NOT EXISTS `bdsegabank` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bdsegabank`;

-- --------------------------------------------------------

--
-- Structure de la table `agence`
--

DROP TABLE IF EXISTS `agence`;
CREATE TABLE IF NOT EXISTS `agence` (
  `idAgence` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `addresse` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idAgence`),
  UNIQUE KEY `idAgence_UNIQUE` (`idAgence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte` (
  `idCompte` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `identifiant` int(11) NOT NULL,
  `solde` double NOT NULL,
  `payant` tinyint(4) NOT NULL,
  `idAgence` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`idCompte`,`idAgence`),
  UNIQUE KEY `idCompte_UNIQUE` (`idCompte`),
  UNIQUE KEY `identifiant_UNIQUE` (`identifiant`),
  KEY `fk_Compte_Agence_idx` (`idAgence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `compteepargne`
--

DROP TABLE IF EXISTS `compteepargne`;
CREATE TABLE IF NOT EXISTS `compteepargne` (
  `tauxInteret` double NOT NULL,
  `idCompte` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`idCompte`),
  KEY `fk_CompteEpargne_Compte1_idx` (`idCompte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `comptesimple`
--

DROP TABLE IF EXISTS `comptesimple`;
CREATE TABLE IF NOT EXISTS `comptesimple` (
  `decouvert` double NOT NULL,
  `idCompte` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`idCompte`),
  KEY `fk_CompteSimple_Compte1_idx` (`idCompte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `fk_Compte_Agence` FOREIGN KEY (`idAgence`) REFERENCES `agence` (`idAgence`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `compteepargne`
--
ALTER TABLE `compteepargne`
  ADD CONSTRAINT `fk_CompteEpargne_Compte1` FOREIGN KEY (`idCompte`) REFERENCES `compte` (`idCompte`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `comptesimple`
--
ALTER TABLE `comptesimple`
  ADD CONSTRAINT `fk_CompteSimple_Compte1` FOREIGN KEY (`idCompte`) REFERENCES `compte` (`idCompte`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
