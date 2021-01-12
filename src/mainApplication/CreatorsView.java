package mainApplication;

import java.util.ArrayList;

import javafx.geometry.Insets;
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
		
		Label title = new Label("Track: " + MusicApp.getInstance().getCurrentTrack().getTrackID());
		title.setStyle("-fx-font: 15 Calibri; -fx-padding: 0 0 10 0;");
			
		creatorsView.getChildren().add(title);
		
		for(User u : users) {
			CreatorsViewUser creatorsViewUser = new CreatorsViewUser(u.getName());
			HBox creatorsViewUserVisual = creatorsViewUser.getCreatorsViewUserVisual();
			
			creatorsView.getChildren().add(creatorsViewUserVisual);
		}
	}
	
	private class CreatorsViewUser extends HBox {
		private HBox creatorsViewUser = new HBox();
		private String user;
		
		public CreatorsViewUser(String user) {
			this.user = user;
			creatorsViewUser.setPrefHeight(21);
			creatorsViewUser.setPrefWidth(177);
			
			Circle c = new Circle();
			c.setFill(Color.web("#eb5934"));
			c.setStroke(Color.web("eb5934"));
			c.setStrokeType(StrokeType.INSIDE);
			c.setRadius(8);
			
			Label l = new Label(user);
			l.setStyle("-fx-padding: 0 0 0 10;");
			
			creatorsViewUser.getChildren().addAll(c,l);
		}
		
		public HBox getCreatorsViewUserVisual() {
			return this.creatorsViewUser;
		}
	}
}
