package ApplicationEntities;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.regex.Pattern;

import UtilitiesPackage.UtilitiesClass;

public class Customer extends TradingPartner {
	private static final long serialVersionUID = 1L;
	public double CreditLimit;
	public String EmailId;
	
//	XMLEncoder requires JavaBeans object to serialize it, so you have to define a public default constructor (with no arguments) in ClassA and ClassB.
	public Customer() {}

	public Customer(int TradingPartnerId, String TradingPartnerName, String City, double CreditLimit, String EmailId) {
		super(TradingPartnerId, TradingPartnerName, City);
		this.CreditLimit = CreditLimit;
		this.EmailId = EmailId;
	}
	
	public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                             
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }
 
	
	@Override
	public String[] Validate() {
		String[] errors = super.Validate();
		if(this.CreditLimit > 50000) {
			errors = UtilitiesClass.addToArray(errors, "Credit limit cannot be beyond 50,000.");
		}
		
		if(!isValidEmail(this.EmailId)) {
			errors = UtilitiesClass.addToArray(errors, "Please enter valid email-id.");
		}
			
		if(errors == null) {
			UtilitiesClass.printCustomer(this);
		}
		
		return errors;
	}

	@Override
	public void SaveToFile(String filepath) {
		try {
			FileOutputStream file = new FileOutputStream
			        (filepath);
			ObjectOutputStream out = new ObjectOutputStream
			        (file);
			out.writeObject(this);
			 
            out.close();
            file.close();
            System.out.println("Record Saved successfully. Please check, " + filepath);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void SaveAllToXML(String filepath, List<Customer> customers) {
		try {
			FileOutputStream file = new FileOutputStream
			        (filepath);
			XMLEncoder encoder = new XMLEncoder(file);
			
			for(Customer customer : customers) {
				encoder.writeObject(customer);
			}
				 
			encoder.close();
            file.close();
            System.out.println("Records Saved successfully. Please check, " + filepath);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
