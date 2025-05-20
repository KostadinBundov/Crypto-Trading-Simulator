CREATE TABLE accounts (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        phone_number VARCHAR(20) NOT NULL,
        balance DECIMAL(20,8) NOT NULL DEFAULT 10000.0
);

CREATE TABLE wallets (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        account_id BIGINT UNIQUE NOT NULL,
        CONSTRAINT fk_wallet_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE crypto_currencies (
        symbol VARCHAR(10) PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        currency VARCHAR(10) NOT NULL
);

CREATE TABLE holdings (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        wallet_id BIGINT NOT NULL,
        crypto_currency_symbol VARCHAR(10) NOT NULL,
        crypto_currency_amount DECIMAL(20,8) NOT NULL,
        CONSTRAINT fk_holding_wallet FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE,
        CONSTRAINT fk_holding_currency FOREIGN KEY (crypto_currency_symbol) REFERENCES crypto_currencies(symbol)
);

CREATE TABLE transactions (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        account_id BIGINT NOT NULL,
        currency_symbol VARCHAR(10) NOT NULL,
        amount DECIMAL(20,8) NOT NULL,
        price DECIMAL(20,8) NOT NULL,
        profit DECIMAL(20,8) NOT NULL,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id),
        CONSTRAINT fk_transaction_currency FOREIGN KEY (currency_symbol) REFERENCES crypto_currencies(symbol)
);