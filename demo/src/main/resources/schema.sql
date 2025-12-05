-- Crear base de datos almacen_db
CREATE DATABASE IF NOT EXISTS almacen_db;
USE almacen_db;

-- Crear tabla de sedes
CREATE TABLE IF NOT EXISTS sedes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    id_sede BIGINT NOT NULL,
    INDEX idx_id_sede (id_sede),
    CONSTRAINT fk_producto_sede FOREIGN KEY (id_sede) REFERENCES sedes(id)
);

-- Insertar sedes
INSERT INTO sedes (nombre) VALUES ('Sede Principal'), ('Sede Norte'), ('Sede Sur');

-- Insertar datos de ejemplo
INSERT INTO productos (nombre, cantidad, id_sede) VALUES
('PT1', 100, 1),
('PT2', 50, 1),
('PT3', 75, 1),
('PT4', 30, 1),
('PT5', 200, 2),
('PT6', 45, 2),
('PT7', 90, 2),
('PT8', 60, 3),
('PT9', 120, 3),
('PT10', 85, 3);
