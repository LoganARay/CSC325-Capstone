package edu.farmingdale.csc325capstone;

import com.google.cloud.firestore.Firestore;
import edu.farmingdale.csc325capstone.PcParts.UserBuilds;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private HashMap<String, String> caseCalls;
    private HashMap<String, String> cpuCalls;
    private HashMap<String, String> motherboardCalls;
    private HashMap<String, String> gpuCalls;
    private HashMap<String, String> ramCalls;
    private HashMap<String, String> storageCalls;
    private HashMap<String, String> psuCalls;

    private UserBuilds build=null;

    @FXML
    public void initialize(){
        FirestoreContent contxtFirebase = new FirestoreContent();
        Firestore fstore = contxtFirebase.firebase();
        setCases();
        setCpus();
        setMotherboards();
        setGpus();
        setRam();
        setStorage();
        setPsu();

        caseCombo.setOnAction(event -> {
            String selected = caseCombo.getValue();
            String dir= caseCalls.get(selected);
            if(build==null){
                build=new UserBuilds();

            }
            build.setCaseDir(dir);
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });

        cpuCombo.setOnAction(event -> {
            String selected = cpuCombo.getValue();
            String dir= cpuCalls.get(selected);
            if(build==null){
                build=new UserBuilds();
            }
            build.setCpuDir(dir);
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });

        gpuCombo.setOnAction(event -> {
            String selected = gpuCombo.getValue();
            String dir= gpuCalls.get(selected);
            if(build==null){
                build=new UserBuilds();
            }
            build.setGpuDir(dir);
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });

        motherboardCombo.setOnAction(event -> {
            String selected = motherboardCombo.getValue();
            String dir= motherboardCalls.get(selected);
            if(build==null){
                build=new UserBuilds();
            }
            build.setMotherboardDir(dir);
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });

        storageCombo.setOnAction(event -> {
            String selected = storageCombo.getValue();
            String dir= storageCalls.get(selected);
            if(build==null){
                build=new UserBuilds();
            }
            build.setStorageDir(dir);
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });

        ramCombo.setOnAction(event -> {
            String selected = ramCombo.getValue();
            String dir= ramCalls.get(selected);
            if(build==null){
                build=new UserBuilds();
            }
            build.setRamDir(dir);
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });

        psuCombo.setOnAction(event -> {
            String selected = psuCombo.getValue();
            String dir= psuCalls.get(selected);
            if(build==null){
                build=new UserBuilds();
            }
            build.setPsusDir(dir);
            totalWattage.setText(build.getWattage() + "");
            System.out.println(build.getTotalValue()+"");
            totalCostNumber.setText(build.getTotalValue()+"");
        });
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
        caseCalls=HelloApplication.getNamesParts(HelloApplication.cases);
        ObservableList<String> names= (FXCollections.observableArrayList(caseCalls.keySet()));
        caseCombo.setItems(names);
    }

    public void setCpus(){
        cpuCalls= HelloApplication.getNamesParts(HelloApplication.cpus);
        ObservableList<String> names= (FXCollections.observableArrayList(cpuCalls.keySet()));
        cpuCombo.setItems(names);
    }

    public void setGpus(){
        gpuCalls=HelloApplication.getNamesParts(HelloApplication.gpus);
        ObservableList<String> names= (FXCollections.observableArrayList(gpuCalls.keySet()));
        gpuCombo.setItems(names);
    }

    public void setRam(){
        ramCalls=HelloApplication.getNamesParts(HelloApplication.ram);
        ObservableList<String> names= (FXCollections.observableArrayList(ramCalls.keySet()));
        ramCombo.setItems(names);
    }

    public void setMotherboards(){
        motherboardCalls=HelloApplication.getNamesParts(HelloApplication.motherboards);
        ObservableList<String> names= (FXCollections.observableArrayList(motherboardCalls.keySet()));
        motherboardCombo.setItems(names);
    }

    public void setPsu(){
        psuCalls=HelloApplication.getNamesParts(HelloApplication.psus);
        ObservableList<String> names= (FXCollections.observableArrayList(psuCalls.keySet()));
        psuCombo.setItems(names);
    }

    public void setStorage(){
        storageCalls=HelloApplication.getNamesParts(HelloApplication.storage);
        ObservableList<String> names= (FXCollections.observableArrayList(storageCalls.keySet()));
        storageCombo.setItems(names);
    }
}

