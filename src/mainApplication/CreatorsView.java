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
		int count = 0;
		
		User temp;
		for(int i = 0; i < users.size(); i++) {
			for(int j = i+1; j < users.size();j++) {
				if(users.get(i).getUserNumberId() < users.get(j).getUserNumberId()) {
					temp = users.get(i);
					users.set(i, users.get(j));
					users.set(j, temp);
					
				}
			}
		}

		for(User u : users) {
			CreatorsViewUser creatorsViewUser = new CreatorsViewUser(u,count);
			HBox creatorsViewUserVisual = creatorsViewUser.getCreatorsViewUserVisual();
			count++;
			creatorsView.getChildren().add(creatorsViewUserVisual);
		}
			
		
		
		
	}

	private class CreatorsViewUser extends HBox {
		private HBox creatorsViewUser = new HBox();
		private User user;
		private int count;
		
		public CreatorsViewUser(User user, int count) {
			this.user = user;
			this.count = count;
			creatorsViewUser.setPrefHeight(21);
			creatorsViewUser.setPrefWidth(177);
			
			Circle c = new Circle();
			c.setFill(Color.web(MusicApp.getInstance().getUserColors()[count]));
			c.setStroke(Color.web(MusicApp.getInstance().getUserColors()[count]));
			c.setStrokeType(StrokeType.INSIDE);
			c.setRadius(8);
			
			Label l = new Label(user.getUserID());
			l.setStyle("-fx-padding: 0 0 0 10;");
			
			creatorsViewUser.getChildren().addAll(c,l);
		}
		
		public HBox getCreatorsViewUserVisual() {
			return this.creatorsViewUser;
		}
	}
}
