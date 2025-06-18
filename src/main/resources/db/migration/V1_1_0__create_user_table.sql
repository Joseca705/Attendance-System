INSERT INTO persons (first_name, last_name, created_at, created_by, status) VALUES
('Jose', 'Calancha', now(), 0, 'ACTIVE'),
('Carlos', 'Pacheco', now(), 0, 'ACTIVE');

INSERT INTO roles (role, description, created_at, created_by, status) VALUES 
('ADMIN', 'The admin can do everything.', now(), 0, 'ACTIVE'), 
('USER', 'Simple user.', now(), 0, 'ACTIVE');

INSERT INTO users (username, password, email, person_id, created_at, created_by, status) VALUES
('joseca', '$2a$12$6PBqiiu0QGGZ6JkwweutsOZlSRhtNpDCnXv3zkBs6Q5y8no4.MoXS', 'jose@gmail.com', 1, now(), 0, 'ACTIVE'),
('carlopa', '$2a$12$6PBqiiu0QGGZ6JkwweutsOZlSRhtNpDCnXv3zkBs6Q5y8no4.MoXS', 'carlos@gmail.com', 2, now(), 0, 'ACTIVE');

INSERT INTO user_roles (user_id, role_id, created_at, created_by, status) VALUES
(1, 1, now(), 0, 'ACTIVE'),
(1, 2, now(), 0, 'ACTIVE'),
(2, 2, now(), 0, 'ACTIVE');