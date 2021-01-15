package mainApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mainApplication.model.Client;

public class MusicApp {
	// Setting up singleton
	private static String[] USER_COLORS = {"#34a8eb", "#eb5934", "#3be835", "#fcfc03", "#c603fc"};
	private static MusicApp instance;
	private static AudioPlayer audioPlayer;
	private ArrayList<User> trackUsers = new ArrayList<User>();
	private ArrayList<User> sortedUsers = trackUsers;
	
	// Instances of different classes
	private Track currentTrack;
	private User currentUser;
	private CreatorsView creatorsView;
	
	private Client client;
	private int trackID;
	
	private MusicApp() {
		audioPlayer= new AudioPlayer();
	}
	
	// Method to sort user in hierchy 
	public void sortUsers() {
		int count = 0;
				
		User temp;
		for(int i = 0; i < sortedUsers.size(); i++) {
			for(int j = i+1; j < sortedUsers.size();j++) {
				if(sortedUsers.get(i).getUserNumberId() < sortedUsers.get(j).getUserNumberId()) {
					temp = sortedUsers.get(i);
					sortedUsers.set(i, sortedUsers.get(j));
					sortedUsers.set(j, temp);
				}
			}
		}
		
		for(User u : sortedUsers) {
			System.out.println(u.getUserNumberId() + " - " + u.getUserID());
		}
	}
	
	// Getter method for USER_COLORS
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
	public void createTrack(int trackID) {
		currentTrack = new Track(trackID);
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
			
			createTrack(trackID);
			currentUser = new User(userID);
			currentUser = trackUsers.get(0);
			this.trackID = trackID;
			currentTrack.addUserToTrack(currentUser);
			creatorsView = new CreatorsView();
		}
	}
	
	// Method to retrieve track data from the server
	public List<Object[]> getKeysFromServer() {
		try {
			List<Object[]> keys = client.getTrackKeys();
			
			System.out.println("Keys in track: " + keys.size());
			if(keys!=null) {
				for(Object[] key : keys) {
					System.out.println(key[0]+", "+key[1]+", "+key[2]+", "+key[3]);
				}
				
			}
			
			return keys;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Method to retrieve users from the server
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
	
	// Method to retrieve track from the server
	public Integer initializeTrackFromServer() {
		return client.initializeTrackFromServer();
	}
	
	public void updateViews() {
		getUsersFromServer();
		//getTrackFromServer();
		//getKeysFromServer();
		
		creatorsView.updateView();
		sortUsers();
		currentTrack.initializeTrack();
		// views....
	}

	public ArrayList<User> getTrackUsers() {
		return this.trackUsers;
	}

	public int getSortedUserIdFromID(int id) {
		for(User u : sortedUsers) {
			if(u.getUserNumberId() == id) {
				return sortedUsers.indexOf(u);
			}
			
		}
		return -1;
	}
	
}
