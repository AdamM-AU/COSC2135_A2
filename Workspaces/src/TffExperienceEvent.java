import java.util.concurrent.atomic.AtomicInteger;

public class TffExperienceEvent extends TffEvent {
	private static final AtomicInteger count = new AtomicInteger(-1);  //see: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicInteger.html
	private int id; // Event ID
	private String type; // Event Type
	private int ticketLimit; // Ticket Limit
	private String[][] bookings;

	public TffExperienceEvent(String name, String description, double priceAdult, double priceChild,
			double priceConcession, int ticketsSold, String type, int ticketLimit) {
		
		super(name, description, priceAdult, priceChild, priceConcession, ticketsSold);
		
		this.id = count.incrementAndGet(); // Special datatype, so ID remains the same after deletions
		this.type = type;
		this.ticketLimit = ticketLimit;
		this.bookings = new String[ticketLimit][2];
	}
	
	/* 
	 * Spec Requirement
	 * bookEvent()
	 * Creates a booking and stores the ticket information
	 */	
	@Override
	public boolean bookEvent(String ticketType, String name) {
		double price;
		int ticketsSold;
		
		// Fetch # tickets sold
		ticketsSold = super.getTicketsSold();
		
		if (ticketsSold >= ticketLimit) {
			return false;
		} else {
			/* Save Booking Information
			 * This is supposed to be a 1D String array according to spec..
			 * But! we have two bits of data to store? sorry going with 2D array :)
			 */
			this.bookings[ticketsSold][0] = name;
			this.bookings[ticketsSold][1] = ticketType;			
			
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
			System.out.println(" Event: " + super.getName()  + "  [ ID: " + this.id + " ]");
			System.out.println(" Ticket Type: " + capitalize(ticketType) + "  \t@ $" + 
					   printableDouble(price));			
			System.out.println("+------------------------------------------------+");		
			
			return true;
		}
	}
	
	/* 
	 * Spec Requirement
	 * Print available events and associated information
	 */
	@Override
	public void displayEvent() {
		String leftAlignFormat = "| %-3s | %-10s | %-18s | %-18s | %-9s | %-9s | %-9s | %-12s | %-12s |%n";
		System.out.format(leftAlignFormat,
						  id,
						  capitalize(type),
						  super.getName(), 
						  super.getDescription(),
						  super.getPriceAdult(), 
						  super.getPriceChild(), 
						  super.getPriceConcession(),
						  super.getTicketsSold(),
						  ticketLimit);
		
	}
	
	/*
	 * Returns Bookings for the current event
	 */
	public void displayBookings() {
		for(int i = 0; i < bookings.length; i++) {
			double price;
			String ticketType = bookings[i][1];
			if (ticketType != null) {
				// Fetch Ticket Price
				if (ticketType.toLowerCase().matches("child")) {
					price = super.getPriceChild();
				} else if (ticketType.toLowerCase().matches("concession")) {
					price = super.getPriceConcession();
				} else {
					price = super.getPriceAdult();
				}	
				
				String leftAlignFormat = "| %-18s | %-18s | %-9s |%n";
				System.out.format(leftAlignFormat,
							  	  bookings[i][0], 
							  	  capitalize(bookings[i][1]),
							  	  price);
			}
		}
	}
	
	/*
	 * Deletes a booking an issues a refund for current event
	 * rebuilds the booking array to remove empty rows
	 */	
	public boolean unBookEvent(String name) {
		if (type.toLowerCase().matches("experience")) {
			// Check if the we have a matching valid ticket
			for(int i = 0; i < bookings.length; i++) {
				if (bookings[i][0] != null) {
					if (bookings[i][0].toLowerCase().matches(name.toLowerCase())) {
						// Match Found!
						double price;
						int ticketsSold;
						String ticketType = bookings[i][1];
						
						// Fetch Ticket Price
						if (ticketType.toLowerCase().matches("child")) {
							price = super.getPriceChild();
						} else if (ticketType.toLowerCase().matches("concession")) {
							price = super.getPriceConcession();
						} else {
							price = super.getPriceAdult();
						}				
						
						// Process Refund
						System.out.println("");
						System.out.println("+---------------------TICKET---------------------+");
						System.out.println("                     [REFUND]");
						System.out.println(" Attendee: " + name);
						System.out.println(" Event: " + super.getName()  + "  [ ID: " + this.id + " ]");
						System.out.println(" Ticket Type: " + capitalize(ticketType) + "  \t@ $" + 
								   printableDouble(price));
						System.out.println("                     [REFUND]");
						System.out.println("+------------------------------------------------+");						
		
						// Delete Booking & Reshuffle array	
						String[][] tempArray = new String[ticketLimit][2];
						
						int counter = 0;  // Loop Counter
						int newIndex = 0; // New Array Index Key 
						while (bookings.length > counter) {
							if (counter != i) {
								tempArray[newIndex][0] = bookings[counter][0]; // Copy Data using new index
								tempArray[newIndex][1] = bookings[counter][1]; // Copy Data using new index
								newIndex++;
							}
							counter++; // Increment Loop Counter
					
						}
						// Overwrite dataStorage with contents of tempDataStorage
						this.bookings = tempArray; // Copy tempArrayStorage over dataStorage
						
						// Fetch # tickets sold
						ticketsSold = super.getTicketsSold();
						
						// Decrease the # tickets sold
						ticketsSold--;
						
						// Update super ticketsSold
						super.setTicketsSold(ticketsSold);
						
						return true;
					}
				}
			}
			System.out.println("");
			System.out.println("Error: No Matching ticket found!");
			return false;
		} else {
			System.out.println("");
			System.out.println("Error: Only Experience events are refundable!");
			return false;
		}
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
