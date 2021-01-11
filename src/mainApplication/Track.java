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
			
			timeBlock.setPrefWidth(75);
			timeBlock.getStyleClass().add("timeBlock");
			
			timeBlock.getChildren().add(timeStamp);
		}
		
		public VBox getTimeBlockVisual() {
			return this.timeBlock;
		}
	}
	
	private class SoundBlock extends Pane {
		private int timeBlockLocation;
		private String type;
		private int soundID;
		private int personID;
		String[] colors = {"#34a8eb", "#eb5934", "#3be835"};
		
		public SoundBlock(int personID) {
			this.personID = personID;
			this.prefHeight(20);
			this.getStyleClass().add("soundBlock");
			this.setStyle("-fx-background-color: " + colors[personID] + ";");
		}
	}
}
