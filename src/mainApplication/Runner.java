package mainApplication;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainApplication.model.Server;

public class Runner extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		// Get instance of MusicApp
		MusicApp instance = MusicApp.getInstance();
		
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Login");
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			root = loader.load(getClass().getResource("view/LoginView.fxml").openStream());
			Scene scene = new Scene(root, 550, 500);
			scene.getStylesheets().addAll(this.getClass().getResource("resources/app.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {}
	}
		
		
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
