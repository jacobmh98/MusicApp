package mainApplication;

import java.util.ArrayList;

import mainApplication.model.Client;

public class MusicApp {
	// Setting up singleton
	private static String[] USER_COLORS = {"#34a8eb", "#eb5934", "#3be835", "#fcfc03", "#c603fc"};
	private static MusicApp instance;
	private static AudioPlayer audioPlayer;
	private ArrayList<Track> allTracks = new ArrayList<Track>();
	private ArrayList<User> trackUsers = new ArrayList<User>();
	
	// Instances of different classes
	private Track currentTrack;
	private User currentUser;
	private CreatorsView creatorsView;
	
	private Client client;
	
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
	
	// Methos for CreatorsView
	public CreatorsView getCreatorsView() {
		return this.creatorsView;
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
	
	// Method to create a new track
	public void enterTrack(int trackID) {
		currentTrack = new Track(trackID);
		allTracks.add(currentTrack);
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
	
	public Client getClient() {
		return client;
	}
	
	/***********************************************/
	// SERVER STUFF UPDATES
	
	// Method to login to either existing track of creating new track
	public void login(String userID, int trackID) {
		client = new Client(userID,trackID);
		new Thread(client).start();
		
		this.loginStatus = true;
		currentUser = new User(userID);
		enterTrack(trackID);
		
		currentTrack.addUserToTrack(currentUser);
		
		creatorsView = new CreatorsView();
	}
	
	// Method to initialize the track with data from the server
	public void getDataFromServer() {
		try {
			ArrayList<String> userIDs = client.getTrackUsers();
			
			for(String userID : userIDs) {
				boolean contains = false;
				
				for(int i = 0; i < trackUsers.size(); i++) {
					if(userID.equals(trackUsers.get(i).getUserID())) {
						contains = true;
						break;
					}
				}
				
				if(!contains) {
					trackUsers.add(new User(userID));
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshView() {
		getDataFromServer();
		
		creatorsView.updateView();
		
		System.out.println("----------------------------------------");
		for(User u : trackUsers) {
			System.out.println(u.getUserID());
		}
		
		
	}

	public ArrayList<User> getTrackUsers() {
		return this.trackUsers;
	}
	
}
