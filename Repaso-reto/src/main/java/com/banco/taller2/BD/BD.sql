CREATE DATABASE prestamos_bancarios
USE prestamos_bancarios

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY, 
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion TEXT
);

CREATE TABLE prestamo (
    id_prestamo SERIAL PRIMARY KEY, 
    monto DECIMAL(15, 2) NOT NULL,
    interes DECIMAL(5, 2) NOT NULL,
    duracion_meses INTEGER NOT NULL,
    estado VARCHAR(50) DEFAULT,
    cliente_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);