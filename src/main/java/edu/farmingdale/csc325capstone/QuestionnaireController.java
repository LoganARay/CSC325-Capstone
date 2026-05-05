package edu.farmingdale.csc325capstone;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.Service;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import edu.farmingdale.csc325capstone.model.PreBuilt;
import edu.farmingdale.csc325capstone.service.PreBuiltService;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class QuestionnaireController {
    private String selectedBudget;
    private int currentQuestion = 1;
    private String selectedPlayStyle;
    private String selectedPerformance;
    private String selectedStorage;
    private String selectedPriority;
    private String selectedLongevity;

    @FXML
    private Button budgetBtn1;

    @FXML
    private Button budgetBtn2;

    @FXML
    private Button budgetBtn3;

    @FXML
    private Button budgetBtn4;

    @FXML
    private Button nextButton;

    @FXML
    private Label questionCounter;

    @FXML
    private Label questionText;

    @FXML
    private Label questionHint;

    @FXML
    private VBox budgetOptions;

    @FXML
    private VBox playStyleOptions;

    @FXML
    private Button playBtn1;

    @FXML
    private Button playBtn2;

    @FXML
    private Button playBtn3;

    @FXML
    private Button playBtn4;

    @FXML
    private VBox performanceOptions;
    @FXML
    private Button perfBtn1;
    @FXML
    private Button perfBtn2;
    @FXML
    private Button perfBtn3;
    @FXML
    private Button perfBtn4;

    @FXML
    private VBox storageOptions;
    @FXML
    private Button storageBtn1;
    @FXML
    private Button storageBtn2;
    @FXML
    private Button storageBtn3;
    @FXML
    private Button storageBtn4;

    @FXML
    private VBox priorityOptions;
    @FXML
    private Button priorityBtn1;
    @FXML
    private Button priorityBtn2;
    @FXML
    private Button priorityBtn3;
    @FXML
    private Button priorityBtn4;

    @FXML
    private VBox longevityOptions;
    @FXML
    private Button longevityBtn1;
    @FXML
    private Button longevityBtn2;
    @FXML
    private Button longevityBtn3;
    @FXML
    private Button longevityBtn4;
    @FXML
    private VBox gameSearch;
    @FXML
    private TextField searchField;
    @FXML
    private VBox gameList;
    @FXML
    private Label gameArea;
    @FXML
    private VBox mainContainer;
    @FXML
    private ImageView pcImageView;
    @FXML
    private HBox imageContainer;

    @FXML
    private VBox rightPanel;

    private List<Map<String, Object>> games=null;

    private MenuItem item1=new MenuItem(" ");
    private MenuItem item2=new MenuItem(" ");
    private MenuItem item3=new MenuItem(" ");
    private MenuItem item4=new MenuItem(" ");
    private MenuItem item5=new MenuItem(" ");


    private ContextMenu cm=new ContextMenu();

    private FirestoreContent contxtFirebase = new FirestoreContent();
    private Firestore fstore = contxtFirebase.firebase();

    @FXML
    public void initialize(){
        budgetBtn1.setText("Under $500");
        budgetBtn2.setText("$500 - $1,000");
        budgetBtn3.setText("$1,000 - $1,500");
        budgetBtn4.setText("$1,500+");

        budgetBtn1.setOnAction( e -> selectBudget(budgetBtn1, "under_500"));
        budgetBtn2.setOnAction(e -> selectBudget(budgetBtn2, "$500 - $1,000") );
        budgetBtn3.setOnAction(e -> selectBudget(budgetBtn3, "1000_1500"));
        budgetBtn4.setOnAction(e -> selectBudget(budgetBtn4, "1500_plus"));

        item1.setOnAction(e -> gameArea.setText(gameArea.getText() + "\n" + item1.getText()));
        item2.setOnAction(e -> gameArea.setText(gameArea.getText() + "\n" + item2.getText()));
        item3.setOnAction(e -> gameArea.setText(gameArea.getText() + "\n" + item3.getText()));
        item4.setOnAction(e -> gameArea.setText(gameArea.getText() + "\n" + item4.getText()));
        item5.setOnAction(e -> gameArea.setText(gameArea.getText() + "\n" + item5.getText()));

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue==null || newValue.length()<2){
                cm.hide();
                games=null;
                return;
            }
            String firstLetter= newValue.substring(0, 1).toUpperCase();
            String secondLetter= newValue.substring(1, 2).toUpperCase();
            DocumentReference docRef = fstore.collection("SteamGames").document(newValue.substring(0,1).toUpperCase());

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            try {
                    if(games==null) {
                        DocumentSnapshot doc = fstore.collection("SteamGames").document((firstLetter + secondLetter).toUpperCase()).get().get();

                        games = (List<Map<String, Object>>) doc.get("Games");
                    }
                    int count=0;
                    for(Map<String, Object> game:games){
                        if(count<=5) {
                            String name = (String) game.get("Name");
                            if (newValue.length() <= name.length()) {
                                if ((name.toUpperCase()).contains(newValue.toUpperCase())) {
                                    if(count==0){
                                        item1.setText(name);
                                        cm.getItems().add(item1);
                                    }
                                    if(count==1){
                                        item2.setText(name);
                                        cm.getItems().add(item2);
                                    }
                                    if(count==2){
                                        item3.setText(name);
                                        cm.getItems().add(item3);
                                    }
                                    if(count==3){
                                        item4.setText(name);
                                        cm.getItems().add(item4);
                                    }
                                    if(count==4){
                                        item5.setText(name);
                                        cm.getItems().add(item5);
                                    }
                                    count++;
                                }
                            }
                        }else{
                            cm.show(searchField, Side.BOTTOM, 0, 0);
                            return;
                        }
                    }
                    cm.show(searchField, Side.BOTTOM, 0, 0);
                    return;
            } catch (InterruptedException e) {
                return;
            } catch (ExecutionException e) {
                return;
            }
        });
        playBtn1.setOnAction(e -> selectOption(playBtn1, "online_competitive", "playStyle"));
        playBtn2.setOnAction(e -> selectOption(playBtn2, "online_casual", "playStyle"));
        playBtn3.setOnAction(e -> selectOption(playBtn3, "solo_offline", "playStyle"));
        playBtn4.setOnAction(e -> selectOption(playBtn4, "mix", "playStyle"));
        perfBtn1.setOnAction(e -> selectOption(perfBtn1, "1080_60", "performance"));
        perfBtn2.setOnAction(e -> selectOption(perfBtn2, "1080_144", "performance"));
        perfBtn3.setOnAction(e -> selectOption(perfBtn3, "1440_high", "performance"));
        perfBtn4.setOnAction(e -> selectOption(perfBtn4, "4k", "performance"));

        storageBtn1.setOnAction(e -> selectOption(storageBtn1, "256_500gb", "storage"));
        storageBtn2.setOnAction(e -> selectOption(storageBtn2, "1tb", "storage"));
        storageBtn3.setOnAction(e -> selectOption(storageBtn3, "2tb", "storage"));
        storageBtn4.setOnAction(e -> selectOption(storageBtn4, "2tb_plus", "storage"));

        priorityBtn1.setOnAction(e -> selectOption(priorityBtn1, "performance", "priority"));
        priorityBtn2.setOnAction(e -> selectOption(priorityBtn2, "value", "priority"));
        priorityBtn3.setOnAction(e -> selectOption(priorityBtn3, "reliability", "priority"));
        priorityBtn4.setOnAction(e -> selectOption(priorityBtn4, "aesthetics", "priority"));

        longevityBtn1.setOnAction(e -> selectOption(longevityBtn1, "1_2_years", "longevity"));
        longevityBtn2.setOnAction(e -> selectOption(longevityBtn2, "3_4_years", "longevity"));
        longevityBtn3.setOnAction(e -> selectOption(longevityBtn3, "5_plus_years", "longevity"));
        longevityBtn4.setOnAction(e -> selectOption(longevityBtn4, "unsure", "longevity"));
        loadQuestion(1);
    }
    private void selectBudget(Button selected, String value){
        budgetBtn1.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
        budgetBtn2.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
        budgetBtn3.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
        budgetBtn4.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");

        selected.setStyle("-fx-background-color: #4c6b22; -fx-text-fill: white; -fx-background-radius: 6; -fx-border-color: #8bc34a; -fx-border-radius: 6;");
        selectedBudget = value;
    }
    @FXML
    private void onNextClicked() {
        if (selectedBudget == null) {
            return;
        }
        if (currentQuestion == 7) {
            getRecommendation();
            return;
        }
        currentQuestion++;
        if (currentQuestion > 7) {
            currentQuestion = 7;
            return;
        }
        loadQuestion(currentQuestion);
    }
    @FXML
    private void onHomeClicked() throws Exception {
        HelloApplication.setRoot("homeView.fxml");
    }

    private void getRecommendation() {
        new Thread(() -> {
            try {
                PreBuiltService service = new PreBuiltService();
                List<PreBuilt> allPCs = service.getAllPreBuilts();

                PreBuilt best = null;
                int bestScore = -1;

                for (PreBuilt pc : allPCs) {
                    if (!isWithinBudget(pc)) continue;
                    int score = scorePC(pc);
                    if (score > bestScore) {
                        bestScore = score;
                        best = pc;
                    }
                }

                final PreBuilt recommendation = best;
                javafx.application.Platform.runLater(() -> showRecommendation(recommendation));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private boolean isWithinBudget(PreBuilt pc) {
        double price = pc.getPrice();
        switch (selectedBudget) {
            case "under_500":     return price < 500;
            case "$500 - $1,000": return price >= 500 && price <= 1000;
            case "1000_1500":     return price >= 1000 && price <= 1500;
            case "1500_plus":     return price > 1500;
            default:              return true;
        }
    }
    private int scorePC(PreBuilt pc) {
        int score = 0;
        String gpu = pc.getGPU() != null ? pc.getGPU().toLowerCase() : "";
        String ram = pc.getRAM() != null ? pc.getRAM().toLowerCase() : "";
        String storage = pc.getStorage() != null ? pc.getStorage().toLowerCase() : "";

        // Score based on performance target
        if ("4k".equals(selectedPerformance)) {
            if (gpu.contains("4090") || gpu.contains("4080") || gpu.contains("3090")) score += 3;
            else if (gpu.contains("4070") || gpu.contains("3080")) score += 2;
            else if (gpu.contains("4060") || gpu.contains("3070")) score += 1;
        } else if ("1440_high".equals(selectedPerformance)) {
            if (gpu.contains("4070") || gpu.contains("3080") || gpu.contains("3070")) score += 3;
            else if (gpu.contains("4060") || gpu.contains("3060")) score += 2;
        } else if ("1080_144".equals(selectedPerformance)) {
            if (gpu.contains("3060") || gpu.contains("4060") || gpu.contains("2070")) score += 3;
            else if (gpu.contains("3050") || gpu.contains("2060")) score += 2;
        } else {
            score += 1;
        }

        // Score based on storage
        if ("2tb_plus".equals(selectedStorage)) {
            if (storage.contains("4tb") || storage.contains("3tb")) score += 3;
            else if (storage.contains("2tb")) score += 2;
        } else if ("2tb".equals(selectedStorage)) {
            if (storage.contains("2tb")) score += 3;
            else if (storage.contains("1tb")) score += 1;
        } else if ("1tb".equals(selectedStorage)) {
            if (storage.contains("1tb")) score += 3;
        } else {
            score += 1;
        }

        // Score based on RAM
        if (ram.contains("32gb")) score += 2;
        else if (ram.contains("16gb")) score += 1;

        // Score based on longevity
        if ("5_plus_years".equals(selectedLongevity)) {
            if (gpu.contains("4080") || gpu.contains("4090")) score += 3;
            else if (gpu.contains("4070") || gpu.contains("3080")) score += 2;
        } else if ("3_4_years".equals(selectedLongevity)) {
            if (gpu.contains("4060") || gpu.contains("3070")) score += 2;
        }

        return score;
    }
    private void showRecommendation(PreBuilt pc) {
        if (pc == null) {
            questionCounter.setText("No match found");
            questionText.setText("Sorry, no pre-built PC matched your budget and preferences.");
            questionHint.setText("Try adjusting your budget or preferences.");
            return;
        }

        questionCounter.setText("Your Recommendation");
        questionText.setText(pc.getName());
        questionHint.setText("Price: $" + pc.getPrice() + " | GPU: " + pc.getGPU() + " | RAM: " + pc.getRAM() + " | Storage: " + pc.getStorage());

        budgetOptions.setVisible(false);
        budgetOptions.setManaged(false);
        playStyleOptions.setVisible(false);
        playStyleOptions.setManaged(false);
        performanceOptions.setVisible(false);
        performanceOptions.setManaged(false);
        storageOptions.setVisible(false);
        storageOptions.setManaged(false);
        priorityOptions.setVisible(false);
        priorityOptions.setManaged(false);
        longevityOptions.setVisible(false);
        longevityOptions.setManaged(false);
        nextButton.setVisible(false);

        if (pc.getImageURL() != null && !pc.getImageURL().isEmpty()) {
            Image image = new Image(pc.getImageURL(), true);
            pcImageView.setImage(image);

            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(500, 350);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            pcImageView.setClip(clip);

            imageContainer.setVisible(true);
            imageContainer.setManaged(true);
        }

        rightPanel.getChildren().removeIf(node -> node instanceof Button && ((Button) node).getText().equals("View Deal →"));
        Button viewDealButton = new Button("View Deal →");
        viewDealButton.setMaxWidth(Double.MAX_VALUE);
        viewDealButton.setStyle("-fx-background-color: #2d6a2d; -fx-text-fill: white; -fx-background-radius: 8; -fx-border-radius: 8; -fx-font-size: 15px; -fx-font-weight: bold;");
        viewDealButton.setOnMouseEntered(e -> viewDealButton.setStyle("-fx-background-color: #4c9a4c; -fx-text-fill: white; -fx-background-radius: 8; -fx-border-radius: 8; -fx-font-size: 15px; -fx-font-weight: bold;"));
        viewDealButton.setOnMouseExited(e -> viewDealButton.setStyle("-fx-background-color: #2d6a2d; -fx-text-fill: white; -fx-background-radius: 8; -fx-border-radius: 8; -fx-font-size: 15px; -fx-font-weight: bold;"));
        viewDealButton.setPrefHeight(50);
        viewDealButton.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(pc.getLink()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        rightPanel.getChildren().add(viewDealButton);
    }

    private void loadQuestion(int questionNumber) {
        questionCounter.setText("Question " + questionNumber + " of 7");
        budgetOptions.setVisible(false);
        budgetOptions.setManaged(false);
        gameSearch.setVisible(false);
        gameSearch.setManaged(false);
        playStyleOptions.setVisible(false);
        playStyleOptions.setManaged(false);
        performanceOptions.setVisible(false);
        performanceOptions.setManaged(false);
        storageOptions.setVisible(false);
        storageOptions.setManaged(false);
        priorityOptions.setVisible(false);
        priorityOptions.setManaged(false);
        longevityOptions.setVisible(false);
        longevityOptions.setManaged(false);
        gameList.setVisible(false);
        gameList.setManaged(false);

        if (questionNumber == 1) {
            budgetOptions.setVisible(true);
            budgetOptions.setManaged(true);
        }

        if (questionNumber == 2) {
            questionText.setText("Which games do you want to play?");
            questionHint.setText("Search and add up to 5 games.");
            gameSearch.setVisible(true);
            gameSearch.setManaged(true);
            gameList.setVisible(true);
            gameList.setManaged(true);
        }
        if (questionNumber == 3) {
            questionText.setText("How do you mostly play games?");
            questionHint.setText("Affects whether online performance or offline experience is prioritized.");
            playStyleOptions.setVisible(true);
            playStyleOptions.setManaged(true);
        }
        if (questionNumber == 4) {
            questionText.setText("What framerate and resolution are you targeting?");
            questionHint.setText("Higher FPS and resolution need a stronger GPU.");
            performanceOptions.setVisible(true);
            performanceOptions.setManaged(true);

        }
        if (questionNumber == 5) {
            questionText.setText("How much storage do you think you need?");
            questionHint.setText("Game libraries eat storage fast.");
            storageOptions.setVisible(true);
            storageOptions.setManaged(true);

        }
        if (questionNumber == 6) {
            questionText.setText("If you had to pick one priority, what matters most?");
            questionHint.setText("Used to break ties when two configs have a similar price.");
            priorityOptions.setVisible(true);
            priorityOptions.setManaged(true);
        }
        if (questionNumber == 7) {
            questionText.setText("How long do you want this PC to last before upgrading?");
            questionHint.setText("Longer lifespan means recommending higher-tier components now.");
            longevityOptions.setVisible(true);
            longevityOptions.setManaged(true);
        }
    }
    private void selectOption(Button selected, String value, String questionType) {
        if (questionType.equals("playStyle")) {
            playBtn1.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            playBtn2.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            playBtn3.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            playBtn4.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            selectedPlayStyle = value;
        }
        if (questionType.equals("performance")) {
            perfBtn1.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            perfBtn2.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            perfBtn3.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            perfBtn4.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            selectedPerformance = value;
        }
        if (questionType.equals("storage")) {
            storageBtn1.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            storageBtn2.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            storageBtn3.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            storageBtn4.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            selectedStorage = value;
        }
        if (questionType.equals("priority")) {
            priorityBtn1.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            priorityBtn2.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            priorityBtn3.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            priorityBtn4.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            selectedPriority = value;
        }
        if (questionType.equals("longevity")) {
            longevityBtn1.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            longevityBtn2.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            longevityBtn3.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            longevityBtn4.setStyle("-fx-background-color: #1b2838; -fx-text-fill: #c7d5e0; -fx-background-radius: 6; -fx-border-color: #4c6b8a; -fx-border-radius: 6;");
            selectedLongevity = value;
        }
        selected.setStyle("-fx-background-color: #4c6b22; -fx-text-fill: white; -fx-background-radius: 6; -fx-border-color: #8bc34a; -fx-border-radius: 6;");
    }
}



