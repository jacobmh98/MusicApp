package mainApplication.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import mainApplication.MusicApp;
import mainApplication.Track;

public class MainController implements Initializable {
	private MusicApp musicApp = MusicApp.getInstance();
	
	private static final int KEY_HEIGHT = 90;
	private static final int KEY_WIDTH_BLACK = 15;
	private static final int KEY_WIDTH_WHITE = 20;
	private static final int TOTAL_KEYS = 88;
	
	private ArrayList<Rectangle> borderKeys = new ArrayList<Rectangle>();;
	private ArrayList<Key> keys = new ArrayList<Key>();
	
	private Track track;
	
	private int playingType = -1; // Default playing Major chord. 0 => Minor, 1 => Individual Notes
	
	@FXML
	private Pane panePiano; 
	
	@FXML
	private RadioButton radioBtnMajor;
	
	@FXML
	private RadioButton radioBtnMinor;
	
	@FXML
	private RadioButton radioBtnNotes;
	
	@FXML
	private HBox hBoxTrack;
	
	@FXML
	private ChoiceBox choiceBoxPosition;
	
	@FXML
	private Label lblPressedKey;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		track = new Track(hBoxTrack);
		
		int whiteCount = 0;
		int blackCount = 1;
		
		for(int i = 0; i < TOTAL_KEYS; i++) {
			int x = i % 12;
			Key tile = null;
			switch (x) {
				case 0: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0); whiteCount++; break;
				case 1: tile = new Key(blackCount*20-7.5, 0, -i);blackCount+=2; break;
				case 2: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0);whiteCount++; break;
				case 3: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0); whiteCount++;break;
				
				case 4: tile = new Key(blackCount*20-7.5, 0, -i); blackCount+=1; break;
				case 5: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0); whiteCount++;break;
				case 6: tile = new Key(blackCount*20-7.5, 0, -i); blackCount+=2; break;
				
				case 7: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0); whiteCount++; break;
				case 8: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0); whiteCount++; break;
				
				case 9: tile = new Key(blackCount*20-7.5, 0, -i); blackCount++; break;
				case 10: tile = new Key(whiteCount*20, 40, i); tile.setViewOrder(1.0); whiteCount++; break;
				case 11: tile = new Key(blackCount*20-7.5, 0, -i); blackCount++; break;
			}
			
			keys.add(tile);
			panePiano.getChildren().add(tile);
		}
	}
	
	public void radioBtnSelect(ActionEvent e) {
		if(radioBtnMajor.isSelected()) {
			this.playingType = -1;
			System.out.println(this.playingType + ": Major chords");
		}
		if(radioBtnMinor.isSelected()) {
			this.playingType = 0;
			System.out.println(this.playingType + ": Minor chords");
		}
		if(radioBtnNotes.isSelected()) {
			this.playingType = 1;
			System.out.println(this.playingType + ": Individual notes");
		}
		
	}
	
	private class Key extends StackPane {
		private double x;
		private int y;
		private int id;
		private Rectangle border;
		
		public Key(double x, int y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
			
			if(id >= 0) {
				border = new Rectangle(KEY_WIDTH_WHITE, KEY_HEIGHT);
				border.setFill(Color.WHITE);
			} else {
				border = new Rectangle(KEY_WIDTH_BLACK, KEY_HEIGHT);
				border.setFill(Color.BLACK);
			}
			borderKeys.add(border);
			
			border.setStroke(Color.BLACK);
			border.setStrokeType(StrokeType.INSIDE);
			border.setArcHeight(5);
			border.setArcWidth(5);
			
			setLayoutX(x);
			setLayoutY(y);
			
			getChildren().addAll(border);
			
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					System.out.println("key: " + id);
					lblPressedKey.setText("Insert key/chord: " + musicApp.translateIdToKey(Math.abs(id), playingType));
					System.out.println(musicApp.translateIdToKey(Math.abs(id), playingType));
					musicApp.getAudioPlayer().playKey(Math.abs(id), playingType);
				}
			});
			
			this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					paintSelectedKeys(Color.ORANGE, Math.abs(id) >= 81);
				}

			});
			
			this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					for(Key key : keys) {
						if(key.id >= 0) {
							key.border.setFill(Color.WHITE);
						} else {
							key.border.setFill(Color.BLACK);
						}
					}
				}
			});
		}
		
		private void paintSelectedKeys(Color color, boolean outOfBounds) {
			if(playingType == -1) {
				if(!outOfBounds) {
					borderKeys.get(Math.abs(id)).setFill(color);
					borderKeys.get(Math.abs(id)+4).setFill(color);
					borderKeys.get(Math.abs(id)+7).setFill(color);
				} else {
					border.setFill(Color.ORANGE);
				}
			} else if(playingType == 0) {
				if(!outOfBounds) {
					borderKeys.get(Math.abs(id)).setFill(color);
					borderKeys.get(Math.abs(id)+3).setFill(color);
					borderKeys.get(Math.abs(id)+7).setFill(color);
				} else {
					border.setFill(Color.ORANGE);
				}
			} else {
				border.setFill(Color.ORANGE);
			}
		}
	}
}
