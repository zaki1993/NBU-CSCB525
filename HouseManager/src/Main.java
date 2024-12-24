import service.*;

import java.sql.Date;
import java.sql.SQLException;
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

    public static void main(String[] args) throws SQLException {
        companyService = new CompanyService();
        buildingService = new BuildingService();
        residentService = new ResidentService();
        employeeService = new EmployeeService();
        feeService = new FeeService();
        reportService = new ReportService();
        paymentService = new PaymentService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // pojo.Company management
                    manageCompanies(scanner);
                    break;
                case 2: // pojo.Building management
                    manageBuildings(scanner);
                    break;
                case 3: // pojo.Resident management
                    manageResidents(scanner);
                    break;
                case 4: // pojo.Employee management
                    manageEmployees(scanner);
                    break;
                case 5: // Fee management
                    manageFees(scanner);
                    break;
                case 6: // Record payments
                    recordPayments(scanner);
                    break;
                case 7: // Filter and sort data
                    filterAndSortData(scanner);
                    break;
                case 8: // Generate reports
                    generateReports(scanner);
                    break;
                case 9: // Exit
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
        System.out.println("3. Manage Residents");
        System.out.println("4. Manage Employees");
        System.out.println("5. Assign Buildings to Employees");
        System.out.println("6. Manage Fees");
        System.out.println("7. Record Payments");
        System.out.println("8. Filter and Sort Data");
        System.out.println("9. Generate Reports");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void manageCompanies(Scanner scanner) {
        System.out.println("\n--- Manage Companies ---");
        System.out.println("1. Add pojo.Company");
        System.out.println("2. Edit pojo.Company");
        System.out.println("3. Delete pojo.Company");
        System.out.println("4. List Companies");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add pojo.Company
                System.out.print("Enter company name: ");
                String name = scanner.nextLine();
                System.out.print("Enter company address: ");
                String address = scanner.nextLine();
                System.out.print("Enter company phone: ");
                String phone = scanner.nextLine();
                companyService.addCompany(name, address, phone);
                break;
            case 2: // Edit pojo.Company
                System.out.print("Enter company ID to edit: ");
                int editId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                System.out.print("Enter new phone: ");
                String newPhone = scanner.nextLine();
                companyService.editCompany(editId, newName, newAddress, newPhone);
                break;
            case 3: // Delete pojo.Company
                System.out.print("Enter company ID to delete: ");
                int deleteId = scanner.nextInt();
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
        System.out.println("1. Add pojo.Building");
        System.out.println("2. Edit pojo.Building");
        System.out.println("3. Delete pojo.Building");
        System.out.println("4. List Buildings");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add pojo.Building
                System.out.print("Enter building address: ");
                String address = scanner.nextLine();
                System.out.print("Enter number of floors: ");
                int floors = scanner.nextInt();
                System.out.print("Enter number of apartments: ");
                int apartments = scanner.nextInt();
                System.out.print("Enter total area: ");
                double totalArea = scanner.nextDouble();
                System.out.print("Enter shared area: ");
                double sharedArea = scanner.nextDouble();
                buildingService.addBuilding(address, floors, apartments, totalArea, sharedArea);
                break;
            case 2: // Edit pojo.Building
                System.out.print("Enter building ID to edit: ");
                int editId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                System.out.print("Enter new number of floors: ");
                int newFloors = scanner.nextInt();
                System.out.print("Enter new number of apartments: ");
                int newApartments = scanner.nextInt();
                System.out.print("Enter new total area: ");
                double newTotalArea = scanner.nextDouble();
                System.out.print("Enter new shared area: ");
                double newSharedArea = scanner.nextDouble();
                buildingService.editBuilding(editId, newAddress, newFloors, newApartments, newTotalArea, newSharedArea);
                break;
            case 3: // Delete pojo.Building
                System.out.print("Enter building ID to delete: ");
                int deleteId = scanner.nextInt();
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
                System.out.print("Enter resident name: ");
                String name = scanner.nextLine();
                System.out.print("Enter resident age: ");
                int age = scanner.nextInt();
                System.out.print("Does the resident use the elevator (true/false)? ");
                boolean usesElevator = scanner.nextBoolean();
                System.out.print("Does the resident have a pet (true/false)? ");
                boolean hasPet = scanner.nextBoolean();
                System.out.print("Enter apartment ID: ");
                int apartmentId = scanner.nextInt();
                residentService.addResident(name, age, usesElevator, hasPet, apartmentId);
                break;
            case 2: // Edit Resident
                System.out.print("Enter resident ID to edit: ");
                int residentId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new age: ");
                int newAge = scanner.nextInt();
                System.out.print("Does the resident use the elevator (true/false)? ");
                boolean newUsesElevator = scanner.nextBoolean();
                System.out.print("Does the resident have a pet (true/false)? ");
                boolean newHasPet = scanner.nextBoolean();
                residentService.editResident(residentId, newName, newAge, newUsesElevator, newHasPet);
                break;
            case 3: // Delete Resident
                System.out.print("Enter resident ID to delete: ");
                int deleteId = scanner.nextInt();
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
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add Employee
                System.out.print("Enter employee name: ");
                String name = scanner.nextLine();
                System.out.print("Enter employee phone: ");
                String phone = scanner.nextLine();
                System.out.print("Enter employee email: ");
                String email = scanner.nextLine();
                employeeService.addEmployee(name, phone, email);
                break;
            case 2: // Edit Employee
                System.out.print("Enter employee ID to edit: ");
                int employeeId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new phone: ");
                String newPhone = scanner.nextLine();
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                employeeService.editEmployee(employeeId, newName, newPhone, newEmail);
                break;
            case 3: // Delete Employee
                System.out.print("Enter employee ID to delete: ");
                int deleteId = scanner.nextInt();
                employeeService.deleteEmployee(deleteId);
                break;
            case 4: // List Employees
                employeeService.listEmployees();
                break;
            case 5: // Assign Building to Employee
                System.out.print("Enter employee ID: ");
                int empId = scanner.nextInt();
                System.out.print("Enter building ID: ");
                int buildingId = scanner.nextInt();
                employeeService.assignBuildingToEmployee(empId, buildingId);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void manageFees(Scanner scanner) {
        System.out.println("\n--- Manage Fees ---");
        System.out.println("1. Add Fee");
        System.out.println("2. Edit Fee");
        System.out.println("3. Delete Fee");
        System.out.println("4. List Fees");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Add Fee
                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter due date (yyyy-mm-dd): ");
                Date dueDate = Date.valueOf(scanner.nextLine());
                System.out.print("Enter apartment ID: ");
                int apartmentId = scanner.nextInt();
                feeService.addFee(amount, dueDate, apartmentId);
                break;
            case 2: // Edit Fee
                System.out.print("Enter fee ID to edit: ");
                int feeId = scanner.nextInt();
                System.out.print("Enter new amount: ");
                double newAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new due date (yyyy-mm-dd): ");
                Date newDueDate = Date.valueOf(scanner.nextLine());
                feeService.editFee(feeId, newAmount, newDueDate);
                break;
            case 3: // Delete Fee
                System.out.print("Enter fee ID to delete: ");
                int deleteId = scanner.nextInt();
                feeService.deleteFee(deleteId);
                break;
            case 4: // List Fees
                feeService.listFees();
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void recordPayments(Scanner scanner) {
        System.out.println("\n--- Record Payments ---");
        System.out.print("Enter fee ID: ");
        int feeId = scanner.nextInt();
        System.out.print("Enter employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.print("Enter company ID: ");
        int companyId = scanner.nextInt();
        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        String paymentDate = scanner.nextLine();
        paymentService.addPayment(amount, paymentDate, feeId, employeeId, companyId);
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
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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
                System.out.print("Enter Building ID: ");
                int buildingId = scanner.nextInt();
                residentService.filterResidentsByBuilding(buildingId);
                break;
            case 5: // Filter Employees by Company
                System.out.print("Enter Company ID: ");
                int companyId = scanner.nextInt();
                employeeService.filterEmployeesByCompany(companyId);
                break;
            case 6: // Filter Fees by Due Date
                System.out.print("Enter Due Date (YYYY-MM-DD): ");
                String dueDate = scanner.nextLine();
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
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: // Generate Summary Report
                reportService.generateSummaryReport();
                break;
            case 2: // Generate Detailed Payment Report
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                reportService.generatePaymentDetailsReport(startDate, endDate);
                break;
            case 3: // Generate Detailed Building Assignment Report
                reportService.generateBuildingAssignmentReport();
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
}
