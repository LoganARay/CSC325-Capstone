package edu.farmingdale.csc325capstone.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import edu.farmingdale.csc325capstone.model.Part;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * One-time utility used to upload PC part data from JSON into Firestore.
 * Only run manually if you need to repopulate the database.
 */
public class PartsUploader {

    public static void main(String[] args) {
        try {

            // 1. Initialize Firebase connection
            initializeFirebase();

            // 2. Read all parts from merged JSON file into Java objects
            List<Part> parts = readPartsFromJson("data/parts.json");

            // Print how many parts were loaded from JSON
            System.out.println("Total parts loaded: " + parts.size());

            // Counters for reporting results
            int uploadedCount = 0;
            int skippedCount = 0;

            // 3. Loop through each part and upload it
            for (Part part : parts) {

                // Determine which Firestore collection this part belongs to
                String collectionName = getCollectionName(part.getCategory());

                // If category is unknown, skip it and log it
                if (collectionName == null) {
                    System.out.println("Skipping unknown category: " + part.getCategory());
                    skippedCount++;
                    continue;
                }

                // Upload the part to Firestore
                uploadPart(part, collectionName);
                uploadedCount++;
            }

            // 4. Print final summary of upload results
            System.out.println("Upload complete.");
            System.out.println("Uploaded: " + uploadedCount);
            System.out.println("Skipped: " + skippedCount);

        } catch (Exception e) {
            // Print any errors that occur during execution
            e.printStackTrace();
        }
    }

    /**
     * Initializes Firebase using a service account key file.
     */
    private static void initializeFirebase() throws Exception {

        // Load the Firebase service account credentials from local file
        FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

        // Build Firebase configuration using the credentials
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Initialize Firebase only once
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        // Get Firestore instance to confirm connection
        Firestore db = FirestoreClient.getFirestore();
        System.out.println("Firestore initialized!");
    }

    /**
     * Reads a JSON file and converts it into a List of Part objects.
     */
    private static List<Part> readPartsFromJson(String filePath) throws Exception {

        // Create Jackson ObjectMapper (used to parse JSON)
        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file and convert it into List<Part>
        return mapper.readValue(
                new File(filePath),
                new TypeReference<List<Part>>() {}
        );
    }

    /**
     * Uploads a single Part object into Firestore.
     * Uses the part ID as the document ID.
     */
    private static void uploadPart(Part part, String collectionName) throws Exception {

        // Get Firestore database instance
        Firestore db = FirestoreClient.getFirestore();

        // Write the part to Firestore under:
        // collectionName / partId
        ApiFuture<WriteResult> future = db.collection(collectionName)
                .document(part.getId())
                .set(part);

        // Wait for the write operation to complete
        future.get();

        // Print confirmation message
        System.out.println("Uploaded " + part.getId() + " to " + collectionName);
    }

    /**
     * Maps a category from JSON to a Firestore collection name.
     */
    private static String getCollectionName(String category) {

        // If category is null, skip it
        if (category == null) {
            return null;
        }

        // Normalize category (trim spaces + lowercase)
        return switch (category.trim().toLowerCase()) {
            case "cpu" -> "cpus";
            case "motherboard" -> "motherboards";
            case "video card" -> "gpus";
            case "ram" -> "ram";
            case "power supply" -> "psus";
            case "case" -> "cases";

            // Storage (internal/external)
            case "internal hard drive", "external hard drive" -> "storage";

            // Unknown category → skip
            default -> null;
        };
    }
}