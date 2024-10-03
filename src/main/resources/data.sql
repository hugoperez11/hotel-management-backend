INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_ADMIN');
INSERT INTO users (id_user, username, password) VALUES (default, 'pepa', '$2a$12$fw8qjZwWYhvR.xzLUN5LuejC03NqCFuJQYwnstpBWki.Yi4L64MkW');
INSERT INTO roles_users (role_id, user_id) VALUES (1, 1);

ALTER TABLE rooms
ALTER COLUMN description SET DATA TYPE TEXT;

-- Superior Rooms
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(1, 'Superior Room', 'Superior', 'Garden View', 'King Bed', 120, '/img/SuperiorRoom.webp', 
'Superior room of 32m²/344ft² with garden view and King bed. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.'),
(2, 'Superior Room', 'Superior', 'Garden View', 'Two Beds', 140, '/img/SuperiorRoom2beds.webp', 
'Superior room of 32m²/344ft² with garden view and two beds. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.');

-- Deluxe Rooms
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(3, 'Deluxe Room', 'Deluxe', 'Pool View', 'King Bed', 180, '/img/DeluxeRoom.jpg', 
'Deluxe room of 32m²/344ft² with pool view and King bed. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.'),
(4, 'Deluxe Room', 'Deluxe', 'Pool View', 'Two Beds', 200, '/img/deluxe2camas.webp', 
'Deluxe room of 32m²/344ft² with pool view and two beds. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.');

-- Junior Suites
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(5, 'Junior Suite', 'Junior Suite', 'Ocean View', 'King Bed', 250, '/img/JuniorSuite.jpg', 
'Junior suite of 40m²/430ft² with ocean view and King bed. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.'),
(6, 'Junior Suite', 'Junior Suite', 'Ocean View', 'Two Beds', 270, '/img/JuniorSuite2beds.webp', 
'Junior suite of 40m²/430ft² with ocean view and two beds. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.');

-- Suites
INSERT INTO rooms (id, name, type, view, bed, price, image, description)
VALUES 
(7, 'Suite', 'Suite', 'Ocean View', 'King Bed', 350, '/img/Suite.jpg', 
'Suite of 60m²/702ft² with ocean view and King bed. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.'),
(8, 'Suite', 'Suite', 'Ocean View', 'Two Beds', 370, '/img/Suite2beds.webp', 
'Suite of 60m²/702ft² with ocean view and two beds. Features: pillow-top mattress, down mattress, comforter. Bathroom features: marble bathroom, shower, double vanity, makeup mirror with lighting, hairdryer. Air-conditioned, it is a non-smoking room. Includes robe and slippers.');


-- Inserción de datos en la tabla reservations

-- Datos de ejemplo
INSERT INTO customers (name, email) VALUES ('John Doe', 'john@example.com');
INSERT INTO customers (name, email) VALUES ('Jane Smith', 'jane@example.com');

INSERT INTO reservations (check_in_date, check_out_date, room_id, customer_id, confirmation_number) VALUES 
('2024-10-01', '2024-10-05', 1, 1, 'C123456'),
('2024-10-10', '2024-10-15', 2, 1, 'C654321'), 
('2024-10-20', '2024-10-25', 1, 2, 'C987654'), 
('2024-11-01', '2024-11-03', 3, 2, 'C234567');






