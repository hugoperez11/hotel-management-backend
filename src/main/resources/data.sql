-- Insertar una habitación por cada tipo en la tabla 'rooms'
INSERT INTO room (name, type, status)
VALUES 
    ('Superior Room', 'Superior', 'available'),
    ('Deluxe Room', 'Deluxe', 'available'),
    ('Junior Suite', 'Junior Suite', 'available'),
    ('Suite', 'Suite', 'available');

INSERT INTO reservation (customer_name, check_in_date, check_out_date, room_id)
VALUES
    ('John Doe', '2024-09-15', '2024-09-20', 1),
    ('Jane Smith', '2024-09-18', '2024-09-25', 3);


