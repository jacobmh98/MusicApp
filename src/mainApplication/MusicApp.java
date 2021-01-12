package mainApplication;

public class MusicApp {
	// Setting up singleton
	private static MusicApp instance;
	private AudioPlayer audioPlayer;
	private Track track;
	
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
	
	
	// Getter method for audio player
	public AudioPlayer getAudioPlayer() {
		return this.audioPlayer;
	}
	
	// Getter & setter method for track
	public void setTrack(Track t) {
		this.track = t;
	}
	public Track getTrack() {
		return this.track;
	}
	
	// Login coordination
	private boolean loginStatus = false;
	public boolean getLoginStatus() {
		return loginStatus;
	}
	public void login(boolean b) {
		this.loginStatus = b;
	}
	
	// Method to translate from node key to string node fx: -40 --> C#4_major
	public String translateIdToKey(int id, int playingType) {
		String[] keys = {"A","A#","B","C","C#","D","D#","E","F","F#","G","G#"};
		String key;
		if(id < 81) {
			key = keys[id % 12] + (int) (Math.floor(id / 12) + 1) + (playingType == -1 ? "_major" : playingType == 0 ? "_minor" : "");
		} else {
			key = keys[id % 12] + (int) (Math.floor(id / 12) + 1);
		}
		return key;
	}
}
