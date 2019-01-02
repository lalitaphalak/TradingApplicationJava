package ApplicationEntities;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import UtilitiesPackage.UtilitiesClass;

public class Supplier extends TradingPartner {

	private static final long serialVersionUID = 1L;
	public double CreditBalance;
	public String PanNo;
	
	//	XMLEncoder requires JavaBeans object to serialize it, so you have to define a public default constructor (with no arguments) in ClassA and ClassB.
	public Supplier() {}

	public Supplier(int TradingPartnerId, String TradingPartnerName, String City, double CreditBalance, String PanNo) {
		super(TradingPartnerId, TradingPartnerName, City);
		this.CreditBalance = CreditBalance;
		this.PanNo = PanNo;
	}
	
	private boolean isValidPanNo(String panNo) {
		Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

		if (panNo == null)
            return false;
        return pattern.matcher(panNo).matches();
	}
	
	public String[] Validate() {
		String[] errors = super.Validate();
		if(this.CreditBalance > 150000) {
			errors = UtilitiesClass.addToArray(errors, "Credit balance cannot be beyond 1,50,000.");
		} 
		
		if(!isValidPanNo(this.PanNo)) {
			errors = UtilitiesClass.addToArray(errors, "Please enter valid PAN No.");
		}
		
		if(errors == null) {
			UtilitiesClass.printSupplier(this);
		}
		
		return errors;
	}

	
	@Override
	public void SaveToFile(String filepath) {
		try {
			FileOutputStream file = new FileOutputStream
			        (filepath);
			XMLEncoder encoder = new XMLEncoder(file);
			
			encoder.writeObject(this);
			 
			encoder.close();
            file.close();
            System.out.println("Record Saved successfully. Please check, " + filepath);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
