-- Creación de la tabla de usuarios
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100),
                       second_name VARCHAR(100),
                       first_lastname VARCHAR(100),
                       second_lastname VARCHAR(100),
                       account_active BOOLEAN DEFAULT FALSE,
                       account_blocked BOOLEAN DEFAULT FALSE,
                       login_attempts INT DEFAULT 0,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creación de la tabla de tarjetas de credito
CREATE TABLE credit_cards (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              card_number VARCHAR(16) NOT NULL,
                              expiration_date VARCHAR(10) NOT NULL,
                              cvv VARCHAR(4) NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Creación de la tabla de roles
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       role_name VARCHAR(50) NOT NULL UNIQUE
);


-- Creación de la tabla de permisos
CREATE TABLE permissions (
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

INSERT INTO roles (role_name) VALUES  ('GERENTE'), ('ADMINISTRADOR'), ('USUARIO');


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


-- Asignar todos los permisos al rol GERENTE
INSERT INTO role_permissions (role_id, permission_id) VALUES
                                                          (1, 1), (1, 2), (1, 3), (1, 4);


-- Asignar algunos permisos al rol ADMINISTRADOR
INSERT INTO role_permissions (role_id, permission_id) VALUES
                                                          (2, 2), (2, 3), (2, 4);


-- Asignar permisos básicos al rol USUARIO
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (3, 4);


INSERT INTO users (email, password, first_name, second_name,  first_lastname, second_lastname, account_active) VALUES
                                                                                                                   ('admin@gmail.com', '$2a$12$amk/1mLvlv/VUEafSw3FC.mHBMbmnUgm2zRXi4cKZFa5YbAwFw2HS', 'Elmer', '', 'Figueroa', 'Arce', 1),
                                                                                                                   ('supervisor@gmail.com', '$2a$12$amk/1mLvlv/VUEafSw3FC.mHBMbmnUgm2zRXi4cKZFa5YbAwFw2HS', 'Claudia', 'Helena', 'Rodriguez', 'Avila', 1),
                                                                                                                   ('user@gmail.com', '$2a$12$amk/1mLvlv/VUEafSw3FC.mHBMbmnUgm2zRXi4cKZFa5YbAwFw2HS', 'Andres', '', 'Jimenez', 'Mantilla', 1);

-- Inserts para el usuario con email 'admin@gmail.com'
INSERT INTO credit_cards (user_id, card_number, expiration_date, cvv) VALUES
                                                                          (1, '1111222233334444', '12/25', '123'),
                                                                          (1, '5555666677778888', '10/24', '456');

-- Inserts para el usuario con email 'supervisor@gmail.com'
INSERT INTO credit_cards (user_id, card_number, expiration_date, cvv) VALUES
                                                                          (2, '2222111133334444', '05/26', '789'),
                                                                          (2, '9999888877776666', '08/23', '012');

-- Inserts para el usuario con email 'user@gmail.com'
INSERT INTO credit_cards (user_id, card_number, expiration_date, cvv) VALUES
    (3, '3333444455556666', '09/25', '345');


-- Asignar rol de ADMIN al usuario GERENTE
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);


-- Asignar rol de SUPERVISOR al usuario ADMINISTRADOR
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);


-- Asignar rol de USER al usuario USUARIO
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);


COMMIT;