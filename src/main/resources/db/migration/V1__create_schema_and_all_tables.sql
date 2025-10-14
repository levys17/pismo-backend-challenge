CREATE TABLE IF NOT EXISTS `transactions_app`.`accounts`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `document_number` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `transactions_app`.`operation_types` (
    id INT PRIMARY KEY,
    description VARCHAR(100) NOT NULL UNIQUE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `transactions_app`.`transactions` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_id BIGINT NOT NULL,
    operation_type_id INT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    event_date DATETIME NOT NULL,

    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_transaction_operation_type FOREIGN KEY (operation_type_id) REFERENCES operation_types(id)
) ENGINE = InnoDB;

INSERT INTO `transactions_app`.`operation_types` (id, description) VALUES
(1, 'Normal Purchase'),
(2, 'Purchase with installments'),
(3, 'Withdrawal'),
(4, 'Credit Voucher')
ON DUPLICATE KEY UPDATE description = VALUES(description);