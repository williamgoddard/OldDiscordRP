CREATE SCHEMA IF NOT EXISTS discordrp;
USE discordrp;

CREATE TABLE IF NOT EXISTS inventories (
	inventory_id INT NOT NULL AUTO_INCREMENT,
	capacity DECIMAL(10,2) NULL DEFAULT 0,
	PRIMARY KEY (inventory_id)
);

CREATE TABLE IF NOT EXISTS rooms (
    room_id INT NOT NULL AUTO_INCREMENT,
    channel_id VARCHAR(18) NOT NULL,
    name VARCHAR(40) NULL DEFAULT NULL,
    inventory INT NOT NULL,
    PRIMARY KEY (room_id),
    FOREIGN KEY (inventory) REFERENCES inventories(inventory_id)
);

CREATE TABLE IF NOT EXISTS players (
    player_id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(18) NOT NULL,
    first_name VARCHAR(40) NULL DEFAULT NULL,
    last_name VARCHAR(40) NULL DEFAULT NULL,
    description VARCHAR(1500) NULL DEFAULT NULL,
    sex VARCHAR(40) NULL DEFAULT NULL,
    height INT NULL DEFAULT 0,
    weight INT NULL DEFAULT 0,
    inv_items INT NOT NULL,
    inv_clothes INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (player_id),
    FOREIGN KEY (inv_items) REFERENCES inventories(inventory_id),
    FOREIGN KEY (inv_clothes) REFERENCES inventories(inventory_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

CREATE TABLE IF NOT EXISTS doors (
    door_id INT NOT NULL AUTO_INCREMENT,
    room1_id INT NOT NULL,
    room2_id INT NOT NULL,
    name VARCHAR(40) NULL DEFAULT NULL,
    hidden BOOLEAN NULL DEFAULT FALSE,
    is_locked BOOLEAN NULL DEFAULT FALSE,
    lock_val VARCHAR(40) NULL DEFAULT NULL,
    PRIMARY KEY (door_id),
    FOREIGN KEY (room1_id) REFERENCES rooms(room_id),
    FOREIGN KEY (room2_id) REFERENCES rooms(room_id)
);

CREATE TABLE IF NOT EXISTS items (
    item_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NULL DEFAULT NULL,
    description VARCHAR(1500) NULL DEFAULT NULL,
    weight DECIMAL(10,2) NULL DEFAULT 1.0,
    takeable BOOLEAN NULL DEFAULT FALSE,
    wearable BOOLEAN NULL DEFAULT FALSE,
    infinite BOOLEAN NULL DEFAULT FALSE,
    key_val VARCHAR(40) NULL DEFAULT NULL,
    inv_id INT NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY (inv_id) REFERENCES inventories(inventory_id)
);