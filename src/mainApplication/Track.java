package mainApplication;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Track {
	HBox paneTrack;
	ArrayList<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
	
	public Track(HBox paneTrack) {
		this.paneTrack = paneTrack;
		this.reset();
		
		// adding test sound blocks just to test it out
		timeBlocks.get(0).addSoundBlock(0, "major", "C1");
		timeBlocks.get(0).addSoundBlock(1, "keys", "A2");
		timeBlocks.get(1).addSoundBlock(1, "keys", "A3#");
		timeBlocks.get(2).addSoundBlock(2, "minor", "G#");
	}
	
	public void reset() {
		for(int i = 0; i < 5; i++) {
			generateTimeBlock();
		}
		for(TimeBlock t : timeBlocks) {
			this.paneTrack.getChildren().add(t.getTimeBlockVisual());
		}
	}
	
	private void generateTimeBlock() {
		TimeBlock timeBlock = new TimeBlock(timeBlocks.size());
		timeBlocks.add(timeBlock);
	}
	
	private class TimeBlock {
		VBox timeBlock;
		int id;
		Label timeStamp;
		ArrayList<SoundBlock> soundBlocks = new ArrayList<SoundBlock>();
		
		public TimeBlock(int id) {
			this.timeBlock = new VBox();
			this.id = id;
			this.timeStamp = new Label(this.id*500 + " ms;");
			
			this.timeBlock.setPrefWidth(75);
			this.timeBlock.getStyleClass().add("timeBlock");
			
			this.timeBlock.getChildren().add(timeStamp);
		}
		
		public void addSoundBlock(int personID, String type, String key) {
			SoundBlock soundBlock = new SoundBlock(personID, type, key);
			
			this.timeBlock.getChildren().add(soundBlock);
		}
		
		public VBox getTimeBlockVisual() {
			return this.timeBlock;
		}
	}
	
	private class SoundBlock extends Pane {
		private String type;
		private String key;
		private int personID;
		String[] colors = {"#34a8eb", "#eb5934", "#3be835"};
		
		public SoundBlock(int personID, String type, String key) {
			this.personID = personID;
			this.type = type;
			this.key = key;
			
			Label labelKey = new Label(this.key);
			if(this.type.length() != 4) {
				labelKey.setText(this.key + "_" + this.type);
			}
			
			this.getChildren().add(labelKey);
			this.prefHeight(20);
			this.setStyle("-fx-background-color: " + colors[this.personID]);
			this.getStyleClass().add("soundBlock");
			//this.setStyle("-fx-background-color: " + colors[personID] + ";");
		}
	}
}
