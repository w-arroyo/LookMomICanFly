CREATE TABLE IF NOT EXISTS posts (
    id VARCHAR(36) PRIMARY KEY,
    amount INTEGER NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    address_id VARCHAR(36) NOT NULL,
    size ENUM('NO_SIZE','SMALL','MEDIUM','LARGE','EXTRA_LARGE','THIRTY_EIGHT','THIRTY_NINE','FORTY','FORTY_AND_HALF','FORTY_ONE','FORTY_TWO','FORTY_TWO_AND_HALF','FORTY_THREE','FORTY_FOUR','FORTY_FOUR_AND_HALF','FORTY_FIVE') NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    finalized BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payments (
    id VARCHAR(36) PRIMARY KEY,
    payment_intent_id VARCHAR(255) NOT NULL UNIQUE,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS shipping_companies (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS selling_fees (
    id VARCHAR(36) PRIMARY KEY,
    percentage INTEGER NOT NULL CHECK (percentage BETWEEN 0 AND 100),
    description VARCHAR(255) NOT NULL,
    by_default BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS shipping_options (
    id VARCHAR(36) PRIMARY KEY,
    price DECIMAL(7,2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    estimated_time VARCHAR(255) NOT NULL,
    shipping_company_id VARCHAR(36) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shipping_company_id) REFERENCES shipping_companies(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS asks (
    id VARCHAR(36) PRIMARY KEY,
    fee_id VARCHAR(36) NOT NULL,
    bank_account_id VARCHAR(36) NOT NULL,
    shipping_fee DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (fee_id) REFERENCES selling_fees(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bids (
    id VARCHAR(36) PRIMARY KEY,
    shipping_option_id VARCHAR(36) NOT NULL,
    payment_id VARCHAR(36) NOT NULL,
    operational_fee DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE,
    FOREIGN KEY (shipping_option_id) REFERENCES shipping_options(id) ON DELETE CASCADE
);


