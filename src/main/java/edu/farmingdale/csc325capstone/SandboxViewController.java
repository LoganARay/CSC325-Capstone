package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.model.CompatibilityChecker;
import edu.farmingdale.csc325capstone.model.Part;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.util.HashMap;
import java.util.Map;
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

        addSelectionListeners();
        updateTotalPriceAndWattage();
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
        cpuCombo.setValue(null);
        gpuCombo.setValue(null);
        ramCombo.setValue(null);
        motherboardCombo.setValue(null);
        storageCombo.setValue(null);
        psuCombo.setValue(null);
        caseCombo.setValue(null);
        totalCostNumber.setText("0");
        totalWattage.setText("0");
        selectedParts.clear();
    }

    public void saveBuildLogic() {
        //TODO



    }

    private void addSelectionListeners() {
        cpuCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
        gpuCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
        ramCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
        motherboardCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
        storageCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
        psuCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
        caseCombo.valueProperty().addListener((obs, old, val) -> updateTotalPriceAndWattage());
    }

    private void updateTotalPriceAndWattage() {
        String cpuName = cpuCombo.getValue();
        String gpuName = gpuCombo.getValue();
        String ramName = ramCombo.getValue();
        String moboName = motherboardCombo.getValue();
        String storageName = storageCombo.getValue();
        String psuName = psuCombo.getValue();
        String caseName = caseCombo.getValue();

        Part cpu = cpuName == null ? null : HelloApplication.getPartByName(HelloApplication.cpus, cpuName, "CPU");
        Part gpu = gpuName == null ? null : HelloApplication.getPartByName(HelloApplication.gpus, gpuName, "Video Card");
        Part ram = ramName == null ? null : HelloApplication.getPartByName(HelloApplication.ram, ramName, "Memory");
        Part mobo = moboName == null ? null : HelloApplication.getPartByName(HelloApplication.motherboards, moboName, "Motherboard");
        Part storage = storageName == null ? null : HelloApplication.getPartByName(HelloApplication.storage, storageName, "Internal Hard Drive");
        Part psu = psuName == null ? null : HelloApplication.getPartByName(HelloApplication.psus, psuName, "Power Supply");
        Part computerCase = caseName == null ? null : HelloApplication.getPartByName(HelloApplication.cases, caseName, "Case");

        selectedParts.clear();
        selectedParts.put("cpu", cpu);
        selectedParts.put("gpu", gpu);
        selectedParts.put("ram", ram);
        selectedParts.put("motherboard", mobo);
        selectedParts.put("storage", storage);
        selectedParts.put("psu", psu);
        selectedParts.put("case", computerCase);

        double total = 0;
        if (cpu != null) total += cpu.getPrice();
        if (gpu != null) total += gpu.getPrice();
        if (ram != null) total += ram.getPrice();
        if (mobo != null) total += mobo.getPrice();
        if (storage != null) total += storage.getPrice();
        if (psu != null) total += psu.getPrice();
        if (computerCase != null) total += computerCase.getPrice();
        totalCostNumber.setText(String.format("%.2f", total));

        int wattage = 0;
        if (cpu != null && gpu != null) {
            wattage = CompatibilityChecker.estimateTotalWattage(cpu, gpu);
        }
        totalWattage.setText(String.valueOf(wattage));

        checkCompatibility(cpu, gpu, ram, mobo, computerCase, psu);
    }

    private void checkCompatibility(Part cpu, Part gpu, Part ram, Part mobo, Part computerCase, Part psu) {
        if (cpu != null && mobo != null && !CompatibilityChecker.isCpuMotherboardCompatible(cpu, mobo)) {
            showAlert("Incompatible", "CPU socket " + cpu.getSpec("socket") +
                    " does not match motherboard socket " + mobo.getSpec("cpu_socket"));
        }
        if (ram != null && mobo != null && !CompatibilityChecker.isRamMotherboardCompatible(ram, mobo)) {
            showAlert("Incompatible", "RAM type/speed incompatible with motherboard.");
        }
        if (gpu != null && computerCase != null && !CompatibilityChecker.isGpuCaseCompatible(gpu, computerCase)) {
            showAlert("Incompatible", "GPU length exceeds case limit.");
        }
        if (mobo != null && computerCase != null && !CompatibilityChecker.isMotherboardCaseCompatible(mobo, computerCase)) {
            showAlert("Incompatible", "Motherboard form factor not supported by case.");
        }
        if (cpu != null && gpu != null && psu != null) {
            int estimated = CompatibilityChecker.estimateTotalWattage(cpu, gpu);
            if (!CompatibilityChecker.isPsuSufficient(psu, estimated)) {
                showAlert("PSU Warning", "PSU wattage (" + (int)psu.getSpecAsDouble("wattage",0) +
                        "W) may be insufficient for estimated " + estimated + "W.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
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
    private final Map<String, Part> selectedParts = new HashMap<>();
}

