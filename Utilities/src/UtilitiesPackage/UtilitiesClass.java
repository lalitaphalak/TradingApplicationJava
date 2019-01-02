package UtilitiesPackage;

import ApplicationEntities.Customer;
import ApplicationEntities.Supplier;

public final class UtilitiesClass {
	
	private UtilitiesClass(){
		
	}
	
	public static String[] addToArray(String[] source, String errorMsg) {
		if(source == null) {
			source = new String[0];
		}
		String[] result = new String[source.length + 1];
		 for(int i = 0; i < source.length; i++)
		        result[i] = source[i];

		    result[result.length - 1] = errorMsg;
        return result;
    }
	
	public static void printErrors(String[] errors) {
		System.out.println();
		System.err.println("Input data is not valid:");
		for(int i = 0; i < errors.length; i++) {
			System.err.println(i+1 + ": " + errors[i]);
		}
		System.out.println();
	}
	
	public static void printCustomer(Customer customer) {
		System.out.println("*************************************");
		System.out.println("Id: " + customer.TradingPartnerId);
		System.out.println("Name: " + customer.TradingPartnerName);
		System.out.println("City: " + customer.City);
		System.out.println("Credit Limit: " + customer.CreditLimit);
		System.out.println("Email-id: " + customer.EmailId);
		System.out.println();
	}
	
	public static void printSupplier(Supplier supplier) {
		System.out.println("*************************************");
		System.out.println("Id: " + supplier.TradingPartnerId);
		System.out.println("Name: " + supplier.TradingPartnerName);
		System.out.println("City: " + supplier.City);
		System.out.println("Credit Balance: " + supplier.CreditBalance);
		System.out.println("PAN No: " + supplier.PanNo);
		System.out.println();
	}

}
