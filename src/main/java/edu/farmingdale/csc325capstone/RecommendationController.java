package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.model.PreBuilt;

import java.util.*;

public abstract class RecommendationController {

    protected List<String> selectedGameGPUs = new ArrayList<>();

    protected String getHighestGameGPU() {
        String[] tiers = {"4090","4080","5070","9070","4070","3080","3070","4060","3060","2070","2060","1080","1070"};
        for (String tier : tiers) {
            for (String gpu : selectedGameGPUs) {
                if (gpu.contains(tier)) return gpu;
            }
        }
        return selectedGameGPUs.isEmpty() ? "" : selectedGameGPUs.get(0);
    }

    protected int scoreForGame(PreBuilt pc, String gameGPU, String gameCPU, long totalStorageGB) {
        int score = 0;
        String gpu = pc.getGPU() != null ? pc.getGPU().toLowerCase() : "";
        String cpu = pc.getCPU() != null ? pc.getCPU().toLowerCase() : "";
        String storage = pc.getStorage() != null ? pc.getStorage().toLowerCase() : "";

        if (!gameGPU.isEmpty()) {
            if (gameGPU.contains("4090") || gameGPU.contains("4080") ||
                    gameGPU.contains("rx 7900") || gameGPU.contains("rx 7800") ||
                    gameGPU.contains("rx 9070") || gameGPU.contains("rx 9080")) {
                if (gpu.contains("4090") || gpu.contains("4080") ||
                        gpu.contains("4070 ti") || gpu.contains("rx 7900 xt") ||
                        gpu.contains("rx 9080")) score += 3;
                else if (gpu.contains("4070") || gpu.contains("rx 7800") ||
                        gpu.contains("rx 9070 xt") || gpu.contains("rx 9070")) score += 2;
                else if (gpu.contains("4060 ti") || gpu.contains("rx 7700")) score += 1;
            } else if (gameGPU.contains("4070") || gameGPU.contains("4070 ti") ||
                    gameGPU.contains("3080") || gameGPU.contains("rx 6800") ||
                    gameGPU.contains("rx 6900") || gameGPU.contains("rx 7700") ||
                    gameGPU.contains("rx 9060")) {
                if (gpu.contains("4070 ti") || gpu.contains("4080") || gpu.contains("4090") ||
                        gpu.contains("rx 7800") || gpu.contains("rx 7900") ||
                        gpu.contains("rx 9070") || gpu.contains("rx 9080")) score += 3;
                else if (gpu.contains("4070") || gpu.contains("rx 6800") ||
                        gpu.contains("rx 7700") || gpu.contains("rx 9060")) score += 2;
                else if (gpu.contains("4060 ti") || gpu.contains("4060") ||
                        gpu.contains("rx 6700") || gpu.contains("rx 7600")) score += 1;
            } else if (gameGPU.contains("3060") || gameGPU.contains("3060 ti") ||
                    gameGPU.contains("2070") || gameGPU.contains("1080") ||
                    gameGPU.contains("1070") || gameGPU.contains("rx 580") ||
                    gameGPU.contains("rx 5700") || gameGPU.contains("radeon") ||
                    gameGPU.contains("directx") || gameGPU.contains("geforce") ||
                    gameGPU.contains("hd ")) {
                if (gpu.contains("4060 ti") || gpu.contains("4060") ||
                        gpu.contains("rx 6600 xt") || gpu.contains("rx 6600") ||
                        gpu.contains("rx 7600")) score += 3;
                else if (gpu.contains("4070") || gpu.contains("rx 6700 xt") ||
                        gpu.contains("rx 6700") || gpu.contains("rx 7700")) score += 2;
                else if (gpu.contains("4080") || gpu.contains("4090") ||
                        gpu.contains("rx 7900") || gpu.contains("rx 9070") ||
                        gpu.contains("rx 9080")) score += 1;
            } else {
                if (gpu.contains("4060 ti") || gpu.contains("4060") ||
                        gpu.contains("rx 6600") || gpu.contains("rx 7600")) score += 2;
                else if (gpu.contains("4070") || gpu.contains("rx 6700") ||
                        gpu.contains("rx 7700") || gpu.contains("rx 9060")) score += 1;
            }
        }

        // CPU scoring
        if (gameCPU != null && !gameCPU.isEmpty()) {
            if (gameCPU.contains("i9") || gameCPU.contains("ryzen 9")) {
                if (cpu.contains("i9") || cpu.contains("ryzen 9")) score += 3;
                else if (cpu.contains("i7") || cpu.contains("ryzen 7")) score += 2;
                else if (cpu.contains("i5") || cpu.contains("ryzen 5")) score += 1;
            } else if (gameCPU.contains("i7") || gameCPU.contains("ryzen 7")) {
                if (cpu.contains("i7") || cpu.contains("ryzen 7") ||
                        cpu.contains("i9") || cpu.contains("ryzen 9")) score += 3;
                else if (cpu.contains("i5") || cpu.contains("ryzen 5")) score += 2;
            } else if (gameCPU.contains("i5") || gameCPU.contains("ryzen 5")) {
                if (cpu.contains("i5") || cpu.contains("ryzen 5") ||
                        cpu.contains("i7") || cpu.contains("ryzen 7")) score += 2;
                else if (cpu.contains("i3") || cpu.contains("ryzen 3")) score += 1;
            } else {
                score += 1;
            }
        }

        // Storage boost
        if (totalStorageGB >= 700) {
            if (storage.contains("2tb")) score += 2;
            else if (storage.contains("1tb")) score += 1;
        }

        return score;
    }

    protected List<PreBuilt> selectDiverseTopThree(List<Map.Entry<PreBuilt, Integer>> scored) {
        List<PreBuilt> result = new ArrayList<>();
        if (scored.isEmpty()) return result;
        PreBuilt first = scored.get(0).getKey();
        result.add(first);
        String firstGPU = first.getGPU() != null ? first.getGPU().toLowerCase() : "";
        double firstPrice = first.getPrice();
        for (Map.Entry<PreBuilt, Integer> entry : scored) {
            if (result.size() >= 3) break;
            if (result.contains(entry.getKey())) continue;
            PreBuilt pc = entry.getKey();
            String pcGPU = pc.getGPU() != null ? pc.getGPU().toLowerCase() : "";
            if (!pcGPU.equals(firstGPU) || Math.abs(pc.getPrice() - firstPrice) >= 100) result.add(pc);
        }
        for (Map.Entry<PreBuilt, Integer> entry : scored) {
            if (result.size() >= 3) break;
            if (!result.contains(entry.getKey())) result.add(entry.getKey());
        }
        return result;
    }

    protected void navigateToSuggestions(List<PreBuilt> topThree, String sourceFxml, String backLabel) {
        AllSuggestionsController.setTopThree(topThree);
        AllSuggestionsController.setSource(sourceFxml, backLabel);
        try { HelloApplication.setRoot("allSuggestionsView.fxml"); }
        catch (Exception e) { e.printStackTrace(); }
    }
}