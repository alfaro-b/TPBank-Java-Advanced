-- ------------------------------------------------------------------------------
-- - Reconstruction de la base de données                                     ---
-- ------------------------------------------------------------------------------
DROP DATABASE IF EXISTS Bank;
CREATE DATABASE Bank;
USE Bank;

-- -----------------------------------------------------------------------------
-- - Construction de la tables des articles en vente                         ---
-- -----------------------------------------------------------------------------

CREATE TABLE bank_account (
	AccountNumber VARCHAR(12) PRIMARY KEY,
	Holder VARCHAR(100)	NOT NULL,
	Balance DOUBLE NOT NULL DEFAULT 0
) ENGINE = InnoDB;

INSERT INTO bank_account (AccountNumber, Holder, Balance)
VALUES
('FR-1234-5678', 'Jean Dupont', 1500.00),
('FR-1111-2222', 'Marie Martin', 850.50),
('FR-9876-5432', 'Paul Durand', 2300.00);