package mainApplication;

public class MusicApp {
	// Setting up singleton
	private static MusicApp instance;
	private AudioPlayer audioPlayer;
	
	private MusicApp() {
		audioPlayer= new AudioPlayer();
	}
	
	// Method returning instance
	public static MusicApp getInstance() {
		if(instance == null) {
			instance = new MusicApp();
		}
		return instance;
	}
	
	// Gettet method for audio player
	public AudioPlayer getAudioPlayer() {
		return this.audioPlayer;
	}
	
	// Login coordination
	private boolean loginStatus = false;
	public boolean getLoginStatus() {
		return loginStatus;
	}
	public void login(boolean b) {
		this.loginStatus = b;
	}
}
