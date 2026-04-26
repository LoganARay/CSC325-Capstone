package edu.farmingdale.csc325capstone;

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


    @FXML
    private void handleFindGames(ActionEvent event) throws IOException{
        HelloApplication.setRoot("gameSearch.fxml");
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
        HelloApplication.setRoot("savedBuilds.fxml");
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }
    @FXML
    private void handleOpenSettings() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.setHeaderText("Enter OpenAI API Key");

        // Create input field (hidden like password)
        PasswordField apiKeyInput = new PasswordField();
        apiKeyInput.setPromptText("Paste API key here");

        // Layout
        VBox content = new VBox(10);
        content.getChildren().add(apiKeyInput);
        dialog.getDialogPane().setContent(content);

        // Buttons
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        // When Save is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return apiKeyInput.getText();
            }
            return null;
        });

        // Show dialog
        dialog.showAndWait().ifPresent(key -> {
            if (key != null && !key.isBlank()) {
                AppState.openAiApiKey = key;
                System.out.println("API key set for session.");
            } else {
                System.out.println("No key entered.");
            }
        });
    }
}
