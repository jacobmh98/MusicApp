package mainApplication;

import java.util.ArrayList;

public class MusicApp {
	// Setting up singleton
	private static String[] USER_COLORS = {"#34a8eb", "#eb5934", "#3be835", "#fcfc03", "#c603fc"};
	private static MusicApp instance;
	private static AudioPlayer audioPlayer;
	private Track currentTrack;
	private ArrayList<Track> allTracks = new ArrayList<Track>();
	private User currentUser;
	
	private ArrayList<Integer> userIDList = new ArrayList<Integer>();
	private String trackID;
	
	private MusicApp() {
		audioPlayer= new AudioPlayer();
	}
	
	// Gettet method for USER_COLORS
	public String[] getUserColors() {
		return USER_COLORS;
	}
	
	// Method returning instance
	public static MusicApp getInstance() {
		if(instance == null) {
			instance = new MusicApp();
		}
		return instance;
	}
	
	
	// Getter method for audio player
	public static AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
	
	// Getter method for track
	public Track getCurrentTrack() {
		return this.currentTrack;
	}
	
	// Login coordination
	private boolean loginStatus = false;
	
	public boolean getLoginStatus() {
		return loginStatus;
	}

	// Method to login to either existing track of creating new track
	public void login(String userName, int trackID, String trackName) {
		this.loginStatus = true;
		
		currentUser = new User(userName);
		
		if(!(trackName.isEmpty())) {
			createNewTrack(userName, trackID, trackName);
		} else {
			enterExistingTrack();
		}
		

		currentTrack.addUserToTrack(currentUser);
	}
	
	// Method to create a new track
	public void createNewTrack(String name, int trackID, String trackName) {
		currentTrack = new Track(trackName, trackID);
		allTracks.add(currentTrack);
	}
	
	// Method to enter existing track
	public void enterExistingTrack() {
		
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
	
	// Getter method for the current user
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/***********************************************************************/
	// TESTING WITH ANOTHER USER
	public void testAddUser() {
		currentTrack.addUserToTrack(new User("Max"));
	}
	
	public void testSwitchUser(String name) {
		for(User u : currentTrack.getTrackUsers()) {
			if(u.getName().equals(name)) {
				this.currentUser = u;
			}
		}
	}
			
}
