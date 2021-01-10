package mainApplication;

public class MusicApp {
	// Setting up singleton
	private static MusicApp instance;
	
	private MusicApp() {}
	
	// Method returning instance
	public static MusicApp getInstance() {
		if(instance == null) {
			instance = new MusicApp();
		}
		return instance;
	}
	
	// Login coordination
	private boolean loginStatus = false;
	public boolean getLoginStatus() {
		return loginStatus;
	}
	public void login(boolean b) {
		this.loginStatus = b;
	}
	
	// 
}
