-- Habitaciones superiores
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(1, 'Superior Room', 'Superior', 'Garden View', 'King Bed', 120, 'superior_garden_king.jpg', 'Habitación superior con vista al jardín y cama King.'),
(2, 'Superior Room', 'Superior', 'Garden View', 'Two Beds', 140, 'superior_garden_2beds.jpg', 'Habitación superior con vista al jardín y dos camas.');

-- Habitaciones Deluxe
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(3, 'Deluxe Room', 'Deluxe', 'Pool View', 'King Bed', 180, 'deluxe_pool_king.jpg', 'Habitación deluxe con vista a la piscina y cama King.'),
(4, 'Deluxe Room', 'Deluxe', 'Pool View', 'Two Beds', 200, 'deluxe_pool_2beds.jpg', 'Habitación deluxe con vista a la piscina y dos camas.');

-- Junior Suites
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(5, 'Junior Suite', 'Junior Suite', 'Ocean View', 'King Bed', 250, 'junior_suite_ocean_king.jpg', 'Junior suite con vista al océano y cama King.'),
(6, 'Junior Suite', 'Junior Suite', 'Ocean View', 'Two Beds', 270, 'junior_suite_ocean_2beds.jpg', 'Junior suite con vista al océano y dos camas.');

-- Suites
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(7, 'Suite', 'Suite', 'Ocean View', 'King Bed', 350, 'suite_ocean_king.jpg', 'Suite con vista al océano y cama King.'),
(8, 'Suite', 'Suite', 'Ocean View', 'Two Beds', 370, 'suite_ocean_2beds.jpg', 'Suite con vista al océano y dos camas.');


-- Inserción de datos en la tabla reservations

-- Datos de ejemplo
INSERT INTO customers (name, email) VALUES ('John Doe', 'john@example.com');
INSERT INTO customers (name, email) VALUES ('Jane Smith', 'jane@example.com');

INSERT INTO reservations (check_in_date, check_out_date, room_id, customer_id) VALUES 
('2024-10-01', '2024-10-05', 1, 1),
('2024-10-10', '2024-10-15', 2, 1), 
('2024-10-20', '2024-10-25', 1, 2), 
('2024-11-01', '2024-11-03', 3, 2);





