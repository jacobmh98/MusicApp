package mainApplication.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Client {

	public static void main(String[] args) {
		try { 
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Set the URI of the chat space
		// Default value
		System.out.print("Enter URI of the chat server or press enter for default: ");
		String uri = input.readLine();
		// Default value
		if (uri.isEmpty()) { 
			uri = "tcp://127.0.0.1:9001/track?keep";
		}

		// Connect to the remote chat space 
		System.out.println("Connecting to track space " + uri + "...");
		RemoteSpace track = new RemoteSpace(uri);

		// Read user name from the console			
		System.out.print("Enter your name: ");
		String name = input.readLine();

		// Keep sending whatever the user types
		System.out.println("Start musicing...");
		while(true) {
			String chord = input.readLine();
			Integer length = Integer.parseInt(input.readLine());
			track.put(chord, length);
		}			


	} catch (InterruptedException e) {
		e.printStackTrace();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
