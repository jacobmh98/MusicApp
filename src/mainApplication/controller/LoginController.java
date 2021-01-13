package mainApplication.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApplication.MusicApp;
import mainApplication.model.Client;
import mainApplication.model.Server;

public class LoginController implements Initializable {
	
	String name;
	String id;
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtId;
	
	private MusicApp instance;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = MusicApp.getInstance(); 
		
	}
	
	public void StartCreating(ActionEvent event ) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		name = txtName.getText();
		id = txtId.getText();
		Thread server = new Thread(new Server(id,instance));
		//add new user
		ArrayList<Integer> userIDs = instance.getUserIDList();
		if(userIDs.size() == 0) {
			userIDs.add(0);
			instance.setUserIDList(userIDs);
			Client client= new Client(0,name,id);
		} else { 
			int current = userIDs.get(userIDs.size()-1);
			userIDs.add(current+1);
			instance.setUserIDList(userIDs);
			Client client = new Client(current + 1,name,id);
		}
		
		
		server.start();
		//client.start();
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Create Music");
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			root = loader.load(getClass().getResource("../view/MainView3.fxml").openStream());
			Scene scene = new Scene(root, 1400, 800);
			scene.getStylesheets().addAll(this.getClass().getResource("../resources/app.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {}
	}

}
