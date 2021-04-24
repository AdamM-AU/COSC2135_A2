/* This is my custom data class for Tffevent */

public class TffEvent {
	private int id; // Event ID
	private String name; // Event Name
	private String description; // Event Description
	private String type; // Event Type (LowerCase)
	private double priceAdult; // Adult Ticket Price
	private double priceChild; // Child Ticket Price
	private double priceConcession; // Concession Ticket Price
	private int ticketsSold;
	private int ticketLimit; // Event Ticket Limit
	
	public TffEvent(String name, String description, String type, double priceAdult, 
								double priceChild, double priceConcession, int ticketsSold, int ticketLimit) {
		// Store Data
		this.id = this.nextId();
		this.name = name;
		this.description = description;
		this.type = type.toLowerCase();
		this.priceAdult = priceAdult;
		this.priceChild = priceChild;
		this.priceConcession = priceConcession;
		this.ticketsSold = ticketsSold;
		this.ticketLimit = ticketLimit;
	}
	
	/*
	 * nextId(): Simple sequential number method 
	 * Returns an entry id number, used in cross-link arrays 
	 */
	private int nextId() {
		return id++;
	}
	
	public double getPrice(String ticketType) {
		if (ticketType.toLowerCase().matches("child")) {
			return priceChild;
		} else if (ticketType.toLowerCase().matches("concession")) {
			return priceConcession;
		} else {
			return priceAdult;
		}		
	}

	public void incrementTicketsSold() {
		this.ticketsSold++;
	}
	
	public void reduceTicketsSold() {
		this.ticketsSold--;
	}
	
	public int getTicketsAvaliable() {
		return ticketLimit - ticketsSold;
	}
	
	/*
	 * Setters and Getters
	 */
	
	public int getId() {
		return id;
	}
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public int getTicketLimit() {
		return ticketLimit;
	}

	public void setTicketLimit(int ticketLimit) {
		this.ticketLimit = ticketLimit;
	}
	
}
