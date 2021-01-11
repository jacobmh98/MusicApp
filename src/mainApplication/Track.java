package mainApplication;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Track {
	private HBox paneTrack;
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
	private MusicApp musicApp = MusicApp.getInstance();
	private int timeBlockCount = 0;
	
	public Track(HBox paneTrack) {
		this.paneTrack = paneTrack;
		this.reset();
		
		// adding test sound blocks just to test it out
		/*timeBlocks.get(0).addSoundBlock(0, "major", "C1");
		timeBlocks.get(0).addSoundBlock(1, "keys", "A2");
		timeBlocks.get(1).addSoundBlock(1, "keys", "A3#");
		timeBlocks.get(2).addSoundBlock(2, "minor", "G#");*/
	}
	
	public void reset() {
		for(int i = 0; i < 5; i++) {
			addTimeBlock();
		}
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
	
	// Getter methods for single and all TimeBlocks
	public TimeBlock getTimeBlock(int index) {
		return timeBlocks.get(index);
	}
	
	public ArrayList<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}
	
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
		public void addSoundBlock(int personID, int playingType, String key) {
			SoundBlock soundBlock = new SoundBlock(personID, playingType, key);
			
			this.timeBlock.getChildren().add(soundBlock);
		}
		
		// Getter methods for fields
		public int getBlockID() {
			return this.blockID;
		}
		
		public VBox getTimeBlockVisual() {
			return this.timeBlock;
		}
	}
	
	private class SoundBlock extends StackPane {
		private int playingType;
		private String key;
		private int personID;
		String[] colors = {"#34a8eb", "#eb5934", "#3be835"};
		
		public SoundBlock(int personID, int playingType, String key) {
			this.personID = personID;
			this.playingType = playingType;
			this.key = key;
			
			Label labelKey = new Label(this.key);
			if(this.playingType != 1) {
				labelKey.setText(this.key);
			}
			
			this.getChildren().add(labelKey);
			this.prefHeight(20);
			this.minHeight(20);
			this.maxHeight(20);
			
			this.setStyle("-fx-background-color: " + colors[this.personID]);
			this.getStyleClass().add("soundBlock");
			//this.setStyle("-fx-background-color: " + colors[personID] + ";");
		}
	}
}
