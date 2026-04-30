package edu.farmingdale.csc325capstone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class SavedBuildsViewController {

    @FXML
    private Button buildButton;

    @FXML
    private ComboBox<String> caseCombo;

    @FXML
    private ComboBox<String> cpuCombo;

    @FXML
    private ListView<Label> gamesListView;

    @FXML
    private ComboBox<String> gpuCombo;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<String> motherboardCombo;

    @FXML
    private ComboBox<String> psuCombo;

    @FXML
    private ComboBox<String> ramCombo;

    @FXML
    private ComboBox<String> storageCombo;


    @FXML
    public void initialize(){{
        ObservableList<Label> names;
        if(HelloApplication.user!=null){
            List<Map<String, Object>> builds=HelloApplication.user.getBuilds();
            List<Label> storedNames= new LinkedList<>();
            for(Map<String, Object> build:builds){
                Label userSavedBuild= new Label(build.get("name") + "");
                userSavedBuild.setOnMouseClicked(e->{
                    cpuCombo.setValue(HelloApplication.cpus.get(build.get("cpu") + "").get("name") + "");
                    gpuCombo.setValue(HelloApplication.gpus.get(build.get("gpu") + "").get("name") + "");
                    ramCombo.setValue(HelloApplication.ram.get(build.get("ram") + "").get("name") + "");
                    motherboardCombo.setValue(HelloApplication.motherboards.get(build.get("motherboard") + "").get("name") + "");
                    psuCombo.setValue(HelloApplication.psus.get(build.get("psu") + "").get("name") + "");
                    storageCombo.setValue(HelloApplication.storage.get(build.get("storage") + "").get("name") + "");
                    caseCombo.setValue(HelloApplication.cases.get(build.get("case") + "").get("name") + "");
                });
                storedNames.add(userSavedBuild);
            }
            names=(FXCollections.observableArrayList(storedNames));
        }
        else{
            List<Label> none= new LinkedList<>();
            none.add(new Label("You need to log in to use this feature"));
            names=(FXCollections.observableArrayList(none));
        }
        gamesListView.setItems(names);
    }}
    @FXML
    void buildButtonHandle(ActionEvent event) throws IOException {
        HelloApplication.setRoot("sandboxView.fxml");
    }

    @FXML
    void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        HelloApplication.setRoot("loginView.fxml");
    }

}
