package mainApplication.model;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.RandomSpace;
import org.jspace.RemoteSpace;
import org.jspace.Space;

public class Database {
	
	public static void main(String[] args) {
		try {
		
		String uri = "tcp://127.0.0.1:9001/chords?keep";
		Space pianoSpace = new RemoteSpace(uri);
		pianoSpace.put("A");
		pianoSpace.put("B");
		pianoSpace.put("C");
		pianoSpace.put("D");
		pianoSpace.put("E");
		pianoSpace.put("F");
		pianoSpace.put("G");
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
