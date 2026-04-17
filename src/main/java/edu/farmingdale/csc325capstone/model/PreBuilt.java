package edu.farmingdale.csc325capstone.model;

public class PreBuilt {

        private String CPU;
        private String GPU;
        private String RAM;
        private String Storage;
        private String Name;
        private double Price;
        private String Link;
        private Object HDMI;
        private Object USB;
        private Object DisplayPort;
        private boolean IsIntel;

        // No-arg constructor required for Firestore
        public PreBuilt() {}

        // Getters
        public String getCPU() { return CPU; }
        public String getGPU() { return GPU; }
        public String getRAM() { return RAM; }
        public String getStorage() { return Storage; }
        public String getName() { return Name; }
        public double getPrice() { return Price; }
        public String getLink() { return Link; }
        public Object getHDMI() { return HDMI; }
        public Object getUSB() { return USB; }
        public Object getDisplayPort() { return DisplayPort; }
        public boolean isIntel() { return IsIntel; }
    }

