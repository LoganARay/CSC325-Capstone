package edu.farmingdale.csc325capstone.PcParts;

import edu.farmingdale.csc325capstone.HelloApplication;

import java.util.HashMap;
import java.util.Map;

public class UserBuilds {
    private String caseDir;
    private String cpuDir;
    private String gpuDir;
    private String psusDir;
    private String ramDir;
    private String motherboardDir;
    private String storageDir;

    public UserBuilds(){
        caseDir=null;
        cpuDir=null;
        gpuDir=null;
        psusDir=null;
        ramDir=null;
        motherboardDir=null;
        storageDir=null;
    }

    public String getCaseDir() {
        return caseDir;
    }

    public void setCaseDir(String caseDir) {
        this.caseDir = caseDir;
    }

    public String getCpuDir() {
        return cpuDir;
    }

    public void setCpuDir(String cpuDir) {
        this.cpuDir = cpuDir;
    }

    public String getGpuDir() {
        return gpuDir;
    }

    public void setGpuDir(String gpuDir) {
        this.gpuDir = gpuDir;
    }

    public String getPsusDir() {
        return psusDir;
    }

    public void setPsusDir(String psusDir) {
        this.psusDir = psusDir;
    }

    public String getRamDir() {
        return ramDir;
    }

    public void setRamDir(String ramDir) {
        this.ramDir = ramDir;
    }

    public String getMotherboardDir() {
        return motherboardDir;
    }

    public void setMotherboardDir(String motherboardDir) {
        this.motherboardDir = motherboardDir;
    }

    public String getStorageDir() {
        return storageDir;
    }

    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }

//    public double getTotalValue(){
//        double total=0.0;
//        total=total + dirCheck(caseDir, "cases");
//        total=total +dirCheck(cpuDir, "cpu");
//        total=total +dirCheck(gpuDir, "gpu");
//        total=total +dirCheck(storageDir, "storage");
//        total=total +dirCheck(ramDir, "ram");
//        total=total +dirCheck(motherboardDir, "motherboards");
//        total=total +dirCheck(psusDir, "psus");
//        return total;
//    }
//
//    public double dirCheck(String dir, String loc){
//        if(dir==null){ return 0.0; }
//
//        switch (loc){
//            case "cases": return (double) HelloApplication.cases.get(dir).get("price");
//            case "gpu": return (double) HelloApplication.gpus.get(dir).get("price");
//            case "cpu": return (double) HelloApplication.cpus.get(dir).get("price");
//            case "motherboards": return (double) HelloApplication.motherboards.get(dir).get("price");
//            case "storage": return (double) HelloApplication.storage.get(dir).get("price");
//            case "ram": return (double) HelloApplication.ram.get(dir).get("price");
//            case "psus": return (double) HelloApplication.psus.get(dir).get("price");
//        }
//        return 0.0;
//    }
//
//    public String getWattage(){
//        Map<String, Object> temp= (Map<String, Object>) HelloApplication.psus.get(psusDir).get("specs");
//        return temp.get("wattage") + "";
//    }
//
//    public HashMap<String, Object> formatParts(){
//        HashMap<String, Object> temp= new HashMap<String, Object>();
//        temp.put("case", caseDir);
//        temp.put("cpu", cpuDir);
//        temp.put("gpu", gpuDir);
//        temp.put("motherboard", motherboardDir);
//        temp.put("ram", ramDir);
//        temp.put("psus", psusDir);
//        temp.put("storage", storageDir);
//        return temp;
//    }
}
