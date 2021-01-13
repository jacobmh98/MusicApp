package mainApplication.controller;

import java.net.URL;
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
import mainApplication.model.Server;

public class LoginController implements Initializable {
	private MusicApp instance;
	
	private String userName;
	private int trackID;
	private String trackName;
	
	private HBox hbox = new HBox();
	private Button btnLogin = new Button();
	private TextField txtFieldName;
	private TextField txtTrackID;
	private TextField txtTrackName;
	
	private int selection = 0;
	
	@FXML
	VBox vboxLogin;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = MusicApp.getInstance(); 
		initializeCreateTrack();
		
		ToggleGroup group = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Create Track");
		RadioButton rb2 = new RadioButton("Join Track");
		
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		rb2.setToggleGroup(group);
		rb1.setStyle("-fx-padding: 0 0 0 20; -fx-text-fill: white;");
		rb2.setStyle("-fx-padding: 0 0 0 20; -fx-text-fill: white;");
		hbox.getChildren().addAll(rb1, rb2);
		hbox.setStyle("-fx-padding: 10 0 10 0;");
		
		btnLogin.setOnAction(e -> {
			try {
				startCreating(e);
			} catch (Exception e1) {}
		});
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				RadioButton rb = (RadioButton) group.getSelectedToggle();
				if(rb != null) {
					if(rb.getText().equals("Create Track")) {
						initializeCreateTrack();
						
						selection = 0;
					} else {
						initializeJoinTrack();
						selection = 1;
					}
				}
			}
		});

	}
	
	// Method for initializing create track login
	public void initializeCreateTrack() {
		vboxLogin.getChildren().clear();

		btnLogin.setText("Create Track");;
		VBox vb1 = new VBox();
		Label lblName = new Label("Enter Username");
		txtFieldName = new TextField();
		txtFieldName.setPromptText("Bob");
		VBox.setMargin(vb1, new Insets(10,0,10,0));
		vb1.getChildren().addAll(lblName,txtFieldName);
		VBox vb2 = new VBox();
		Label lblTrackID = new Label("Enter Track ID");
		txtTrackID = new TextField();
		txtTrackID.setPromptText("1234");
		VBox.setMargin(vb1, new Insets(10,0,10,0));
		vb2.getChildren().addAll(lblTrackID, txtTrackID);
		VBox vb3 = new VBox();
		Label lblTrackName = new Label("Enter Track Name");
		txtTrackName = new TextField();
		txtTrackName.setPromptText("Awesome Track");
		VBox.setMargin(vb3, new Insets(10,0,10,0));
		vb3.getChildren().addAll(lblTrackName, txtTrackName);
		lblName.setStyle("-fx-font: 20 Calibri; -fx-text-fill: #f0f0f0;");
		lblTrackID.setStyle("-fx-font: 20 Calibri; -fx-text-fill: #f0f0f0;");
		lblTrackName.setStyle("-fx-font: 20 Calibri; -fx-text-fill: #f0f0f0;");
		txtFieldName.getStyleClass().add("textFieldLogin");
		vboxLogin.getChildren().addAll(vb1, vb2, vb3, hbox, btnLogin);
	}
	
	// Method for initializing join track login
	public void initializeJoinTrack() {
		vboxLogin.getChildren().clear();
		
		btnLogin.setText("Join Track");
		VBox vb1 = new VBox();
		Label lblName = new Label("Enter Username");
		txtFieldName = new TextField();
		txtFieldName.setPromptText("Bob");
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
		txtFieldName.getStyleClass().add("textFieldLogin");
		vboxLogin.getChildren().addAll(vb1, vb2, hbox, btnLogin);
	}
	
	public void startCreating(ActionEvent event ) throws Exception {
		/************************************************************************/
		// SETTING UP THE SERVER
		//Thread server = new Thread(new Server(id));
		//server.start();
		
		//SIMULATE LOGIN FOR CREATING TRACK
		if(this.selection == 0) {
			setLoginFieds();
			((Node) event.getSource()).getScene().getWindow().hide();
			MusicApp.getInstance().login(this.userName, this.trackID, this.trackName);
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
		} else {
			System.out.println("NOT IMPLEMENTED YET");
		}
	}
	
	// Method for setting loginFields
	public void setLoginFieds() {
		// NEEDS BASIC CHECKS TO SEE IF INPUT IS VALID
		this.userName = txtFieldName.getText();
		this.trackID = Integer.parseInt(txtTrackID.getText());
		this.trackName = txtTrackName.getText();
	}
}
