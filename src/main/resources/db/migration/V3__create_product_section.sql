CREATE TABLE IF NOT EXISTS manufacturers (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS colors (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS products (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  release_year INT NOT NULL,
  active BOOLEAN DEFAULT TRUE NOT NULL,
  category ENUM('SNEAKERS','CLOTHING','ACCESSORIES','COLLECTIBLES','SKATEBOARDS','MUSIC') NOT NULL,
  subcategory ENUM('HIGH','LOW','LIGHT','PANTS','HOODIE','JACKET','COAT','PUFFER','BOXERS','CREWNECK','TEE','CAP','GLOVES','SHADES','BACKPACK','CARD','FIGURE','PLUSH','SKATEBOARD','SNOWBOARD','CD','VINYL','TAPE') NOT NULL,
  manufacturer_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS users_favorite_products (
  product_id VARCHAR(36) NOT NULL,
  user_id VARCHAR(36) NOT NULL,
  PRIMARY KEY (product_id, user_id),
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sneakers (
  id VARCHAR(36) PRIMARY KEY,
  sku VARCHAR(100) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS colors_products (
  product_id VARCHAR(36) NOT NULL,
  color_id VARCHAR(36) NOT NULL,
  PRIMARY KEY (product_id, color_id),
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS accessories (
  id VARCHAR(36) PRIMARY KEY,
  material ENUM('Cotton', 'Wool','Polyester','Nylon','Leather','Suede', 'Denim','Silk','Rubber','Plastic','Cashmere', 'Velvet') NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clothing (
  id VARCHAR(36) PRIMARY KEY,
  season ENUM('SS','FW') NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS collectibles (
  id VARCHAR(36) PRIMARY KEY,
  collection_name VARCHAR(255) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS skateboards (
  id VARCHAR(36) PRIMARY KEY,
  length VARCHAR(10) NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS music (
  id VARCHAR(36) PRIMARY KEY,
  format ENUM('Single','Album','Mixtape') NOT NULL,
  FOREIGN KEY (id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
