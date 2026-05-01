package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import edu.farmingdale.csc325capstone.model.CompatibilityChecker;
import edu.farmingdale.csc325capstone.model.Part;
import edu.farmingdale.csc325capstone.service.AppState;
import edu.farmingdale.csc325capstone.service.PartService;
import edu.farmingdale.csc325capstone.service.AIService;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;

public class SandboxViewController {

    @FXML
    private TextField buildName;

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
    private TextArea aiOutputArea;

    private AIService aiService = new AIService();

    private PartService partService = new PartService();

    @FXML
    private VBox detailsVBox;

    private HashMap<String, String> caseCalls;
    private HashMap<String, String> cpuCalls;
    private HashMap<String, String> motherboardCalls;
    private HashMap<String, String> gpuCalls;
    private HashMap<String, String> ramCalls;
    private HashMap<String, String> storageCalls;
    private HashMap<String, String> psuCalls;


    @FXML
    public void initialize() throws Exception {
        SandBoxParts s= new SandBoxParts();
        if(HelloApplication.storage==null){
            for(int i=0; i<2; i++){ s.fillingOrder();}
        }
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
        HelloApplication.setRoot("savedBuilds.fxml");
    }

    @FXML
    private void clearBuildHandle(ActionEvent event) throws IOException {
        clearBuildLogic();
    }

    @FXML
    private void saveBuildHandle(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        saveBuildLogic();
    }
    @FXML
    private void handleAnalyzeBuild(ActionEvent event) {
        List<Part> selectedParts = new ArrayList<>();

        Part cpu = findSelectedPart(cpuCombo.getValue(), "cpus");
        Part gpu = findSelectedPart(gpuCombo.getValue(), "gpus");
        Part ram = findSelectedPart(ramCombo.getValue(), "ram");
        Part motherboard = findSelectedPart(motherboardCombo.getValue(), "motherboards");
        Part storage = findSelectedPart(storageCombo.getValue(), "storage");
        Part psu = findSelectedPart(psuCombo.getValue(), "psus");
        Part pcCase = findSelectedPart(caseCombo.getValue(), "cases");

        if (cpu != null) selectedParts.add(cpu);
        if (gpu != null) selectedParts.add(gpu);
        if (ram != null) selectedParts.add(ram);
        if (motherboard != null) selectedParts.add(motherboard);
        if (storage != null) selectedParts.add(storage);
        if (psu != null) selectedParts.add(psu);
        if (pcCase != null) selectedParts.add(pcCase);

        if (selectedParts.isEmpty()) {
            aiOutputArea.setText("Please select at least one part before analyzing.");
            return;
        }

        aiOutputArea.setText("Analyzing build...");

        String result = aiService.analyzeBuild(selectedParts, AppState.openAiApiKey);
        aiOutputArea.setText(result);
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
        detailsVBox.getChildren().clear();
    }

    public void saveBuildLogic() throws ExecutionException, InterruptedException {
        if(HelloApplication.user!=null) {
            if (cpuCombo.getValue() != null && gpuCombo.getValue() != null
                    && ramCombo.getValue() != null && motherboardCombo.getValue() != null && storageCombo.getValue() != null
                    && psuCombo.getValue() != null && caseCombo.getValue() != null && buildName.getText()!=null) {
                HelloApplication.user.updateBuilds(cpuCalls.get(cpuCombo.getValue()), gpuCalls.get(gpuCombo.getValue()), ramCalls.get(ramCombo.getValue()), motherboardCalls.get(motherboardCombo.getValue()), storageCalls.get(storageCombo.getValue()), psuCalls.get(psuCombo.getValue()), caseCalls.get(caseCombo.getValue()), buildName.getText());
            }
        }else{
            System.out.println("no user");
        }
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

        updateDetailsPanel();
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

    private void updateDetailsPanel() {
        detailsVBox.getChildren().clear();
        addPartDetail("CPU", selectedParts.get("cpu"));
        addPartDetail("GPU", selectedParts.get("gpu"));
        addPartDetail("RAM", selectedParts.get("ram"));
        addPartDetail("Motherboard", selectedParts.get("motherboard"));
        addPartDetail("Storage", selectedParts.get("storage"));
        addPartDetail("Power Supply", selectedParts.get("psu"));
        addPartDetail("Case", selectedParts.get("case"));
    }

    private void addPartDetail(String label, Part part) {
        if (part == null) return;

        VBox partBox = new VBox(2);
        partBox.setStyle("-fx-background-color: #1a2340; -fx-background-radius: 6; -fx-padding: 6;");

        Label nameLabel = new Label(part.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");

        Label brandPriceLabel = new Label(part.getBrand() + "  |  $" + part.getPrice());
        brandPriceLabel.setStyle("-fx-text-fill: #c7d5e0; -fx-font-size: 11px;");

        String specsText = getKeySpecsText(part);
        Label specsLabel = new Label(specsText);
        specsLabel.setStyle("-fx-text-fill: #8ba3b5; -fx-font-size: 10px;");
        specsLabel.setWrapText(true);

        partBox.getChildren().addAll(nameLabel, brandPriceLabel, specsLabel);
        detailsVBox.getChildren().add(partBox);
    }

    private String getKeySpecsText(Part part) {
        String category = part.getCategory();
        Map<String, Object> specs = part.getSpecs();
        if (specs == null) return "";

        switch (category) {
            case "CPU":
                return String.format("Cores: %s | Clock: %s GHz",
                        specs.getOrDefault("core_count", "?"),
                        specs.getOrDefault("core_clock", "?"));
            case "Video Card":
                return String.format("Chipset: %s | Memory: %s GB",
                        specs.getOrDefault("chipset", "?"),
                        specs.getOrDefault("memory", "?"));
            case "Memory":
                return String.format("Type: %s | Speed: %s MHz",
                        specs.getOrDefault("type", "?"),
                        specs.getOrDefault("speed", "?"));
            case "Motherboard":
                return String.format("Socket: %s | Form: %s",
                        specs.getOrDefault("cpu_socket", "?"),
                        specs.getOrDefault("form_factor", "?"));
            case "Internal Hard Drive":
                return String.format("Capacity: %s GB | Type: %s",
                        specs.getOrDefault("capacity", "?"),
                        specs.getOrDefault("type", "?"));
            case "Power Supply":
                return String.format("Wattage: %s W | Efficiency: %s",
                        specs.getOrDefault("wattage", "?"),
                        specs.getOrDefault("efficiency", "?"));
            case "Case":
                return String.format("Type: %s | Max GPU: %s mm",
                        specs.getOrDefault("type", "?"),
                        specs.getOrDefault("max_gpu_length", "?"));
            default:
                return "";
        }
    }
    private Part findSelectedPart(String selectedName, String collectionName) {
        if (selectedName == null) return null;

        try {
            List<Part> parts = partService.getPartsByCollection(collectionName);

            for (Part part : parts) {
                if (part.getName().equals(selectedName)) {
                    return part;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

    private final Map<String, Part> selectedParts = new HashMap<>();
}

