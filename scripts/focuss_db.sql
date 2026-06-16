-- ─────────────────────────────────────────────
-- Focuss App — Script de base de datos
-- Base de datos: focuss_db
-- Motor: MySQL 8.0
-- ─────────────────────────────────────────────

CREATE DATABASE IF NOT EXISTS focuss_db;
USE focuss_db;

-- ─── TABLA: usuarios ─────────────────────────
CREATE TABLE IF NOT EXISTS usuarios (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    nombre   VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ─── TABLA: tareas ───────────────────────────
CREATE TABLE IF NOT EXISTS tareas (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(255),
    tiempo     INTEGER      NOT NULL,
    done       BIT          NOT NULL,
    usuario_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ─── TABLA: metas ────────────────────────────
CREATE TABLE IF NOT EXISTS metas (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(255),
    progreso    INTEGER      NOT NULL,
    usuario_id  BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ─── DATA DE PRUEBA ───────────────────────────
INSERT INTO usuarios (nombre, email, password) VALUES
('Juan Pérez',   'juan@email.com',  '12345678'),
('María García', 'maria@email.com', '12345678');

INSERT INTO tareas (nombre, tiempo, done, usuario_id) VALUES
('Estudiar matemáticas', 25, 0, 1),
('Leer capítulo 3',      30, 0, 1),
('Hacer ejercicios',     45, 1, 1),
('Repasar apuntes',      20, 0, 2);

INSERT INTO metas (descripcion, progreso, usuario_id) VALUES
('Leer 10 libros este año',       40, 1),
('Aprender Python avanzado',      70, 1),
('Hacer ejercicio 3 veces/semana', 20, 1),
('Estudiar inglés diario',         50, 2);
