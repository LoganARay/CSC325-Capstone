package edu.farmingdale.csc325capstone.PcParts;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import edu.farmingdale.csc325capstone.HelloApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SandBoxParts {

    public Map<String, Map<String, Object>> loadRepo(String name) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(name).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        Map<String, Map<String, Object>> cases = new HashMap<>();
        for (QueryDocumentSnapshot doc : documents) {
            cases.put(doc.getId(), doc.getData());
        }
        return cases;
    }
}
