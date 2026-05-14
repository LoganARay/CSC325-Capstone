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
}
