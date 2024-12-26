# Electronic Property Manager Documentation

## General Overview

The **Electronic Property Manager** is a Java-based application designed to streamline the management of multi-family residential buildings. The application provides tools to manage companies, employees, buildings, apartments, fees, payments, and residents. It also generates comprehensive reports to assist property management companies in monitoring and maintaining their operations.

### Key Features
1. **Company Management**: Manage companies and their employees.
2. **Building and Apartment Management**: Assign buildings to employees and manage apartments, including their residents.
3. **Fee and Payment Management**: Calculate and track fees for apartments, record payments, and report outstanding balances.
4. **Dynamic Building Reassignment**: Automatically reassign buildings when an employee leaves the company.
5. **Reporting**: Generate detailed reports for:
    - Building assignments to employees.
    - Fee and payment tracking by apartment or building.
    - Outstanding balances by apartment.
6. **Validation**: Ensure data integrity with validation for names, email addresses, phone numbers, and assignment rules.

---

## Updated Database Structure

### 1. `companies`
**Description**: Stores information about property maintenance companies.
- **Columns**:
    - `id` (Primary Key)
    - `name`: Name of the company.
    - `address`: Address of the company.
    - `phone`: Contact number.

---

### 2. `employees`
**Description**: Represents employees managing buildings.
- **Columns**:
    - `id` (Primary Key)
    - `name`: Name of the employee.
    - `phone`: Contact number.
    - `email`: Email address.
    - `company_id` (Foreign Key): References `companies(id)`.

---

### 3. `buildings`
**Description**: Represents residential buildings managed by companies.
- **Columns**:
    - `id` (Primary Key)
    - `address`: Address of the building.
    - `floors`: Number of floors.
    - `number_of_apartments`: Total apartments.
    - `total_area`: Total area of the building.
    - `shared_area`: Shared area in square meters (default is 0).
    - `employee_id` (Foreign Key): References `employees(id)`.

---

### 4. `apartments`
**Description**: Represents individual apartments within buildings.
- **Columns**:
    - `id` (Primary Key)
    - `number`: Apartment number.
    - `floor`: Floor number.
    - `area`: Area of the apartment.
    - `building_id` (Foreign Key): References `buildings(id)`.

---

### 5. `residents`
**Description**: Tracks residents living in apartments.
- **Columns**:
    - `id` (Primary Key)
    - `name`: Resident name.
    - `age`: Age of the resident.
    - `uses_elevator`: Indicates if the resident uses the elevator.
    - `has_pet`: Indicates if the resident has a pet.
    - `apartment_id` (Foreign Key): References `apartments(id)`.

---

### 6. `fees`
**Description**: Tracks maintenance fees assigned to apartments.
- **Columns**:
    - `id` (Primary Key)
    - `amount`: Fee amount.
    - `due_date`: Payment due date.
    - `apartment_id` (Foreign Key): References `apartments(id)`.

---

### 7. `payments`
**Description**: Tracks payments made towards fees.
- **Columns**:
    - `id` (Primary Key)
    - `amount`: Payment amount.
    - `payment_date`: Date of payment.
    - `fee_id` (Foreign Key): References `fees(id)`.
    - `employee_id` (Foreign Key): References `employees(id)`.
    - `company_id` (Foreign Key): References `companies(id)`.

---

## Application Workflow and Usage

### 1. **Setup**
- First, run the SQL script to create the necessary tables (`companies`, `employees`, `buildings`, `apartments`, `residents`, `fees`, `payments`).
- Populate the tables with initial data such as companies, employees, buildings, apartments, residents, fees, and payments.

### 2. **Running the Application**
- The main class (e.g., `Main.java`) contains the application's entry point.
- Upon running the application, the system will present a menu with available options for managing entities and generating reports.

### 3. **Menu Options and How to Use Them**

Once the application is launched, you will see a menu with the following options. Each option performs a specific action or generates a report.

#### 1. **Add Company**
- **Description**: Allows you to add a new company to the system.
- **Usage**: When prompted, enter the company name, address, and phone number.
- **Example**:
  ```
  Enter company name: Global Properties
  Enter company address: 123 Main St, City, Country
  Enter company phone number: +1-234-567-890
  ```
- **Outcome**: The company will be added to the `companies` table in the database.

#### 2. **Add Employee**
- **Description**: Allows you to add an employee to a company.
- **Usage**: Provide the employee's name, phone, email, and the company ID to which the employee belongs.
- **Example**:
  ```
  Enter employee name: John Doe
  Enter employee phone number: +1-234-567-891
  Enter employee email: johndoe@email.com
  Enter company ID: 1
  ```
- **Outcome**: The employee will be added to the `employees` table and assigned to the specified company.

#### 3. **Assign Building to Employee**
- **Description**: Allows you to assign a building to an employee for management.
- **Usage**: Provide the building details (address, number of floors, total area) and the employee ID to whom the building will be assigned.
- **Example**:
  ```
  Enter building address: 45 Sunset Blvd, City, Country
  Enter number of floors: 5
  Enter total area: 1500.00
  Enter employee ID: 1
  ```
- **Outcome**: The building will be added to the `buildings` table and linked to the specified employee.

#### 4. **Add Apartment**
- **Description**: Allows you to add apartments to a building.
- **Usage**: Provide the apartment number, floor, area, and building ID to which the apartment belongs.
- **Example**:
  ```
  Enter apartment number: 101
  Enter floor number: 1
  Enter apartment area: 120.00
  Enter building ID: 1
  ```
- **Outcome**: The apartment will be added to the `apartments` table and linked to the specified building.

#### 5. **Add Resident**
- **Description**: Assigns a resident to an apartment.
- **Usage**: Enter the resident's name, age, elevator usage (true/false), pet ownership (true/false), and the apartment ID.
- **Example**:
  ```
  Enter resident name: Alice Johnson
  Enter resident age: 30
  Does the resident use the elevator (true/false): true
  Does the resident have a pet (true/false): false
  Enter apartment ID: 1
  ```
- **Outcome**: The resident will be added to the `residents` table and linked to the specified apartment.

#### 6. **Record Payment**
- **Description**: Records a payment made by a resident for a specific fee.
- **Usage**: Enter the payment amount, the fee ID, employee ID, and company ID.
- **Example**:
  ```
  Enter payment amount: 100.00
  Enter fee ID: 1
  Enter employee ID: 1
  Enter company ID: 1
  ```
- **Outcome**: The payment will be recorded in the `payments` table, updating the outstanding fee balance.

#### 7. **Generate Building Assignment Report**
- **Description**: Generates a report that lists all buildings assigned to each employee, including details like the number of apartments and the outstanding fees for each apartment.
- **Usage**: Select the report generation option. The system will fetch and display the data from the database.
- **Outcome**: A detailed report showing each employee's assigned buildings, the number of apartments, and any unpaid fees.

#### 8. **Generate Payment Report**
- **Description**: Generates a report showing payments made during a specified date range.
- **Usage**: Enter the date range (start and end dates) for which you want the report.
  ```
  Enter start date (YYYY-MM-DD): 2024-12-01
  Enter end date (YYYY-MM-DD): 2024-12-31
  ```
- **Outcome**: A report listing all payments made during that time period, including the amount paid and any outstanding balances.

---

## Example SQL Script for Setup

```sql
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
