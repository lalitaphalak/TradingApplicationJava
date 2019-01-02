package ApplicationEntities;

import java.io.Serializable;

import UtilitiesPackage.UtilitiesClass;

public abstract class TradingPartner implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public int TradingPartnerId = -1;
	public String TradingPartnerName;
	public String City;
	
	//	XMLEncoder requires JavaBeans object to serialize it, so you have to define a public default constructor (with no arguments) in ClassA and ClassB.
	public TradingPartner() {}
	
	TradingPartner(int TradingPartnerId, String TradingPartnerName, String City) {
		this.TradingPartnerId = TradingPartnerId;
		this.TradingPartnerName = TradingPartnerName;
		this.City = City;
	}

	public String[] Validate() {
		String[] errors = null;
		if(this.TradingPartnerId < 0) {
			errors = UtilitiesClass.addToArray(errors, "Id cannot be negative.");
		} 
		
		if(this.TradingPartnerName == null || this.TradingPartnerName.length() < 5) {
			errors = UtilitiesClass.addToArray(errors, "Name cannot be less than 5 characters in length.");
		}
		
		if(this.City == null || this.City.length() < 3) {
			errors = UtilitiesClass.addToArray(errors, "City cannot be less than 3 characters in length.");
		}
		
		return errors;
	}
	
	public abstract void SaveToFile(String filepath);
}
