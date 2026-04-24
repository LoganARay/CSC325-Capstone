package edu.farmingdale.csc325capstone;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
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

public class HelloApplication extends Application {
    public static Scene scene;

    public  static FirestoreContent contxtFirebase = new FirestoreContent();
    public  static Firestore fstore = contxtFirebase.firebase();
    public  static CollectionReference steamGames;
    public static Map<String, Map<String, Object>> cases;
    public static Map<String, Map<String, Object>> cpus;
    public static Map<String, Map<String, Object>> gpus;
    public static Map<String, Map<String, Object>> motherboards;
    public static Map<String, Map<String, Object>> psus;
    public static Map<String, Map<String, Object>> ram;
    public static Map<String, Map<String, Object>> storage;

    public static User user=null;
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        steamGames= fstore.collection("FullSteamGames");
        SandBoxParts c= new SandBoxParts();
        try {
            cases=c.loadRepo("cases");
            cpus=c.loadRepo("cpus");
            gpus=c.loadRepo("gpus");
            motherboards=c.loadRepo("motherboards");
            psus=c.loadRepo("psus");
            ram=c.loadRepo("ram");
            storage=c.loadRepo("storage");
        scene = new Scene(loadFXML("homeView.fxml"), 950, 750);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(loadFXML("homeView.fxml"));
        stage.setTitle("Steam Builder");

        stage.setScene(scene);
        stage.show();
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static DocumentSnapshot gett(String firstLetter){
        try {
            return steamGames.document(firstLetter).get().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, String> getNamesParts(Map<String, Map<String, Object>> part){
        HashMap<String, String> ids= new HashMap<String, String>();
        Set<String> source= part.keySet();
        Object[] id = source.toArray();
        int count=0;
        for(Map<String, Object> name: part.values()){
            if((double)name.get("price")!=0.0) {
                ids.put(name.get("name") + "", id[count] + "");
            }
            count++;
        }
        return ids;
    }

    public static void setCurrentUser(String name, String email, String password, HashMap<String, Object> builds){
        user= new User(name, email, password, builds);
    }

    public static Part getPartByName(Map<String, Map<String, Object>> partMap, String name, String category) {
        for (Map.Entry<String, Map<String, Object>> entry : partMap.entrySet()) {
            Map<String, Object> data = entry.getValue();
            if (name.equals(data.get("name"))) {
                Part part = new Part();
                part.setId(entry.getKey());
                part.setName((String) data.get("name"));
                part.setCategory(category);
                part.setBrand((String) data.get("brand"));
                Object priceObj = data.get("price");
                part.setPrice(priceObj instanceof Number ? ((Number) priceObj).doubleValue() : 0.0);
                part.setLink((String) data.get("link"));
                Object yearObj = data.get("year");
                part.setYear(yearObj instanceof Number ? ((Number) yearObj).intValue() : null);
                part.setSpecs((Map<String, Object>) data.get("specs"));
                return part;
            }
        }
        return null;
    }



    public static void main(String[] args) {
        launch(args);
    }
}