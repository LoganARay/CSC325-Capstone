package edu.farmingdale.csc325capstone;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirestoreContent {

    private static Firestore firestore;

    public static Firestore firebase() {
        if (firestore != null) {
            return firestore;
        }

//        try {
//            //InputStream serviceAccount = new java.io.FileInputStream("src/main/resources/file/key.json");
//            InputStream serviceAccount =
//                    FirestoreContent.class.getClassLoader().getResourceAsStream("file/key.json");
//
//            System.out.println("ClassLoader file/key.json = " +
//                    FirestoreContent.class.getClassLoader().getResource("file/key.json"));
//
//            if (serviceAccount == null) {
//                throw new RuntimeException("key.json not found");
//            }
//
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseOptions options = FirebaseOptions.builder()
//                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                        .build();
//
//                FirebaseApp.initializeApp(options);
//                System.out.println("Firebase is initialized");
//            }
//
//            firestore = FirestoreClient.getFirestore();
//            return firestore;
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            InputStream serviceAccount = new java.io.FileInputStream("src/main/resources/file/key.json");

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase is initialized");
            }

            firestore = FirestoreClient.getFirestore();
            return firestore;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}