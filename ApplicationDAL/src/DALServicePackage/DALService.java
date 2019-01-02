package DALServicePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ApplicationEntities.*;
import UtilitiesPackage.DBConnector;

public class DALService {
	private static Connection connection = null;
	
	public static void SaveCustomerDetails(Customer customer) {
		try {
			connection = DBConnector.getConnection();
			PreparedStatement preparedStatement =  connection.prepareStatement("insert into customers values (?,?,?,?,?)");
			preparedStatement.setInt(1, customer.TradingPartnerId);
			preparedStatement.setString(2, customer.TradingPartnerName);
			preparedStatement.setString(3, customer.City);
			preparedStatement.setDouble(4, customer.CreditLimit);
			preparedStatement.setString(5, customer.EmailId);
			
			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Record saved successfully.");
			} else {
				System.out.println("Could not save record.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void SaveSupplierDetails(Supplier supplier) {
		try {
			connection = DBConnector.getConnection();
			PreparedStatement preparedStatement =  connection.prepareStatement("insert into suppliers values (?,?,?,?,?)");
			preparedStatement.setInt(1, supplier.TradingPartnerId);
			preparedStatement.setString(2, supplier.TradingPartnerName);
			preparedStatement.setString(3, supplier.City);
			preparedStatement.setDouble(4, supplier.CreditBalance);
			preparedStatement.setString(5, supplier.PanNo);
			
			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Record saved successfully.");
			} else {
				System.out.println("Could not save record.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Customer> GetAllCustomers() {
		List<Customer> customersList = new LinkedList<Customer>();
		ResultSet customers;
		try {
			connection = DBConnector.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			customers = statement.executeQuery("select * from customers");
			while(customers.next()) {
				int id = customers.getInt("TradingPartnerId");
				String name = customers.getString("TradingPartnerName");
				String city = customers.getString("City");
				double creditLimit = customers.getDouble("CreditLimit");
				String emailId = customers.getString("EmailId");
				
				Customer customer = new Customer(id, name, city, creditLimit, emailId);
				customersList.add(customer);
			}
			return customersList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<Supplier> GetAllSuppliers() {
		List<Supplier> suppliersList = new LinkedList<Supplier>();
		ResultSet suppliers;
		try {
			connection = DBConnector.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			suppliers = statement.executeQuery("select * from suppliers");
			while(suppliers.next()) {
				int id = suppliers.getInt("TradingPartnerId");
				String name = suppliers.getString("TradingPartnerName");
				String city = suppliers.getString("City");
				double creditBalance = suppliers.getDouble("CreditBalance");
				String panNo = suppliers.getString("PanNo");
				
				Supplier supplier = new Supplier(id, name, city, creditBalance, panNo);
				suppliersList.add(supplier);
			}
			return suppliersList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Customer GetCustomerById(int customerid) {
		try {
			connection = DBConnector.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("Select * from customers where TradingPartnerId = ?");
			preparedStatement.setInt(1, customerid);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("TradingPartnerId");
				String name = resultSet.getString("TradingPartnerName");
				String city = resultSet.getString("City");
				double creditLimit = resultSet.getDouble("CreditLimit");
				String emailId = resultSet.getString("EmailId");
				return new Customer(id, name, city, creditLimit, emailId);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static Supplier GetSupplierById(int supplierid) {
		try {
			connection = DBConnector.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("Select * from suppliers where TradingPartnerId = ?");
			preparedStatement.setInt(1, supplierid);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("TradingPartnerId");
				String name = resultSet.getString("TradingPartnerName");
				String city = resultSet.getString("City");
				double creditBalance = resultSet.getDouble("CreditBalance");
				String panNo = resultSet.getString("PanNo");
				return new Supplier(id, name, city, creditBalance, panNo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void UpdateCustomerById(Customer customer) {
		try {
			connection = DBConnector.getConnection();
			String query = "update customers set TradingPartnerName = ?, City = ?, CreditLimit = ?, EmailId = ? where TradingPartnerId = ?";
			PreparedStatement preparedStatement =  connection.prepareStatement(query);
			preparedStatement.setString(1, customer.TradingPartnerName);
			preparedStatement.setString(2, customer.City);
			preparedStatement.setDouble(3, customer.CreditLimit);
			preparedStatement.setString(4, customer.EmailId);
			preparedStatement.setInt(5, customer.TradingPartnerId);

			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Could not save record.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void UpdateSupplierById(Supplier supplier) {
		try {
			connection = DBConnector.getConnection();
			String query = "update suppliers set TradingPartnerName = ?, City = ?, CreditBalance = ?, PanNo = ? where TradingPartnerId = ?";
			PreparedStatement preparedStatement =  connection.prepareStatement(query);
			preparedStatement.setString(1, supplier.TradingPartnerName);
			preparedStatement.setString(2, supplier.City);
			preparedStatement.setDouble(3, supplier.CreditBalance);
			preparedStatement.setString(4, supplier.PanNo);
			preparedStatement.setInt(5, supplier.TradingPartnerId);

			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Could not save record.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
