package mainApplication;

public class User {
	private int color;
	private String userID;
	
	public User(String userID) {
		this.userID = userID;
	}
	
	// Getter method for name
	public String getUserID() {
		return this.userID;
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
