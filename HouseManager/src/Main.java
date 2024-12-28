import pojo.*;
import service.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Service instances
    private static CompanyService companyService;
    private static BuildingService buildingService;
    private static ResidentService residentService;
    private static EmployeeService employeeService;
    private static FeeService feeService;
    private static ReportService reportService;
    private static PaymentService paymentService;
    private static ApartmentService apartmentService;

    public static void main(String[] args) throws SQLException {
        companyService = new CompanyService();
        buildingService = new BuildingService();
        residentService = new ResidentService();
        employeeService = new EmployeeService();
        feeService = new FeeService();
        reportService = new ReportService();
        paymentService = new PaymentService();
        apartmentService = new ApartmentService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Company management
                    manageCompanies(scanner);
                    break;
                case 2: // Building management
                    manageBuildings(scanner);
                    break;
                case 3: // Apartments management
                    manageApartments(scanner);
                    break;
                case 4: // Resident management
                    manageResidents(scanner);
                    break;
                case 5: // Employee management
                    manageEmployees(scanner);
                    break;
                case 6: // Fee management
                    manageFees(scanner);
                    break;
                case 7: // Record payments
                    recordPayments(scanner);
                    break;
                case 8: // Filter and sort data
                    filterAndSortData(scanner);
                    break;
                case 9: // Generate reports
                    generateReports(scanner);
                    break;
                case 10: // Exit
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\nElectronic Property Manager");
        System.out.println("1. Manage Companies");
        System.out.println("2. Manage Buildings");
        System.out.println("3. Manage Apartments");
        System.out.println("4. Manage Residents");
        System.out.println("5. Manage Employees");
        System.out.println("6. Manage Fees");
        System.out.println("7. Record Payments");
        System.out.println("8. Filter and Sort Data");
        System.out.println("9. Generate Reports");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void manageCompanies(Scanner scanner) {
        System.out.println("\n--- Manage Companies ---");
        System.out.println("1. Add company");
        System.out.println("2. Edit company");
        System.out.println("3. Delete company");
        System.out.println("4. List Companies");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add company
                // Validate company name
                System.out.print("Enter company name: ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Company name cannot be empty. Please try again.");
                    return;
                }

                // Validate company address
                System.out.print("Enter company address: ");
                String address = scanner.nextLine();
                if (address.isEmpty()) {
                    System.out.println("Company address cannot be empty. Please try again.");
                    return;
                }

                // Validate company phone (simple format check for 10 digits)
                System.out.print("Enter company phone: ");
                String phone = scanner.nextLine();
                if (!phone.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    System.out.println("Invalid phone number format. Please enter in the format XXX-XXX-XXXX.");
                    return;
                }

                // Proceed with adding the company
                companyService.addCompany(name, address, phone);
                break;

            case 2: // Edit company
                // Validate company ID
                System.out.print("Enter company ID to edit: ");
                int editId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (!companyService.isCompanyExist(editId)) {
                    System.out.println("Company ID does not exist. Please try again.");
                    return;
                }

                // Validate new company name
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                if (newName.isEmpty()) {
                    System.out.println("Company name cannot be empty. Please try again.");
                    return;
                }

                // Validate new company address
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                if (newAddress.isEmpty()) {
                    System.out.println("Company address cannot be empty. Please try again.");
                    return;
                }

                // Validate new company phone (simple format check for 10 digits)
                System.out.print("Enter new phone: ");
                String newPhone = scanner.nextLine();
                if (!newPhone.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    System.out.println("Invalid phone number format. Please enter in the format XXX-XXX-XXXX.");
                    return;
                }

                // Proceed with editing the company
                companyService.editCompany(editId, newName, newAddress, newPhone);
                break;

            case 3: // Delete company
                // Validate company ID
                System.out.print("Enter company ID to delete: ");
                int deleteId = scanner.nextInt();
                if (!companyService.isCompanyExist(deleteId)) {
                    System.out.println("Company ID does not exist. Please try again.");
                    return;
                }

                // Proceed with deleting the company
                companyService.deleteCompany(deleteId);
                break;

            case 4: // List Companies
                companyService.listCompanies();
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void manageBuildings(Scanner scanner) {
        System.out.println("\n--- Manage Buildings ---");
        System.out.println("1. Add building");
        System.out.println("2. Edit building");
        System.out.println("3. Delete building");
        System.out.println("4. List Buildings");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add building
                // Validate building address
                System.out.print("Enter building address: ");
                String address = scanner.nextLine();
                if (address.isEmpty()) {
                    System.out.println("Building address cannot be empty. Please try again.");
                    return;
                }

                // Validate number of floors
                System.out.print("Enter number of floors: ");
                int floors = scanner.nextInt();
                if (floors <= 0) {
                    System.out.println("Number of floors must be greater than 0. Please try again.");
                    return;
                }

                // Validate number of apartments
                System.out.print("Enter number of apartments: ");
                int apartments = scanner.nextInt();
                if (apartments <= 0) {
                    System.out.println("Number of apartments must be greater than 0. Please try again.");
                    return;
                }

                // Validate total area
                System.out.print("Enter total area: ");
                double totalArea = scanner.nextDouble();
                if (totalArea <= 0) {
                    System.out.println("Total area must be greater than 0. Please try again.");
                    return;
                }

                // Validate shared area
                System.out.print("Enter shared area: ");
                double sharedArea = scanner.nextDouble();
                if (sharedArea < 0) {
                    System.out.println("Shared area cannot be negative. Please try again.");
                    return;
                }

                // Proceed with adding the building
                buildingService.addBuilding(address, floors, apartments, totalArea, sharedArea);
                break;

            case 2: // Edit building
                // Validate building ID
                System.out.print("Enter building ID to edit: ");
                int editId = scanner.nextInt();
                if (!buildingService.isBuildingExist(editId)) {
                    System.out.println("Building ID does not exist. Please try again.");
                    return;
                }
                scanner.nextLine(); // Consume newline

                // Validate new address
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                if (newAddress.isEmpty()) {
                    System.out.println("Building address cannot be empty. Please try again.");
                    return;
                }

                // Validate new number of floors
                System.out.print("Enter new number of floors: ");
                int newFloors = scanner.nextInt();
                if (newFloors <= 0) {
                    System.out.println("Number of floors must be greater than 0. Please try again.");
                    return;
                }

                // Validate new number of apartments
                System.out.print("Enter new number of apartments: ");
                int newApartments = scanner.nextInt();
                if (newApartments <= 0) {
                    System.out.println("Number of apartments must be greater than 0. Please try again.");
                    return;
                }

                // Validate new total area
                System.out.print("Enter new total area: ");
                double newTotalArea = scanner.nextDouble();
                if (newTotalArea <= 0) {
                    System.out.println("Total area must be greater than 0. Please try again.");
                    return;
                }

                // Validate new shared area
                System.out.print("Enter new shared area: ");
                double newSharedArea = scanner.nextDouble();
                if (newSharedArea < 0) {
                    System.out.println("Shared area cannot be negative. Please try again.");
                    return;
                }

                // Proceed with editing the building
                buildingService.editBuilding(editId, newAddress, newFloors, newApartments, newTotalArea, newSharedArea);
                break;

            case 3: // Delete building
                // Validate building ID
                System.out.print("Enter building ID to delete: ");
                int deleteId = scanner.nextInt();
                if (!buildingService.isBuildingExist(deleteId)) {
                    System.out.println("Building ID does not exist. Please try again.");
                    return;
                }

                // Proceed with deleting the building
                buildingService.deleteBuilding(deleteId);
                break;

            case 4: // List Buildings
                buildingService.listBuildings();
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void manageResidents(Scanner scanner) {
        System.out.println("\n--- Manage Residents ---");
        System.out.println("1. Add Resident");
        System.out.println("2. Edit Resident");
        System.out.println("3. Delete Resident");
        System.out.println("4. List Residents");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add Resident
                // Validate resident name
                System.out.print("Enter resident name: ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Resident name cannot be empty. Please try again.");
                    return;
                }

                // Validate resident age
                System.out.print("Enter resident age: ");
                int age = scanner.nextInt();
                if (age <= 0) {
                    System.out.println("Age must be a positive number. Please try again.");
                    return;
                }

                // Validate elevator use
                System.out.print("Does the resident use the elevator (true/false)? ");
                boolean usesElevator = scanner.nextBoolean();
                if (!(usesElevator || !usesElevator)) {
                    System.out.println("Invalid input for elevator use. Please enter 'true' or 'false'.");
                    return;
                }

                // Validate pet status
                System.out.print("Does the resident have a pet (true/false)? ");
                boolean hasPet = scanner.nextBoolean();
                if (!(hasPet || !hasPet)) {
                    System.out.println("Invalid input for pet status. Please enter 'true' or 'false'.");
                    return;
                }

                // Validate apartment ID
                System.out.print("Enter apartment ID: ");
                int apartmentId = scanner.nextInt();
                if (!apartmentService.isApartmentExist(apartmentId)) {
                    System.out.println("Apartment ID does not exist. Please try again.");
                    return;
                }

                // Proceed with adding the resident
                residentService.addResident(name, age, usesElevator, hasPet, apartmentId);
                break;

            case 2: // Edit Resident
                // Validate resident ID
                System.out.print("Enter resident ID to edit: ");
                int residentId = scanner.nextInt();
                if (!residentService.isResidentExist(residentId)) {
                    System.out.println("Resident ID does not exist. Please try again.");
                    return;
                }
                scanner.nextLine(); // Consume newline

                // Validate new name
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                if (newName.isEmpty()) {
                    System.out.println("Resident name cannot be empty. Please try again.");
                    return;
                }

                // Validate new age
                System.out.print("Enter new age: ");
                int newAge = scanner.nextInt();
                if (newAge <= 0) {
                    System.out.println("Age must be a positive number. Please try again.");
                    return;
                }

                // Validate new elevator use
                System.out.print("Does the resident use the elevator (true/false)? ");
                boolean newUsesElevator = scanner.nextBoolean();
                if (!(newUsesElevator || !newUsesElevator)) {
                    System.out.println("Invalid input for elevator use. Please enter 'true' or 'false'.");
                    return;
                }

                // Validate new pet status
                System.out.print("Does the resident have a pet (true/false)? ");
                boolean newHasPet = scanner.nextBoolean();
                if (!(newHasPet || !newHasPet)) {
                    System.out.println("Invalid input for pet status. Please enter 'true' or 'false'.");
                    return;
                }

                // Proceed with editing the resident
                residentService.editResident(residentId, newName, newAge, newUsesElevator, newHasPet);
                break;

            case 3: // Delete Resident
                // Validate resident ID
                System.out.print("Enter resident ID to delete: ");
                int deleteId = scanner.nextInt();
                if (!residentService.isResidentExist(deleteId)) {
                    System.out.println("Resident ID does not exist. Please try again.");
                    return;
                }

                // Proceed with deleting the resident
                residentService.deleteResident(deleteId);
                break;

            case 4: // List Residents
                residentService.listResidents();
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    private static void manageEmployees(Scanner scanner) {
        System.out.println("\n--- Manage Employees ---");
        System.out.println("1. Add Employee");
        System.out.println("2. Edit Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. List Employees");
        System.out.println("5. Assign Building to Employee");
        System.out.println("6. UnAssign Building");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add Employee
                // Validate employee name
                System.out.print("Enter employee name: ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Employee name cannot be empty. Please try again.");
                    return;
                }

                // Validate phone number
                System.out.print("Enter employee phone: ");
                String phone = scanner.nextLine();
                if (!phone.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    System.out.println("Invalid phone number format. Please try again.");
                    return;
                }

                // Validate email
                System.out.print("Enter employee email: ");
                String email = scanner.nextLine();
                if (!isValidEmail(email)) {
                    System.out.println("Invalid email format. Please try again.");
                    return;
                }

                // Validate company ID
                System.out.print("Enter company id: ");
                int companyId = scanner.nextInt();
                if (!companyService.isCompanyExist(companyId)) {
                    System.out.println("Company ID does not exist. Please try again.");
                    return;
                }

                // Proceed with adding the employee
                employeeService.addEmployee(name, phone, email, companyId);
                break;

            case 2: // Edit Employee
                // Validate employee ID
                System.out.print("Enter employee ID to edit: ");
                int employeeId = scanner.nextInt();
                if (!employeeService.isEmployeeExist(employeeId)) {
                    System.out.println("Employee ID does not exist. Please try again.");
                    return;
                }
                scanner.nextLine(); // Consume newline

                // Validate new name
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                if (newName.isEmpty()) {
                    System.out.println("Employee name cannot be empty. Please try again.");
                    return;
                }

                // Validate new phone number
                System.out.print("Enter new phone: ");
                String newPhone = scanner.nextLine();
                if (!newPhone.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    System.out.println("Invalid phone number format. Please try again.");
                    return;
                }

                // Validate new email
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                if (!isValidEmail(newEmail)) {
                    System.out.println("Invalid email format. Please try again.");
                    return;
                }

                // Validate new company ID
                System.out.print("Enter new company id: ");
                int newCompanyId = scanner.nextInt();
                if (!companyService.isCompanyExist(newCompanyId)) {
                    System.out.println("Company ID does not exist. Please try again.");
                    return;
                }

                // Proceed with editing the employee
                employeeService.editEmployee(employeeId, newName, newPhone, newEmail, newCompanyId);
                break;

            case 3: // Delete Employee
                // Validate employee ID
                System.out.print("Enter employee ID to delete: ");
                int deleteId = scanner.nextInt();
                if (!employeeService.isEmployeeExist(deleteId)) {
                    System.out.println("Employee ID does not exist. Please try again.");
                    return;
                }

                // Proceed with deleting the employee
                employeeService.deleteEmployee(deleteId);
                break;

            case 4: // List Employees
                employeeService.listEmployees();
                break;

            case 5: // Assign Building to Employee
                // Validate employee ID
                System.out.print("Enter employee ID: ");
                int empId = scanner.nextInt();
                if (!employeeService.isEmployeeExist(empId)) {
                    System.out.println("Employee ID does not exist. Please try again.");
                    return;
                }

                // Validate building ID
                System.out.print("Enter building ID: ");
                int buildingId = scanner.nextInt();
                if (!buildingService.isBuildingExist(buildingId)) {
                    System.out.println("Building ID does not exist. Please try again.");
                    return;
                }

                // Proceed with assigning building to employee
                employeeService.assignBuildingToEmployee(empId, buildingId);
                break;

            case 6: // UnAssign Building
                // Validate building ID
                System.out.print("Enter building ID: ");
                int buildingIdToUnassign = scanner.nextInt();
                if (!buildingService.isBuildingExist(buildingIdToUnassign)) {
                    System.out.println("Building ID does not exist. Please try again.");
                    return;
                }

                // Proceed with unassigning building from employee
                employeeService.unassignBuildingToEmployee(buildingIdToUnassign);
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void manageFees(Scanner scanner) {
        System.out.println("\n--- Manage Fees ---");
        System.out.println("1. Create Base Fee");
        System.out.println("2. Edit Base Fee");
        System.out.println("3. List Base Fees");
        System.out.println("4. List Fees");
        System.out.println("5. Collect Fees");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Create Base Fee
                // Input for base fee
                System.out.print("Enter base fee per square meter: ");
                double baseFeePerSqMeter = scanner.nextDouble();
                if (baseFeePerSqMeter <= 0) {
                    System.out.println("Base fee must be greater than zero. Please try again.");
                    return;
                }

                // Input for elevator fee
                System.out.print("Enter elevator fee per person: ");
                double elevatorFeePerPerson = scanner.nextDouble();
                if (elevatorFeePerPerson < 0) {
                    System.out.println("Elevator fee cannot be negative. Please try again.");
                    return;
                }

                // Input for pet fee
                System.out.print("Enter pet fee: ");
                double petFee = scanner.nextDouble();
                if (petFee < 0) {
                    System.out.println("Pet fee cannot be negative. Please try again.");
                    return;
                }

                // Create base fee if it doesn't already exist
                feeService.createBaseFee(baseFeePerSqMeter, elevatorFeePerPerson, petFee);
                System.out.println("Base fee created successfully (if it didn't already exist).");
                break;

            case 2: // Edit Base Fee
                // Input for updated base fee
                System.out.print("Enter new base fee per square meter: ");
                double newBaseFeePerSqMeter = scanner.nextDouble();
                if (newBaseFeePerSqMeter <= 0) {
                    System.out.println("Base fee must be greater than zero. Please try again.");
                    return;
                }

                // Input for updated elevator fee
                System.out.print("Enter new elevator fee per person: ");
                double newElevatorFeePerPerson = scanner.nextDouble();
                if (newElevatorFeePerPerson < 0) {
                    System.out.println("Elevator fee cannot be negative. Please try again.");
                    return;
                }

                // Input for updated pet fee
                System.out.print("Enter new pet fee: ");
                double newPetFee = scanner.nextDouble();
                if (newPetFee < 0) {
                    System.out.println("Pet fee cannot be negative. Please try again.");
                    return;
                }

                // Edit base fee
                feeService.editBaseFee(newBaseFeePerSqMeter, newElevatorFeePerPerson, newPetFee);
                System.out.println("Base fee updated successfully.");
                break;

            case 3: // List Base Fees
                feeService.listBaseFee();
                break;

            case 4: // List Fees
                feeService.listFees();
                break;
            case 5:
                System.out.print("Enter Building ID: ");
                int buildingId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Validate Building ID
                if (!buildingService.isBuildingExist(buildingId)) {
                    System.out.println("Building ID does not exist. Please try again.");
                    break;
                }

                // Validate and parse due date
                System.out.print("Enter due date for fee collection (yyyy-mm-dd): ");
                String dueDateStr = scanner.nextLine();
                Date dueDate = validateDate(dueDateStr);
                if (dueDate == null) {
                    System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                    break;
                }

                // Collect fees for the building
                feeService.collectFeesForBuilding(buildingId, dueDate);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void recordPayments(Scanner scanner) {
        System.out.println("\n--- Manage Payments ---");
        System.out.println("1. Record Payment");
        System.out.println("2. List All Payments");
        System.out.println("3. List Payments Between Two Dates");
        System.out.println("4. List Payments for a Specific Apartment");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Record Payment
                // Validate apartment ID
                System.out.print("Enter apartment ID: ");
                int apartmentId = scanner.nextInt();
                if (!apartmentService.isApartmentExist(apartmentId)) {
                    System.out.println("Invalid apartment ID. Please try again.");
                    return;
                }
                // Validate fee ID
                System.out.print("Enter fee ID: ");
                int feeId = scanner.nextInt();
                if (!feeService.isFeeExist(feeId)) {
                    System.out.println("Invalid fee ID. Please try again.");
                    return;
                }

                // Validate employee ID
                System.out.print("Enter employee ID: ");
                int employeeId = scanner.nextInt();
                if (!employeeService.isEmployeeExist(employeeId)) {
                    System.out.println("Invalid employee ID. Please try again.");
                    return;
                }
                int companyId = 0;
                try {
                    companyId = companyService.getCompanyByEmployee(employeeId).getId();
                } catch (SQLException e) {
                    System.out.println("Company is not valid for employee");
                }

                // Validate payment amount
                System.out.print("Enter payment amount: ");
                double amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Payment amount must be greater than zero. Please try again.");
                    return;
                }

                scanner.nextLine(); // Consume newline
                System.out.print("Enter payment date (YYYY-MM-DD): ");
                String paymentDate = scanner.nextLine();
                if (!isValidDateFormat(paymentDate)) {
                    System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
                    return;
                }

                // Proceed with recording the payment
                paymentService.addPayment(amount, paymentDate, feeId, employeeId, companyId);
                recordPaidFeesToFile(apartmentId, validateDate(paymentDate));
                break;

            case 2: // List All Payments
                paymentService.listAllPayments();
                break;

            case 3: // List Payments Between Two Dates
                // Validate start date
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                if (!isValidDateFormat(startDate)) {
                    System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
                    return;
                }

                // Validate end date
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                if (!isValidDateFormat(endDate)) {
                    System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
                    return;
                }

                // Proceed with listing payments by date range
                paymentService.listPaymentsByDateRange(startDate, endDate);
                break;

            case 4: // List Payments for a Specific Apartment
                // Validate apartment ID
                System.out.print("Enter apartment ID: ");
                apartmentId = scanner.nextInt();
                if (!apartmentService.isApartmentExist(apartmentId)) {
                    System.out.println("Invalid apartment ID. Please try again.");
                    return;
                }

                // Proceed with listing payments by apartment ID
                paymentService.listPaymentsByApartment(apartmentId);
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void filterAndSortData(Scanner scanner) throws SQLException {
        System.out.println("\n--- Filter and Sort Data ---");
        System.out.println("1. Sort Companies by Name");
        System.out.println("2. Sort Employees by Name");
        System.out.println("3. Sort Residents by Name");
        System.out.println("4. Filter Residents by Building");
        System.out.println("5. Filter Employees by Company");
        System.out.println("6. Filter Fees by Due Date");
        System.out.print("Enter your choice: ");

        // Validate the choice input
        int choice = -1;
        while (choice < 1 || choice > 6) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 6) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        switch (choice) {
            case 1: // Sort Companies by Name
                companyService.sortCompaniesByName();
                break;

            case 2: // Sort Employees by Name
                employeeService.sortEmployeesByName();
                break;

            case 3: // Sort Residents by Name
                residentService.sortResidentsByName();
                break;

            case 4: // Filter Residents by Building
                // Validate Building ID
                int buildingId = -1;
                boolean validBuildingId = false;
                while (!validBuildingId) {
                    System.out.print("Enter Building ID: ");
                    buildingId = scanner.nextInt();
                    if (buildingService.isBuildingExist(buildingId)) {
                        validBuildingId = true;
                    } else {
                        System.out.println("Invalid Building ID. Please enter a valid Building ID.");
                    }
                }
                residentService.filterResidentsByBuilding(buildingId);
                break;

            case 5: // Filter Employees by Company
                // Validate Company ID
                int companyId = -1;
                boolean validCompanyId = false;
                while (!validCompanyId) {
                    System.out.print("Enter Company ID: ");
                    companyId = scanner.nextInt();
                    if (companyService.isCompanyExist(companyId)) {
                        validCompanyId = true;
                    } else {
                        System.out.println("Invalid Company ID. Please enter a valid Company ID.");
                    }
                }
                employeeService.filterEmployeesByCompany(companyId);
                break;

            case 6: // Filter Fees by Due Date
                // Validate Due Date format
                String dueDate = "";
                boolean validDueDate = false;
                while (!validDueDate) {
                    System.out.print("Enter Due Date (YYYY-MM-DD): ");
                    dueDate = scanner.nextLine();
                    if (isValidDateFormat(dueDate)) {
                        validDueDate = true;
                    } else {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                    }
                }
                feeService.filterFeesByDueDate(dueDate);
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    private static void generateReports(Scanner scanner) throws SQLException {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("1. Summary Report");
        System.out.println("2. Detailed Payment Report");
        System.out.println("3. Detailed Building Assignment Report");
        System.out.print("Enter your choice: ");

        // Validate report type choice
        int choice = -1;
        while (choice < 1 || choice > 3) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        switch (choice) {
            case 1: // Generate Summary Report
                reportService.generateSummaryReport();
                break;

            case 2: // Generate Detailed Payment Report
                // Validate start date and end date format
                String startDate = "";
                String endDate = "";
                boolean validDateRange = false;

                while (!validDateRange) {
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    startDate = scanner.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    endDate = scanner.nextLine();

                    if (isValidDateFormat(startDate) && isValidDateFormat(endDate)) {
                        // Check that startDate is not after endDate
                        if (startDate.compareTo(endDate) <= 0) {
                            validDateRange = true; // Dates are valid
                        } else {
                            System.out.println("Start date cannot be later than end date. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
                    }
                }

                reportService.generatePaymentDetailsReport(startDate, endDate);
                break;

            case 3: // Generate Detailed Building Assignment Report
                reportService.generateBuildingAssignmentReport();
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void manageApartments(Scanner scanner) {
        System.out.println("\n--- Manage Apartments ---");
        System.out.println("1. Add Apartment");
        System.out.println("2. Edit Apartment");
        System.out.println("3. Delete Apartment");
        System.out.println("4. List Apartments");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add Apartment
                // Input validation
                int number = -1;
                int floor = -1;
                double area = -1;
                int buildingId = -1;

                // Validate apartment number
                while (number <= 0) {
                    System.out.print("Enter apartment number: ");
                    number = scanner.nextInt();
                    if (number <= 0) {
                        System.out.println("Apartment number must be a positive integer.");
                    }
                }

                // Validate apartment floor
                while (floor <= 0) {
                    System.out.print("Enter apartment floor: ");
                    floor = scanner.nextInt();
                    if (floor <= 0) {
                        System.out.println("Apartment floor must be a positive integer.");
                    }
                }

                // Validate apartment area
                while (area <= 0) {
                    System.out.print("Enter apartment area (sq ft): ");
                    area = scanner.nextDouble();
                    if (area <= 0) {
                        System.out.println("Apartment area must be a positive number.");
                    }
                }

                // Validate building ID
                while (buildingId <= 0) {
                    System.out.print("Enter building ID: ");
                    buildingId = scanner.nextInt();
                    if (buildingId <= 0) {
                        System.out.println("Building ID must be a positive integer.");
                    } else if (!buildingService.isBuildingExist(buildingId)) {
                        System.out.println("Building with ID " + buildingId + " does not exist.");
                        buildingId = -1; // Reset to retry input
                    }
                }

                // Call service to add the apartment
                apartmentService.addApartment(number, floor, area, buildingId);
                break;

            case 2: // Edit Apartment
                int id = -1;
                while (id <= 0 || !apartmentService.isApartmentExist(id)) {
                    System.out.print("Enter apartment ID to edit: ");
                    id = scanner.nextInt();
                    if (id <= 0) {
                        System.out.println("Apartment ID must be a positive integer.");
                    } else if (!apartmentService.isApartmentExist(id)) {
                        System.out.println("Apartment with ID " + id + " does not exist.");
                    }
                }

                // Input validation for editing
                int newApartmentNumber = -1;
                int newFloor = -1;
                double newArea = -1;
                int newBuildingId = -1;

                // Validate new apartment number
                while (newApartmentNumber <= 0) {
                    System.out.print("Enter new apartment number: ");
                    newApartmentNumber = scanner.nextInt();
                    if (newApartmentNumber <= 0) {
                        System.out.println("Apartment number must be a positive integer.");
                    }
                }

                // Validate new apartment floor
                while (newFloor <= 0) {
                    System.out.print("Enter new apartment floor: ");
                    newFloor = scanner.nextInt();
                    if (newFloor <= 0) {
                        System.out.println("Apartment floor must be a positive integer.");
                    }
                }

                // Validate new apartment area
                while (newArea <= 0) {
                    System.out.print("Enter new apartment area (sq ft): ");
                    newArea = scanner.nextDouble();
                    if (newArea <= 0) {
                        System.out.println("Apartment area must be a positive number.");
                    }
                }

                // Validate new building ID
                while (newBuildingId <= 0) {
                    System.out.print("Enter new building ID: ");
                    newBuildingId = scanner.nextInt();
                    if (newBuildingId <= 0) {
                        System.out.println("Building ID must be a positive integer.");
                    } else if (!buildingService.isBuildingExist(newBuildingId)) {
                        System.out.println("Building with ID " + newBuildingId + " does not exist.");
                        newBuildingId = -1; // Reset to retry input
                    }
                }

                // Call service to update the apartment
                apartmentService.updateApartment(id, newApartmentNumber, newFloor, newArea, newBuildingId);
                break;

            case 3: // Delete Apartment
                int deleteId = -1;
                while (deleteId <= 0 || !apartmentService.isApartmentExist(deleteId)) {
                    System.out.print("Enter apartment ID to delete: ");
                    deleteId = scanner.nextInt();
                    if (deleteId <= 0) {
                        System.out.println("Apartment ID must be a positive integer.");
                    } else if (!apartmentService.isApartmentExist(deleteId)) {
                        System.out.println("Apartment with ID " + deleteId + " does not exist.");
                    }
                }

                // Call service to delete the apartment
                apartmentService.deleteApartment(deleteId);
                break;

            case 4: // List Apartments
                apartmentService.listAllApartments();
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }


    // Helper method to validate the date format (YYYY-MM-DD)
    private static boolean isValidDateFormat(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateRegex);
    }

    public static Date validateDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Prevents invalid dates like "2024-02-30"
        try {
            return new Date(dateFormat.parse(dateStr).getTime());
        } catch (ParseException e) {
            return null; // Invalid date
        }
    }

    public static boolean isValidEmail(String email) {
        // A simple regex to validate email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void recordPaidFeesToFile(int apartmentId, Date paymentDate) {
        try {
            // Fetch payments for the apartment
            List<Payment> payments = paymentService.getPaymentsForApartment(apartmentId);

            // Fetch the relevant building and employee
            Apartment apartment = apartmentService.getApartmentById(apartmentId);
            Building building = buildingService.getBuildingById(apartment.getBuildingId());
            Employee employee = building.getAssignedEmployee();
            Company company = companyService.getCompanyByEmployee(employee.getId());

            // Prepare file for writing
            FileWriter fw = new FileWriter("paid_fees.csv", true); // Append to the file
            BufferedWriter bw = new BufferedWriter(fw);

            // Write header if the file is empty
            if (new java.io.File("paid_fees.csv").length() == 0) {
                bw.write("Company, Employee, Building, Apartment, Amount, Payment Date\n");
            }

            // Loop through all payments and write them to the file
            for (Payment payment : payments) {
                String record = String.format("%s, %s, %s, %s, %.2f, %s\n",
                        company.getName(),
                        employee.getName(),
                        building.getAddress(),
                        apartment.getNumber(),
                        payment.getAmount(),
                        new SimpleDateFormat("yyyy-MM-dd").format(payment.getPaymentDate())
                );
                bw.write(record); // Write the record
            }

            // Close the file
            bw.close();
            fw.close();

            System.out.println("Paid fees have been recorded successfully!");
        } catch (IOException | SQLException e) {
            System.out.println("Error while recording paid fees: " + e.getMessage());
        }
    }
}
