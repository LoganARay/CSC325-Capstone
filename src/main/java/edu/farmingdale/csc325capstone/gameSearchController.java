package edu.farmingdale.csc325capstone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gameSearchController {

@FXML
    private Button buildButton;

    @FXML
    private Label gameStudioLabel;

    @FXML
    private ListView<?> gamesListView;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label minCpuLabel;

    @FXML
    private Label minGpuLabel;

    @FXML
    private Label minRamLabel;

    @FXML
    private Label minStorageLabel;

    @FXML
    private Label recCpuLabel;

    @FXML
    private Label recGpuLabel;

    @FXML
    private Label recRamLabel;

    @FXML
    private Label recStorageLabel;

    @FXML
    private Button savedButton;

    @FXML
    private TextField searchField;


    @FXML
    private void buildButtonHandle() {
        // TODO: Navigate to the builder with the selected game.


    }
    
    @FXML
    void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }

    @FXML
    void handleSavedBuild(ActionEvent event) throws IOException {
        HelloApplication.setRoot("savedBuilds.fxml");
    }
   
    
}
