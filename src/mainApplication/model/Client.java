package mainApplication.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Client implements Runnable {
	private int id;
	private String name;
	private String trackUri;
	
	public Client(int id, String name, String trackUri) {
		this.id = id;
		this.name = name;
		this.trackUri = trackUri;
		
	}

	public void run() {
		try { 
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Set the URI of the chat space
		// Default value
		System.out.print("Enter URI of the chat server or press enter for default: ");
		String uri = trackUri;
		// Default value
		if (uri.isEmpty()) { 
			uri = "tcp://127.0.0.1:9001/track?keep";
		}

		// Connect to the remote chat space 
		System.out.println("Connecting to track space " + uri + "...");
		RemoteSpace track = new RemoteSpace(uri);

		// Read user name from the console			
		System.out.println(name);

		// Keep sending whatever the user types
		System.out.println("Start musicing...");		

		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public void putInTrackSpace(String chord, int timeSlot) {
		try {
			RemoteSpace trackSpace = new RemoteSpace(trackUri);
			trackSpace.put(chord,timeSlot);
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
}
