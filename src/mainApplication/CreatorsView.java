package mainApplication;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CreatorsView {
	private VBox creatorsView;
	
	public CreatorsView(VBox creatorsView) {
		this.creatorsView = creatorsView;
		
		ArrayList<User> users = MusicApp.getInstance().getCurrentTrack().getTrackUsers();
		
		Label title = new Label("Track: " + MusicApp.getInstance().getCurrentTrack().getTrackName());
		title.setStyle("-fx-font: 15 Calibri; -fx-padding: 0 0 10 0;");
			
		creatorsView.getChildren().add(title);
		
		for(User u : users) {
			CreatorsViewUser creatorsViewUser = new CreatorsViewUser(u);
			HBox creatorsViewUserVisual = creatorsViewUser.getCreatorsViewUserVisual();
			
			creatorsView.getChildren().add(creatorsViewUserVisual);
		}
		
		// TEMPORARY BUTTON FOR TESTING
		Button b = new Button("SWITCH USER");
		
		b.setOnAction( e -> {
			System.out.println("test");// TESTING WITH ANOTHER USER
			
			MusicApp.getInstance().testSwitchUser("Max");
		});
		creatorsView.getChildren().add(b);
	}
	
	private class CreatorsViewUser extends HBox {
		private HBox creatorsViewUser = new HBox();
		private User user;
		
		public CreatorsViewUser(User user) {
			this.user = user;
			creatorsViewUser.setPrefHeight(21);
			creatorsViewUser.setPrefWidth(177);
			
			Circle c = new Circle();
			int colorIndex = MusicApp.getInstance().getCurrentTrack().getTrackUsers().indexOf(user);
			c.setFill(Color.web(MusicApp.getInstance().getUserColors()[colorIndex]));
			c.setStroke(Color.web(MusicApp.getInstance().getUserColors()[colorIndex]));
			c.setStrokeType(StrokeType.INSIDE);
			c.setRadius(8);
			
			Label l = new Label(user.getName());
			l.setStyle("-fx-padding: 0 0 0 10;");
			
			creatorsViewUser.getChildren().addAll(c,l);
		}
		
		public HBox getCreatorsViewUserVisual() {
			return this.creatorsViewUser;
		}
	}
}
