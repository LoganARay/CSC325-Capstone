package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import edu.farmingdale.csc325capstone.service.AppState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;



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
    private Button settingsButton;

    private SandBoxParts s= new SandBoxParts();

    @FXML
    public void initialize() throws Exception {
        s.fillingOrder();
    }
    @FXML
    private void handleFindGames(ActionEvent event) throws Exception {
        s.fillingOrder();
        HelloApplication.setRoot("gameSearch.fxml");
    }

    @FXML
    private void handleQuestionnaire(ActionEvent event) throws Exception {
        s.fillingOrder();
        HelloApplication.setRoot("buildQuestionnaireView.fxml");
    }

    @FXML
    private void handleSandbox(ActionEvent event) throws Exception {
        if(HelloApplication.storage==null){
            for(int i=0; i<3; i++){ s.fillingOrder();}
        }
        HelloApplication.setRoot("sandboxView.fxml");
    }


    @FXML
    private void handleHome(ActionEvent event) throws Exception {
        s.fillingOrder();
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    private void handleSavedBuild(ActionEvent event) throws Exception {
        s.fillingOrder();
        HelloApplication.setRoot("savedBuilds.fxml");
    }

    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        s.fillingOrder();
        HelloApplication.setRoot("loginView.fxml");
    }

}
