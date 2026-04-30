package edu.farmingdale.csc325capstone.service;

import edu.farmingdale.csc325capstone.model.Part;
import okhttp3.*;                 // Used to make HTTP requests to OpenAI API
import org.json.JSONObject;       // Used to build and parse JSON data

import java.util.List;

public class AIService {

    /**
     * Main method that:
     * 1. Builds a prompt from selected PC parts
     * 2. Sends it to OpenAI API
     * 3. Returns the AI-generated response
     */
    public String analyzeBuild(List<Part> selectedParts, String apiKey) {

        // Check if API key exists before making request
        if (apiKey == null || apiKey.isBlank()) {
            return "Please set your OpenAI API key first.";
        }

        try {
            // Builds the prompt that will be sent to AI
            String prompt = buildPrompt(selectedParts);

            // Create HTTP client (used to send request)
            OkHttpClient client = new OkHttpClient();

            // Create JSON request body
            JSONObject json = new JSONObject();
            json.put("model", "gpt-5.4-mini");     // AI model to use
            json.put("input", prompt);        // The text we want AI to analyze

            // Convert JSON into HTTP request body
            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.get("application/json")
            );

            // Step 4: Build the HTTP POST request
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/responses")  // OpenAI endpoint
                    .addHeader("Authorization", "Bearer " + apiKey) // API key authentication
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            // Step 5: Send request and get response
            try (Response response = client.newCall(request).execute()) {

                // Convert response body into string
                String responseBody = response.body().string();

                // If API call failed, return error message
                if (!response.isSuccessful()) {
                    return "AI request failed:\n" + responseBody;
                }

                // Step 6: Parse JSON response
                JSONObject result = new JSONObject(responseBody);

                if (result.has("output_text")) {
                    return result.getString("output_text");
                }

                return result
                        .getJSONArray("output")
                        .getJSONObject(0)
                        .getJSONArray("content")
                        .getJSONObject(0)
                        .getString("text");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting AI response.";
        }
    }

    /**
     * Helper method that builds the prompt sent to AI.
     * Converts selected Part objects into readable text.
     */
    private String buildPrompt(List<Part> selectedParts) {

        StringBuilder prompt = new StringBuilder();

        // Tell AI how to behave
        prompt.append("You are a PC build advisor. Analyze this PC build in simple language.\n\n");

        // List all selected parts
        prompt.append("Selected parts:\n");

        for (Part part : selectedParts) {
            prompt.append("- ")
                    .append(part.getName())
                    .append(" | Category: ")
                    .append(part.getCategory())
                    .append(" | Price: $")
                    .append(part.getPrice())
                    .append("\n");
        }

        // Tell AI what type of answer we want
        prompt.append("\nGive feedback on:\n");
        prompt.append("1. What this build is good for\n");
        prompt.append("2. Any weak points\n");
        prompt.append("3. Upgrade suggestions\n");
        prompt.append("Keep it short and beginner-friendly.");

        return prompt.toString();
    }
}