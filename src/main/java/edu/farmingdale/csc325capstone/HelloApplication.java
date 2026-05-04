package edu.farmingdale.csc325capstone;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import edu.farmingdale.csc325capstone.model.Part;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class HelloApplication extends Application {
    public static Scene scene;

    public  static FirestoreContent contxtFirebase = new FirestoreContent();
    public  static Firestore fstore = contxtFirebase.firebase();
//    public static Map<String, Map<String, Object>> cases=null;
//    public static Map<String, Map<String, Object>> cpus=null;
//    public static Map<String, Map<String, Object>> gpus=null;
//    public static Map<String, Map<String, Object>> motherboards=null;
//    public static Map<String, Map<String, Object>> psus=null;
//    public static Map<String, Map<String, Object>> ram=null;
//    public static Map<String, Map<String, Object>> storage=null;
    public static List<Map<String, Object>> cases=null;
    public static List<Map<String, Object>> cpus=null;
    public static List<Map<String, Object>> gpus=null;
    public static List<Map<String, Object>> motherboards=null;
    public static List<Map<String, Object>> psus=null;
    public static List<Map<String, Object>> ram=null;
    public static List<Map<String, Object>> storage=null;

    public static List<Part> allCpuParts;
    public static List<Part> allGpuParts;
    public static List<Part> allRamParts;
    public static List<Part> allMoboParts;
    public static List<Part> allPsuParts;
    public static List<Part> allCaseParts;
    public static List<Part> allStorageParts;

    public static User user=null;
    @Override
    public void start(Stage stage) throws IOException {
        Label loadingLabel = new Label("Loading parts…");
        loadingLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        Scene loadingScene = new Scene(loadingLabel, 400, 300);
        stage.setScene(loadingScene);
        stage.setTitle("Steam Builder");
        stage.show();

        new Thread(() -> {
            try {
                CollectionReference parts = fstore.collection("Parts");
                cases = (List<Map<String, Object>>) parts.document("cases").get().get().get("list");
                cpus = (List<Map<String, Object>>) parts.document("cpus").get().get().get("list");
                gpus = (List<Map<String, Object>>) parts.document("gpus").get().get().get("list");
                motherboards = (List<Map<String, Object>>) parts.document("motherboards").get().get().get("list");
                psus = (List<Map<String, Object>>) parts.document("psus").get().get().get("list");
                ram = (List<Map<String, Object>>) parts.document("ram").get().get().get("list");
                storage = (List<Map<String, Object>>) parts.document("storage").get().get().get("list");

                Platform.runLater(() -> {
                    try {
                        scene = new Scene(loadFXML("homeView.fxml"), 950, 750);
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }


    public static HashMap<String, String> getNamesParts(List<Map<String, Object>> part, String database) throws ExecutionException, InterruptedException {
        HashMap<String, String> ids= new HashMap<String, String>();
        for(Map<String, Object> name: part){
            //if((double)fstore.collection(database).document(name.get("id")+"").get().get().get("price")!=0.0) {
                ids.put(name.get("name") + "", name.get("id") + "");
            //}
        }
        return ids;
    }

    public static void setCurrentUser(String name, String email, String password, List<Map<String, Object>> builds){
        user= new User(name, email, password, builds);
    }

//    public static Part getPartByName(Map<String, Map<String, Object>> partMap, String name, String category) {
//        for (Map.Entry<String, Map<String, Object>> entry : partMap.entrySet()) {
//            Map<String, Object> data = entry.getValue();
//            if (name.equals(data.get("name"))) {
//                Part part = new Part();
//                part.setId(entry.getKey());
//                part.setName((String) data.get("name"));
//                part.setCategory(category);
//                part.setBrand((String) data.get("brand"));
//                Object priceObj = data.get("price");
//                part.setPrice(priceObj instanceof Number ? ((Number) priceObj).doubleValue() : 0.0);
//                part.setLink((String) data.get("link"));
//                Object yearObj = data.get("year");
//                part.setYear(yearObj instanceof Number ? ((Number) yearObj).intValue() : null);
//                part.setSpecs((Map<String, Object>) data.get("specs"));
//                return part;
//            }
//        }
//        return null;
//    }

    public static Part getPartByName(HashMap<String, String> partMap, String database, String name, String category) throws ExecutionException, InterruptedException {
        Part part=new Part();
        part.setId(partMap.get(name));
        part.setName(name);
        part.setCategory(category);
        part.setBrand((String)fstore.collection(database).document(partMap.get(name)).get().get().get("brand") );
        Object priceObj = fstore.collection(database).document(partMap.get(name)).get().get().get("price");
        part.setPrice(priceObj instanceof Number ? ((Number) priceObj).doubleValue() : 0.0);
        part.setLink((String)fstore.collection(database).document(partMap.get(name)).get().get().get("link"));
        Object yearObj = fstore.collection(database).document(partMap.get(name)).get().get().get("year");
        part.setYear(yearObj instanceof Number ? ((Number) yearObj).intValue() : null);
        part.setSpecs((Map<String, Object>) fstore.collection(database).document(partMap.get(name)).get().get().get("specs"));
        return part;
    }

    public static void loadAllParts() throws Exception {
        allCpuParts   = loadPartList(cpus, "cpus", "CPU");
        allGpuParts   = loadPartList(gpus, "gpus", "Video Card");
        allRamParts   = loadPartList(ram, "ram", "Memory");
        allMoboParts  = loadPartList(motherboards, "motherboards", "Motherboard");
        allCaseParts  = loadPartList(cases, "cases", "Case");
        allPsuParts   = loadPartList(psus, "psus", "Power Supply");
        allStorageParts = loadPartList(storage, "storage", "Internal Hard Drive");
    }

    private static List<Part> loadPartList(List<Map<String, Object>> partialList, String collection, String category) throws Exception {
        List<Part> parts = new ArrayList<>();
        for (Map<String, Object> entry : partialList) {
            String id = (String) entry.get("id");
            String name = (String) entry.get("name");
            DocumentSnapshot doc = fstore.collection(collection).document(id).get().get();
            if (doc.exists()) {
                Part part = new Part();
                part.setId(id);
                part.setName(name);
                part.setCategory(category);
                part.setBrand((String) doc.get("brand"));
                Object priceObj = doc.get("price");
                part.setPrice(priceObj instanceof Number ? ((Number) priceObj).doubleValue() : 0.0);
                part.setLink((String) doc.get("link"));
                Object yearObj = doc.get("year");
                part.setYear(yearObj instanceof Number ? ((Number) yearObj).intValue() : null);
                part.setSpecs((Map<String, Object>) doc.get("specs"));
                parts.add(part);
            }
        }
        return parts;
    }

    public static void main(String[] args) {
        launch(args);
    }
}