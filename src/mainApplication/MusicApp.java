package mainApplication;

import java.util.ArrayList;
import java.util.List;

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
	
	// GetteR method for USER_COLORS
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
	// Getter method for CreaterView
	public CreatorsView getCreatorsView() {
		return this.creatorsView;
	}
	
	/***********************************************/
	// SERVER STUFF UPDATES
	
	// Method to login to either existing track of creating new track
	public void login(String userID, int trackID) {
		client = new Client(userID,trackID);
		Thread t = new Thread(client);
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(client.getClientReady()) {
			getUsersFromServer();
			
			currentUser = new User(userID);
			enterTrack(trackID);
			
			currentUser = trackUsers.get(0);
			
			currentTrack.addUserToTrack(currentUser);
			
			creatorsView = new CreatorsView();
		}
	}
	
	// Method to retrieve track data from the server
	public void getKeysFromServer() {
		client.sendToServer(1, -1, 0, 300);
		try {
			List<Object[]> keys = client.getTrackKeys();
			
			if(keys==null) {
				
			}
			//System.out.println(keys.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Method to retrieve data from the server
	public void getUsersFromServer() {
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
	
	public void updateViews() {
		getUsersFromServer();
		getKeysFromServer();
		
		creatorsView.updateView();
	}

	public ArrayList<User> getTrackUsers() {
		return this.trackUsers;
	}
	
}
