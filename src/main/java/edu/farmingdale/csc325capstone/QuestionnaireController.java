package edu.farmingdale.csc325capstone;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class QuestionnaireController {
    private String selectedBudget;
    private int currentQuestion = 1;
    private String selectedPlayStyle;

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
    public void initialize(){
        budgetBtn1.setText("Under $500");
        budgetBtn2.setText("$500 - $1,000");
        budgetBtn3.setText("$1,000 - $1,500");
        budgetBtn4.setText("$1,500+");

        budgetBtn1.setOnAction( e -> selectBudget(budgetBtn1, "under_500"));
        budgetBtn2.setOnAction(e -> selectBudget(budgetBtn2, "$500 - $1,000") );
        budgetBtn3.setOnAction(e -> selectBudget(budgetBtn3, "1000_1500"));
        budgetBtn4.setOnAction(e -> selectBudget(budgetBtn4, "1500_plus"));
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
        currentQuestion++;
        if (currentQuestion > 7) {
            currentQuestion = 7;
            return;
        }
        loadQuestion(currentQuestion);
    }

    private void loadQuestion(int questionNumber) {
        questionCounter.setText("Question " + questionNumber + " of 7");
        budgetOptions.setVisible(false);
        budgetOptions.setManaged(false);
            if (questionNumber == 1) {
                budgetOptions.setVisible(true);
                budgetOptions.setManaged(true);
            }

            if (questionNumber == 2) {
                questionText.setText("Which games do you want to play?");
                questionHint.setText("Search and add up to 5 games.");
            }
            if (questionNumber == 3) {
                questionText.setText("How do you mostly play games?");
                questionHint.setText("Affects whether online performance or offline experience is prioritized.");
            }
            if (questionNumber == 4) {
                questionText.setText("What framerate and resolution are you targeting?");
                questionHint.setText("Higher FPS and resolution need a stronger GPU.");
            }
            if (questionNumber == 5) {
                questionText.setText("How much storage do you think you need?");
                questionHint.setText("Game libraries eat storage fast.");
            }
            if (questionNumber == 6) {
                questionText.setText("If you had to pick one priority, what matters most?");
                questionHint.setText("Used to break ties when two configs have a similar price.");
            }
            if (questionNumber == 7) {
                questionText.setText("How long do you want this PC to last before upgrading?");
                questionHint.setText("Longer lifespan means recommending higher-tier components now.");
            }
        }
    }



