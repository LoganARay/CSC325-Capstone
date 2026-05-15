package edu.farmingdale.csc325capstone.service;

public class GameRequirementsService {
    private String gpu;
    private String cpu;
    private String ram;

    public GameRequirementsService(String gpu, String cpu, String ram) {
        this.gpu = gpu;
        this.cpu = cpu;
        this.ram = ram;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
}
