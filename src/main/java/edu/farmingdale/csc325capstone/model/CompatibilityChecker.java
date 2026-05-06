package edu.farmingdale.csc325capstone.model;

import edu.farmingdale.csc325capstone.model.Part;
import java.util.Map;

public class CompatibilityChecker {

    // CPU – Motherboard: socket must match
    public static boolean isCpuMotherboardCompatible(Part cpu, Part mobo) {
        if (cpu == null || mobo == null) return true;
        String cpuSocket = cpu.getSpec("socket");
        String moboSocket = mobo.getSpec("socket");
        return cpuSocket != null && cpuSocket.equalsIgnoreCase(moboSocket);
    }

    // RAM – Motherboard: type (DDR4/DDR5) and speed
    public static boolean isRamMotherboardCompatible(Part ram, Part mobo) {
        if (ram == null || mobo == null) return true;
        String ramType = ram.getSpec("type");
        String moboRamType = mobo.getSpec("memory_type");
        if (ramType == null || moboRamType == null) return true;
        if (!ramType.equalsIgnoreCase(moboRamType)) return false;

        int ramSpeed = (int) ram.getSpecAsDouble("speed", 0);
        int moboMaxSpeed = (int) mobo.getSpecAsDouble("max_memory_speed", Integer.MAX_VALUE);
        return ramSpeed <= moboMaxSpeed;
    }

    // GPU – Case: max GPU length
    public static boolean isGpuCaseCompatible(Part gpu, Part computerCase) {
        if (gpu == null || computerCase == null) return true;
        double gpuLength = gpu.getSpecAsDouble("length", 0);
        double caseMaxGpuLength = computerCase.getSpecAsDouble("max_gpu_length", Double.MAX_VALUE);
        return gpuLength <= caseMaxGpuLength;
    }

    // Motherboard – Case: form factor compatibility
    public static boolean isMotherboardCaseCompatible(Part mobo, Part computerCase) {
        if (mobo == null || computerCase == null) return true;
        String moboFormFactor = mobo.getSpec("form_factor");
        String caseSupported = computerCase.getSpec("supported_motherboards");
        if (moboFormFactor == null || caseSupported == null) return true;
        return caseSupported.toLowerCase().contains(moboFormFactor.toLowerCase());
    }

    // PSU – Estimated total wattage: PSU wattage >= total estimated
    public static boolean isPsuSufficient(Part psu, int estimatedWattage) {
        if (psu == null) return true;
        int psuWattage = (int) psu.getSpecAsDouble("wattage", 0);
        return psuWattage >= estimatedWattage;
    }

    // Estimate total power draw (CPU TDP + GPU TDP + 50W buffer)
    public static int estimateTotalWattage(Part cpu, Part gpu) {
        int cpuTdp = (int) cpu.getSpecAsDouble("tdp", 65);
        int gpuTdp = (int) gpu.getSpecAsDouble("tdp", 150);
        return cpuTdp + gpuTdp + 50;
    }
}