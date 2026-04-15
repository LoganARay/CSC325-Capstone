package edu.farmingdale.csc325capstone.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseService {

    // Prevents Firebase from being initialized multiple times
    private static boolean initialized = false;

    public static void initialize() {
        // If already initialized, do nothing
        if (initialized) return;

        try {
            // Load your Firebase service account key file
            FileInputStream serviceAccount =
                    new FileInputStream("serviceAccountKey.json");

            // Build Firebase configuration using credentials
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Only initialize if no app exists yet
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            initialized = true; // Mark as initialized
            System.out.println("Firebase initialized!");

        } catch (Exception e) {
            // Print any errors during initialization
            e.printStackTrace();
        }
    }
}