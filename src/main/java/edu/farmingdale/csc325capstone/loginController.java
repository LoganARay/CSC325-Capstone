package edu.farmingdale.csc325capstone;
import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void initialize() throws Exception {
        SandBoxParts s= new SandBoxParts();
        loginButton.setOnAction(e->{
            SandBoxParts r= new SandBoxParts();
            Map<String, Map<String, Object>> users;
            try {
                users = r.loadRepo("Users");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            for(Map<String, Object> user: users.values()){
                if((user.get("email") + "").equals(emailField.getText()) && (user.get("password") + "").equals(passwordField.getText())){
                    System.out.println("Email in use!!!");
                    HelloApplication.setCurrentUser(user.get("name") + "", user.get("email") + "", user.get("password") + "", (List<Map<String, Object>>)user.get("builds"));
                    try {
                        HelloApplication.setRoot("homeView.fxml");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
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