-- Creación de la tabla de usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    first_name VARCHAR(100),
    second_name VARCHAR(100),
    first_surname VARCHAR(100),
    second_surname VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- Creación de la tabla de roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);


-- Creación de la tabla de permisos
CREATE TABLE permissionsusers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    permission_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);


-- Creación de la tabla de unión de usuarios y roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);


-- Creación de la tabla de unión de roles y permisos
CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
);

INSERT INTO roles (role_name) VALUES  ('ADMIN'), ('SUPERVISOR'), ('USER');


-- Permiso para gestión de usuarios
INSERT INTO permissions (permission_name, description) VALUES 
('MANAGE', 'Permite la creación, lectura, actualización y eliminación de usuarios');

-- Permiso para gestión de parqueaderos
INSERT INTO permissions (permission_name, description) VALUES 
('PARKING', 'Permite la gestión de espacios de parqueadero');

-- Permiso para ver y generar reportes y estadísticas
INSERT INTO permissions (permission_name, description) VALUES 
('REPORTS', 'Permite la visualización y generación de reportes y estadísticas');

-- Permiso para gestionar una reserva propia
INSERT INTO permissions (permission_name, description) VALUES 
('BOOKING', 'Permite gestión de todo lo referente a una reserva propia');


-- Asignar todos los permisos al rol ADMIN
INSERT INTO role_permissions (role_id, permission_id) VALUES 
(1, 1), (1, 2), (1, 3), (1, 4);


-- Asignar algunos permisos al rol SUPERVISOR 
INSERT INTO role_permissions (role_id, permission_id) VALUES 
(2, 2), (2, 3), (2, 4);


-- Asignar permisos básicos al rol USER
INSERT INTO role_permissions (role_id, permission_id) VALUES 
(3, 4);


INSERT INTO users (username, password, email, first_name, second_name,  first_surname, second_surname, is_active) VALUES 
('admin', '123', 'admin@gmail.com', 'Elmer', '', 'Figueroa', 'Arce', 1),
('supervisor', '123', 'supervisor@gmail.com', 'Claudia', 'Helena', 'Rodriguez', 'Avila', 1),
('user', '123', 'user@gmail.com', 'Andres', '', 'Jimenez', 'Mantilla', 1);


-- Asignar rol de ADMIN al usuario admin
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);


-- Asignar rol de SUPERVISOR al usuario supervisor
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);


-- Asignar rol de USER al usuario user
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);


COMMIT;