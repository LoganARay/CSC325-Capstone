package edu.farmingdale.csc325capstone;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Firestore fstore;
        FirebaseAuth fauth;
        FirestoreContent contxtFirebase = new FirestoreContent();
        fstore = contxtFirebase.firebase();
        update(fstore);
        search(fstore);
        stage.show();
    }

    public static void update(Firestore fstore) throws ExecutionException, InterruptedException {
        DocumentReference docUsers = fstore.collection("Database name").document("10");

        Map<String, Object> data = new HashMap<>();
        data.put("Variable name1", "info");
        data.put("Variable name2", "info");

        ApiFuture<WriteResult> result = docUsers.set(data);
        // If you have an array inside your index and want to put something in there
    }

    public static void search(Firestore fstore) {
        ApiFuture<QuerySnapshot> future = fstore.collection("Database name").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                for (QueryDocumentSnapshot document : documents) {
                    if (document.get("Variable name1").equals("searching for") && document.get("Variable name2").equals("searching for")) {
                        System.out.println("found them!");
                    }
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

