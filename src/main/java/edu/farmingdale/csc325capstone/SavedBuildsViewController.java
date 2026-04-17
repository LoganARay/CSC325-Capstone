package edu.farmingdale.csc325capstone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.io.IOException;


public class SavedBuildsViewController {

    @FXML
    private Button buildButton;

    @FXML
    private ComboBox<?> caseCombo;

    @FXML
    private ComboBox<?> cpuCombo;

    @FXML
    private ListView<?> gamesListView;

    @FXML
    private ComboBox<?> gpuCombo;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<?> motherboardCombo;

    @FXML
    private ComboBox<?> psuCombo;

    @FXML
    private ComboBox<?> ramCombo;

    @FXML
    private ComboBox<?> storageCombo;

    @FXML
    void buildButtonHandle(ActionEvent event) throws IOException {
        HelloApplication.setRoot("sandboxView.fxml");
    }

    @FXML
    void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }

}
