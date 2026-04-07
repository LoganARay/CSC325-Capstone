package edu.farmingdale.csc325capstone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class loginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;


    @FXML
    private Button loginButton;
    @FXML
    private Button goToRegisterButton;
    @FXML
    private Button goToHome;


    @FXML
    private void handleLogin(ActionEvent event) {
        signInLogic();
    }

    @FXML
    private void handleGoToRegister(ActionEvent event) throws IOException {
        HelloApplication.setRoot("registrationView.fxml");
    }

    @FXML
    private void handleToHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }


    public void signInLogic(){
        String email = emailField.getText();
        String password = passwordField.getText();

        //TODO finish logic



    }
}