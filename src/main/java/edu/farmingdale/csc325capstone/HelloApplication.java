package edu.farmingdale.csc325capstone;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import edu.farmingdale.csc325capstone.PcParts.SandBoxParts;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static ArrayList<String> getNamesParts(Map<String, Map<String, Object>> part){
        ArrayList<String> names= new ArrayList<String>();
        for(Map<String, Object> name: part.values()){
            names.add(name.get("name") + "");
        }
        return names;
    }

    public static ArrayList<String> getNamesCpus(){
        ArrayList<String> names= new ArrayList<String>();
        for(Map<String, Object> name: cpus.values()){
            names.add(name.get("name") + "");
        }
        return names;
    }



    public static void main(String[] args) {
        launch(args);
    }
}