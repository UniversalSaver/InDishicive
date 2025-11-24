package data_access;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.search_ingredients.SearchIngredientsDataAccessInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for fetching ingredient data from TheMealDB API.
 * Uses JSON parsing library for proper data handling.
 */
public class MealDBIngredientDataAccess implements SearchIngredientsDataAccessInterface {

    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";

    /**
     * Fetches all available ingredients from TheMealDB API.
     * @return a list of ingredient names
     * @throws RuntimeException if the API request fails
     */
    @Override
    public List<String> getAllIngredients() {
        List<String> ingredients = new ArrayList<>();
        
        try {
            URI uri = URI.create(API_URL);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Parse JSON response using org.json library
            String jsonResponse = response.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray mealsArray = jsonObject.getJSONArray("meals");
            
            // Extract ingredient names from the JSON array
            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject meal = mealsArray.getJSONObject(i);
                String ingredientName = meal.getString("strIngredient");
                ingredients.add(ingredientName);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch ingredients from API: " + e.getMessage());
        }
        
        return ingredients;
    }
}

