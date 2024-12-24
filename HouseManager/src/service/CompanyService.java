package service;

import dao.CompanyDAO;
import pojo.Company;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class CompanyService {
    private final CompanyDAO companyDAO = new CompanyDAO();

    public CompanyService() throws SQLException {
    }

    public void addCompany(String name, String address, String phone) {
        try {
            companyDAO.createCompany(name, address, phone);
            System.out.println("pojo.Company added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding company: " + e.getMessage());
        }
    }

    public void editCompany(int id, String name, String address, String phone) {
        try {
            companyDAO.updateCompany(id, name, address, phone);
            System.out.println("pojo.Company updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating company: " + e.getMessage());
        }
    }

    public void deleteCompany(int id) {
        try {
            companyDAO.deleteCompany(id);
            System.out.println("pojo.Company deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting company: " + e.getMessage());
        }
    }

    public void listCompanies() {
        try {
            List<Company> companies = companyDAO.getAllCompanies();
            companies.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("Error retrieving companies: " + e.getMessage());
        }
    }

    public void sortCompaniesByName() throws SQLException {
        List<Company> companies = companyDAO.getAllCompanies();
        companies.sort(Comparator.comparing(Company::getName));
        companies.forEach(System.out::println);
    }
}
