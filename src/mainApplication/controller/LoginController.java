package mainApplication.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainApplication.MusicApp;
import mainApplication.model.Client;
import mainApplication.model.Server;

public class LoginController implements Initializable {
	private MusicApp instance;
	
	private String userID;
	private int trackID;
	
	private HBox hbox = new HBox();
	private Button btnLogin = new Button();
	private TextField txtFieldName;
	private TextField txtTrackID;
	
	@FXML
	VBox vboxLogin;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = MusicApp.getInstance(); 
		initializeCreateTrack();
		
		btnLogin.setOnAction(e -> {
			try {
				startCreating(e);
			} catch (Exception e1) {}
		});
		

	}
	
	// Method for initializing create track login
	public void initializeCreateTrack() {
		vboxLogin.getChildren().clear();

		btnLogin.setText("Create Track");;
		VBox vb1 = new VBox();
		Label lblName = new Label("Enter UserID");
		txtFieldName = new TextField();
		txtFieldName.setPromptText("Bob123");
		VBox.setMargin(vb1, new Insets(10,0,10,0));
		vb1.getChildren().addAll(lblName,txtFieldName);
		VBox vb2 = new VBox();
		Label lblTrackID = new Label("Enter Track ID");
		txtTrackID = new TextField();
		txtTrackID.setPromptText("1234");
		VBox.setMargin(vb1, new Insets(10,0,10,0));
		vb2.getChildren().addAll(lblTrackID, txtTrackID);
		lblName.setStyle("-fx-font: 20 Calibri; -fx-text-fill: #f0f0f0;");
		lblTrackID.setStyle("-fx-font: 20 Calibri; -fx-text-fill: #f0f0f0;");
		VBox.setMargin(vb2, new Insets(10,0,10,0));
		txtFieldName.getStyleClass().add("textFieldLogin");
		vboxLogin.getChildren().addAll(vb1, vb2, hbox, btnLogin);
	}
	
	public void startCreating(ActionEvent event ) throws Exception {
		setLoginFieds();
		((Node) event.getSource()).getScene().getWindow().hide();
		MusicApp.getInstance().login(this.userID, this.trackID);
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Create Music");
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("../view/MainView4.fxml").openStream());
			Scene scene = new Scene(root, 1300, 730);
			scene.getStylesheets().addAll(this.getClass().getResource("../resources/app.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {}
		
	}
	
	// Method for setting loginFields
	public void setLoginFieds() {
		// NEEDS BASIC CHECKS TO SEE IF INPUT IS VALID
		this.userID = txtFieldName.getText();
		this.trackID = Integer.parseInt(txtTrackID.getText());
	}
}
