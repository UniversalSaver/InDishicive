package databases.inventory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Ingredient;
import logic.inventory.search_ingredients.SearchIngredientsDataAccessInterface;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientDataAccessInterface;

/**
 * Data Access Object for fetching ingredient data from TheMealDB API.
 * Uses JSON parsing library for proper data handling.
 */
public class MealDbIngredientDataAccess implements SearchIngredientsDataAccessInterface,
        AddRecipeIngredientDataAccessInterface {

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
            final JSONArray mealsArray = getMealsArray(uri);

            // Extract ingredient names from the JSON array
            for (int i = 0; i < mealsArray.length(); i++) {
                final JSONObject meal = mealsArray.getJSONObject(i);
                final String ingredientName = meal.getString("strIngredient");
                ingredients.add(ingredientName);
            }
        } catch (IOException | JSONException ingredientFailFetch) {
            throw new IngredientDataAccessException("Failed to fetch ingredients from API", ingredientFailFetch);
        }

        return ingredients;
    }

    private static JSONArray getMealsArray(URI uri) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse JSON response using org.json library
        final String jsonResponse = response.toString();
        final JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getJSONArray("meals");
    }

    @Override
    public List<Ingredient> listPossibleIngredients() {
        List<Ingredient> result = new ArrayList<>();

        List<String> ingredientNames = new ArrayList<>();
        try {
            ingredientNames = getAllIngredients();
        } catch (IngredientDataAccessException dataAccessException) {
            // Do nothing, as already null.
        }

        if (ingredientNames.isEmpty()) {
            result = null;
        } else {
            for (String ingredientName : ingredientNames) {
                result.add(new Ingredient(ingredientName, ""));
            }
        }

        return result;
    }
}

