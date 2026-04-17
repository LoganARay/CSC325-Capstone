package edu.farmingdale.csc325capstone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class SandboxViewController {
    @FXML
    private ComboBox<String> caseCombo;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<String> cpuCombo;

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
    private Button saveButton;

    @FXML
    private ComboBox<String> storageCombo;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Label totalCostNumber;

    @FXML
    private Label totalWLabel;

    @FXML
    private Label totalWattage;

    @FXML
    private Button viewSavedButton;


    @FXML
    public void initialize(){
        setCases();
        setCpus();
        setMotherboards();
        setGpus();
        setRam();
        setStorage();
        setGpus();
        setPsu();
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
    void handleViewSavedBuild(ActionEvent event) throws IOException {
        HelloApplication.setRoot("savedBuildsView.fxml");
    }

    @FXML
    private void clearBuildHandle(ActionEvent event) throws IOException {
        clearBuildLogic();
    }

    @FXML
    private void saveBuildHandle(ActionEvent event) throws IOException {
        saveBuildLogic();
    }



    public void clearBuildLogic() {
        //TODO




    }

    public void saveBuildLogic() {
        //TODO



    }

    public void setCases(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.cases)));
        caseCombo.setItems(names);
    }

    public void setCpus(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.cpus)));
        cpuCombo.setItems(names);
    }

    public void setGpus(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.gpus)));
        gpuCombo.setItems(names);
    }

    public void setRam(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.ram)));
        ramCombo.setItems(names);
    }

    public void setMotherboards(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.motherboards)));
        motherboardCombo.setItems(names);
    }

    public void setPsu(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.psus)));
        psuCombo.setItems(names);
    }

    public void setStorage(){
        ObservableList<String> names= (FXCollections.observableArrayList(HelloApplication.getNamesParts(HelloApplication.storage)));
        storageCombo.setItems(names);
    }
}

