-- create database
CREATE DATABASE IF NOT EXISTS restaurantdb
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- create user (MySQL 8+ syntax)
CREATE USER IF NOT EXISTS 'restaurantuser'@'localhost' IDENTIFIED BY 'restaurantpass';

-- give the user rights on that database
GRANT ALL PRIVILEGES ON restaurantdb.* TO 'restaurantuser'@'localhost';

FLUSH PRIVILEGES;

SHOW DATABASES;
USE restaurantdb;