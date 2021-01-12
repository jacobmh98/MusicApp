package mainApplication;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Track {
	private HBox paneTrack;
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
	private MusicApp musicApp = MusicApp.getInstance();
	private int timeBlockCount = 0;
	
	public Track(HBox paneTrack) {
		this.paneTrack = paneTrack;
		reset();
		musicApp.setTrack(this);
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
		public void addSoundBlock(int personID, int playingType, String key, int keyID) {
			SoundBlock soundBlock = new SoundBlock(personID, playingType, key, keyID, blockID);
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
	
	public class SoundBlock extends StackPane {
		private int playingType;
		private String key;
		private int keyID;
		private int personID;
		private int blockID;
		String[] colors = {"#34a8eb", "#eb5934", "#3be835"};
		SoundBlock s;
		
		public SoundBlock(int personID, int playingType, String key, int keyID, int blockID) {
			this.personID = personID;
			this.playingType = playingType;
			this.key = key;
			this.keyID = keyID;
			this.blockID = blockID;
			s = this;
			
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
			
			//ActionEvent left mouse click
			this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(arg0.getButton() == MouseButton.SECONDARY) {
						//MusicApp.getInstance().getTrack().getTimeBlock(blockID).getSoundBlocks().remove(this);
						MusicApp.getInstance().getTrack().getTimeBlock(blockID).deleteSoundBlock(s);
					} else {
						System.out.println("left");
						MusicApp.getAudioPlayer().playKey(keyID, playingType);
					}
				}
			});
		}
		
		// Getter methods for fields
		public int getPlayingType() {
			return this.playingType;
		}
		public int getKeyID() {
			return this.keyID;
		}
	}
}
