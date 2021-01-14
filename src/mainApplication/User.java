package mainApplication;

public class User {
	private int color;
	private String userID;
	private int userNumberID = 0;
	
	public User(String userID) {
		this.userID = userID;
		for(int i = 0; i < userID.length(); i++) {
			this.userNumberID += (int) userID.charAt(i);
		}
	}
	
	// Getter method for name
	public String getUserID() {
		return this.userID;
	}
	
	public int getUserNumberId() {
		return this.userNumberID;
	}
}
