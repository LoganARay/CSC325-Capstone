package edu.farmingdale.csc325capstone.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import edu.farmingdale.csc325capstone.model.Part;

import java.util.ArrayList;
import java.util.List;

public class PartService {

    public List<Part> getPartsByCollection(String collectionName) throws Exception {
        // Get Firestore database instance
        Firestore db = FirestoreClient.getFirestore();

        // Read all documents from the given collection
        QuerySnapshot snapshot = db.collection(collectionName).get().get();

        // Store converted Part objects here
        List<Part> parts = new ArrayList<>();

        // Convert each Firestore document into a Part object
        for (QueryDocumentSnapshot doc : snapshot) {
            Part part = doc.toObject(Part.class);
            parts.add(part);
        }

        return parts;
    }
    public List<Part> getCompatibleMotherboards(Part selectedCpu) throws Exception {

        String cpuSocket = selectedCpu.getSpec("socket");

        List<Part> motherboards = getPartsByCollection("motherboards");

        List<Part> compatibleMotherboards = new ArrayList<>();

        for (Part motherboard : motherboards) {

            String motherboardSocket = motherboard.getSpec("socket");

            if (cpuSocket != null &&
                    motherboardSocket != null &&
                    motherboardSocket.equalsIgnoreCase(cpuSocket)) {

                compatibleMotherboards.add(motherboard);
            }
        }

        return compatibleMotherboards;
    }

}