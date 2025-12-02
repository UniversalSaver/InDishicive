package databases.inventory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.inventory.search_ingredients.SearchIngredientsDataAccessInterface;

/**
 * Data Access Object for fetching ingredient data from TheMealDB API.
 * Uses JSON parsing library for proper data handling.
 */
public class MealDbIngredientDataAccess implements SearchIngredientsDataAccessInterface {

    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";

    /**
     * Fetches all available ingredients from TheMealDB API.
     * @return a list of ingredient names
     * @throws IngredientDataAccessException if the API request fails or the response cannot be parsed
     */
    @Override
    public List<String> getAllIngredients() throws IngredientDataAccessException {
        final List<String> ingredients = new ArrayList<>();

        try {
            final URI uri = URI.create(API_URL);
            final HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            final String jsonResponse = response.toString();
            final JSONObject jsonObject = new JSONObject(jsonResponse);
            final JSONArray mealsArray = jsonObject.getJSONArray("meals");

            for (int i = 0; i < mealsArray.length(); i++) {
                final JSONObject meal = mealsArray.getJSONObject(i);
                final String ingredientName = meal.getString("strIngredient");
                ingredients.add(ingredientName);
            }

        }
        catch (Exception ex) {
            throw new IngredientDataAccessException("Failed to fetch ingredients from API", ex);
        }

        return ingredients;
    }
}
