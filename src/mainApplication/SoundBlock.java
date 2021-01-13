package mainApplication;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SoundBlock extends StackPane {
		private int playingType;
		private String key;
		private int keyID;
		private int colorIndex;
		private int blockID;
		String[] colors = MusicApp.getInstance().getUserColors();
		
		public SoundBlock(int playingType, String key, int keyID, int blockID) {
			this.colorIndex = MusicApp.getInstance().getCurrentTrack().getTrackUsers().indexOf(MusicApp.getInstance().getCurrentUser());
			this.playingType = playingType;
			this.key = key;
			this.keyID = keyID;
			this.blockID = blockID;
			
			Label labelKey = new Label(this.key);
			if(this.playingType != 1) {
				labelKey.setText(this.key);
			}
			
			this.getChildren().add(labelKey);
			this.prefHeight(20);
			this.minHeight(20);
			this.maxHeight(20);
			
			this.setStyle("-fx-background-color: " + colors[this.colorIndex]);
			this.getStyleClass().add("soundBlock");
			
			//ActionEvent for both left and right mouse click
			this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(arg0.getButton() == MouseButton.SECONDARY) {
						MusicApp.getInstance().getCurrentTrack().getTimeBlock(SoundBlock.this.blockID).deleteSoundBlock(SoundBlock.this);
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