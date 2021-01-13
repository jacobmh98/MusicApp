package mainApplication.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;
import org.jspace.SpaceRepository;

import mainApplication.MusicApp;

public class Server implements Runnable {
	private MusicApp instance;
	
	public Server(MusicApp instance) {
		this.instance = instance;
	}
	
	public void run() {
		
		try {
			
			//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			//System.out.println(trackID);
			// Create a repository 
			SpaceRepository repository = new SpaceRepository();

			//SequentialSpace chords = new SequentialSpace();
			//"Lobby" is our login
			Space login = new SequentialSpace();
			Space tracks = new SequentialSpace();
//			
//			chords.put("A");
//			chords.put("B");
//			chords.put("C");
//			chords.put("D");
//			chords.put("E");
//			chords.put("F");
//			chords.put("G");

			// Add the space to the repository
			repository.add("login",login);
			repository.add("tracks",tracks);
			
			//repository.add("chords",chords);
			
			// Set the URI of the chat space
			System.out.print("Enter URI of the chat server or press enter for default: ");
			String uri = "tcp://127.0.0.1:9001/login?keep";
			
			
			repository.addGate(uri);
			System.out.println("Opening repository gate at " + uri + "...");
			
			// Keep reading chat messages and printing them 
			while (true) {
				Integer trackC = 0;
				String trackURI;
				while(true) {
					System.out.println("About to get request");
					Object[] request = login.get(new ActualField("enter"), new FormalField(String.class), new FormalField(Integer.class));
					
					String who = (String) request[1];
					Integer trackID = (Integer) request[2];
					
					System.out.println(who + " requesting to enter track with id " + trackID + "...");	
				
					Object[] the_track = tracks.queryp(new ActualField(trackID),new FormalField(Integer.class));
					//If track exists, join it
					if(the_track != null) {
						System.out.println(the_track[0] + "  " + the_track[1]);
						trackURI = "tcp://127.0.0.1:9001/track" + the_track[1] + "?keep";
					//If track doesn't exist, create one
					} else {
						System.out.println("Creating room " + trackID + " for " + who + " ...");	
						trackURI = "tcp://127.0.0.1:9001/track" + trackC + "?keep";
						System.out.println("Setting up chat space " + trackURI + "...");
						new Thread(new roomHandler(trackID,"track"+trackC,trackURI,repository)).start();
						tracks.put(trackID,trackC);
						trackC++;
					}
					
					
					System.out.println("Telling " + who + " to go for room " + trackID + " at " + trackURI + "...");
					login.put("trackURI", who, trackID, trackURI);
					
				}
				
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	

}



class roomHandler implements Runnable {

	private Space track;
	private Integer trackID;
	private String spaceID;

	public roomHandler(Integer trackID, String spaceID, String uri, SpaceRepository repository) {

		this.trackID = trackID;
		this.spaceID = spaceID;
		
		// Create a local space for the chatroom
		track = new SequentialSpace();

		// Add the space to the repository
		repository.add(this.spaceID, track);

	}

	@Override
	public void run() {
		try {

			// Keep reading chat messages and printing them 
			while (true) {
				Object[] message = track.get(new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class),new FormalField(Integer.class) );
				System.out.println("Key " + message[0] + " | PlayingType " + message[1] + " | BlockID " + message[2] + " | UserID " + message[3] );
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
}
