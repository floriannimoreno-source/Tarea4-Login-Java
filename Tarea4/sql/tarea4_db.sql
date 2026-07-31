-- Script de creación de la base de datos para Tarea 4
-- Ejecutar en MySQL antes de correr la aplicación

CREATE DATABASE IF NOT EXISTS tarea4_db;
USE tarea4_db;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
