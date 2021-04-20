
public class TffEvent {
	private int id;
	private String name;
	private int age;
	
	public TffEvent() {
		/* Initialize Variables in class where required */
		this.id = 0;
	}
	
	public boolean tffEventAdd (String name, String age) {
		this.id = this.nextId();
		this.name = name;
	    
		// Attempt to parseInt
		try {
	    	this.age = Integer.parseInt(age);
	        return true;
	    } catch (final NumberFormatException e) {
	        return false;
	    }
	}
	
	/*
	 * nextId(): Simple sequential number method 
	 * Returns an entry id number, used in cross-link arrays 
	 */
	private int nextId() {
		return id++;
	}

	/*
	 * Setters and Getters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getId() {
		return id;
	}
	
}
