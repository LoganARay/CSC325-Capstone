package edu.farmingdale.csc325capstone;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class User {
    private String name;
    private String email;
    private String password;
    private List<Map<String, Object>> builds;

    public User(String name, String email, String password, List<Map<String, Object>> builds){
        this.name=name;
        this.email=email;
        this.password=password;
        this.builds=builds;
    }

    public void updateBuilds(String cpu, String gpu, String ram, String motherboard, String storage, String psu, String pcCase, String buildName) throws ExecutionException, InterruptedException {
        Map<String, Object> build = new HashMap<>();
        Map<String, Object> buildInfo = new HashMap<>();
        buildInfo.put("name", buildName);
        buildInfo.put("cpu", cpu);
        buildInfo.put("ram", ram);
        buildInfo.put("gpu", gpu);
        buildInfo.put("psu", psu);
        buildInfo.put("case", pcCase);
        buildInfo.put("motherboard", motherboard);
        buildInfo.put("storage", storage);
        build.put(buildName, buildInfo);

        HelloApplication.fstore.collection("Users")
                .document(email)
                .update("builds", FieldValue.arrayUnion(buildInfo))
                .get();

        builds.add(buildInfo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Map<String, Object>> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Map<String, Object>> builds) {
        this.builds = builds;
    }
}
