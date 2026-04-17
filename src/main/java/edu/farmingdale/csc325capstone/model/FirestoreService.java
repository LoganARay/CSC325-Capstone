package edu.farmingdale.csc325capstone.model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.farmingdale.csc325capstone.model.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    private final Firestore db;

    public FirestoreService() {
        FirestoreContent content = new FirestoreContent();
        this.db = content.firebase();
    }

    public List<Part> getPartsByCategory(String category) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("components")
                .whereEqualTo("category", category)
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Part> parts = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            Part part = doc.toObject(Part.class);
            part.setId(doc.getId());
            parts.add(part);
        }
        return parts;
    }

    public void saveBuild(String userId, String buildName, Map<String, String> selectedPartIds, double totalCost, int totalWattage) {
        DocumentReference buildRef = db.collection("users").document(userId)
                .collection("builds").document();
        Map<String, Object> buildData = Map.of(
                "name", buildName,
                "selectedParts", selectedPartIds,
                "totalCost", totalCost,
                "totalWattage", totalWattage,
                "timestamp", FieldValue.serverTimestamp()
        );
        buildRef.set(buildData);
    }

    public void getBuildsForUser(String userId, BuildsCallback callback) {
        db.collection("users").document(userId).collection("builds")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Map<String, Object>> builds = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        builds.add(doc.getData());
                    }
                    callback.onSuccess(builds);
                })
                .addOnFailureListener(callback::onFailure);
    }

    public interface BuildsCallback {
        void onSuccess(List<Map<String, Object>> builds);
        void onFailure(Exception e);
    }
}