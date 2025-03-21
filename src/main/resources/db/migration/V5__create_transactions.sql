CREATE TABLE IF NOT EXISTS orders (
    id VARCHAR(36) PRIMARY KEY,
    bid_id VARCHAR(36) NOT NULL,
    status ENUM('WAITING', 'ON_THE_WAY_TO_US','AUTHENTICATING','AUTHENTICATED','FAKE_PRODUCT','SHIPPED','DELIVERED') NOT NULL DEFAULT 'WAITING',
    FOREIGN KEY (bid_id) REFERENCES bids(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sales (
    id VARCHAR(36) PRIMARY KEY,
    ask_id VARCHAR(36) NOT NULL,
    status ENUM('PENDING', 'SHIPPED','RECEIVED','VERIFYING','FAILED','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (ask_id) REFERENCES asks(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(36) PRIMARY KEY,
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    order_id VARCHAR(36) NULL,
    sale_id VARCHAR(36) NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tracking_numbers (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS tracking_and_orders (
    tracking_number_id VARCHAR(36) NOT NULL,
    order_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (tracking_number_id, order_id),
    FOREIGN KEY (tracking_number_id) REFERENCES tracking_numbers(id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tracking_and_sales (
    tracking_number_id VARCHAR(36) NOT NULL,
    sale_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (tracking_number_id, sale_id),
    FOREIGN KEY (tracking_number_id) REFERENCES tracking_numbers(id) ON DELETE CASCADE,
    FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE
);
