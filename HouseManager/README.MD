# Electronic Property Manager Documentation

## General Idea of the Project

The "Electronic Property Manager" application is designed to facilitate the management of multi-family residential buildings by professional property maintenance companies. The application helps manage properties, tenants, employees, and associated fees. It provides tools for assigning buildings to employees, calculating and recording fees, and generating detailed reports.

### Key Functionalities
1. Manage companies, employees, and buildings.
2. Assign buildings to employees based on workload.
3. Track apartments, tenants, and their attributes.
4. Calculate fees based on apartment area, number of residents, elevator usage, and pet ownership.
5. Record payments for fees and generate reports on payments and pending amounts.
6. Automatically reassign buildings when an employee leaves the company.

---

## Database Structure

The database consists of the following tables:

### 1. `companies`
- **Columns:**
    - `id`: Primary key.
    - `name`: Name of the company.
    - `address`: Address of the company.
    - `phone`: Contact phone number.
- **Relations:**
    - One-to-Many with `employees`.

### 2. `employees`
- **Columns:**
    - `id`: Primary key.
    - `name`: Name of the employee.
    - `phone`: Contact phone number.
    - `email`: pojo.Employee email.
    - `company_id`: Foreign key referencing `companies`.
- **Relations:**
    - One-to-Many with `buildings`.

### 3. `buildings`
- **Columns:**
    - `id`: Primary key.
    - `address`: pojo.Building address.
    - `floors`: Number of floors in the building.
    - `apartments_count`: Number of apartments.
    - `total_area`: Total area of the building.
    - `employee_id`: Foreign key referencing `employees`.
- **Relations:**
    - One-to-Many with `apartments`.

### 4. `apartments`
- **Columns:**
    - `id`: Primary key.
    - `number`: Apartment number.
    - `floor`: Floor number.
    - `area`: Area of the apartment.
    - `building_id`: Foreign key referencing `buildings`.
- **Relations:**
    - One-to-Many with `residents` and `fees`.

### 5. `residents`
- **Columns:**
    - `id`: Primary key.
    - `name`: Name of the resident.
    - `age`: Age of the resident.
    - `uses_elevator`: Whether the resident uses the elevator.
    - `has_pet`: Whether the resident has a pet.
    - `apartment_id`: Foreign key referencing `apartments`.

### 6. `fees`
- **Columns:**
    - `id`: Primary key.
    - `amount`: Fee amount.
    - `due_date`: Due date for payment.
    - `apartment_id`: Foreign key referencing `apartments`.
- **Relations:**
    - One-to-Many with `payments`.

### 7. `payments`
- **Columns:**
    - `id`: Primary key.
    - `amount`: Payment amount.
    - `payment_date`: Date of payment.
    - `fee_id`: Foreign key referencing `fees`.
    - `employee_id`: Foreign key referencing `employees`.
    - `company_id`: Foreign key referencing `companies`.

---

## Java Implementation

### Key Classes

1. **`pojo.Company`**
    - Represents a property maintenance company.
    - Attributes: `id`, `name`, `address`, `phone`, `employees`.

2. **`pojo.Employee`**
    - Represents an employee managing buildings.
    - Attributes: `id`, `name`, `phone`, `email`, `assignedBuildings`.

3. **`pojo.Building`**
    - Represents a building managed by a company.
    - Attributes: `id`, `address`, `floors`, `apartmentsCount`, `totalArea`, `assignedEmployee`.

4. **`Apartment`**
    - Represents an apartment in a building.
    - Attributes: `id`, `number`, `floor`, `area`, `building`.

5. **`pojo.Resident`**
    - Represents a resident in an apartment.
    - Attributes: `id`, `name`, `age`, `usesElevator`, `hasPet`.

6. **`Fee`**
    - Represents a maintenance fee for an apartment.
    - Attributes: `id`, `amount`, `dueDate`, `apartment`.

7. **`Payment`**
    - Represents a payment for a fee.
    - Attributes: `id`, `amount`, `paymentDate`, `fee`, `employee`, `company`.

---

### Service Layer

The service layer contains the following:
- **DAO Classes**: Perform database operations for each entity.
- **Business Logic**:
    - Assigning buildings to employees.
    - Calculating fees based on apartment attributes.
    - Recording and retrieving payments.

---

### Relationships in Java
Each Java class mirrors its database table and maintains relationships using lists and references. For example:
- `pojo.Employee` contains a `List<pojo.Building>`.
- `pojo.Building` contains a `List<Apartment>`.
- `Apartment` contains a `List<pojo.Resident>`.

---

## Features and Validation

### Validation
- Ensures valid phone numbers, email addresses, and non-empty names.
- Checks for duplicate assignments (e.g., building already assigned to an employee).

### Reports
1. Buildings managed by each employee.
2. Total fees and payments by company, employee, or building.
3. Tenants in each building or apartment.

---

## Example SQL Script

### Create Database
```sql
CREATE DATABASE electronic_property_manager;
USE electronic_property_manager;
