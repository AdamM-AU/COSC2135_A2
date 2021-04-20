import java.util.Scanner; // Import the Scanner class

public class Main {
	private final Scanner consoleInput = new Scanner(System.in);
	private String userInput;
	private TffEvent Events = new TffEvent();

	public Main() {
		this.displayMenu(); // Display the main Menu
		
		// do-while loop for menu selections
		do {
			this.userInput = consoleInput.nextLine().toLowerCase();
			
			switch(this.userInput) {
			/* Menu Option: 1 */
			case "1":
				this.print(true, "Option 1 Selected!");
				this.addPerson();
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
	
	
	/*
	 * Display Menu
	 */
	public void displayMenu() {
		this.print(true, "*************************");
		this.print(true, "*       Main Menu       *");
		this.print(true, "*************************");
		
		this.print(true, "1: Add Person");
		this.print(true, "2: Option");
		this.print(true, "3: Option");
		this.print(true, "1: Option");
		this.print(true, "");
		this.print(true, "X: Exit");
		this.print(true, "");
		
	}
	
	public void addPerson() {
		String[] processedInput = new String[2];
		boolean response;

		this.print(true, "Name: ");
		processedInput[0] = this.consoleInput.nextLine();
		this.print(true, "Age: ");
		processedInput[1] = this.consoleInput.nextLine();
		response = this.Events.tffEventAdd(processedInput[0], processedInput[1]);
		if (!response) {
			this.print(true,"Error bad input");
		}
		
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

}
