package edu.farmingdale.csc325capstone.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import edu.farmingdale.csc325capstone.FirestoreContent;
import edu.farmingdale.csc325capstone.model.PreBuilt;

import java.util.ArrayList;
import java.util.List;

public class PreBuiltService {

    private final Firestore db;

    public PreBuiltService() {
        this.db = FirestoreContent.firebase();
    }

    public List<PreBuilt> getAllPreBuilts() throws Exception {
        QuerySnapshot snapshot = db.collection("PreBuilds").get().get();
        List<PreBuilt> preBuilts = new ArrayList<>();
        for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
            PreBuilt pc = doc.toObject(PreBuilt.class);
            preBuilts.add(pc);
        }
        return preBuilts;
    }
}