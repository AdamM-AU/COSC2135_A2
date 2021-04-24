
public class TffEvent {
	/* Variable Declarations for our event items */
	private String name; // Event Name
	private String description; // Event Description
	private double priceAdult; // Adult Ticket Price
	private double priceChild; // Child Ticket Price
	private double priceConcession; // Concession Ticket Price
	private int ticketsSold; // # of tickets sold

   public TffEvent(String name, String description, double priceAdult, double priceChild, double priceConcession, int ticketsSold) {
		this.name = name;
		this.description = description;
		this.priceAdult = priceAdult;
		this.priceChild = priceChild;
		this.priceConcession = priceConcession;
		this.ticketsSold = ticketsSold;
   }
   
	/* 
	 * printableDouble()
	 * Method converts a double (0.00000000) to a 2 decimal place printable string
	 * We do not store this, we only store the full double to be more accurate
	 */
	public static String printableDouble(double input) {
		String result;
		result = new java.text.DecimalFormat("0.00").format(input);
		
		return result;
	}
	
	/* 
	 * Taken from: https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
	 * Capitalizes first letter of string
	 * Why? I was to lazy to write it myself
	 */
	public static String capitalize(String str)	{
	    if(str == null) return str;
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/* 
	 * Spec Requirement
	 * Print Event Data
	 */
	
	public void displayEvent() {
			String leftAlignFormat = "| %-3s | %-18s | %-18s | %-9s | %-9s | %-9s | %-12s |%n";
			System.out.format(leftAlignFormat,
							  "",
							  name, 
							  description,
							  priceAdult, 
							  priceChild, 
							  priceConcession,
							  ticketsSold);

	}
	/* 
	 * Spec Requirement
	 * Attempts to lodge booking after running a couple of checks
	 * returns true on success
	 * returns fales on failure
	 */
	public boolean bookEvent(String ticketType, String name) {
		double price;
		
		// Increment Ticket Sales
		this.ticketsSold++;
		
		// Get Ticket Price
		if (ticketType.toLowerCase().matches("child")) {
			price = priceChild;
		} else if (ticketType.toLowerCase().matches("concession")) {
			price = priceConcession;
		} else {
			price = priceAdult;
		}
		
		System.out.println("");
		System.out.println("+---------------------TICKET---------------------+");
		System.out.println(" Attendee: " + name);
		System.out.println(" Event: " + this.name);
		System.out.println(" Ticket Type: " + capitalize(ticketType) + "  \t@ $" + 
				   printableDouble(price));			
		System.out.println("+------------------------------------------------+");		
		
		return true;
	}
	
	/* Setters and Getters */
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPriceAdult() {
		return priceAdult;
	}
	
	public void setPriceAdult(double priceAdult) {
		this.priceAdult = priceAdult;
	}
	
	public double getPriceChild() {
		return priceChild;
	}
	
	public void setPriceChild(double priceChild) {
		this.priceChild = priceChild;
	}
	
	public double getPriceConcession() {
		return priceConcession;
	}
	
	public void setPriceConcession(double priceConcession) {
		this.priceConcession = priceConcession;
	}
	
	public int getTicketsSold() {
		return ticketsSold;
	}
	
	public void setTicketsSold(int ticketsSold) {
		this.ticketsSold = ticketsSold;
	}
}
