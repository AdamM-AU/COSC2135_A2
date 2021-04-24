import java.util.Scanner; // Import the Scanner class

public class Main {
	private final Scanner consoleInput = new Scanner(System.in);
	private String userInput;
	private boolean initialized;
	private int nextEventEntry;
	private int nextTicketEntry;
	private int counter;
	private TffEvent[] TffEvents;
	private Object[][] Tickets = new Object[1000][2];
	
	// Temp Variables
	public int maxTffEvents;

	public Main() {
		do {
			this.initialize(); // force user to run setup
		} while (!this.initialized);
		
		this.displayMenu(); // Display the main Menu
		
		// do-while loop for menu selections
		do {
			this.userInput = consoleInput.nextLine().toLowerCase(); // drop to lower case for easy matching
			
			switch(this.userInput) {
			/* Menu Option: 1 */
			case "1":
				this.print(true, "Option 1 Selected!");
				this.displayEvent();
				this.print(true, ""); // Give me a new line
				break;
				
			/* Menu Option: 2 - Create Event */
			case "2":
				this.consoleEventAdd();
				this.displayMenu();
				break;
			
			/* Menu Option: 5 - Create Booking/Ticket */
			case "5":
				this.consoleBookingCreate();
				this.displayMenu();
				break;		
				
			/* Exit Application */
			case "x":
				this.print(true, "Farewell!");
				break;
			/* No Matching case */
			default:
				this.print(true, "Invalid Selection. Please try again!");
			}
		} while (!this.userInput.contentEquals("x"));
	}
	
	public void consoleBookingCreate() {
		boolean eventExists = false;
		String[][] ticketData;
		int ticketsReq;
		int eventID = 0;
		
		this.userInput = "";
		this.print(true, "**** Booking - Create ****");

		// Loop until we have a valid event id
		while(!eventExists) {
			this.print(false, "Event ID: ");
			this.userInput = consoleInput.nextLine().toLowerCase();
			eventID = Integer.parseInt(this.userInput);
			eventExists = this.eventExists(eventID);
			// Return event Title and ask for confirmation
		}
		// Fetch OBJ using eventID and fetch pricing
		TffEvent currentOBJ = TffEvents[eventID];
		this.print(true, "");
		this.print(true,"****");
		this.print(true, "Event: " + currentOBJ.getName());
		this.print(true, "Description: " + currentOBJ.getDescription());
		this.print(true,"");
		
		this.print(true, "Ticket Pricing:");
		this.print(true, "  Adult: " + currentOBJ.getPriceAdult());
		this.print(true, "  Child: " + currentOBJ.getPriceChild());
		this.print(true, "  Concession: " + currentOBJ.getPriceConcession());
		this.print(true,"****");
		this.print(true, "");
		
		// Request Number of Tickets
		this.print(false, "Number of Tickets Required: ");
		this.userInput = consoleInput.nextLine().toLowerCase();
		ticketsReq = Integer.parseInt(this.userInput);
		
		// Init Array
		ticketData = new String[ticketsReq][3];
		
		this.counter = 0;
		
		while (ticketData.length > this.counter) {
			this.print(true, "");
			this.print(true, "** Ticket #: " + (this.counter + 1)  + " **");
			// Loop to process tickets
			ticketData[this.counter][0] = Integer.toString(currentOBJ.getId());
			
			this.print(true, "Ticket Type: [Adult, Child, Concession] ");
			ticketData[this.counter][1] = consoleInput.nextLine().toLowerCase();
			this.print(true, "");
			
			this.print(true, "Name for Booking: ");
			ticketData[this.counter][2] = consoleInput.nextLine();
			
			// Pass to booking method
			this.bookEvent(ticketData[this.counter][1], ticketData[this.counter][2]);			
			
			// Increment Ticket Sales for event
			currentOBJ.incrementTicketsSold();
			
			// Increment Loop Counter
			this.counter++;
		}

		// Loop Print Tickets
		this.counter = 0;
		while (ticketData.length > this.counter) {
			// Print a Ticket..
			this.print(true, "");
			this.print(true, "+---------------------TICKET---------------------+");
			this.print(true, " Attendee: " + ticketData[this.counter][2]);
			this.print(true, " Event: " + currentOBJ.getName());
			this.print(true, " Ticket Type: " + this.capitalize(ticketData[this.counter][1]) + "  \t@ $" + 
					   this.printableDouble(currentOBJ.getPrice(ticketData[this.counter][1])));			
			this.print(true, "+------------------------------------------------+");
			this.print(true, "");
			this.counter++;
		}
		
		this.print(true, "Press [Enter] to continue");
		this.userInput = consoleInput.nextLine();
	}

	// Confirm or deny the existence of an eventID
	public boolean eventExists(int eventID) {
		TffEvent currentOBJ = this.TffEvents[eventID];
		
		if ((currentOBJ == null)) {
			return false;
		} else { 
			return true;
		}
	}

	public void initialize() {
		this.userInput = "";
		this.print(true, "**** Setup Required ****");
		
		// make sure we only have digits
		while (!this.userInput.matches("^[0-9]+$")) {
			this.print(false, "Maximum Number of TffEvents: ");
			this.userInput = consoleInput.nextLine();
			this.print(true, "");
		}
		
		// Store the max TffEvents somewhere 
		this.maxTffEvents = Integer.parseInt(this.userInput);

		// Set the next Event & Ticket Array Entry to 0
		this.nextEventEntry = 0; 
		this.nextTicketEntry = 0;
		
		// Set TffEvents Array Size
		this.TffEvents = new TffEvent[this.maxTffEvents];
		
		// mark as initialized
		this.initialized = true;
	}
	
	// Input handler for creating TffEvents
	public void consoleEventAdd() {
		Object[] inputStorage = new Object[5];
		this.userInput = ""; // Empty the variable
		
		this.print(true, ""); // Give me a new line
		this.print(true, "**** Event Creation ****");
		
		if (nextEventEntry >= maxTffEvents) {
			this.print(true, "Error Max Event Limit Reached!");
			this.print(true, "");
		} else {
			this.print(false, "Event Title: ");
			inputStorage[0] = consoleInput.nextLine();
			
			this.print(false, "Description: ");
			inputStorage[1] = consoleInput.nextLine();
			this.print(true, "");
			
			this.print(true, "Ticket Pricing: ");
			this.print(false, "  Adult: ");
			inputStorage[2] = Double.parseDouble(consoleInput.nextLine());
			this.print(false, "  Child: ");
			inputStorage[3] = Double.parseDouble(consoleInput.nextLine());
			this.print(false, "  Concession: ");
			inputStorage[4] = Double.parseDouble(consoleInput.nextLine());
			this.print(true, "");
			
			this.TffEvents[this.nextEventEntry] = new TffEvent((String) inputStorage[0],
													   (String) inputStorage[1],
													   "venue",
													   (double) inputStorage[2],
													   (double) inputStorage[3],
													   (double) inputStorage[4],
													   0,
													   1000);
			// Increment next event counter
			this.nextEventEntry++;
		}
	}
	
	/*
	 * Display Menu
	 */
	public void displayMenu() {
		this.print(true, "*************************");
		this.print(true, "*     Event Control     *");
		this.print(true, "*************************");
		this.print(true, "  1: Event - Show");
		this.print(true, "  2: Event - Create");
		this.print(true, "  3: Event - Modify");
		this.print(true, "  4: Event - Delete");
		this.print(true, "");
		
		this.print(true, "*************************");
		this.print(true, "*        Booking        *");
		this.print(true, "*************************");
		this.print(true, "  5: Booking - Create");
		this.print(true, "  6: Booking - Refund");
		this.print(true, "  7: Booking - Show");
		this.print(true, "");
		this.print(true, "  X: Exit");
		
	}
	/*
	public void addPerson() {
		String[] processedInput = new String[2];
		boolean response;

		this.print(true, "Name: ");
		processedInput[0] = this.consoleInput.nextLine();
		this.print(true, "Age: ");
		processedInput[1] = this.consoleInput.nextLine();
		response = this.TffEvents.tffEventAdd(processedInput[0], processedInput[1]);
		
		if (!response) {
			this.print(true,"Error bad input");
		}
		
	}*/
	
	// Spec Requirement
	public void displayEvent() {
			this.print(true, "**** Event Show ****");		
	
			String leftAlignFormat = "| %-3s | %-18s | %-18s | %-7s | %-12s | %-13s |%n";
			
			System.out.format("+-----+--------------------+--------------------+---------+--------------+---------------+%n");
			System.out.format("| ID  | Event Name         | Description        | T/ Sold | T/ Avaliable | Total Tickets |%n");
			System.out.format("|-----|--------------------|--------------------|---------|--------------|---------------|%n");

			// Dump the data arrays provided to table
			this.counter=0;
			while (this.TffEvents.length > this.counter) {
				TffEvent currentOBJ = this.TffEvents[this.counter];
				if (!(currentOBJ == null)) {
					System.out.format(leftAlignFormat,
									  currentOBJ.getId(),
									  currentOBJ.getName(), 
									  currentOBJ.getDescription(),
									  currentOBJ.getTicketsSold(), 
									  currentOBJ.getTicketsAvaliable(), 
									  currentOBJ.getTicketLimit());
									   							   
				}
				this.counter++;
			}		
			
			System.out.format("+-----+--------------------+--------------------+---------+--------------+---------------+%n");	
		
	}
	
	// Spec Requirement
	public void bookEvent(String ticketType, String name) {
		this.Tickets[this.nextTicketEntry][0] = ticketType;
		this.Tickets[this.nextTicketEntry][1] = name;
		this.nextTicketEntry++;
	}
	
	/*
	 *  Simple method for printing output
	 *  While this method isn't technically needed i have it here
	 *  so its easier to make adjustments to formatting globally 
	 *  later if required.
	 */
	public void print(boolean newline, String args) {
		if (newline) {
			System.out.println(args);
		} else {
			System.out.print(args);
		}	
	}
	
	/*
	 * Method converts a double (0.00000000) to a 2 decimal place printable string
	 * I have put it in the back end as both user interface require it.
	 * however we do not store this, we only store the full double to be more accurate
	 */
	public String printableDouble(double input) {
		String result;
		result = new java.text.DecimalFormat("0.00").format(input);
		
		return result;
	}
	
	// Taken from: https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
	// Capitalizes first letter of string
	public String capitalize(String str)	{
	    if(str == null) return str;
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}	

}
