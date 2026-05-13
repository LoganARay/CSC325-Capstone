package edu.farmingdale.csc325capstone.ViewModel;

import edu.farmingdale.csc325capstone.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


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
    public void initialize() {
        if(HelloApplication.user!=null){
            loginButton.setText("Hi, " + HelloApplication.user.getName());
            loginButton.setOnMouseEntered( e->{
                loginButton.setText("Change accounts?");
            });
            loginButton.setOnMouseExited( e->{
                loginButton.setText("Hi, " + HelloApplication.user.getName());
            });
        }
    }

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
