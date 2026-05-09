package edu.farmingdale.csc325capstone.ViewModel;

import com.google.cloud.firestore.DocumentReference;
import edu.farmingdale.csc325capstone.HelloApplication;
import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.*;

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
                    if(passwordCheck(passwordField.getText())){
                        for(Map<String, Object> user: users.values()){
                            if((user.get("email") + "").equals(emailField.getText())){
                                System.out.println("Email in use!!!");
                                inUse=false;
                            }
                        }
                        if(inUse) {
                            DocumentReference docUsers = HelloApplication.fstore.collection("Users").document(emailField.getText());
                            Map<String, Object> user = new HashMap<String, Object>();
                            List<Map<String, Object>> builds= new ArrayList<Map<String, Object>>();
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
                    else{
                        System.out.println("Your password SUCKS!");
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

    public boolean passwordCheck(String password){
        char[] numbers = {'1', '1', '3', '4', '5', '6', '7', '8', '9', '0'};
        char[] specialCharacters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '=', '+', ']', '}', '[', '{', '|', ';', ':', '.', '>', ',', '<', '~', '`'};
        if(password.length()>7){
            boolean pass=false;
            for (char number : numbers) {
                if (password.contains(number + "")) {
                    pass = true;
                    break;
                }
            }
            if(pass){
                pass=false;
                for (char specialCharacter : specialCharacters) {
                    if (password.contains(specialCharacter + "")) {
                        pass = true;
                        break;
                    }
                }
                return pass;
            }
        }
        return false;
    }
}
