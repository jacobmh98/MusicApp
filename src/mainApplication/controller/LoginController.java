package mainApplication.controller;

import java.net.URL;
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

public class LoginController implements Initializable {
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
		
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Create Music");
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			root = loader.load(getClass().getResource("../view/MainView.fxml").openStream());
			Scene scene = new Scene(root, 1400, 800);
			scene.getStylesheets().addAll(this.getClass().getResource("../resources/app.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {}
	}

}
