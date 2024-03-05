CREATE TABLE `campaign`
(
    `idCamp`          int(255)     NOT NULL,
    `number`          int(255)     NOT NULL,
    `goal`            int(255)     NOT NULL,
    `titre`           varchar(255) NOT NULL,
    `associationName` varchar(255) NOT NULL,
    `campaignType`    varchar(255) NOT NULL,
    `description`     text         NOT NULL,
    `image`           blob                  DEFAULT NULL,
    `current`         float        NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `campaign`
--

INSERT INTO `campaign` (`idCamp`, `number`, `goal`, `titre`, `associationName`, `campaignType`, `description`, `image`,
                        `current`)
VALUES (3, 98765432, 20, 'naderrr', 'khjsBEFAWE', 'Money', 'NADER', NULL, 20),
       (5, 12345678, 2000, 'ijaSDF', 'JKHSdbvhks', 'Food', 'jkasdnvj', NULL, 500),
       (8, 12345678, 2000, 'hisdvjhsdf', 'ihwfdssdf', 'Money', 'skadjv', NULL, 20);

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande`
(
    `idCommande`           int(20)      NOT NULL,
    `dateCommande`         datetime     NOT NULL,
    `adresseLivraison`     varchar(30)  NOT NULL,
    `montantTotalCommande` double       NOT NULL,
    `idUser`               int(11)      NOT NULL,
    `plats`                varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`idCommande`, `dateCommande`, `adresseLivraison`, `montantTotalCommande`, `idUser`, `plats`)
VALUES (1, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (2, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (3, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (4, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (5, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (6, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (7, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (8, '2024-02-24 13:45:00', 'Nowhere', 34, 2, 'Pasta'),
       (9, '2024-02-01 12:44:00', 'Nowhere', 77, 2, 'Pizza'),
       (10, '2024-02-02 12:34:00', 'newyork,568', 77, 6, 'Pizza'),
       (11, '2024-02-03 12:12:00', 'Nowhere', 7, 2, 'Pasta'),
       (12, '2024-02-03 12:12:00', 'Nowhere', 7777, 2, 'Pasta'),
       (13, '2024-02-03 12:12:00', 'Nowhere', 7777, 2, 'Pasta'),
       (14, '2024-02-03 12:12:00', 'Nowhere', 7777, 2, 'Pasta'),
       (15, '2024-02-07 12:12:00', 'Everwhere', 49, 3, 'Pizza');

-- --------------------------------------------------------

--
-- Table structure for table `deliveryperson`
--

CREATE TABLE `deliveryperson`
(
    `id`           int(11) NOT NULL,
    `Vehicle`      varchar(50) DEFAULT NULL,
    `Availability` varchar(50) DEFAULT NULL,
    `Sector`       varchar(50) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `donation`
--

CREATE TABLE `donation`
(
    `idDon`     int(11) NOT NULL,
    `idCamp`    int(11) NOT NULL,
    `valeurDon` int(20) NOT NULL,
    `idDonator` int(11) NOT NULL,
    `history`   DATE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `donation`
--

INSERT INTO `donation` (`idDon`, `idCamp`, `valeurDon`, `idDonator`, `history`)
VALUES (1, 3, 20, 2, '2024-02-24'),
       (2, 3, 20, 2, '2024-02-24'),
       (3, 3, 20, 2, '2024-02-24'),
       (4, 3, 20, 2, '2024-02-24'),
       (5, 3, 20, 2, '2024-02-24');

-- --------------------------------------------------------

--
-- Table structure for table `ingredients`
--

CREATE TABLE `ingredients`
(
    `idIng`   int(10)     NOT NULL,
    `nameIng` varchar(50) NOT NULL,
    `amount`  varchar(50) NOT NULL,
    `idRec`   int(10)     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `livraison`
--

CREATE TABLE `livraison`
(
    `idLivraison` int(11) NOT NULL,
    `idLivreur`   int(11) NOT NULL,
    `idCommande`  int(11) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu`
(
    `idP`          int(5)       NOT NULL,
    `nameP`        varchar(30)  NOT NULL,
    `priceP`       float        NOT NULL,
    `categoryP`    varchar(100) NOT NULL,
    `ingredientsP` varchar(255) NOT NULL,
    `restaurantId` int(11)      NOT NULL,
    `imageP`       varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recette`
--

CREATE TABLE `recette`
(
    `idRec`       int(15)      NOT NULL,
    `nomRec`      varchar(30)  NOT NULL,
    `categoryR`   varchar(50)  NOT NULL,
    `difficulty`  varchar(30)  NOT NULL,
    `serves`      int(3)       NOT NULL,
    `prep`        varchar(15)  NOT NULL,
    `description` text         NOT NULL,
    `date`        varchar(30)  NOT NULL,
    `rating`      int(3)       NOT NULL,
    `idUser`      int(11)      NOT NULL,
    `imageRec`    varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `recette`
--

INSERT INTO `recette` (`idRec`, `nomRec`, `categoryR`, `difficulty`, `serves`, `prep`, `description`, `date`, `rating`,
                       `idUser`, `imageRec`)
VALUES (2, 'Pasta', '', 'Average', 4, '0H35min', 'Example', '02/25/24', 0, 2,
        '@../../image/310647679_630098571820169_6283887971064782386_n.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation`
(
    `reservationId`   int(11) NOT NULL,
    `userId`          int(11)  DEFAULT NULL,
    `dateTime`        datetime DEFAULT NULL,
    `tableId`         int(11)  DEFAULT NULL,
    `numberOfPersons` int(11)  DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`reservationId`, `userId`, `dateTime`, `tableId`, `numberOfPersons`)
VALUES (17, 6, '2024-02-22 00:07:31', 19, 4);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant`
--

CREATE TABLE `restaurant`
(
    `restaurantId` int(11)      NOT NULL,
    `name`         varchar(255) NOT NULL,
    `address`      varchar(255) NOT NULL,
    `description`  text    DEFAULT NULL,
    `userId`       int(11) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`restaurantId`, `name`, `address`, `description`, `userId`)
VALUES (9, 'Test Restaurant', '123 Test Address', 'A test restaurant', 2);

-- --------------------------------------------------------

--
-- Table structure for table `restauranttable`
--

CREATE TABLE `restauranttable`
(
    `tableId`       int(11) NOT NULL,
    `numberOfSeats` int(11)    DEFAULT NULL,
    `isOccupied`    tinyint(1) DEFAULT NULL,
    `description`   text       DEFAULT NULL,
    `restaurantId`  int(11)    DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `restauranttable`
--

INSERT INTO `restauranttable` (`tableId`, `numberOfSeats`, `isOccupied`, `description`, `restaurantId`)
VALUES (18, 4, 0, 'Table for testing purposes', 9),
       (19, 4, 0, 'Table for testing purposes', 9);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user`
(
    `idUser`    int(11)     NOT NULL,
    `FirstName` varchar(20) NOT NULL,
    `LastName`  varchar(20) NOT NULL,
    `Email`     varchar(50) NOT NULL,
    `Address`   varchar(50) NOT NULL,
    `Role`      varchar(20) NOT NULL,
    `Number`    int(11)     NOT NULL,
    `Rating`    float DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `FirstName`, `LastName`, `Email`, `Address`, `Role`, `Number`, `Rating`)
VALUES (2, 'Alexander', 'Brown', 'alex@gmail.com', 'Nowhere', 'Manager', 5686799, 5),
       (3, 'Maximus', 'Galagtix', 'justmaximus@gmail.com', 'Everwhere', 'Manager', 95494874, 5),
       (6, 'Darwin', 'Smith', 'darwin889@gmail.com', 'newyork,568', 'Client', 7984, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `campaign`
--
ALTER TABLE `campaign`
    ADD PRIMARY KEY (`idCamp`);

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
    ADD PRIMARY KEY (`idCommande`),
    ADD KEY `frK_idUser` (`idUser`);

--
-- Indexes for table `deliveryperson`
--
ALTER TABLE `deliveryperson`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `donation`
--
ALTER TABLE `donation`
    ADD PRIMARY KEY (`idDon`),
    ADD KEY `idCamp` (`idCamp`),
    ADD KEY `idUser` (`idDonator`);

--
-- Indexes for table `ingredients`
--
ALTER TABLE `ingredients`
    ADD PRIMARY KEY (`idIng`),
    ADD KEY `idRec` (`idRec`);

--
-- Indexes for table `livraison`
--
ALTER TABLE `livraison`
    ADD PRIMARY KEY (`idLivraison`),
    ADD KEY `frk_idLivreur` (`idLivreur`),
    ADD KEY `fk_keyIdCommande` (`idCommande`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
    ADD PRIMARY KEY (`idP`),
    ADD KEY `restaurantId` (`restaurantId`);

--
-- Indexes for table `recette`
--
ALTER TABLE `recette`
    ADD PRIMARY KEY (`idRec`),
    ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
    ADD PRIMARY KEY (`reservationId`),
    ADD KEY `tableId` (`tableId`),
    ADD KEY `idUser` (`userId`);

--
-- Indexes for table `restaurant`
--
ALTER TABLE `restaurant`
    ADD PRIMARY KEY (`restaurantId`),
    ADD KEY `idUser` (`userId`);

--
-- Indexes for table `restauranttable`
--
ALTER TABLE `restauranttable`
    ADD PRIMARY KEY (`tableId`),
    ADD KEY `restaurantId` (`restaurantId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
    ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `campaign`
--
ALTER TABLE `campaign`
    MODIFY `idCamp` int(255) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 9;

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
    MODIFY `idCommande` int(20) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 16;

--
-- AUTO_INCREMENT for table `donation`
--
ALTER TABLE `donation`
    MODIFY `idDon` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 6;

--
-- AUTO_INCREMENT for table `ingredients`
--
ALTER TABLE `ingredients`
    MODIFY `idIng` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `livraison`
--
ALTER TABLE `livraison`
    MODIFY `idLivraison` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
    MODIFY `idP` int(5) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 2;

--
-- AUTO_INCREMENT for table `recette`
--
ALTER TABLE `recette`
    MODIFY `idRec` int(15) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 3;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
    MODIFY `reservationId` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 18;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
    MODIFY `restaurantId` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 10;

--
-- AUTO_INCREMENT for table `restauranttable`
--
ALTER TABLE `restauranttable`
    MODIFY `tableId` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 20;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
    MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
    ADD CONSTRAINT `frK_idUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE;

--
-- Constraints for table `deliveryperson`
--
ALTER TABLE `deliveryperson`
    ADD CONSTRAINT `deliveryperson_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `donation`
--
ALTER TABLE `donation`
    ADD CONSTRAINT `donation_ibfk_1` FOREIGN KEY (`idCamp`) REFERENCES `campaign` (`idCamp`) ON DELETE CASCADE;
   -- ADD CONSTRAINT `donation_ibfk_2` FOREIGN KEY (`idDonator`) REFERENCES `user` (`idUser`) ON DELETE CASCADE;

--
-- Constraints for table `ingredients`
--
ALTER TABLE `ingredients`
    ADD CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`idRec`) REFERENCES `recette` (`idRec`);

--
-- Constraints for table `menu`
--
ALTER TABLE `menu`
    ADD CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`restaurantId`) REFERENCES `restaurant` (`restaurantId`);

--
-- Constraints for table `recette`
--
ALTER TABLE `recette`
    ADD CONSTRAINT `recette_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
    ADD CONSTRAINT `fk_tableIdreservation` FOREIGN KEY (`tableId`) REFERENCES `restauranttable` (`tableId`),
    ADD CONSTRAINT `fk_userIdreservation` FOREIGN KEY (`userId`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `restaurant`
--
ALTER TABLE `restaurant`
    ADD CONSTRAINT `fk_userIdrestaurant` FOREIGN KEY (`userId`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `restauranttable`
--
ALTER TABLE `restauranttable`
    ADD CONSTRAINT `fk_restaurantIdtable` FOREIGN KEY (`restaurantId`) REFERENCES `restaurant` (`restaurantId`);
COMMIT;