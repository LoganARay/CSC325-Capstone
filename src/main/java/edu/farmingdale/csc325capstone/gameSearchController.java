package edu.farmingdale.csc325capstone;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import edu.farmingdale.csc325capstone.service.GameRequirements;
import edu.farmingdale.csc325capstone.service.SteamGame;
import javafx.collections.FXCollections;
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
import edu.farmingdale.csc325capstone.model.PreBuilt;
import edu.farmingdale.csc325capstone.service.PreBuiltService;
import java.util.AbstractMap;

public class gameSearchController extends RecommendationController {

    @FXML
    private Button buildButton;

    @FXML
    private Label gameStudioLabel;

    @FXML
    private ListView<Label> gamesListView;

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

    @FXML
    private Button recommendButton;


    private List<SteamGame> library = null;

    private List<Label> gameLists = null;


    private MenuItem item1 = new MenuItem(" ");
    private MenuItem item2 = new MenuItem(" ");
    private MenuItem item3 = new MenuItem(" ");
    private MenuItem item4 = new MenuItem(" ");
    private MenuItem item5 = new MenuItem(" ");


    private ContextMenu cm = new ContextMenu();


    private List<Map<String, Object>> currentScope;
    private Map<String, Object> currentGame;

    public void initialize() {
        System.out.println("initialize ran");

        item1.setOnAction(e -> {
            displayStats(item1.getText());
        });
        item2.setOnAction(e -> {
            displayStats(item2.getText());
        });
        item3.setOnAction(e -> {
            displayStats(item3.getText());
        });
        item4.setOnAction(e -> {
            displayStats(item4.getText());
        });
        item5.setOnAction(e -> {
            displayStats(item5.getText());
        });

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.length() < 2) {
                cm.hide();
                cm.getItems().clear();
                currentScope = null;
                return;
            }
            String firstLetter = newValue.substring(0, 1).toUpperCase();
            String secondLetter = newValue.substring(1, 2).toUpperCase();
            DocumentReference docRef = HelloApplication.fstore.collection("SteamGames").document(newValue.substring(0, 1).toUpperCase());

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            try {
                if (currentScope == null) {
                    DocumentSnapshot doc = HelloApplication.fstore.collection("SteamGames").document((firstLetter + secondLetter).toUpperCase()).get().get();

                    currentScope = (List<Map<String, Object>>) doc.get("Games");
                }
                cm.getItems().clear();
                int count = 0;
                for (Map<String, Object> game : currentScope) {
                    if (count <= 5) {
                        String name = (String) game.get("Name");
                        if (newValue.length() <= name.length()) {
                            if ((name.toUpperCase()).contains(newValue.toUpperCase())) {
                                if (count == 0) {
                                    item1.setText(name);
                                    cm.getItems().add(item1);
                                }
                                if (count == 1) {
                                    item2.setText(name);
                                    cm.getItems().add(item2);
                                }
                                if (count == 2) {
                                    item3.setText(name);
                                    cm.getItems().add(item3);
                                }
                                if (count == 3) {
                                    item4.setText(name);
                                    cm.getItems().add(item4);
                                }
                                if (count == 4) {
                                    item5.setText(name);
                                    cm.getItems().add(item5);
                                }
                                count++;
                            }
                        }
                    } else {
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
    }

    @FXML
    void buildButtonHandle() throws IOException {
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

    @FXML
    void handleSavedBuild(ActionEvent event) throws IOException {
        HelloApplication.setRoot("savedBuilds.fxml");
    }

    public void displayStats(String name) {
        setGame(name);
        gameStudioLabel.setText(name);
        String titleName = currentGame.get("Name") + "";
        Map<String, Object> min = (Map<String, Object>) currentGame.get("Minimum");
        String minCpu = min.get("Processor") + "";
        String minGpu = min.get("Graphics") + "";
        String minRam = min.get("Memory") + "";
        Map<String, Object> rec = (Map<String, Object>) currentGame.get("Recommended");
        String recCpu = rec.get("Processor") + "";
        String recGpu = rec.get("Graphics") + "";
        String recRam = rec.get("Memory") + "";
        String storage = currentGame.get("Storage") + "";
        GameRequirements minG = new GameRequirements(minGpu, minCpu, minRam);
        GameRequirements recG = new GameRequirements(recGpu, recCpu, recRam);
        SteamGame sg = new SteamGame(name, currentGame.get("AppId") + "", storage, minG, recG);

        if (library == null) {
            library = new ArrayList<SteamGame>();
        }
        Label title = new Label(titleName);
        title.setOnMouseClicked(e -> {
            gameStudioLabel.setText(name);
            minCpuLabel.setText(minCpu);
            minGpuLabel.setText(minGpu);
            minRamLabel.setText(minRam);
            recCpuLabel.setText(recCpu);
            recGpuLabel.setText(recGpu);
            recRamLabel.setText(recRam);
            recStorageLabel.setText(storage);
        });
        if (gameLists == null) {
            gameLists = new ArrayList<Label>();
        }
        gameLists.add(title);
        gamesListView.setItems((FXCollections.observableArrayList(gameLists)));
        library.add(sg);
        minCpuLabel.setText(minCpu);
        minGpuLabel.setText(minGpu);
        minRamLabel.setText(minRam);
        recCpuLabel.setText(recCpu);
        recGpuLabel.setText(recGpu);
        recRamLabel.setText(recRam);
        if (currentGame.get("Storage") == "0") {
            recStorageLabel.setText("Less than 1 GB");
        } else {
            recStorageLabel.setText(currentGame.get("Storage") + " GB");
        }
        currentGame = null;
        currentScope = null;
        searchField.setText("");
    }

    public void setGame(String name) {
        for (Map<String, Object> game : currentScope) {
            if (game.get("Name").equals(name)) {
                currentGame = game;
                break;
            }
        }
    }

    @FXML
    private void handleRecommend(ActionEvent event) {
        if (library == null || library.isEmpty()) return;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                String highestGPU = getHighestLibraryGPU();
                String highestCPU = getHighestLibraryCPU();
                System.out.println("Highest GPU: " + highestGPU);
                System.out.println("Highest CPU: " + highestCPU);
                long totalStorage = library.stream()
                        .mapToLong(g -> {
                            try {
                                return Long.parseLong(g.getStorage().replaceAll("[^0-9]", ""));
                            } catch (Exception e) {
                                return 0L;
                            }
                        }).sum();
                PreBuiltService service = new PreBuiltService();
                List<PreBuilt> allPCs = service.getAllPreBuilts();
                List<Map.Entry<PreBuilt, Integer>> scored = new ArrayList<>();
                for (PreBuilt pc : allPCs) {
                    scored.add(new AbstractMap.SimpleEntry<>(pc, scoreForGame(pc, highestGPU, highestCPU, totalStorage)));
                }
                scored.sort((a, b) -> b.getValue() - a.getValue());
                for (Map.Entry<PreBuilt, Integer> entry : scored) {
                    System.out.println(entry.getKey().getName() + " - Score: " + entry.getValue());
                }
                List<PreBuilt> topThree = selectByPriceTier(scored);
                javafx.application.Platform.runLater(() -> {
                    navigateToSuggestions(topThree, "gameSearch.fxml", "← Back to Games");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private String getHighestLibraryGPU() {
        List<String> gpus = new ArrayList<>();
        if (library != null) {
            for (SteamGame g : library) {
                if (g.getRecReq() != null && g.getRecReq().getGpu() != null) {
                    gpus.add(g.getRecReq().getGpu().toLowerCase());
                }
            }
        }
        selectedGameGPUs = gpus;
        return getHighestGameGPU();
    }

    private String getHighestLibraryCPU() {
        String[] tiers = {"i9", "ryzen 9", "i7", "ryzen 7", "i5", "ryzen 5", "i3", "ryzen 3"};
        List<String> cpus = new ArrayList<>();
        if (library != null) {
            for (SteamGame g : library) {
                if (g.getRecReq() != null && g.getRecReq().getCpu() != null) {
                    cpus.add(g.getRecReq().getCpu().toLowerCase());
                }
            }
        }
        for (String tier : tiers) {
            for (String cpu : cpus) {
                if (cpu.contains(tier)) return cpu;
            }
        }
        return cpus.isEmpty() ? "" : cpus.get(0);
    }
}