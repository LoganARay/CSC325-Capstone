package edu.farmingdale.csc325capstone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.io.IOException;

public class SandboxViewController {
    @FXML
    private ComboBox<?> caseCombo;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> cpuCombo;

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
    private Button saveButton;

    @FXML
    private ComboBox<?> storageCombo;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Label totalCostNumber;

    @FXML
    private Label totalWLabel;

    @FXML
    private Label totalWattage;

    @FXML
    private Button viewSavedButton;



    @FXML
    void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }

    @FXML
    void handleViewSavedBuild(ActionEvent event) throws IOException {
        HelloApplication.setRoot("savedBuildsView.fxml");
    }

    @FXML
    private void clearBuildHandle(ActionEvent event) throws IOException {
        clearBuildLogic();
    }

    @FXML
    private void saveBuildHandle(ActionEvent event) throws IOException {
        saveBuildLogic();
    }



    public void clearBuildLogic() {
        //TODO




    }

    public void saveBuildLogic() {
        //TODO



    }

}

