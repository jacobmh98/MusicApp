package mainApplication.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import mainApplication.CreatorsView;
import mainApplication.MusicApp;
import mainApplication.Track;
import mainApplication.Track.TimeBlock;

public class MainController implements Initializable {
	private MusicApp musicApp = MusicApp.getInstance();
	Track track;
	private CreatorsView creatorsView;
	
	//Space trackSpace;
	
	private static final int KEY_HEIGHT = 90;
	private static final int KEY_WIDTH_BLACK = 15;
	private static final int KEY_WIDTH_WHITE = 20;
	private static final int TOTAL_KEYS = 88;
	
	private ArrayList<Rectangle> borderKeys = new ArrayList<Rectangle>();;
	private ArrayList<Key> keys = new ArrayList<Key>();
	
	private int playingType = -1; // Default playing Major chord. 0 => Minor, 1 => Individual Notes
	
	private String pressedKey = null;
	private int pressedKeyID;
	
	private int blockID = 0;
	
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
	private ChoiceBox<String> choiceBoxPosition;
	
	@FXML
	private Label lblPressedKey;
	
	@FXML
	private VBox vBoxInsert;
	
	@FXML
	private Text lblErrorMsg;
	
	@FXML
	private VBox vboxCreatorsView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		track = MusicApp.getInstance().getCurrentTrack();
		track.setPane(hBoxTrack);
		
		
		track.initializeTrack();
		
		
		
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
		
		// Initializing choiceBoxPosition
		updatePositionChoice();
		choiceBoxPosition.setValue("0 ms");
		
		// Listening for changes in choiceBoxPosition
		choiceBoxPosition.getSelectionModel().selectedIndexProperty().addListener(
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
	            blockID = (int) new_val;
			});
		
		
		// Setting up creators view aka. who's currently working on the track
		creatorsView = MusicApp.getInstance().getCreatorsView();
		creatorsView.setView(vboxCreatorsView);

		MusicApp.getInstance().updateViews();
	}
	
	// Method that runs when Insert Column is clicked
	public void btnInsertColumn() {
		track.addTimeBlock();
		updatePositionChoice();
	}
	
	public void btnDeleteColumn() {
		track.deleteTimeBlock();
		updatePositionChoice();
	}
	
	// Method that runs when refresh is clicked
	public void onActionRefresh() {
		lblErrorMsg.setText("");
		MusicApp.getInstance().removeUpdateLock(MusicApp.getInstance().getCurrentUser().getUserNumberId());
		MusicApp.getInstance().updateViews();
	}
	
	// Method that runs when play button is clicked
	public void btnPlaySong() {
		MusicApp.getAudioPlayer().playSong(track);
	}
	
	// Method that runs when Insert button is clicked
	public void btnInsertSound() {
		if(pressedKey == null) {
			lblErrorMsg.setText("Please enter key on the piano");
		} else {
			lblErrorMsg.setText("");
			//System.out.println("blockID:" + blockID + "     playingType:" + playingType + "      pressedKey:" + pressedKey + "     pressedKeyID" + pressedKeyID);
			boolean contains = musicApp.sendKeyToServer(pressedKeyID, playingType,blockID,MusicApp.getInstance().getCurrentUser().getUserNumberId());
			if(contains) {
				lblErrorMsg.setText("The track contains work you don't currently have. Please refresh before inserting sound.");
			} else {
				//musicApp.getKeysFromServer();
				musicApp.updateViews();
			}
			
		}
		
	}
	
//	public void updateSounds(int pressedKeyID,int playingType, int blockID) {
//		String key = musicApp.translateIdToKey(Math.abs(pressedKeyID), playingType);
//		if(key == null) {
//			lblErrorMsg.setText("Please enter key on the piano");
//		} else {
//			lblErrorMsg.setText("");
//			track.getTimeBlock(blockID).addSoundBlock(playingType, key ,Math.abs(pressedKeyID));
//			//System.out.println("blockID:" + blockID + "     playingType:" + playingType + "      pressedKey:" + pressedKey + "     pressedKeyID" + pressedKeyID);
//		}
//		
//	}
	
	public void updatePositionChoice() {
		choiceBoxPosition.getItems().clear();
		choiceBoxPosition.setValue("0 ms");
		blockID = 0;
		for(TimeBlock t : track.getTimeBlocks()) {
			choiceBoxPosition.getItems().add(t.getBlockID()*500 + " ms");
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
					
					pressedKey =  musicApp.translateIdToKey(Math.abs(id), playingType);
					pressedKeyID = id;
					lblPressedKey.setText("Insert key/chord: " + pressedKey);
					
					System.out.println(musicApp.translateIdToKey(Math.abs(id), playingType));
					MusicApp.getAudioPlayer().playKey(Math.abs(id), playingType);
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
