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

    protected int scoreForGame(PreBuilt pc, String gameGPU, long totalStorageGB) {
        int score = 0;
        String gpu = pc.getGPU() != null ? pc.getGPU().toLowerCase() : "";
        String storage = pc.getStorage() != null ? pc.getStorage().toLowerCase() : "";
        if (!gameGPU.isEmpty()) {
            if (gameGPU.contains("4090") || gameGPU.contains("4080")) {
                if (gpu.contains("4090") || gpu.contains("4080")) score += 3;
                else if (gpu.contains("4070")) score += 1;
            } else if (gameGPU.contains("4070") || gameGPU.contains("3080")) {
                if (gpu.contains("4070") || gpu.contains("4080") || gpu.contains("4090")) score += 3;
                else if (gpu.contains("3080") || gpu.contains("4060")) score += 2;
            } else if (gameGPU.contains("3060") || gameGPU.contains("2070")) {
                if (gpu.contains("3060") || gpu.contains("4060")) score += 2;
                else if (gpu.contains("3070") || gpu.contains("4070")) score += 3;
            } else {
                if (gpu.contains("3060") || gpu.contains("4060")) score += 1;
            }
        }
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