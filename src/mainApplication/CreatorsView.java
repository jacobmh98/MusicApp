package mainApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CreatorsView {
	private VBox creatorsView;
	
	public CreatorsView() {
	}
	
	public void setView(VBox creatorsView) {
		this.creatorsView = creatorsView;
	}
	
	public void updateView() {
		this.creatorsView.getChildren().removeAll(creatorsView.getChildren());
		
		Label title = new Label("Track: " + "");
		title.setStyle("-fx-font: 15 Calibri; -fx-padding: 0 0 10 0;");
		creatorsView.getChildren().add(title);
		
		ArrayList<User> users = MusicApp.getInstance().getTrackUsers();
		ArrayList<User> usersSorted = MusicApp.getInstance().getTrackUsers();
		ArrayList<Integer> usersIDs = new ArrayList<Integer>();
		for(User u : users) {
			usersIDs.add(u.getUserNumberId());
		}
		
		Collections.sort(usersIDs);
		
		for(Integer userID : usersIDs) {
			CreatorsViewUser creatorsViewUser = new CreatorsViewUser(userID);
			HBox creatorsViewUserVisual = creatorsViewUser.getCreatorsViewUserVisual();
			
			creatorsView.getChildren().add(creatorsViewUserVisual);
		}
			
		
		
		
	}

	private class CreatorsViewUser extends HBox {
		private HBox creatorsViewUser = new HBox();
		private int user;
		
		public CreatorsViewUser(int user) {
			this.user = user;
			creatorsViewUser.setPrefHeight(21);
			creatorsViewUser.setPrefWidth(177);
			
			Circle c = new Circle();
			//int colorIndex = MusicApp.getInstance().getTrackUsers().indexOf(user);
			//c.setFill(Color.web(MusicApp.getInstance().getUserColors()[colorIndex]));
			//c.setStroke(Color.web(MusicApp.getInstance().getUserColors()[colorIndex]));
			c.setFill(Color.BLUE);
			c.setStroke(Color.BLUE);
			c.setStrokeType(StrokeType.INSIDE);
			c.setRadius(8);
			
			Label l = new Label(user+""/*.getUserID()*/);
			l.setStyle("-fx-padding: 0 0 0 10;");
			
			creatorsViewUser.getChildren().addAll(c,l);
		}
		
		public HBox getCreatorsViewUserVisual() {
			return this.creatorsViewUser;
		}
	}
}
