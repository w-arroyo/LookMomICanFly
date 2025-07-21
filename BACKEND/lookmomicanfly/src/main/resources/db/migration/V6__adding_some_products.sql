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

INSERT INTO manufacturers (id, name) VALUES
  (UUID(), 'Yeezy'),
  (UUID(), 'Nike'),
  (UUID(), 'Jordan'),
  (UUID(), 'Adidas'),
  (UUID(), 'Travis Scott'),
  (UUID(), 'Corteiz'),
  (UUID(), 'Seventh Store'),
  (UUID(), 'NOCTA'),
  (UUID(), 'Patta'),
  (UUID(), 'Fragment'),
  (UUID(), 'Kith'),
  (UUID(), 'KAWS'),
  (UUID(), 'Supreme'),
  (UUID(), 'Palace'),
  (UUID(), 'Aime Leon Dore'),
  (UUID(), 'New Balance'),
  (UUID(), 'Asics'),
  (UUID(), 'Salomon'),
  (UUID(), 'Puma'),
  (UUID(), 'Timberland'),
  (UUID(), 'UGG'),
  (UUID(), 'Crocs'),
  (UUID(), 'Off-White'),
  (UUID(), 'Union LA'),
  (UUID(), 'Stussy'),
  (UUID(), 'Fear of God'),
  (UUID(), 'Bape'),
  (UUID(), 'Denim Tears'),
  (UUID(), 'The North Face'),
  (UUID(), 'Sp5der'),
  (UUID(), 'Balenciaga'),
  (UUID(), 'Carhartt'),
  (UUID(), 'Sony'),
  (UUID(), 'NVIDIA'),
  (UUID(), 'Apple'),
  (UUID(), 'Pokemon'),
  (UUID(), 'Topps'),
  (UUID(), 'Yu-Gi-Oh!'),
  (UUID(), 'Pop Mart'),
  (UUID(), 'Barbie'),
  (UUID(), 'CPFM'),
  (UUID(), 'LEGO'),
  (UUID(), 'Prada'),
  (UUID(), 'Versace'),
  (UUID(), 'Oakley'),
  (UUID(), 'Vlone'),
  (UUID(), 'Represent'),
  (UUID(), 'ROSALÍA'),
  (UUID(), 'Ye-Kanye West'),
  (UUID(), 'Future'),
  (UUID(), 'ASAP Rocky'),
  (UUID(), 'The Weeknd'),
  (UUID(), 'Playboi Carti'),
  (UUID(), 'MIKE DEAN'),
  (UUID(), 'Chief Keef'),
  (UUID(), 'Myke Towers'),
  (UUID(), 'Rihanna'),
  (UUID(), 'Uniqlo');

INSERT INTO colors (id, name) VALUES
  (UUID(), 'Black'),
  (UUID(), 'White'),
  (UUID(), 'Grey'),
  (UUID(), 'Red'),
  (UUID(), 'Blue'),
  (UUID(), 'Orange'),
  (UUID(), 'Gold'),
  (UUID(), 'Silver'),
  (UUID(), 'Green'),
  (UUID(), 'Brown'),
  (UUID(), 'Purple'),
  (UUID(), 'Cream'),
  (UUID(), 'Navy'),
  (UUID(), 'Pink'),
  (UUID(), 'Yellow'),
  (UUID(), 'Off White'),
  (UUID(), 'Multicolor'),
  (UUID(), 'Lilac'),
  (UUID(), 'Tan');

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 750 Light Brown', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown', 'White')
WHERE p.name = 'Yeezy Boost 750 Light Brown';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Turtle Dove', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Grey')
WHERE p.name = 'Yeezy Boost 350 Turtle Dove';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Pirate Black', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 350 Pirate Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Pirate Black', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 950 Pirate Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Moonrock', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy Boost 950 Moonrock';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Peyote', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Boost 950 Peyote';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Turtle Dove', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Grey')
WHERE p.name = 'Yeezy Boost 950 Turtle Dove';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 950 Chocolate', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy Boost 950 Chocolate';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Moonrock', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Boost 350 Moonrock';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 750 Triple Black', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 750 Triple Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 Oxford Tan', 2015, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Boost 350 Oxford Tan';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Beluga', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Orange')
WHERE p.name = 'Yeezy Boost 350 V2 Beluga';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Black - Red', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Red')
WHERE p.name = 'Yeezy Boost 350 V2 Black - Red';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Oreo', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'White')
WHERE p.name = 'Yeezy Boost 350 V2 Oreo';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Green - Black', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Green')
WHERE p.name = 'Yeezy Boost 350 V2 Green - Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Copper - Black', 2016, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Grey')
WHERE p.name = 'Yeezy Boost 350 V2 Copper - Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 750 Chocolate', 2015, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy Boost 750 Chocolate';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 Wave Runner', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Navy')
WHERE p.name = 'Yeezy Boost 700 Wave Runner';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Powerphase Calabasas White', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Powerphase Calabasas White';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Powerphase Calabasas Grey', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Grey'
WHERE p.name = 'Yeezy Powerphase Calabasas Grey';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Zebra', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Zebra';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Bred', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Red')
WHERE p.name = 'Yeezy Boost 350 V2 Bred';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Cream', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Cream';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Semi Frozen', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Yellow', 'Green')
WHERE p.name = 'Yeezy Boost 350 V2 Semi Frozen';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Blue Tint', 2017, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue', 'Grey')
WHERE p.name = 'Yeezy Boost 350 V2 Blue Tint';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Blush', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy 500 Blush';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Super Moon', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Yellow'
WHERE p.name = 'Yeezy 500 Super Moon';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Utility Black', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy 500 Utility Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Salt', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy 500 Salt';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Static', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 700 V2 Static';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Mauve', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy Boost 700 V2 Mauve';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Powerphase Calabasas Black', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Powerphase Calabasas Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Butter', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Boost 350 V2 Butter';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Sesame', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Boost 350 V2 Sesame';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Static', 2018, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Static';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Desert Boot Rock', 2019, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Desert Boot Rock';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Desert Boot Oil', 2019, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Desert Boot Oil';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Desert Boot Salt', 2019, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy Desert Boot Salt';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Bone', 2019, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Slide Bone';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Resin', 2019, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Tan'
WHERE p.name = 'Yeezy Slide Resin';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 380 Alien', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Pink')
WHERE p.name = 'Yeezy Boost 380 Alien';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 700 V3 Azael', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Black')
WHERE p.name = 'Yeezy 700 V3 Azael';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 Salt', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 700 Salt';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 Inertia', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue', 'Grey')
WHERE p.name = 'Yeezy Boost 700 Inertia';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Glow', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Green'
WHERE p.name = 'Yeezy Boost 350 V2 Glow';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Vanta', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 700 V2 Vanta';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Static Black', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Boost 350 V2 Static Black';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Cloud', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy Boost 350 V2 Cloud';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 500 Soft Vision', 2019, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Pink'
WHERE p.name = 'Yeezy 500 Soft Vision';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 MVMN', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Orange'
WHERE p.name = 'Yeezy Boost 700 MVMN';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy QNTM OG', 2020, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Black')
WHERE p.name = 'Yeezy QNTM OG';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Ararat', 2020, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy FOAM RNNR Ararat';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Tail Light', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Orange')
WHERE p.name = 'Yeezy Boost 350 V2 Tail Light';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 350 V2 Desert Sage', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Green'
WHERE p.name = 'Yeezy Boost 350 V2 Desert Sage';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 700 V3 Alvah', 2020, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy 700 V3 Alvah';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 450 Cloud', 2021, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'White'
WHERE p.name = 'Yeezy 450 Cloud';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy KNIT RNNR Sulfur', 2021, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Yellow'
WHERE p.name = 'Yeezy KNIT RNNR Sulfur';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy NSLTD Boot Khaki', 2021, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Brown'
WHERE p.name = 'Yeezy NSLTD Boot Khaki';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Boost 700 V2 Cream', 2021, TRUE, 'SNEAKERS', 'LOW', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Boost 700 V2 Cream';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Sand', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Off White'
WHERE p.name = 'Yeezy FOAM RNNR Sand';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR MX Moon Grey', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey', 'Cream')
WHERE p.name = 'Yeezy FOAM RNNR MX Moon Grey';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Pure', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Cream'
WHERE p.name = 'Yeezy Slide Pure';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy 1050 Hi Res', 2021, TRUE, 'SNEAKERS', 'HIGH', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Orange'
WHERE p.name = 'Yeezy 1050 Hi Res';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Enflame', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Orange'
WHERE p.name = 'Yeezy Slide Enflame';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Vermillon', 2021, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Red'
WHERE p.name = 'Yeezy FOAM RNNR Vermillon';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Slide Onyx', 2022, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Slide Onyx';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy FOAM RNNR Onyx', 2022, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy FOAM RNNR Onyx';


INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id)
SELECT UUID(), 'Yeezy Pods', 2023, TRUE, 'SNEAKERS', 'LIGHT', id
FROM manufacturers WHERE name = 'Yeezy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name = 'Black'
WHERE p.name = 'Yeezy Pods';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 750 Light Brown';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Turtle Dove';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Pirate Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Pirate Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Moonrock';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Peyote';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Turtle Dove';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 950 Chocolate';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Moonrock';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 750 Triple Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 Oxford Tan';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Beluga';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Black - Red';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Oreo';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Green - Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Copper - Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 750 Chocolate';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 Wave Runner';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Powerphase Calabasas White';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Powerphase Calabasas Grey';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Zebra';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Bred';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Cream';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Semi Frozen';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Blue Tint';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Blush';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Super Moon';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Utility Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Salt';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Static';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Mauve';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Powerphase Calabasas Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Butter';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Sesame';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Static';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Desert Boot Rock';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Desert Boot Oil';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Desert Boot Salt';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Bone';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Resin';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 380 Alien';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 700 V3 Azael';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 Salt';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 Inertia';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Glow';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Vanta';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Static Black';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Cloud';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 500 Soft Vision';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 MVMN';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy QNTM OG';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Ararat';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Tail Light';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 350 V2 Desert Sage';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 700 V3 Alvah';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 450 Cloud';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy KNIT RNNR Sulfur';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy NSLTD Boot Khaki';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Boost 700 V2 Cream';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Sand';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR MX Moon Grey';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Pure';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy 1050 Hi Res';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Enflame';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Vermillon';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Slide Onyx';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy FOAM RNNR Onyx';

INSERT INTO sneakers (id, sku)
SELECT id, 'YeezusGOAT' FROM products WHERE name = 'Yeezy Pods';



INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id) VALUES
((SELECT UUID()), 'Yeezy Gap Hoodie', 2021, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Engineered by Balenciaga Dove Hoodie', 2022, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Engineered by Balenciaga Dove Tee', 2022, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Engineered by Balenciaga Padded Denim Jacket', 2022, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezy Gap Round Jacket', 2021, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Native Skull Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Flying Reaper Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Skeleton Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'Yeezus Made in America Merch', 2013, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'TLOP Merch', 2016, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Yeezy')),
((SELECT UUID()), 'The North Face 1996 Retro Nuptse', 1996, 1, 'CLOTHING', 'PUFFER', (SELECT id FROM manufacturers WHERE name = 'The North Face')),
((SELECT UUID()), 'Supreme X The North Face Map Expedition Coaches Jacket', 2014, 1, 'CLOTHING', 'COAT', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Palace Fallen City Hood', 2022, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Palace')),
((SELECT UUID()), 'BAPE ABC Camo Patchwork Zip Hoodie', 2025, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Bape')),
((SELECT UUID()), 'BAPE ABC Camo Shark Full Zip Hoodie', 2023, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Bape')),
((SELECT UUID()), 'Nike x NOCTA NRG Tech Fleece Full Zip Hoodie', 2023, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Tech Fleece Pants', 2023, 1, 'CLOTHING', 'PANTS', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA NRG Tech Fleece Crewneck', 2023, 1, 'CLOTHING', 'CREWNECK', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Sunset Puffer Jacket', 2020, 1, 'CLOTHING', 'PUFFER', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Black Sunset Puffer Jacket', 2020, 1, 'CLOTHING', 'PUFFER', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA SYSMAS Tee', 2022, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA Distant Regards Jersey', 2023, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Nike x NOCTA L Art Racing Jacket', 2024, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'NOCTA')),
((SELECT UUID()), 'Fear of God Essentials Sweatpants', 2020, 1, 'CLOTHING', 'PANTS', (SELECT id FROM manufacturers WHERE name = 'Fear of God')),
((SELECT UUID()), 'Fear of God Essentials Fleece Hoodie', 2024, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Fear of God')),
((SELECT UUID()), 'Denim Tears The Cotton Wreath Sweatshirt', 2023, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Denim Tears')),
((SELECT UUID()), 'Sp5der OG Web Hoodie', 2024, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Sp5der')),
((SELECT UUID()), 'Juice Wrld x Vlone 999 Tee', 2020, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Supreme Hanes Boxer Briefs', 2022, 1, 'CLOTHING', 'BOXERS', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Represent Owner s Club Hoodie', 2022, 1, 'CLOTHING', 'HOODIE', (SELECT id FROM manufacturers WHERE name = 'Represent')),
((SELECT UUID()), 'Represent Owner s Club Tee', 2023, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Represent')),
((SELECT UUID()), 'Represent Storms In Heaven Tee', 2023, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Represent')),
((SELECT UUID()), 'Palace X Oakley Tee', 2024, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Palace')),
((SELECT UUID()), 'Palace x Oakley Puffa', 2024, 1, 'CLOTHING', 'COAT', (SELECT id FROM manufacturers WHERE name = 'Palace')),
((SELECT UUID()), 'Carhartt WIP OG Active Rivet Jacket', 2022, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Carhartt')),
((SELECT UUID()), 'Carhartt WIP OG Detroit Aged Canvas Jacket', 2024, 1, 'CLOTHING', 'JACKET', (SELECT id FROM manufacturers WHERE name = 'Carhartt')),
((SELECT UUID()), 'Nike Lakers Lebron James Jersey', 2019, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Supreme X Fox Racing Moto Jersey Top', 2018, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Supreme')),
((SELECT UUID()), 'Travis Scott Astroworld WYWH Tee', 2018, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Supreme Box Logo Tee', 2014, 1, 'CLOTHING', 'TEE', (SELECT id FROM manufacturers WHERE name = 'Supreme'));

INSERT INTO clothing (id, season) VALUES
((SELECT id FROM products WHERE name = 'Yeezy Gap Hoodie'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Padded Denim Jacket'), 'SS'),
((SELECT id FROM products WHERE name = 'Yeezy Gap Round Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Native Skull Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Flying Reaper Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Skeleton Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'Yeezus Made in America Merch'), 'FW'),
((SELECT id FROM products WHERE name = 'TLOP Merch'), 'SS'),
((SELECT id FROM products WHERE name = 'The North Face 1996 Retro Nuptse'), 'FW'),
((SELECT id FROM products WHERE name = 'Supreme X The North Face Map Expedition Coaches Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Palace Fallen City Hood'), 'SS'),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Patchwork Zip Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Shark Full Zip Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Full Zip Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Tech Fleece Pants'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Crewneck'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Sunset Puffer Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Black Sunset Puffer Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA SYSMAS Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Distant Regards Jersey'), 'SS'),
((SELECT id FROM products WHERE name = 'Nike x NOCTA L Art Racing Jacket'), 'SS'),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Sweatpants'), 'FW'),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Fleece Hoodie'), 'FW'),
((SELECT id FROM products WHERE name = 'Denim Tears The Cotton Wreath Sweatshirt'), 'SS'),
((SELECT id FROM products WHERE name = 'Sp5der OG Web Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Juice Wrld x Vlone 999 Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Supreme Hanes Boxer Briefs'), 'SS'),
((SELECT id FROM products WHERE name = 'Represent Owner s Club Hoodie'), 'SS'),
((SELECT id FROM products WHERE name = 'Represent Owner s Club Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Represent Storms In Heaven Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Palace X Oakley Tee'), 'FW'),
((SELECT id FROM products WHERE name = 'Palace x Oakley Puffa'), 'FW'),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Active Rivet Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Detroit Aged Canvas Jacket'), 'FW'),
((SELECT id FROM products WHERE name = 'Nike Lakers Lebron James Jersey'), 'FW'),
((SELECT id FROM products WHERE name = 'Supreme X Fox Racing Moto Jersey Top'), 'SS'),
((SELECT id FROM products WHERE name = 'Travis Scott Astroworld WYWH Tee'), 'SS'),
((SELECT id FROM products WHERE name = 'Supreme Box Logo Tee'), 'SS');

INSERT INTO colors_products (product_id, color_id) VALUES
((SELECT id FROM products WHERE name = 'Yeezy Gap Hoodie'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Hoodie'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Dove Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Engineered by Balenciaga Padded Denim Jacket'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Yeezy Gap Round Jacket'), (SELECT id FROM colors WHERE name = 'Red')),
((SELECT id FROM products WHERE name = 'Yeezus Native Skull Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezus Flying Reaper Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezus Skeleton Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Yeezus Made in America Merch'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'TLOP Merch'), (SELECT id FROM colors WHERE name = 'Yellow')),
((SELECT id FROM products WHERE name = 'The North Face 1996 Retro Nuptse'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Supreme X The North Face Map Expedition Coaches Jacket'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Supreme X The North Face Map Expedition Coaches Jacket'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Palace Fallen City Hood'), (SELECT id FROM colors WHERE name = 'Purple')),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Patchwork Zip Hoodie'), (SELECT id FROM colors WHERE name = 'Multicolor')),
((SELECT id FROM products WHERE name = 'BAPE ABC Camo Shark Full Zip Hoodie'), (SELECT id FROM colors WHERE name = 'Pink')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Full Zip Hoodie'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Tech Fleece Pants'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA NRG Tech Fleece Crewneck'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Sunset Puffer Jacket'), (SELECT id FROM colors WHERE name = 'Yellow')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Black Sunset Puffer Jacket'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA SYSMAS Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA Distant Regards Jersey'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA L Art Racing Jacket'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Nike x NOCTA L Art Racing Jacket'), (SELECT id FROM colors WHERE name = 'Navy')),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Sweatpants'), (SELECT id FROM colors WHERE name = 'Cream')),
((SELECT id FROM products WHERE name = 'Fear of God Essentials Fleece Hoodie'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Denim Tears The Cotton Wreath Sweatshirt'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Sp5der OG Web Hoodie'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Juice Wrld x Vlone 999 Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Supreme Hanes Boxer Briefs'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Represent Owner s Club Hoodie'), (SELECT id FROM colors WHERE name = 'Green')),
((SELECT id FROM products WHERE name = 'Represent Owner s Club Tee'), (SELECT id FROM colors WHERE name = 'Lilac')),
((SELECT id FROM products WHERE name = 'Represent Storms In Heaven Tee'), (SELECT id FROM colors WHERE name = 'Pink')),
((SELECT id FROM products WHERE name = 'Palace X Oakley Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Palace x Oakley Puffa'), (SELECT id FROM colors WHERE name = 'Blue')),
((SELECT id FROM products WHERE name = 'Palace x Oakley Puffa'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Active Rivet Jacket'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Carhartt WIP OG Detroit Aged Canvas Jacket'), (SELECT id FROM colors WHERE name = 'Tan')),
((SELECT id FROM products WHERE name = 'Nike Lakers Lebron James Jersey'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Nike Lakers Lebron James Jersey'), (SELECT id FROM colors WHERE name = 'Yellow')),
((SELECT id FROM products WHERE name = 'Supreme X Fox Racing Moto Jersey Top'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Supreme X Fox Racing Moto Jersey Top'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Travis Scott Astroworld WYWH Tee'), (SELECT id FROM colors WHERE name = 'Black')),
((SELECT id FROM products WHERE name = 'Supreme Box Logo Tee'), (SELECT id FROM colors WHERE name = 'White')),
((SELECT id FROM products WHERE name = 'Supreme Box Logo Tee'), (SELECT id FROM colors WHERE name = 'Red'));

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id) VALUES
((SELECT UUID()), 'Nike SB Dunk Low X Yuto Horigome - Matcha', 2025, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk Low Chicago', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk x Concepts - Orange Lobster', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk x Ebay - Sandy Bodecker', 2022, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk x Jarritos', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X The Powerpuff Girls - Blossom', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Parra - Abstract Art', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X HUF - San Francisco', 2022, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Fly Streetwear Gardenia', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk Los Angeles Dodgers', 2022, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Supreme - Rammellzee', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk St. Patrick s Day', 2022, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X atmos - Elephant', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X The Wizard of Oz - Poppy Field', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk Court Purple', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Travis Scott', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Nike SB Dunk X Ben & Jerry s - Chunky Dunky', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk Street Hawker', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X StrangeLove Skateboards', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Medicom Toy', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Grateful Dead Bears - Opti Yellow', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk High X Gundam - RX-0 Unicorn', 2021, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Supreme - Stars Mean Green', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk Freddy Krueger', 2007, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk x Concepts - Green Lobster', 2018, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X DJ Clark Kent X Primitive 112', 2013, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk De La Soul', 2015, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Supreme Red Cement', 2012, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike')),
((SELECT UUID()), 'Nike SB Dunk X Heineken', 2003, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Nike'));


INSERT INTO sneakers (id, sku) VALUES
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Heineken'), '304292-302'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Supreme Red Cement'), '313170-600'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk De La Soul'), '504750-016'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X DJ Clark Kent X Primitive 112'), '504750-017'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk x Concepts - Green Lobster'), 'BV1310-337'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Supreme - Stars Mean Green'), 'DH3228-101'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk Low X Yuto Horigome - Matcha'), 'HF8022-300'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk Low Chicago'), 'BQ6817-600'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk x Concepts - Orange Lobster'), 'FD8776-800'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk x Ebay - Sandy Bodecker'), 'FD8777-100'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk x Jarritos'), 'FD0860-001'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X The Powerpuff Girls - Blossom'), 'FD2631-600'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Parra - Abstract Art'), 'DH7695-600'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X HUF - San Francisco'), 'FD8775-001'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Fly Streetwear Gardenia'), 'DQ5130-400'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk Los Angeles Dodgers'), 'DO9395-400'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Supreme - Rammellzee'), 'FD8778-001'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk St. Patrick s Day'), 'BQ6817-303'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X atmos - Elephant'), 'BQ6817-300'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X The Wizard of Oz - Poppy Field'), 'FZ1291-600'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk Court Purple'), 'BQ6817-500'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Travis Scott'), 'CT5053-001'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Ben & Jerry s - Chunky Dunky'), 'CU3244-100'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk Street Hawker'), 'CV1628-800'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X StrangeLove Skateboards'), 'CT2552-800'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Medicom Toy'), 'CZ5127-001'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk X Grateful Dead Bears - Opti Yellow'), 'CJ5378-700'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk High X Gundam - RX-0 Unicorn'), 'DH7717-100'),
((SELECT id FROM products WHERE name = 'Nike SB Dunk Freddy Krueger'), '313170-202');


INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Green','White')
WHERE p.name = 'Nike SB Dunk X Heineken';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Red','Grey')
WHERE p.name = 'Nike SB Dunk X Supreme Red Cement';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk De La Soul';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk X DJ Clark Kent X Primitive 112';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Green','Black')
WHERE p.name = 'Nike SB Dunk x Concepts - Green Lobster';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk Freddy Krueger';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Green','White')
WHERE p.name = 'Nike SB Dunk X Supreme - Stars Mean Green';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White','Yellow')
WHERE p.name = 'Nike SB Dunk High X Gundam - RX-0 Unicorn';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Yellow', 'Blue')
WHERE p.name = 'Nike SB Dunk X Grateful Dead Bears - Opti Yellow';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'White')
WHERE p.name = 'Nike SB Dunk X Medicom Toy';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Pink', 'Red')
WHERE p.name = 'Nike SB Dunk X StrangeLove Skateboards';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk Street Hawker';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk X Ben & Jerry s - Chunky Dunky';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Tan')
WHERE p.name = 'Nike SB Dunk X Travis Scott';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Purple', 'White')
WHERE p.name = 'Nike SB Dunk Court Purple';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Red', 'Gold')
WHERE p.name = 'Nike SB Dunk X The Wizard of Oz - Poppy Field';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Grey')
WHERE p.name = 'Nike SB Dunk X atmos - Elephant';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Green','Gold')
WHERE p.name = 'Nike SB Dunk St. Patrick s Day';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black', 'Tan')
WHERE p.name = 'Nike SB Dunk X Supreme - Rammellzee';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Navy', 'White')
WHERE p.name = 'Nike SB Dunk Los Angeles Dodgers';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue','Tan')
WHERE p.name = 'Nike SB Dunk X Fly Streetwear Gardenia';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White', 'Black')
WHERE p.name = 'Nike SB Dunk X HUF - San Francisco';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk X Parra - Abstract Art';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Pink', 'Orange')
WHERE p.name = 'Nike SB Dunk X The Powerpuff Girls - Blossom';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Cream', 'Brown')
WHERE p.name = 'Nike SB Dunk x Jarritos';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Nike SB Dunk x Ebay - Sandy Bodecker';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Orange', 'Black')
WHERE p.name = 'Nike SB Dunk x Concepts - Orange Lobster';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Red', 'White')
WHERE p.name = 'Nike SB Dunk Low Chicago';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Green', 'Tan')
WHERE p.name = 'Nike SB Dunk Low X Yuto Horigome - Matcha';

INSERT INTO products (id, name, release_year, active, category, subcategory, manufacturer_id) VALUES
((SELECT UUID()), 'Travis Scott X Jordan 1 High OG SP - Mocha', 2019, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 6 - Olive', 2019, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 4 - Oilers', 2018, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Fragment Jordan 1 High OG SP', 2021, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 6 - Khaki', 2021, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X PlayStation X Nike SB Dunk - PS5', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Olive', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Mocha', 2019, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Fragment Jordan 1 Low OG SP', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Reverse Mocha', 2022, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Canary', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Phantom', 2022, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low Golf - Neutral', 2023, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Medium Olive', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan 1 Low OG SP - Velvet Brown', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan Jumpman Jack - Sail', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan Jumpman Jack - Red', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan Jumpman Jack - Dark Mocha', 2024, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Jordan Jumpman Jack - Bright Cactus', 2025, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Zoom Field Jaxx - Chocolate', 2025, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Air Max 1 - Saturn', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Air Max 1 - Baroque', 2021, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Mac Attack', 2023, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Air Max 270', 2020, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Air Force 1 - Cactus Jack', 2019, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Air Force 1 - Sail', 2018, 1, 'SNEAKERS', 'LOW', (SELECT id FROM manufacturers WHERE name = 'Travis Scott')),
((SELECT UUID()), 'Travis Scott X Nike Air Trainer', 2023, 1, 'SNEAKERS', 'HIGH', (SELECT id FROM manufacturers WHERE name = 'Travis Scott'));


INSERT INTO sneakers (id, sku) VALUES
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Air Trainer'), 'AQ4211-101'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Air Force 1 - Sail'), 'AQ4211-101'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Mac Attack'), 'HF4198-001'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Air Max 1 - Baroque'), 'DO9392-701'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Air Max 1 - Saturn'), 'DO9392-700'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Zoom Field Jaxx - Chocolate'), 'HQ3073-100'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan Jumpman Jack - Bright Cactus'), 'FZ8117-102'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan Jumpman Jack - Dark Mocha'), 'FZ8117-204'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan Jumpman Jack - Red'), 'FZ8117-101'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan Jumpman Jack - Sail'), 'FZ8117-100'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 High OG SP - Mocha'), 'CD4487-100'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 6 - Olive'), 'CN1084-200'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 4 - Oilers'), '308497-406'),
((SELECT id FROM products WHERE name = 'Travis Scott X Fragment Jordan 1 High OG SP'), 'DH3227-105'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 6 - Khaki'), 'DH0690-200'),
((SELECT id FROM products WHERE name = 'Travis Scott X PlayStation X Nike SB Dunk - PS5'), 'Unknown'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Olive'), 'DZ4137-106'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Mocha'), 'CQ4277-001'),
((SELECT id FROM products WHERE name = 'Travis Scott X Fragment Jordan 1 Low OG SP'), 'DM7866-140'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Reverse Mocha'), 'DM7866-162'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Canary'), 'DZ4137-700'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Phantom'), 'DM7866-001'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low Golf - Neutral'), 'FZ3124-200'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Medium Olive'), 'DM7866-200'),
((SELECT id FROM products WHERE name = 'Travis Scott X Jordan 1 Low OG SP - Velvet Brown'), 'DM7866-210'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Air Max 270'), 'CT2864-200'),
((SELECT id FROM products WHERE name = 'Travis Scott X Nike Air Force 1 - Cactus Jack'), 'CN2405-900');


INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown')
WHERE p.name = 'Travis Scott X Nike Air Trainer';


INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Cream')
WHERE p.name = 'Travis Scott X Nike Air Force 1 - Sail';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Green')
WHERE p.name = 'Travis Scott X Nike Air Force 1 - Cactus Jack';


INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Multicolor')
WHERE p.name = 'Travis Scott X Nike Air Max 270';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Grey')
WHERE p.name = 'Travis Scott X Nike Mac Attack';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown','Yellow')
WHERE p.name = 'Travis Scott X Nike Air Max 1 - Baroque';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Gold')
WHERE p.name = 'Travis Scott X Nike Air Max 1 - Saturn';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White','Brown')
WHERE p.name = 'Travis Scott X Nike Zoom Field Jaxx - Chocolate';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Yellow','Cream')
WHERE p.name = 'Travis Scott X Jordan Jumpman Jack - Bright Cactus';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black','Brown')
WHERE p.name = 'Travis Scott X Jordan Jumpman Jack - Dark Mocha';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White','Black')
WHERE p.name = 'Travis Scott X Jordan Jumpman Jack - Red';


INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown','Cream')
WHERE p.name = 'Travis Scott X Jordan Jumpman Jack - Sail';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Velvet Brown';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown','White')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Medium Olive';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown','Tan')
WHERE p.name = 'Travis Scott X Jordan 1 Low Golf - Neutral';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Black')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Phantom';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Yellow','Blue')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Canary';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White','Brown')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Reverse Mocha';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue','White')
WHERE p.name = 'Travis Scott X Fragment Jordan 1 Low OG SP';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown','Black')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Mocha';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('White','Black')
WHERE p.name = 'Travis Scott X Jordan 1 Low OG SP - Olive';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Cream','Blue')
WHERE p.name = 'Travis Scott X PlayStation X Nike SB Dunk - PS5';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Tan')
WHERE p.name = 'Travis Scott X Jordan 6 - Khaki';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue','White')
WHERE p.name = 'Travis Scott X Fragment Jordan 1 High OG SP';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Blue','Red')
WHERE p.name = 'Travis Scott X Jordan 4 - Oilers';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown')
WHERE p.name = 'Travis Scott X Jordan 6 - Olive';

INSERT INTO colors_products (product_id, color_id)
SELECT p.id, c.id
FROM products p
JOIN colors c ON c.name IN ('Brown','White')
WHERE p.name = 'Travis Scott X Jordan 1 High OG SP - Mocha';