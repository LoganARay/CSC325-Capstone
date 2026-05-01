package edu.farmingdale.csc325capstone.service;

import edu.farmingdale.csc325capstone.model.Part;

// OkHttp is used to send HTTP requests to the OpenAI API
import okhttp3.*;

// JSON library used to build request and read response
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AIService {

    /**
     * Main method that:
     * 1. Builds a prompt from selected PC parts
     * 2. Sends request to OpenAI API
     * 3. Returns AI-generated response
     */
    public String analyzeBuild(List<Part> selectedParts, String apiKey) {

        // If no API key is provided, stop early
        if (apiKey == null || apiKey.isBlank()) {
            return "Please set your OpenAI API key first.";
        }

        try {
            // Convert selected parts into a readable AI prompt
            String prompt = buildPrompt(selectedParts);

            // Create HTTP client (used to send API request)
            OkHttpClient client = new OkHttpClient();

            // Build JSON request body
            JSONObject json = new JSONObject();

            // Specify which AI model to use
            json.put("model", "gpt-5.4-mini");

            // The actual input text sent to the AI
            json.put("input", prompt);

            // Convert JSON object into HTTP request body
            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.get("application/json")
            );

            // Build HTTP request
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/responses") // OpenAI endpoint

                    // Authorization header with API key
                    // trim() prevents newline errors (important fix)
                    .addHeader("Authorization", "Bearer " + apiKey.trim())

                    .addHeader("Content-Type", "application/json")
                    .post(body) // Send as POST request
                    .build();

            // Send request and wait for response
            try (Response response = client.newCall(request).execute()) {

                // Convert response into string
                String responseBody = response.body().string();

                // If request failed, return error message
                if (!response.isSuccessful()) {
                    return "AI request failed:\n" + responseBody;
                }

                // Parse JSON response
                JSONObject result = new JSONObject(responseBody);



                JSONArray output = result.getJSONArray("output");
                JSONObject first = output.getJSONObject(0);

                JSONArray content = first.getJSONArray("content");
                JSONObject textObj = content.getJSONObject(0);

                // Extract the actual AI-generated text
                return textObj.getString("text");
            }

        } catch (Exception e) {
            // Print error for debugging
            e.printStackTrace();

            return "Error getting AI response.";
        }
    }

    /**
     * Helper method that builds a prompt for the AI.
     * Converts selected PC parts into readable text.
     */
    private String buildPrompt(List<Part> selectedParts) {

        StringBuilder prompt = new StringBuilder();

        // Tell AI how to behave
        prompt.append("You are a PC build advisor. Analyze this PC build in simple terms.\n\n");

        // Add all selected parts
        prompt.append("Selected parts:\n");

        for (Part part : selectedParts) {
            prompt.append("- ")
                    .append(part.getName())       // Part name
                    .append(" | Category: ")
                    .append(part.getCategory())   // CPU, GPU, etc.
                    .append(" | Price: $")
                    .append(part.getPrice())      // Price
                    .append("\n");
        }

        // Tell AI what type of answer to give
        prompt.append("\nGive:\n");
        prompt.append("1. What this build is good for\n");
        prompt.append("2. Any weak points\n");
        prompt.append("3. Upgrade suggestions\n");
        prompt.append("Keep it short.");

        return prompt.toString();
    }
}