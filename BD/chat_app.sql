-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 12 jan. 2023 à 23:48
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `chat_app`
--

-- --------------------------------------------------------

--
-- Structure de la table `friendship`
--

CREATE TABLE `friendship` (
  `id_fr` int(11) NOT NULL,
  `id_p1` int(200) NOT NULL,
  `id_p2` int(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `friendship`
--

INSERT INTO `friendship` (`id_fr`, `id_p1`, `id_p2`) VALUES
(2, 31, 23),
(11, 20, 32),
(13, 26, 32),
(21, 31, 33),
(25, 25, 20),
(26, 33, 20),
(32, 25, 33),
(33, 20, 34);

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

CREATE TABLE `groupe` (
  `id` int(200) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `pseudos` varchar(2000) NOT NULL,
  `creator` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `groupe`
--

INSERT INTO `groupe` (`id`, `nom`, `pseudos`, `creator`) VALUES
(13, 'sisters', 'ouiam//salma//abbbbbcdo//', 20),
(14, 'ss', 'ouiam//salma//', 20),
(15, 'irisi', 'ouiam//salma//abbbbbcdo//', 20);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id_user` int(200) NOT NULL,
  `pseudo_user` varchar(20) NOT NULL,
  `password_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id_user`, `pseudo_user`, `password_user`) VALUES
(20, 'ouiam', 'oui'),
(22, '2334', '11'),
(23, 'ouia', 'oi'),
(24, 'khoui', 'oii'),
(25, 'fff', 'ttt'),
(26, 'ooo', 'iii'),
(27, 'oooi', '111'),
(28, 'ouiam11', '111'),
(29, 'tyjxyj', '11'),
(30, 'ouiam123', 'ouiam'),
(31, 'ooo', '111'),
(32, 'abbbbbcdo', '123123'),
(33, 'salma', '2010'),
(34, 'amal', 'amalamal');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `friendship`
--
ALTER TABLE `friendship`
  ADD PRIMARY KEY (`id_fr`),
  ADD KEY `fk_fr1` (`id_p1`),
  ADD KEY `fk_fr2` (`id_p2`);

--
-- Index pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `creator` (`creator`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `friendship`
--
ALTER TABLE `friendship`
  MODIFY `id_fr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT pour la table `groupe`
--
ALTER TABLE `groupe`
  MODIFY `id` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `friendship`
--
ALTER TABLE `friendship`
  ADD CONSTRAINT `fk_fr1` FOREIGN KEY (`id_p1`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `fk_fr2` FOREIGN KEY (`id_p2`) REFERENCES `users` (`id_user`);

--
-- Contraintes pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD CONSTRAINT `groupe_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `users` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
