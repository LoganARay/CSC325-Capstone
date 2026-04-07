package edu.farmingdale.csc325capstone;

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
    private void handleFindGames(ActionEvent event) throws IOException{
        HelloApplication.setRoot("findGames.fxml");
    }

    @FXML
    private void handleQuestionnaire(ActionEvent event) throws IOException{
        HelloApplication.setRoot("buildQuestionnaireView.fxml");
    }

    @FXML
    private void handleSandbox(ActionEvent event) throws IOException{
        HelloApplication.setRoot("sandboxView.fxml");
    }


    @FXML
    private void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    private void handleSavedBuild(ActionEvent event) throws IOException {
        HelloApplication.setRoot("savedBuildsView.fxml");
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }
}
