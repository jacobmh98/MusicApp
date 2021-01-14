package mainApplication.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Client implements Runnable {
	private String name;
	private int trackId;
	private String trackName;
	RemoteSpace trackRoom_space;
	
	public Client(String name, int trackId, String trackName) {
		this.name = name;
		this.trackId = trackId;
		this.trackName = trackName;
	}

	public void run() {
		try { 
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Set the URI of the chat space
		// Default value
		String uri = "tcp://127.0.0.1:9001/login?keep";
		
		// Connect to the remote login
		System.out.println("Connecting to login space " + uri + "...");
		RemoteSpace login = new RemoteSpace(uri);

		// Read user name from the console			
		System.out.print("Enter your name: ");
		System.out.print(name);

		System.out.print("Select a track room: ");
		System.out.println(trackName + " has id: " + trackId);
		Integer trackRoom = trackId;
		
		// Send request to enter chatroom
		login.put("enter",name,trackRoom);
		System.out.println(name + " requested the track " + trackRoom);
		
		Object[] response = login.get(new ActualField("trackURI"), new ActualField(name), new ActualField(trackRoom), new FormalField(String.class));
		String trackRoom_uri = (String) response[3];
	    System.out.println("Connecting to track space " + trackRoom_uri);
		trackRoom_space = new RemoteSpace(trackRoom_uri);
		
		
		System.out.println("Start chatting...");
		
		
		
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
	
	public void sendToServer(int keyID, int playingType, int blockID, int userID) {
		try {
			trackRoom_space.put(keyID,playingType,blockID,userID);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getFromServer() {
		try {
			
			Object[] update;
			update = trackRoom_space.query(new ActualField("update view"));
			System.out.println(update[0]);
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
