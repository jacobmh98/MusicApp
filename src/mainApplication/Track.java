package mainApplication;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Track {
	private HBox paneTrack;
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
	private MusicApp musicApp = MusicApp.getInstance();
	private int timeBlockCount = 0;
	private ArrayList<User> trackUsers = new ArrayList<User>();
	private int trackID;
	
	public Track(int trackID) {
		this.trackID = trackID;
	}
	
	// Method to intialize the empty track with a pane
	public void initializeTrack(HBox paneTrack) {
		this.paneTrack = paneTrack;
		
		for(int i = 0; i < 5; i++) {
			addTimeBlock();
		}
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
		public void addSoundBlock(int playingType, String key, int keyID) {
			SoundBlock soundBlock = new SoundBlock(playingType, key, keyID, blockID);
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
