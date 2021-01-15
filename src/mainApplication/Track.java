package mainApplication;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Track {
	private HBox paneTrack;
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
	private int timeBlockCount = 0;
	private ArrayList<User> trackUsers = new ArrayList<User>();
	private int trackID;
	
	public Track(int trackID) {
		this.trackID = trackID;
	}
	
	// Method to initialize the empty track with a pane
	public void initializeTrack() {
		timeBlocks = new ArrayList<TimeBlock>();
		paneTrack.getChildren().clear();
		timeBlockCount = 0;
		
		int nTimeBlocks = MusicApp.getInstance().initializeTrackFromServer();
		List<Object[]> keys = MusicApp.getInstance().getKeysFromServer();
		
		for(int i = 0; i < nTimeBlocks; i++) {
			addTimeBlock();
		}
		
		if(keys != null) {
			System.out.println(keys);
			for(int i = 0; i < keys.size(); i++) {
				Object[] key = keys.get(i);
				String keyName = MusicApp.getInstance().translateIdToKey(Math.abs((int) key[0]), (int) key[1]);
				int colorIndex = MusicApp.getInstance().getSortedUserIdFromID((int) key[3]);
				timeBlocks.get((int) key[2]).addSoundBlock((int) key[1], keyName, (int) key[0], colorIndex);
				System.out.println("KeyID: " + keys.get(i)[0]);
			}
		}
	}
	
	public void setPane(HBox paneTrack) {
		this.paneTrack = paneTrack;
	}
	
	// Getter method for Track ID
	public int getTrackID() {
		return this.trackID;
	}
	
	// Getter method for trackUsers
	public ArrayList<User> getTrackUsers() {
		return trackUsers;
	}
	
	// Add user to track
	public void addUserToTrack(User u) {
		this.trackUsers.add(u);
	}
	
	// Add TimeBlock
	public void addTimeBlock() {
		TimeBlock timeBlock = new TimeBlock(timeBlockCount);
		timeBlockCount++;
		timeBlocks.add(timeBlock);
		this.paneTrack.getChildren().add(timeBlock.getTimeBlockVisual());
	}
	
	// Delete TimeBlock
	public void deleteTimeBlock() {
		if(timeBlockCount > 1) {
			TimeBlock timeBlock = timeBlocks.get(timeBlockCount-1);
			timeBlockCount--;
			timeBlocks.remove(timeBlock);
			this.paneTrack.getChildren().remove(timeBlock.getTimeBlockVisual());
		}
	}
	
	// Getter methods for single, leftover and all TimeBlocks
	public TimeBlock getTimeBlock(int index) {
		return timeBlocks.get(index);
	}
	
	public ArrayList<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}
	
	public ArrayList<TimeBlock> getLeftOverTimeBlocks(int index) {
		ArrayList<TimeBlock> leftOverTimeBlocks = new ArrayList<TimeBlock>();
		
		for(int i = index; i < timeBlocks.size(); i++) {
			leftOverTimeBlocks.add(timeBlocks.get(i));
		}
		
		return leftOverTimeBlocks;
	}
	
	// new class Timeblock
	public class TimeBlock {
		private VBox timeBlock;
		private int blockID;
		private Label timeStamp;
		private ArrayList<SoundBlock> soundBlocks = new ArrayList<SoundBlock>();
		
		public TimeBlock(int blockID) {
			this.timeBlock = new VBox();
			this.blockID = blockID;
			this.timeStamp = new Label(this.blockID*500 + " ms;");
			
			this.timeBlock.setPrefWidth(75);
			this.timeBlock.setMinWidth(75);
			this.timeBlock.setMaxWidth(75);
			this.timeBlock.getStyleClass().add("timeBlock");
			
			this.timeBlock.getChildren().add(timeStamp);
		}
		
		// Add SoundBlock
		public void addSoundBlock(int playingType, String key, int keyID, int colorIndex) {
			SoundBlock soundBlock = new SoundBlock(playingType, key, keyID, blockID, colorIndex);
			soundBlocks.add(soundBlock);
			this.timeBlock.getChildren().add(soundBlock);
		}
		
		// Getter method for sound blocks
		public ArrayList<SoundBlock> getSoundBlocks() {
			return soundBlocks;
		}
		
		// Method for deleting sound block
		public void deleteSoundBlock(SoundBlock s) {
			this.soundBlocks.remove(s);
			this.timeBlock.getChildren().remove(s);
		}
		
		// Getter methods for fields
		public int getBlockID() {
			return this.blockID;
		}
		
		public VBox getTimeBlockVisual() {
			return this.timeBlock;
		}
	}
	
}
