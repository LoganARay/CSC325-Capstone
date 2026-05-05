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

    public void fillingOrder() throws Exception {
        if(HelloApplication.cases==null){
            HelloApplication.cases= loadRepo("cases");
        }
        else if(HelloApplication.cpus==null){
            HelloApplication.cpus= loadRepo("cpus");
        }
        else if(HelloApplication.gpus==null){
            HelloApplication.gpus= loadRepo("gpus");
        }
        else if(HelloApplication.motherboards==null){
            HelloApplication.motherboards= loadRepo("motherboards");
        }
        else if(HelloApplication.psus==null && HelloApplication.ram==null){
            HelloApplication.psus= loadRepo("psus");
        }
        else if(HelloApplication.ram==null){
            HelloApplication.ram= loadRepo("ram");
        }
       else if (HelloApplication.storage==null){
            HelloApplication.storage = loadRepo("storage");
        }
    }
}
