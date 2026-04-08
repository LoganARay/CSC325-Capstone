package edu.farmingdale.csc325capstone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField emailField;

    @FXML
    private Button goToHome;

    @FXML
    private Button goToLoginButton;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleCreate(ActionEvent event) {
        registrationLogic();
    }

    @FXML
    private void handleToLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }

    @FXML
    private void handleToHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }


    public void registrationLogic(){
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        //TODO finish logic




    }
}
