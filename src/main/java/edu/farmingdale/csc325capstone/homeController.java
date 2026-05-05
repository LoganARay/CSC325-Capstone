package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;



public class homeController {
    @FXML
    private Button homeButton;
    @FXML
    private Button savedButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button findGames;
    @FXML
    private Button takeQuestionnaire;
    @FXML
    private Button buildSandbox;

    @FXML
    private void handleFindGames(ActionEvent event) throws Exception {
        HelloApplication.setRoot("gameSearch.fxml");
    }

    @FXML
    private void handleQuestionnaire(ActionEvent event) throws Exception {
        HelloApplication.setRoot("buildQuestionnaireView.fxml");
    }

    @FXML
    private void handleSandbox(ActionEvent event) throws Exception {
        HelloApplication.setRoot("sandboxView.fxml");
    }


    @FXML
    private void handleHome(ActionEvent event) throws Exception {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    private void handleSavedBuild(ActionEvent event) throws Exception {
        HelloApplication.setRoot("savedBuilds.fxml");
    }

    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        HelloApplication.setRoot("loginView.fxml");
    }
}
