import java.util.concurrent.atomic.AtomicInteger;

public class TffExperienceEvent extends TffEvent {
	private static final AtomicInteger count = new AtomicInteger(-1);  //see: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicInteger.html
	private int id; // Event ID
	private String type; // Event Type
	private int ticketLimit; // Ticket Limit

	public TffExperienceEvent(String name, String description, double priceAdult, double priceChild,
			double priceConcession, int ticketsSold, String type, int ticketLimit) {
		
		super(name, description, priceAdult, priceChild, priceConcession, ticketsSold);
		
		this.id = count.incrementAndGet(); // Special datatype, so ID remains the same after deletions
		this.type = type;
		this.ticketLimit = ticketLimit;
	}
	
	@Override
	public boolean bookEvent(String ticketType, String name) {
		double price;
		int ticketsSold;
		
		// Fetch # tickets sold
		ticketsSold = super.getTicketsSold();
		
		if (ticketsSold >= ticketLimit) {
			return false;
		} else {
			// Increment Ticket Sales
			ticketsSold++;
			super.setTicketsSold(ticketsSold);
			
			// Get Ticket Price
			if (ticketType.toLowerCase().matches("child")) {
				price = super.getPriceChild();
			} else if (ticketType.toLowerCase().matches("concession")) {
				price = super.getPriceConcession();
			} else {
				price = super.getPriceAdult();
			}
			
			System.out.println("");
			System.out.println("+---------------------TICKET---------------------+");
			System.out.println(" Attendee: " + name);
			System.out.println(" Event: " + super.getName());
			System.out.println(" Ticket Type: " + capitalize(ticketType) + "  \t@ $" + 
					   printableDouble(price));			
			System.out.println("+------------------------------------------------+");		
			
			return true;
		}
	}
	
	/* 
	 * Spec Requirement
	 * Print Event Data
	 */
	@Override
	public void displayEvent() {
		String leftAlignFormat = "| %-3s | %-10s | %-18s | %-18s | %-9s | %-9s | %-9s | %-12s | %-12s |%n";
		System.out.format(leftAlignFormat,
						  id,
						  type,
						  super.getName(), 
						  super.getDescription(),
						  super.getPriceAdult(), 
						  super.getPriceChild(), 
						  super.getPriceConcession(),
						  super.getTicketsSold(),
						  ticketLimit);
		
	}
	
	/* Setters and Getters */

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTicketLimit() {
		return ticketLimit;
	}

	public void setTicketLimit(int ticketLimit) {
		this.ticketLimit = ticketLimit;
	}
	
	public int getId() {
		return id;
	}		
}
