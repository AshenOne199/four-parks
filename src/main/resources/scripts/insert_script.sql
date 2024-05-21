-- Tipos de Vehiculos
INSERT INTO vehicle_type (type) VALUES  ('CARRO'), ('MOTO'), ('BICICLETA'),('VEHICULO_PESADO');
-- Tipos de Parqueaderos
INSERT INTO parking_type (type) VALUES  ('CUBIERTO'), ('SEMI-CUBIERTO'), ('DESCUBIERTO');
INSERT INTO slot_status (status) VALUES  ('EMPTY'), ('FULL');
INSERT INTO city (city) VALUES 
                            ('Bogotá D.C.'),
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
                                                                                                                  ('chrodrigueza@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Claudia', 'Helena', 'Rodriguez', 'Avila', 1),
                                                                                                                  ('lihernandezr@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Laura', 'Isabel', 'Hernández', 'Ramírez', 1),
                                                                                                                  ('lagarciaf@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Luis', 'Alberto', 'García', 'Fernández', 1),
                                                                                                                  ('asmartinezl@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Ana', 'Sofía', 'Martínez', 'López', 1),
                                                                                                                  ('jsrodriguezs@gmail.com', '$2a$12$p/If5oGCgdEnstBg7V9SQe5Wyq34KGK9bcdDCZRTs/lCQZdfsFGqK', 'Juan', 'Sebastian', 'Rodriguez', 'Sánchez', 1),
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
INSERT INTO user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO user_role (user_id, role_id) VALUES (4, 2);
INSERT INTO user_role (user_id, role_id) VALUES (5, 2);
INSERT INTO user_role (user_id, role_id) VALUES (6, 2);


-- Asignar rol de USER al usuario USUARIO
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);

-- Insert para Opening Hours
INSERT INTO opening_hours (open_time,close_time) VALUES 
                            ('04:00','23:00'),
                            ('06:00','23:59'),
                            ('08:00','22:00'),
                            ('10:00','18:00'),
                            ('23:59','00:00');

-- Insert para Location
INSERT INTO location (address,latitude,longitude,city_id) VALUES
                            ('Cra. 8 #42-2 a 42-36',4.629744013910357,-74.06570687142106,1),
                            ('Cll. 58 #35a-41',4.648625,-74.080508,1),
                            ('Cll. 52 #70c-2',4.667980, -74.105721,1),
                            ('Cll. 101 #13-41',4.684511, -74.045931,1),
                            ('Cra. 29a Bis #22c-99 a 22c-1',4.622206, -74.084291,1);
-- Inserts para Parqueaderos
INSERT INTO parking(available_slots,car_slots,motorcycle_slots,bicycle_slots,heavy_vehicle_slots,loyalty,name,total_slots,admin_id,location_id,opening_hours_id,parking_type_id) VALUES
                            (10,2,2,2,2,true,'Cuatro Parques',10,2,1,1,1),
                            (20,10,2,2,6,false,'Park & Go',20,3,2,2,2),
                            (15,5,3,3,4,true,'AutoPARK',15,4,3,3,3),
                            (20,5,5,5,5,false,'Parqueadero Seguro',20,5,4,4,1),
                            (10,2,2,2,2,true,'ParkSmart',10,6,5,5,2);

-- Inserts de espacios para el Parqueadero con nombre 'Cuatro Parques'
INSERT INTO parking_slot(parking_id, slot_status_id, vehicle_type_id) VALUES
                            (1, 1, 1),(1, 1, 1),
                            (1, 1, 2),(1, 1, 2),
                            (1, 1, 3),(1, 1, 3),
                            (1, 1, 4),(1, 1, 4);

-- Inserts de espacios para el Parqueadero con nombre 'Park & Go'
INSERT INTO parking_slot (parking_id, slot_status_id, vehicle_type_id) VALUES
                            (2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),(2, 1, 1),
                            (2, 1, 2),(2, 1, 2),
                            (2, 1, 3),(2, 1, 3),
                            (2, 1, 4),(2, 1, 4),(2, 1, 4),(2, 1, 4),(2, 1, 4),(2, 1, 4);

-- Inserts de espacios para el Parqueadero con nombre 'AutoPARK'
INSERT INTO parking_slot (parking_id, slot_status_id, vehicle_type_id) VALUES
                            (3, 1, 1),(3, 1, 1),(3, 1, 1),(3, 1, 1),
                            (3, 1, 2),(3, 1, 2),
                            (3, 1, 3),(3, 1, 3),
                            (3, 1, 4),(3, 1, 4),(3, 1, 4),(3, 1, 4);

-- Inserts de espacios para el Parqueadero con nombre 'Parqueadero Seguro'
INSERT INTO parking_slot (parking_id, slot_status_id, vehicle_type_id) VALUES
                            (4, 1, 1),(4, 1, 1),(4, 1, 1),(4, 1, 1),(4, 1, 1),
                            (4, 1, 2),(4, 1, 2),(4, 1, 2),(4, 1, 2),(4, 1, 2),
                            (4, 1, 3),(4, 1, 3),(4, 1, 3),(4, 1, 3),(4, 1, 3),
                            (4, 1, 4),(4, 1, 4),(4, 1, 4),(4, 1, 4),(4, 1, 4);

-- Inserts de espacios para el Parqueadero con nombre 'ParkSmart'
INSERT INTO parking_slot (parking_id, slot_status_id, vehicle_type_id) VALUES
                            (5, 1, 1),(5, 1, 1),
                            (5, 1, 2),(5, 1, 2),
                            (5, 1, 3),(5, 1, 3),
                            (5, 1, 4),(5, 1, 4);

-- Insert de precios para el Parqueadero con nombre 'Cuatro Parques'
INSERT INTO parking_rate (rate, parking_id, vehicle_type_id) VALUES
                            (163 , 1, 1),
                            (114 , 1, 2),
                            (10 , 1, 3),
                            (300  , 1, 4);

-- Insert de precios para el Parqueadero con nombre 'Park & Go'
INSERT INTO parking_rate (rate, parking_id, vehicle_type_id) VALUES
                            (120 , 2, 1),
                            (85  , 2, 2),
                            (10 , 2, 3),
                            (200  , 2, 4);

-- Insert de precios para el Parqueadero con nombre 'AutoPARK'
INSERT INTO parking_rate (rate, parking_id, vehicle_type_id) VALUES
                            (279 , 3, 1),
                            (195  , 3, 2),
                            (10 , 3, 3),
                            (250  , 3, 4);

-- Insert de precios para el Parqueadero con nombre 'Parqueadero Seguro'
INSERT INTO parking_rate (rate, parking_id, vehicle_type_id) VALUES
                            (130 , 4, 1),
                            (90  , 4, 2),
                            (10 , 4, 3),
                            (350  , 4, 4);

-- Insert de precios para el Parqueadero con nombre 'ParkSmart'
INSERT INTO parking_rate (rate, parking_id, vehicle_type_id) VALUES
                            (150 , 5, 1),
                            (100  , 5, 2),
                            (10 , 5, 3),
                            (280  , 5, 4);

COMMIT;