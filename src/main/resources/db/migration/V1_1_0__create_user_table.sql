-- 🔹 LOCATIONS
INSERT INTO locations (created_at, created_by, status, city, street_address)
VALUES
(NOW(), 0, 'ACTIVE', 'New York', '123 Main St'),
(NOW(), 0, 'ACTIVE', 'San Francisco', '456 Market St');

-- 🔹 DEPARTMENTS
INSERT INTO departments (created_at, created_by, status, name, location_id, manager_id)
VALUES
(NOW(), 0, 'ACTIVE', 'Engineering', 1, NULL),
(NOW(), 0, 'ACTIVE', 'HR', 2, NULL);

-- 🔹 JOBS
INSERT INTO jobs (created_at, created_by, status, name, min_salary, max_salary)
VALUES
(NOW(), 0, 'ACTIVE', 'Software Engineer', 60000, 120000),
(NOW(), 0, 'ACTIVE', 'HR Specialist', 40000, 80000);

INSERT INTO employees (created_at, created_by, status, email, first_name, last_name, phone_number, hire_date, department_id, job_id, salary, manager_id)
VALUES
(NOW(), 0, 'ACTIVE', 'jose@gmail.com', 'Jose', 'Calancha', '555-1234', '2020-01-15', 1, 1, 95000.000, NULL),
(NOW(), 0, 'ACTIVE', 'carlos@gmail.com', 'Carlos', 'Pacheco', '555-2345', '2021-03-01', 1, 1, 85000.000, 1),
(NOW(), 0, 'ACTIVE', 'leydi@gmail.com', 'Leydi', 'Coronel', '555-3456', '2022-06-20', 2, 2, 50000.000, 1);

UPDATE departments SET manager_id = 1 WHERE id = 1;
UPDATE departments SET manager_id = 3 WHERE id = 2;

INSERT INTO users (username, password, employee_id, created_at, created_by, status) VALUES
('joseca', '$2a$12$6PBqiiu0QGGZ6JkwweutsOZlSRhtNpDCnXv3zkBs6Q5y8no4.MoXS', 1, now(), 0, 'ACTIVE'),
('carlopa', '$2a$12$6PBqiiu0QGGZ6JkwweutsOZlSRhtNpDCnXv3zkBs6Q5y8no4.MoXS', 2, now(), 0, 'ACTIVE'),
('leydico', '$2a$12$6PBqiiu0QGGZ6JkwweutsOZlSRhtNpDCnXv3zkBs6Q5y8no4.MoXS', 3, now(), 0, 'ACTIVE');

INSERT INTO job_history (created_at, created_by, status, employee_id, job_id, department_id, start_date, end_date)
VALUES
(NOW(), 0, 'ACTIVE', 2, 1, 1, '2021-03-01', '2022-12-31'),
(NOW(), 0, 'ACTIVE', 3, 2, 2, '2022-06-20', NULL);

INSERT INTO roles (role, description, created_at, created_by, status) VALUES 
('ADMIN', 'The admin can do everything.', now(), 0, 'ACTIVE'), 
('USER', 'Simple user.', now(), 0, 'ACTIVE');

INSERT INTO user_roles (user_id, role_id, created_at, created_by, status) VALUES
(1, 1, now(), 0, 'ACTIVE'),
(1, 2, now(), 0, 'ACTIVE'),
(2, 2, now(), 0, 'ACTIVE'),
(3, 2, now(), 0, 'ACTIVE');