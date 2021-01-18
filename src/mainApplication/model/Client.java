package mainApplication.model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Client implements Runnable {
	private String userID;
	private int trackId;
	RemoteSpace trackRoom_space;
	private boolean clientReady = false;
	
	public Client(String userID, int trackId) {
		this.userID = userID;
		this.trackId = trackId;
	}
	
	public boolean getClientReady() {
		return  this.clientReady;
	}

	public void run() {
		try { 
		// Set the URI of the chat space
		// Default value
		String uri = "tcp://127.0.0.1:9001/login?keep";
		
		// Connect to the remote login
		System.out.println("Connecting to login space " + uri + "...");
		RemoteSpace login = new RemoteSpace(uri);

		// Read user name from the console		
		System.out.println("Current userID: " + userID);

		System.out.println("Track room: " + trackId);
		Integer trackRoom = trackId;
		
		// Send request to enter trackRoom
		login.put("enter",userID,trackRoom);
		System.out.println(userID + " requested the track " + trackRoom);
		
		Object[] response = login.get(new ActualField("trackURI"), new ActualField(userID), new ActualField(trackRoom), new FormalField(String.class));
		String trackRoom_uri = (String) response[3];
	    System.out.println("Connecting to track space " + trackRoom_uri);
	    
		trackRoom_space = new RemoteSpace(trackRoom_uri);
		trackRoom_space.put("userID", userID);
		
		System.out.println("Start playing...");
		clientReady = true;
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	// Method to insert key in track
	public void sendKeyToServer(int keyID, int playingType, int blockID, int userID) {
		try {
			trackRoom_space.put(keyID,playingType,blockID,userID);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	// Method to retrieve keys from track
	public List<Object[]> getTrackKeys() throws InterruptedException {
		List<Object[]> keys = trackRoom_space.queryAll(new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class));
		
		return keys;
	}
	
	// Method to delete particular key from track
	public boolean deleteKeyFromServer(int keyID, int playingType, int blockID, int userID) {
		Object[] key;
		try {
			key = trackRoom_space.getp(new ActualField(keyID), new ActualField(playingType), new ActualField(blockID), new ActualField(userID));
			return key != null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Method to set default info in track
	public Integer initializeTrackFromServer() {
		try {
			Object[] numberOfTimeBlocks = trackRoom_space.queryp(new ActualField("nTimeBlocks"), new FormalField(Integer.class));
			
			if(numberOfTimeBlocks == null) {
				trackRoom_space.put("nTimeBlocks", 10);
				return 10;
			}
		
			return (Integer) numberOfTimeBlocks[1];
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public ArrayList<String> getTrackUsers() throws InterruptedException {
		Object[] testUser = trackRoom_space.queryp(new ActualField("userID"), new FormalField(String.class));
		
		if(testUser != null) {
			List<Object[]> users = trackRoom_space.queryAll(new ActualField("userID"), new FormalField(String.class));
			ArrayList<String> userIDs = new ArrayList<String>();
			
			System.out.println("All users in track");
			for(Object[] u : users) {
				System.out.println(u[1]);
				userIDs.add((String) u[1]);
			}
			return userIDs;
		}
		
		return new ArrayList<String>();
		
		
	}

	// Get Update Lock
	public boolean containsUpdateLock(int userID, boolean searchOrRemove) {
		try {
			boolean contains = false;
			if(searchOrRemove) {
				Object[] updateLock = trackRoom_space.queryp(new ActualField("update"), new ActualField(userID));
				if(updateLock != null) {
					contains = true;
				}
			} else {
				while(true) {
					Object[] updateLock = trackRoom_space.getp(new ActualField("update"), new ActualField(userID));
					if(updateLock == null) {
						break;
					} else {
						contains = true;
					}
				}
			}
			return contains;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Set update lock
	public void setUpdateLock(int userID) {
		try {
			Object[] updateLock = trackRoom_space.queryp(new ActualField("update"), new ActualField(userID));
			if(updateLock == null) {
				trackRoom_space.put("update", userID);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
