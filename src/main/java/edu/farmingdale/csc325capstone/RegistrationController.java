package edu.farmingdale.csc325capstone;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public void initialize() throws RuntimeException{
        createAccountButton.setOnAction(e->{
            boolean inUse=true;
            SandBoxParts r= new SandBoxParts();
            Map<String, Map<String, Object>> users;
            try {
                users = r.loadRepo("Users");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if(nameField.getText()!=null && passwordField.getText()!=null && emailField.getText()!=null){
                if(emailField.getText().contains("@gmail.com") && nameField.getText().length()>4){
                    for(Map<String, Object> user: users.values()){
                        if((user.get("email") + "").equals(emailField.getText())){
                            System.out.println("Email in use!!!");
                            inUse=false;
                        }
                    }
                    if(inUse) {
                        DocumentReference docUsers = HelloApplication.fstore.collection("Users").document(emailField.getText());
                        HashMap<String, Object> user = new HashMap<String, Object>();
                        HashMap<String, Object> builds= new HashMap<String, Object>();
                        user.put("name", nameField.getText());
                        user.put("email", emailField.getText());
                        user.put("password", passwordField.getText());
                        user.put("builds", builds);
                        docUsers.set(user);
                        HelloApplication.setCurrentUser(user.get("name") + "", user.get("email") + "", user.get("password") + "", builds);
                        try {
                            HelloApplication.setRoot("homeView.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }
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
