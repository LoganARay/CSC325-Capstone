package edu.farmingdale.csc325capstone;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class gameSearchController {

@FXML
    private Button buildButton;

    @FXML
    private Label gameStudioLabel;

    @FXML
    private ListView<?> gamesListView;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label minCpuLabel;

    @FXML
    private Label minGpuLabel;

    @FXML
    private Label minRamLabel;

    @FXML
    private Label minStorageLabel;

    @FXML
    private Label recCpuLabel;

    @FXML
    private Label recGpuLabel;

    @FXML
    private Label recRamLabel;

    @FXML
    private Label recStorageLabel;

    @FXML
    private Button savedButton;

    @FXML
    private TextField searchField;


    private MenuItem item1=new MenuItem(" ");
    private MenuItem item2=new MenuItem(" ");
    private MenuItem item3=new MenuItem(" ");
    private MenuItem item4=new MenuItem(" ");
    private MenuItem item5=new MenuItem(" ");


    private ContextMenu cm=new ContextMenu();


    private List<Map<String, Object>> currentScope;
    private Map<String, Object> currentGame;

    public void initialize(){
        System.out.println("initialize ran");

        item1.setOnAction(e -> {
            displayStats(item1.getText());
            item1=new MenuItem();
            return;
        });
        item2.setOnAction(e -> {
            displayStats(item2.getText());
            item2=new MenuItem();
            return;
        });
        item3.setOnAction(e -> {
            displayStats(item3.getText());
            item3=new MenuItem();
            return;
        });
        item4.setOnAction(e -> {
            displayStats(item4.getText());
            item4=new MenuItem();
            return;
        });
        item5.setOnAction(e -> {
            displayStats(item5.getText());
            item5=new MenuItem();
            return;
        });

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue==null || newValue.length()<2){
                cm.hide();
                return;
            }
            String firstLetter= newValue.substring(0, 1).toUpperCase();
            String secondLetter= newValue.substring(1, 2).toUpperCase();
            DocumentReference docRef = HelloApplication.steamGames.document(newValue.substring(0,1).toUpperCase());

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            try {
                document = future.get();
                ArrayList<String> list=(ArrayList<String>) document.get(newValue.substring(1,2).toUpperCase());
                if(list==null){
                    cm.getItems().clear();
                    MenuItem m= new MenuItem("No Games");
                    cm.getItems().add(m);
                    if(!cm.isShowing()){
                        cm.show(searchField, Side.BOTTOM, 0, 0);
                    }
                    System.out.println("Document does NOT exist!");
                    currentScope=null;
                    currentGame=null;
                    return;
                }else{

                    cm.getItems().clear();
                    DocumentSnapshot doc = HelloApplication.gett(firstLetter);

                    currentScope = (List<Map<String, Object>>) doc.get(secondLetter);

                    int count=0;
                    assert currentScope != null;
                    for(Map<String, Object> game:currentScope){
                        if(count<=5) {
                            String name = (String) game.get("Name");
                            if (newValue.length() <= name.length() && (name.toUpperCase()).substring(0, newValue.length()).equals(newValue.toUpperCase())) {
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
                    }
                    cm.show(searchField, Side.BOTTOM, 0, 0);
                    return;
                }
            } catch (InterruptedException e) {
                return;
            } catch (ExecutionException e) {
                return;
            }
        });
    }

    @FXML
    private void buildButtonHandle() {
        // TODO: Navigate to the builder with the selected game.


    }
    
    @FXML
    void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }

    @FXML
    void handleSavedBuild(ActionEvent event) throws IOException {
        HelloApplication.setRoot("savedBuilds.fxml");
    }

    public void displayStats(String name){
        setGame(name);
        gameStudioLabel.setText(name);
        Map<String, Object> min= (Map<String, Object>)currentGame.get("Minimum");
        minCpuLabel.setText(min.get("Processor") + "");
        minGpuLabel.setText(min.get("Graphics") + "");
        minRamLabel.setText(min.get("Memory") + "");
        Map<String, Object> rec= (Map<String, Object>)currentGame.get("Recommended");
        recCpuLabel.setText(rec.get("Processor") + "");
        recGpuLabel.setText(rec.get("Graphics") + "");
        recRamLabel.setText(rec.get("Memory") + "");
        if(currentGame.get("Storage")=="0"){
            recStorageLabel.setText("Less than 1 GB");
        }
        else {
            recStorageLabel.setText(currentGame.get("Storage") + " GB");
        }
        currentGame=null;
        currentScope=null;
        searchField.setText("");
    }

    public void setGame(String name){
        for(Map<String, Object> game:currentScope){
            if(game.get("Name").equals(name)){
                currentGame=game;
                break;
            }
        }
    }
}
