package mainApplication;

public class User {
	private String name;
	private String color;
	
	public User(String name) {
		this.name = name;
	}
	
	// Getter method for name
	public String getName() {
		return this.name;
	}
	
	// Getter method for field color
	public void setColor(String c) {
		this.color = c;
	}
	
	// Setter method for field color
	public String getColor() {
		return this.color;
	}
}
