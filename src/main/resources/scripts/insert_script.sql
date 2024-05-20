-- Tipos de Vehiculos
INSERT INTO vehicle_type (type) VALUES  ('CARRO'), ('MOTO'), ('BICICLETA'),('VEHICULO_PESADO');
-- Tipos de Parqueaderos
INSERT INTO parking_type (type) VALUES  ('CUBIERTO'), ('SEMI-CUBIERTO'), ('DESCUBIERTO');
INSERT INTO slot_status (status) VALUES  ('EMPTY'), ('FULL');
INSERT INTO city (city) VALUES
                            ('Medellín'),
                            ('Cali'),
                            ('Barranquilla'),
                            ('Cartagena'),
                            ('Cúcuta'),
                            ('Bucaramanga'),
                            ('Pereira'),
                            ('Santa Marta'),
                            ('Ibagué'),
                            ('Manizales'),
                            ('Villavicencio'),
                            ('Neiva'),
                            ('Armenia'),
                            ('Pasto'),
                            ('Montería'),
                            ('Sincelejo'),
                            ('Valledupar'),
                            ('Quibdó'),
                            ('Riohacha'),
                            ('Tunja'),
                            ('Florencia'),
                            ('Popayán'),
                            ('San Andrés'),
                            ('Mitú'),
                            ('Mocoa'),
                            ('Inírida'),
                            ('Leticia'),
                            ('Yopal');
-- Horas de apertura
-- 24 Horas
INSERT INTO opening_hours (close_time,open_time) VALUES ('23:59','00:00');
-- Roles
INSERT INTO role (role_name) VALUES  ('GERENTE'), ('ADMINISTRADOR'), ('USUARIO');
-- Permiso para gestión de usuarios
INSERT INTO permission (permission_name, description) VALUES
    ('MANAGE', 'Permite la creación, lectura, actualización y eliminación de usuarios');

-- Permiso para gestión de parqueaderos
INSERT INTO permission (permission_name, description) VALUES
    ('PARKING', 'Permite la gestión de espacios de parqueadero');

-- Permiso para ver y generar reportes y estadísticas
INSERT INTO permission (permission_name, description) VALUES
    ('REPORTS', 'Permite la visualización y generación de reportes y estadísticas');

-- Permiso para gestionar una reserva propia
INSERT INTO permission (permission_name, description) VALUES
    ('BOOKING', 'Permite parking_slotgestión de todo lo referente a una reserva propia');


-- Asignar todos los permisos al rol GERENTE
INSERT INTO role_permission (role_id, permission_id) VALUES
                                                         (1, 1), (1, 2), (1, 3), (1, 4);


-- Asignar algunos permisos al rol ADMINISTRADOR
INSERT INTO role_permission (role_id, permission_id) VALUES
                                                         (2, 2), (2, 3), (2, 4);


-- Asignar permisos básicos al rol USUARIO
INSERT INTO role_permission (role_id, permission_id) VALUES
    (3, 4);


INSERT INTO user (email, password, first_name, second_name,  first_lastname, second_lastname, account_active) VALUES
                                                                                                                  ('admin@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Elmer', '', 'Figueroa', 'Arce', 1),
                                                                                                                  ('supervisor@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Claudia', 'Helena', 'Rodriguez', 'Avila', 1),
                                                                                                                  ('user@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Andres', '', 'Jimenez', 'Mantilla', 1);

-- Inserts para el usuario con email 'admin@gmail.com'
INSERT INTO credit_card (user_id, card_number, expiration_date, cvv) VALUES
                                                                         (1, '1111222233334444', '12/25', '123'),
                                                                         (1, '5555666677778888', '10/24', '456');

-- Inserts para el usuario con email 'supervisor@gmail.com'
INSERT INTO credit_card (user_id, card_number, expiration_date, cvv) VALUES
                                                                         (2, '2222111133334444', '05/26', '789'),
                                                                         (2, '9999888877776666', '08/23', '012');

-- Inserts para el usuario con email 'user@gmail.com'
INSERT INTO credit_card (user_id, card_number, expiration_date, cvv) VALUES
    (3, '3333444455556666', '09/25', '345');


-- Asignar rol de ADMIN al usuario GERENTE
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);


-- Asignar rol de SUPERVISOR al usuario ADMINISTRADOR
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);


-- Asignar rol de USER al usuario USUARIO
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);


COMMIT;