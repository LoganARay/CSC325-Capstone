package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
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
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.Label;
import java.util.function.Predicate;

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
    private VBox detailsVBox;

    @FXML
    void handleHome(ActionEvent event) throws IOException {
        HelloApplication.setRoot("homeView.fxml");
    }

    private HashMap<String, String> caseCalls;
    private HashMap<String, String> cpuCalls;
    private HashMap<String, String> motherboardCalls;
    private HashMap<String, String> gpuCalls;
    private HashMap<String, String> ramCalls;
    private HashMap<String, String> storageCalls;
    private HashMap<String, String> psuCalls;

    private List<Part> allCpuParts;
    private List<Part> allGpuParts;
    private List<Part> allRamParts;
    private List<Part> allMoboParts;
    private List<Part> allPsuParts;
    private List<Part> allCaseParts;
    private List<Part> allStorageParts;

    private Map<String, Part> cpuPartMap = new HashMap<>();
    private Map<String, Part> gpuPartMap = new HashMap<>();
    private Map<String, Part> ramPartMap = new HashMap<>();
    private Map<String, Part> moboPartMap = new HashMap<>();
    private Map<String, Part> psuPartMap = new HashMap<>();
    private Map<String, Part> casePartMap = new HashMap<>();
    private Map<String, Part> storagePartMap = new HashMap<>();

    private boolean filtering = false;


    @FXML
    public void initialize() throws Exception {
        setCases();
        setCpus();
        setMotherboards();
        setGpus();
        setRam();
        setStorage();
        setPsu();

        if (HelloApplication.allCpuParts == null) {
            // Show a temporary loading message in the details area
            detailsVBox.getChildren().clear();
            Label loadingMsg = new Label("Loading parts…");
            loadingMsg.setTextFill(javafx.scene.paint.Color.WHITE);
            detailsVBox.getChildren().add(loadingMsg);

            new Thread(() -> {
                try {
                    HelloApplication.loadAllParts();               // heavy load on background thread
                    Platform.runLater(() -> {
                        try {
                            initFilterData();
                            addSelectionListeners();
                            updateTotalPriceAndWattage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            initFilterData();
            addSelectionListeners();
            updateTotalPriceAndWattage();
        }
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
        resetAllComboBoxes();
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
        cpuCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
        gpuCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
        ramCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
        motherboardCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
        storageCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
        psuCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
        caseCombo.valueProperty().addListener((obs, old, val) -> onSelectionChanged());
    }

    private void onSelectionChanged() {
        if (filtering) return;
        filtering = true;
        applyFilters();
        filtering = false;
        try {
            updateTotalPriceAndWattage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTotalPriceAndWattage() throws ExecutionException, InterruptedException {
        String cpuName = cpuCombo.getValue();
        String gpuName = gpuCombo.getValue();
        String ramName = ramCombo.getValue();
        String moboName = motherboardCombo.getValue();
        String storageName = storageCombo.getValue();
        String psuName = psuCombo.getValue();
        String caseName = caseCombo.getValue();

        Part cpu = cpuPartMap.get(cpuName);
        Part gpu = gpuPartMap.get(gpuName);
        Part ram = ramPartMap.get(ramName);
        Part mobo = moboPartMap.get(moboName);
        Part storage = storagePartMap.get(storageName);
        Part psu = psuPartMap.get(psuName);
        Part computerCase = casePartMap.get(caseName);


//        Part cpu = cpuName == null ? null : HelloApplication.getPartByName(cpuCalls, "cpus", cpuName, "CPU");
//        Part gpu = gpuName == null ? null : HelloApplication.getPartByName(gpuCalls, "gpus", gpuName, "Video Card");
//        Part ram = ramName == null ? null : HelloApplication.getPartByName(ramCalls, "ram", ramName, "Memory");
//        Part mobo = moboName == null ? null : HelloApplication.getPartByName(motherboardCalls, "motherboards", moboName, "Motherboard");
//        Part storage = storageName == null ? null : HelloApplication.getPartByName(storageCalls, "storage", storageName, "Internal Hard Drive");
//        Part psu = psuName == null ? null : HelloApplication.getPartByName(psuCalls, "psus", psuName, "Power Supply");
//        Part computerCase = caseName == null ? null : HelloApplication.getPartByName(caseCalls, "cases", caseName, "Case");
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

        VBox card = new VBox(4);
        card.setStyle("-fx-background-color: #101730; -fx-background-radius: 6; " + "-fx-padding: 8; -fx-border-color: #2a3a5a; -fx-border-radius: 6;");

        Label nameLabel = new Label(label + ": " + part.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");

        Label brandPriceLabel = new Label(part.getBrand() + "  |  $" + String.format("%.2f", part.getPrice()));
        brandPriceLabel.setStyle("-fx-text-fill: #b0c4de; -fx-font-size: 11px;");

        String specsText = getKeySpecsText(part);
        Label specsLabel = new Label(specsText);
        specsLabel.setStyle("-fx-text-fill: #8ba3b5; -fx-font-size: 10px;");
        specsLabel.setWrapText(true);

        card.getChildren().addAll(nameLabel, brandPriceLabel, specsLabel);
        detailsVBox.getChildren().add(card);
    }

    private String getKeySpecsText(Part part) {
        String category = part.getCategory();
        Map<String, Object> specs = part.getSpecs();
        if (specs == null) return "";

        switch (category) {
            case "CPU":
                return String.format("Cores: %s | Clock: %s GHz",
                        specs.getOrDefault("cores", "?"),
                        specs.getOrDefault("base_clock", "?"));
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

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void setCases() throws ExecutionException, InterruptedException{
        caseCalls=HelloApplication.getNamesParts(HelloApplication.cases, "cases");
        ObservableList<String> names= (FXCollections.observableArrayList(caseCalls.keySet()));
        caseCombo.setItems(names);
    }

    public void setCpus() throws ExecutionException, InterruptedException{
        cpuCalls= HelloApplication.getNamesParts(HelloApplication.cpus, "cpus");
        ObservableList<String> names= (FXCollections.observableArrayList(cpuCalls.keySet()));
        cpuCombo.setItems(names);
    }

    public void setGpus() throws ExecutionException, InterruptedException{
        gpuCalls=HelloApplication.getNamesParts(HelloApplication.gpus, "gpus");
        ObservableList<String> names= (FXCollections.observableArrayList(gpuCalls.keySet()));
        gpuCombo.setItems(names);
    }

    public void setRam() throws ExecutionException, InterruptedException{
        ramCalls=HelloApplication.getNamesParts(HelloApplication.ram, "ram");
        ObservableList<String> names= (FXCollections.observableArrayList(ramCalls.keySet()));
        ramCombo.setItems(names);
    }

    public void setMotherboards() throws ExecutionException, InterruptedException{
        motherboardCalls=HelloApplication.getNamesParts(HelloApplication.motherboards, "motherboards");
        ObservableList<String> names= (FXCollections.observableArrayList(motherboardCalls.keySet()));
        motherboardCombo.setItems(names);
    }

    public void setPsu() throws ExecutionException, InterruptedException {
        psuCalls=HelloApplication.getNamesParts(HelloApplication.psus, "psus");
        ObservableList<String> names= (FXCollections.observableArrayList(psuCalls.keySet()));
        psuCombo.setItems(names);
    }

    public void setStorage() throws ExecutionException, InterruptedException {
        storageCalls=HelloApplication.getNamesParts(HelloApplication.storage, "storage");
        ObservableList<String> names= (FXCollections.observableArrayList(storageCalls.keySet()));
        storageCombo.setItems(names);
    }

    private void initFilterData() {
        allCpuParts   = HelloApplication.allCpuParts;
        allGpuParts   = HelloApplication.allGpuParts;
        allRamParts   = HelloApplication.allRamParts;
        allMoboParts  = HelloApplication.allMoboParts;
        allCaseParts  = HelloApplication.allCaseParts;
        allPsuParts   = HelloApplication.allPsuParts;
        allStorageParts = HelloApplication.allStorageParts;

        cpuPartMap.clear();    for (Part p : allCpuParts) cpuPartMap.put(p.getName(), p);
        gpuPartMap.clear();    for (Part p : allGpuParts) gpuPartMap.put(p.getName(), p);
        ramPartMap.clear();    for (Part p : allRamParts) ramPartMap.put(p.getName(), p);
        moboPartMap.clear();   for (Part p : allMoboParts) moboPartMap.put(p.getName(), p);
        psuPartMap.clear();    for (Part p : allPsuParts) psuPartMap.put(p.getName(), p);
        casePartMap.clear();   for (Part p : allCaseParts) casePartMap.put(p.getName(), p);
        storagePartMap.clear(); for (Part p : allStorageParts) storagePartMap.put(p.getName(), p);
    }

    private void applyFilters() {
        Part cpuSel  = cpuPartMap.get(cpuCombo.getValue());
        Part gpuSel  = gpuPartMap.get(gpuCombo.getValue());
        Part ramSel  = ramPartMap.get(ramCombo.getValue());
        Part moboSel = moboPartMap.get(motherboardCombo.getValue());
        Part caseSel = casePartMap.get(caseCombo.getValue());

        filterCombo(cpuCombo, allCpuParts, cpu -> {
            if (moboSel == null) return true;
            return CompatibilityChecker.isCpuMotherboardCompatible(cpu, moboSel);
        });

        filterCombo(gpuCombo, allGpuParts, gpu -> {
            if (caseSel == null) return true;
            return CompatibilityChecker.isGpuCaseCompatible(gpu, caseSel);
        });

        filterCombo(ramCombo, allRamParts, ram -> {
            if (moboSel == null) return true;
            return CompatibilityChecker.isRamMotherboardCompatible(ram, moboSel);
        });

        filterCombo(motherboardCombo, allMoboParts, mobo -> {
            if (cpuSel != null && !CompatibilityChecker.isCpuMotherboardCompatible(cpuSel, mobo)) return false;
            if (ramSel != null && !CompatibilityChecker.isRamMotherboardCompatible(ramSel, mobo)) return false;
            if (caseSel != null && !CompatibilityChecker.isMotherboardCaseCompatible(mobo, caseSel)) return false;
            return true;
        });

        filterCombo(caseCombo, allCaseParts, c -> {
            if (gpuSel != null && !CompatibilityChecker.isGpuCaseCompatible(gpuSel, c)) return false;
            if (moboSel != null && !CompatibilityChecker.isMotherboardCaseCompatible(moboSel, c)) return false;
            return true;
        });

        // PSU & Storage always show all items – no filtering needed
        // but we must ensure they still display their full lists if they were never changed:
        // (they are already set in setPsu/setStorage, so no action needed)
    }

    private void filterCombo(ComboBox<String> combo, List<Part> source, java.util.function.Predicate<Part> test) {
        String currentSelection = combo.getValue();
        List<String> allowedNames = new ArrayList<>();
        for (Part p : source) {
            if (test.test(p)) {
                allowedNames.add(p.getName());
            }
        }
        ObservableList<String> filteredItems = FXCollections.observableArrayList();
        filteredItems.addAll(allowedNames);
        combo.setItems(filteredItems);
        if (currentSelection != null && !allowedNames.contains(currentSelection)) {
            combo.setValue(null);
        } else if (currentSelection != null) {
            combo.setValue(currentSelection);
        }
    }

    private void resetAllComboBoxes() {
        comboSetAll(cpuCombo, allCpuParts);
        comboSetAll(gpuCombo, allGpuParts);
        comboSetAll(ramCombo, allRamParts);
        comboSetAll(motherboardCombo, allMoboParts);
        comboSetAll(caseCombo, allCaseParts);
        comboSetAll(psuCombo, allPsuParts);
        comboSetAll(storageCombo, allStorageParts);
    }

    private void comboSetAll(ComboBox<String> combo, List<Part> parts) {
        ObservableList<String> names = FXCollections.observableArrayList();
        for (Part p : parts) {
            names.add(p.getName());
        }
        combo.setItems(names);
    }

    private final Map<String, Part> selectedParts = new HashMap<>();
}

