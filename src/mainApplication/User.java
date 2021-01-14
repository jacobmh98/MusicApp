package mainApplication;

import java.util.Random;

public class User {
	private String name;
	private int color;
	private int userID;
	
	public User(String name) {
		this.name = name;
		this.userID = new Random().nextInt();
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
