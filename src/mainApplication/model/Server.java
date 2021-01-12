package mainApplication.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class Server implements Runnable {
	private String trackID;
	
	public Server(String trackID) {
		this.trackID = trackID;
		
	}
	
	public void run() {
		
		try {
			
			//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			// Create a repository 
			SpaceRepository repository = new SpaceRepository();

			// Create a local space for the track
			SequentialSpace track = new SequentialSpace();
			SequentialSpace chords = new SequentialSpace();
			chords.put("A");
			chords.put("B");
			chords.put("C");
			chords.put("D");
			chords.put("E");
			chords.put("F");
			chords.put("G");

			// Add the space to the repository
			repository.add("track", track);
			
			// Set the URI of the chat space
			System.out.print("Enter URI of the chat server or press enter for default: ");
			String uri = trackID;
			// Default value
			if (uri.isEmpty()) { 
				uri = "tcp://127.0.0.1:9001/track?keep";
			}
			
			// Open a gate
			URI myUri = new URI(uri);
			String gateUri = "tcp://" + myUri.getHost() + ":" + myUri.getPort() +  "/track?keep" ;
			System.out.println("Opening repository gate at " + gateUri + "...");
			repository.addGate(gateUri);
		
//			URI chordsURI = new URI("tcp://127.0.0.1:9001/chords?keep");
//			String gateUriChords = "tcp://" + chordsURI.getHost() + ":" + chordsURI.getPort() +  "/chords?keep" ;
//			System.out.println("Opening repository gate at " + gateUriChords + "...");
//			repository.addGate(gateUriChords);
			
			// Keep reading chat messages and printing them 
			while (true) {
				Object[] t = track.get(new FormalField(String.class), new FormalField(Integer.class));				
				System.out.println("Client wants " + t[0] + " for " + t[1] + " milliseconds");
				String t0 = (String) t[0];
				Object[] c = chords.queryp(new ActualField(t0));
				System.out.println("Server retrieved: " + c[0]);
				
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}