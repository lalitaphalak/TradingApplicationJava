package ClientPackage;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import ApplicationEntities.*;
import DALServicePackage.DALService;
import UtilitiesPackage.UtilitiesClass;

public class ClientClass {
	
	private static Scanner cLimit;
	private static Scanner emailId;
	private static Scanner cBalance;
	private static Scanner panNo;
	private static Scanner menuOption;
	private static Scanner value;
	private static Scanner option;
	private static String[] errors;

	private static int printMenu() {
		System.out.println("Please choose option from following list and enter the number:");
		System.out.println("1: Add Customer");
		System.out.println("2: Add Supplier");
		System.out.println("3: Show All Customers");
		System.out.println("4: Show All Suppliers");
		System.out.println("5: Export a Customer");
		System.out.println("6: Export a Supplier");
		System.out.println("7: Update Customer Details");
		System.out.println("8: Update Supplier Details");
		System.out.println("9: Export All Customer Details to an XML");
		System.out.println("10: Exit");
		menuOption = new Scanner(System.in);
		return menuOption.nextInt();
	}
	
	private static void redirectAsPerOption(int menuOption) {
		switch(menuOption) {
		case 1:
			AddCustomerConsoleScreen();
			break;
		case 2:
			AddSupplierConsoleScreen();
			break;
		case 3:
			System.out.println("Fetching details...");
			List<Customer> customers = DALService.GetAllCustomers();
			printCustomers(customers);
			break;
		case 4:
			System.out.println("Fetching details...");
			List<Supplier> suppliers = DALService.GetAllSuppliers();
			printSuppliers(suppliers);
			break;
		case 5:
			System.out.println("Please enter customer id:");
			Scanner sc = new Scanner(System.in);
			
			if(sc.hasNext()) {
				System.out.println("Fetching details...");
			}
			
			Customer customer = DALService.GetCustomerById(sc.nextInt());
			if(customer == null) {
				System.out.println("Customer with required id does not exist. Please create first.");
				break;
			}
			System.out.println("Saving...");
			String customerFilePath = "D:\\customer-" + customer.TradingPartnerId + ".txt";
			customer.SaveToFile(customerFilePath);
			break;
		case 6:
			System.out.println("Please enter supplier id:");
			Scanner ss = new Scanner(System.in);
			
			if(ss.hasNext()) {
				System.out.println("Fetching details...");
			}
			
			Supplier supplier = DALService.GetSupplierById(ss.nextInt());
			if(supplier == null) {
				System.out.println("Supplier with required id does not exist. Please create first.");
				break;
			}
			System.out.println("Saving...");
			String supplierFilePath = "D:\\supplier-" + supplier.TradingPartnerId + ".xml";
			supplier.SaveToFile(supplierFilePath);
			break;
		case 7:
			System.out.println("Please enter customer id to update:");
			Scanner cid = new Scanner(System.in);
			boolean isStillEditing = true;
			if(cid.hasNextInt()) {
				System.out.println("Fetching details...");
				Customer customerUpdate = DALService.GetCustomerById(cid.nextInt());
				
				if(customerUpdate == null) {
					System.out.println("Customer with required id does not exist. Please create first.");
					break;
				} else {
					do {
						Customer tempCust = UpdateCustomerConsoleScreen(customerUpdate);
						if(tempCust != null) {
							errors = tempCust.Validate();
							if(errors == null)
								customerUpdate = tempCust;
							else
								UtilitiesClass.printErrors(errors);
						} else
							isStillEditing = false;
					} while(isStillEditing);
					
						
					if(customerUpdate != null) {
						DALService.UpdateCustomerById(customerUpdate);
					}
				}
				 
			} else {
				System.out.println("Please enter a valid id.");
			}
			break;
		case 8:
			System.out.println("Please enter supplier id to update:");
			Scanner sid = new Scanner(System.in);
			boolean isStillEditingSupplier = true;
			if(sid.hasNextInt()) {
				System.out.println("Fetching details...");
				Supplier supplierUpdate = DALService.GetSupplierById(sid.nextInt());
				
				if(supplierUpdate == null) {
					System.out.println("Supplier with required id does not exist. Please create first.");
					break;
				} else {
					do {
						Supplier tempSup = UpdateSupplierConsoleScreen(supplierUpdate);
						if(tempSup != null) {
							errors = tempSup.Validate();
							if(errors == null)
								supplierUpdate = tempSup;
							else
								UtilitiesClass.printErrors(errors);
						} else
							isStillEditingSupplier = false;
					} while(isStillEditingSupplier);
					
						
					if(supplierUpdate != null) {
						DALService.UpdateSupplierById(supplierUpdate);
					}
				}
				 
			} else {
				System.out.println("Please enter a valid id.");
			}
			break;
		case 9:
			System.out.println("Fetching details...");
			List<Customer> customersList = DALService.GetAllCustomers();
			if(customersList.size() > 0) {
				System.out.println("Saving...");
				Customer.SaveAllToXML("D:\\all-customers.xml", customersList);
			} else {
				System.out.println("No records found.");
			}
			
			break;
		case 10:
			System.out.println("Thanks for using application!");
			break;
		default:
			System.out.println("Option does not exist. Please check again.");
			break;
		}
		
	}
	
	private static Supplier UpdateSupplierConsoleScreen(Supplier supplier) {
		System.out.println("Please select data to update:");
		System.out.println("1: Name");
		System.out.println("2: City");
		System.out.println("3: Credit Balance");
		System.out.println("4: PAN No");
		System.out.println("5: Exit");
		int[] options = {1,2,3,4};
		option = new Scanner(System.in);
		
		if(option.hasNextInt()) {
			int optionInt = option.nextInt();
			if(IntStream.of(options).anyMatch(x -> x == optionInt)) {
				System.out.println("Please enter value:");
				value = new Scanner(System.in);
				switch(optionInt) {
				case 1: supplier.TradingPartnerName = value.next();
						break;
				case 2: supplier.City = value.next();
						break;
				case 3: supplier.CreditBalance = value.nextDouble();
					break;
				case 4: supplier.PanNo = value.next();
					break;
				}
				return supplier;
			} else if(optionInt == 5) {
				return null;
			} else {
				System.out.println("Please enter a valid option.");
				return supplier;
			}
			
		} else {
			System.out.println("Please enter a valid option.");
			return supplier;
		}
	}

	private static Customer UpdateCustomerConsoleScreen(Customer customer) {
		System.out.println("Please select data to update:");
		System.out.println("1: Name");
		System.out.println("2: City");
		System.out.println("3: Credit Limit");
		System.out.println("4: Email Id");
		System.out.println("5: Exit");
		int[] options = {1,2,3,4};
		option = new Scanner(System.in);
		
		if(option.hasNextInt()) {
			int optionInt = option.nextInt();
			if(IntStream.of(options).anyMatch(x -> x == optionInt)) {
				System.out.println("Please enter value:");
				value = new Scanner(System.in);
				switch(optionInt) {
				case 1: customer.TradingPartnerName = value.next();
						break;
				case 2: customer.City = value.next();
						break;
				case 3: customer.CreditLimit = value.nextDouble();
					break;
				case 4: customer.EmailId = value.next();
					break;
				}
				return customer;
			} else if(optionInt == 5) {
				System.out.println("Still working...");
				return null;
			} else {
				System.out.println("Please enter a valid option.");
				return customer;
			}
			
		} else {
			System.out.println("Please enter a valid option.");
			return customer;
		}
	}

	private static void printSuppliers(List<Supplier> suppliers) {
		if(suppliers.size() > 0) {
			for (Supplier supplier : suppliers) {
				UtilitiesClass.printSupplier(supplier);
			}
		} else {
			System.out.println("No records found.");
		}	
	}

	private static void printCustomers(List<Customer> customers) {
		if(customers.size() > 0) {
			for (Customer customer : customers) {
				UtilitiesClass.printCustomer(customer);
			}
		} else {
			System.out.println("No records found.");
		}
		
	}

	private static Scanner[] GatherCommonInfo() {
		System.out.println("Please enter details as follows:");
		System.out.println("Id:");
		Scanner partnerId = new Scanner(System.in);
		System.out.println("Name:");
		Scanner name = new Scanner(System.in);
		System.out.println("City:");
		Scanner city = new Scanner(System.in);
		return new Scanner[] {partnerId, name, city};
	}
	
	private static void AddSupplierConsoleScreen() {
		Scanner[] sc = GatherCommonInfo();
		System.out.println("Credit Balance:");
		cBalance = new Scanner(System.in);
		System.out.println("Pan no:");
		panNo = new Scanner(System.in);
		Supplier supplierInstance = new Supplier(
				sc[0].nextInt(),
				sc[1].next(),
				sc[2].next(),
				cBalance.nextDouble(),
				panNo.next()
		);
		String[] errors = supplierInstance.Validate();
		if(errors != null) {
			UtilitiesClass.printErrors(errors);
			AddSupplierConsoleScreen();
		} else {
			DALService.SaveSupplierDetails(supplierInstance);
		}
	}
	
	private static void AddCustomerConsoleScreen() {
		Scanner[] sc = GatherCommonInfo();
		System.out.println("Credit Limit:");
		cLimit = new Scanner(System.in);
		System.out.println("Email-Id:");
		emailId = new Scanner(System.in);
		Customer customerInstance = new Customer(
				sc[0].nextInt(),
				sc[1].next(),
				sc[2].next(),
				cLimit.nextDouble(),
				emailId.next()
		);
		String[] errors = customerInstance.Validate();
		if(errors != null) {
			UtilitiesClass.printErrors(errors);
			AddCustomerConsoleScreen();
		} else {
			DALService.SaveCustomerDetails(customerInstance);
		}
	}

	public static void main(String[] args) {
		int menuOption;
		System.out.println("Welcome to Trading Console Application!");
		
		do {
			System.out.println("");
			menuOption = printMenu();
			redirectAsPerOption(menuOption);
		} while(menuOption != 10);
	
	}

}
