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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

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

    public static User user=null;
    @Override
    public void start(Stage stage) throws IOException {
        SandBoxParts c= new SandBoxParts();
        try {
            CollectionReference parts=fstore.collection("Parts");
            cases = (List<Map<String, Object>>) parts.document("cases").get().get().get("list");
            cpus = (List<Map<String, Object>>) parts.document("cpus").get().get().get("list");
            gpus= (List<Map<String, Object>>) parts.document("gpus").get().get().get("list");
            motherboards = (List<Map<String, Object>>) parts.document("motherboards").get().get().get("list");

        scene = new Scene(loadFXML("homeView.fxml"), 950, 750);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(loadFXML("homeView.fxml"));
        stage.setTitle("Steam Builder");
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/edu/farmingdale/csc325capstone/SteamBuilderLogo2.png"))
        );
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
        DocumentSnapshot snapshot = fstore
                .collection(database)
                .document(partMap.get(name))
                .get()
                .get();

        if (snapshot.exists()) {
            part.setBrand((String)snapshot.get("brand"));
            Object priceObj = snapshot.get("price");
            part.setPrice(priceObj instanceof Number ? ((Number) priceObj).doubleValue() : 0.0);
            part.setLink((String)snapshot.get("link"));
            Object yearObj = snapshot.get("year");
            part.setYear(yearObj instanceof Number ? ((Number) yearObj).intValue() : null);
            part.setSpecs((Map<String, Object>) snapshot.get("specs"));
        }

        return part;
    }



    public static void main(String[] args) {
        launch(args);
    }
}