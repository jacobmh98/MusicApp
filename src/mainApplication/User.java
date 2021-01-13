package mainApplication;

public class User {
	private String name;
	private int color;
	
	public User(String name) {
		this.name = name;
	}
	
	// Getter method for name
	public String getName() {
		return this.name;
	}
	
	// Getter method for field color
	public void setColor(int c) {
		this.color = c;
	}
	
	// Setter method for field color
	public int getColor() {
		return this.color;
	}
}
