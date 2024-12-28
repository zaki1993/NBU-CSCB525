-- Create 'companies' table
CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Create 'employees' table
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    company_id INT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

-- Create 'buildings' table
CREATE TABLE buildings (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    floors INT NOT NULL,
    number_of_apartments INT NOT NULL,
    total_area NUMERIC(10, 2) NOT NULL,
    shared_area NUMERIC(10, 2) DEFAULT 0,
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL
);

-- Create 'apartments' table
CREATE TABLE apartments (
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    floor INT NOT NULL,
    area NUMERIC(10, 2) NOT NULL,
    building_id INT NOT NULL,
    FOREIGN KEY (building_id) REFERENCES buildings(id) ON DELETE CASCADE
);

-- Create 'residents' table
CREATE TABLE residents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    uses_elevator BOOLEAN NOT NULL DEFAULT FALSE,
    has_pet BOOLEAN NOT NULL DEFAULT FALSE,
    apartment_id INT NOT NULL,
    FOREIGN KEY (apartment_id) REFERENCES apartments(id) ON DELETE CASCADE
);

-- Create 'fees' table
CREATE TABLE fees (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    apartment_id INT NOT NULL,
    FOREIGN KEY (apartment_id) REFERENCES apartments(id) ON DELETE CASCADE
);

-- Create 'payments' table
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(10, 2) NOT NULL,
    payment_date DATE NOT NULL,
    fee_id INT NOT NULL,
    employee_id INT NOT NULL,
    company_id INT NOT NULL,
    FOREIGN KEY (fee_id) REFERENCES fees(id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

CREATE TABLE fee_configurations (
    id SERIAL PRIMARY KEY,
    base_fee_per_sq_meter DECIMAL(10, 2) NOT NULL,  -- Base fee per square meter
    elevator_fee_per_person DECIMAL(10, 2) NOT NULL, -- Fee per person using elevator
    pet_fee DECIMAL(10, 2) NOT NULL,                -- Fee for each pet in the apartment
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Timestamp for record creation
);