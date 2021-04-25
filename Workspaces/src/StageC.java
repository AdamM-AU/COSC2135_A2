import java.util.*; // Lazy loading everything...

public class StageC {
	// Scanner Object
	private static Scanner consoleInput = new Scanner(System.in);
	
	// Track if we have run the setup 
	private static boolean initialized;
	
	// Max Allowed Events
	private static int maxEvents = 0;
	
	// Tracking Number of Events
	private static int nextEvent = 0;
	
	// Storage Array
	private static TffExperienceEvent[] tffEvents;
	
	// Storage Array Tickets?
	
	public static void initialize() {
		String userInput = "";
		System.out.println("**** Setup Required ****");
		
		// make sure we only have digits
		while (!userInput.matches("^[0-9]+$")) {
			System.out.println("Maximum Number of Events: ");
			userInput = consoleInput.nextLine();
			System.out.println("");
		}
		// Store the Max # of Events
		maxEvents = Integer.parseInt(userInput);
		
		// Init TffEvents using Max # of Events
		tffEvents = new TffExperienceEvent[maxEvents];
		
		// Mark as initialized
		initialized = true;
	}
	
	// Main() Method
	public static void main(String[] args) {
		String userInput = "";
		
		do {
			initialize(); // force user to run setup
		} while (!initialized);
		
		displayMenu(); // Display the main Menu
		
		// do-while loop for menu selections
		do {
			userInput = consoleInput.nextLine().toLowerCase(); // drop to lower case for easy matching
			
			switch(userInput) {
			/* Menu Option: 1 */
			case "1":
				displayEvents();
				displayMenu();
				break;
				
			/* Menu Option: 2 - Create Event */
			case "2":
				consoleEventAdd();
				displayMenu();
				break;
			
			/* Menu Option: 5 - Create Booking/Ticket */
			case "5":
				consoleBookingCreate();
				displayMenu();
				break;
			
			case "6":
				consoleBookingRefund();
				displayMenu();
				break;
				
			case "7":
				consoleBookingDisplay();
				displayMenu();
				break;
				
			/* Exit Application */
			case "x":
				System.out.println("Farewell!");
				break;
			/* No Matching case */
			default:
				System.out.println("Invalid Selection. Please try again!");
			}
		} while (!userInput.contentEquals("x"));		
	}
	
	/*
	 * Display Menu
	 */
	public static void displayMenu() {
		System.out.println("*************************");
		System.out.println("*     Event Control     *");
		System.out.println("*************************");
		System.out.println("  1: Event - List"); // Show Events
		System.out.println("  2: Event - Create");
		//System.out.println("  3: Event - Modify");
		//System.out.println("  4: Event - Delete");
		System.out.println("");
		
		System.out.println("*************************");
		System.out.println("*        Booking        *");
		System.out.println("*************************");
		System.out.println("  5: Booking - Create"); // Create a Booking
		System.out.println("  6: Booking - Refund") ; // Refund a Booking
		System.out.println("  7: Booking - Show"); // Show Bookings 
		System.out.println("");
		System.out.println("  X: Exit");	
	}
	
	public static void displayEvents() {
		String userInput;
		System.out.println("**** Event - List ****");
		System.out.println(""); // Blank Line
		
		// Fancy Table B.S 
		System.out.format("+-----+------------+--------------------+--------------------+-----------+-----------+-----------+--------------+--------------+%n");
		System.out.format("| ID  |    Type    | Event Name         | Description        | Adult ($) | Child ($) | Conc. ($) | Tickets Sold | Ticket Limit |%n");
		System.out.format("+-----+------------+--------------------+--------------------+-----------+-----------+-----------+--------------+--------------+%n");
		
		// Loop until we have called displayEvent for every entered event
		 for(int i = 0; i < nextEvent; i ++) {
			 tffEvents[i].displayEvent();
		 }
		 
		System.out.format("+-----+------------+--------------------+--------------------+-----------+-----------+-----------+--------------+--------------+%n");
		System.out.println(""); // Blank Line
		
		System.out.print("Press [Enter] to continue");
		userInput = consoleInput.nextLine();
		
	}
	
	// Input handler for creating TffEvents
	public static void consoleEventAdd() {
		Object[] inputStorage = new Object[7];
		String userInput = "";
		
		System.out.println(""); // Give me a new line
		System.out.println("**** Event Creation ****");
		
		if (nextEvent >= maxEvents) {
			System.out.println("Error Max Event Limit Reached!");
			System.out.println("");
		} else {
			while(!userInput.toLowerCase().contains("venue") && !userInput.toLowerCase().contains("experience")) {
				System.out.println("Event Type: [Venue, Experience] ");
				userInput = consoleInput.nextLine();
			}
			inputStorage[5] = userInput;
			
			System.out.println("");
			System.out.print("Event Title: ");
			inputStorage[0] = consoleInput.nextLine();
			
			System.out.print("Description: ");
			inputStorage[1] = consoleInput.nextLine();
			System.out.println("");

			// Check if input string is decimal or integer (REGEX)
			userInput = ""; // Reset userInput
			while (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {
				System.out.print("Ticket Limit: ");
				userInput = consoleInput.nextLine();
			}
			inputStorage[6] = Integer.parseInt(userInput);
			System.out.println("");			
			
			System.out.println("Ticket Pricing: ");			
			// Check if input string is decimal or integer (REGEX)
			userInput = ""; // Reset userInput
			while (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {
				System.out.print("  Adult: ");
				userInput = consoleInput.nextLine();
				
				if (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {
					System.out.println("Error: Expected whole number or whole number with decimals [1, 1.00]");
				}
			}
			inputStorage[2] = Double.parseDouble(userInput);
			
			// Check if input string is decimal or integer (REGEX)
			userInput = ""; // Reset userInput
			while (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {
				System.out.print("  Child: ");
				userInput = consoleInput.nextLine();
				
				if (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {
					System.out.println("Error: Expected whole number or whole number with decimals [1, 1.00]");
				}
			}
			inputStorage[3] = Double.parseDouble(userInput);

			// Check if input string is decimal or integer (REGEX)
			userInput = ""; // Reset userInput
			while (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {			
				System.out.print("  Concession: ");
				userInput = consoleInput.nextLine();
				
				if (!userInput.matches("^[0-9]+$") && !userInput.matches("^\\d+\\.\\d+")) {
					System.out.println("Error: Expected whole number or whole number with decimals [1, 1.00]");
				}
			}
				inputStorage[4] = Double.parseDouble(userInput);
			System.out.println("");

			tffEvents[nextEvent] = new TffExperienceEvent((String) inputStorage[0], // Name
													   	  (String) inputStorage[1], // Description
													   	  (double) inputStorage[2], // priceAdult
													   	  (double) inputStorage[3], // priceChild
													   	  (double) inputStorage[4], // priceConcession 
													   	  (int) 0, // Tickets Sold 
													   	  (String) inputStorage[5], // Event Type
													   	  (int) inputStorage[6]); // Ticket Limit 
													   	  
			// Increment next event counter
			nextEvent++;
		}
	}
	
	/* Method for Creating an Event Booking */
	public static void consoleBookingCreate() {
		String userInput;
		boolean eventExists = false;
		boolean response;
		String[][] ticketData;
		int ticketsReq;
		int eventID = 0;
		
		userInput = "";
		System.out.println("**** Booking - Create ****");

		// Loop until we have a valid event id
		while(!eventExists) {
			while (!userInput.matches("^[0-9]+$")) {
				System.out.print("Event ID: ");
				userInput = consoleInput.nextLine().toLowerCase();
			}
			eventID = Integer.parseInt(userInput);
			eventExists = eventExists(eventID);
			userInput = "";
			if (!eventExists) {
				System.out.println("Error: Event Not Found!");
			}
		}
		// Fetch OBJ using eventID and fetch pricing
		TffExperienceEvent currentOBJ = (TffExperienceEvent) tffEvents[eventID];
		System.out.println("");
		System.out.println("****");
		System.out.println("Event: " + currentOBJ.getName());
		System.out.println("Description: " + currentOBJ.getDescription());
		System.out.println("");
		
		System.out.println("Ticket Pricing:");
		System.out.println("  Adult: " + currentOBJ.getPriceAdult());
		System.out.println("  Child: " + currentOBJ.getPriceChild());
		System.out.println("  Concession: " + currentOBJ.getPriceConcession());
		System.out.println("****");
		System.out.println("");
		
		// Request Number of Tickets
		// make sure we only have digits
		userInput = ""; // Reset userInput
		while (!userInput.matches("^[0-9]+$")) {
			System.out.print("Number of Tickets Required: ");
			userInput = consoleInput.nextLine().toLowerCase();
			
			if (!userInput.matches("^[0-9]+$")) {
				System.out.println("Error: Only whole numbers can be accepted!");
			}
		}
		ticketsReq = Integer.parseInt(userInput);
		
		// Init Array
		ticketData = new String[ticketsReq][3];

		for(int i = 0; i < ticketData.length; i ++) {
			System.out.println("");
			System.out.println("** Ticket #: " + (i + 1)  + " **");
			// Loop to process tickets
			ticketData[i][0] = Integer.toString(currentOBJ.getId());
			
			System.out.println("Ticket Type: [Adult, Child, Concession]");
			ticketData[i][1] = consoleInput.nextLine().toLowerCase();
			System.out.println("");
			
			System.out.println("Name for Booking: ");
			ticketData[i][2] = consoleInput.nextLine();
			
			// Pass to booking method
			response = currentOBJ.bookEvent(ticketData[i][1], ticketData[i][2]);
			
			if (response) {
				System.out.println("Booking Successful!");
				System.out.println("");
			} else {
				System.out.println("ERROR: Booking Failure!");
				System.out.println("");
			}
		}
		
		System.out.print("Press [Enter] to continue");
		userInput = consoleInput.nextLine();
	}
	
	/* Method for Refunding an Event Booking */
	public static void consoleBookingRefund() {
		String userInput = "";
		String name;
		int eventID = 0;
		boolean result = false;
		boolean eventExists = false;
		
		// Loop until we have a valid event id
		while(!eventExists) {
			while (!userInput.matches("^[0-9]+$")) {
				System.out.print("Event ID: ");
				userInput = consoleInput.nextLine().toLowerCase();
			}
			eventID = Integer.parseInt(userInput);
			eventExists = eventExists(eventID);
			userInput = "";
			if (!eventExists) {
				System.out.println("Error: Event Not Found!");
			}
		}
		
		System.out.println("Name/Attendee: ");
		name = consoleInput.nextLine();
		
		result = tffEvents[eventID].unBookEvent(name);
		
		if (!result) {
			System.out.println("Error: Refund Unsuccessful");
		}
		System.out.println("");
	}

	/* Method for Showing all Bookings for an event */
	public static void consoleBookingDisplay() {
		String userInput = "";
		int eventID = 0;
		boolean eventExists = false;
		
		System.out.println("**** Booking - List ****");
		System.out.println(""); // Blank Line
		
		// Loop until we have a valid event id
		while(!eventExists) {
			while (!userInput.matches("^[0-9]+$")) {
				System.out.print("Event ID: ");
				userInput = consoleInput.nextLine().toLowerCase();
			}
			eventID = Integer.parseInt(userInput);
			eventExists = eventExists(eventID);
			userInput = "";
			if (!eventExists) {
				System.out.println("Error: Event Not Found!");
			}
		}
		if (eventExists) {
			System.out.println("");
			System.out.println("****");
			System.out.println("Event: " + tffEvents[eventID].getName());
			System.out.println("Description: " + tffEvents[eventID].getDescription());
			System.out.println("****");
			System.out.println("");
			
			// Fancy Table B.S 
			System.out.format("+--------------------+--------------------+-----------+%n");
			System.out.format("| Name/Attendee      | Ticket Type        | Price     |%n");
			System.out.format("+--------------------+--------------------+-----------+%n");
			tffEvents[eventID].displayBookings();
			System.out.format("+--------------------+--------------------+-----------+%n");
			System.out.println("");
			
			System.out.print("Press [Enter] to continue");
			userInput = consoleInput.nextLine();
		}		
	}


	// Confirm or deny the existence of an eventID
	public static boolean eventExists(int eventID) {
		if (eventID > tffEvents.length) {
			return false;
		} else {
			TffEvent currentOBJ = tffEvents[eventID];
			
			if ((currentOBJ == null)) {
				return false;
			} else { 
				return true;
			}
		}
	}	
}
