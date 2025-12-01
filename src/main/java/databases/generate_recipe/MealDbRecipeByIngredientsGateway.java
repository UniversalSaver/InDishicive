package databases.generate_recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Ingredient;
import entity.Recipe;
import logic.generate_recipe.generate_by_ingredients.RecipeByIngredientsGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MealDbRecipeByIngredientsGateway implements RecipeByIngredientsGateway {

    private static final Logger LOGGER = Logger.getLogger(MealDbRecipeByIngredientsGateway.class.getName());
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String MEALS_KEY = "meals";
    private static final String TITLE_KEY = "strMeal";
    private static final String THUMB_KEY = "strMealThumb";
    private static final String ID_KEY = "idMeal";

    private final OkHttpClient client = new OkHttpClient();

    /**
     * Executes a GET request to the MealDB API.
     *
     * @param path the API endpoint path to append to the base URL.
     * @return the response body as a string, or null if the request failed or body was empty.
     * @throws IOException if a network error occurs.
     */
    private String get(String path) throws IOException {
        final String url = BASE_URL + path;

        final Request request = new Request.Builder()
                .url(url)
                .build();

        String body = null;

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                body = response.body().string();
            }
        }

        return body;
    }

    /**
     * Finds a list of recipes that contain all the provided ingredients.
     *
     * @param ingredients the list of ingredient names to search for.
     * @return a list of fully populated Recipe objects matching the criteria.
     */
    @Override
    public List<Recipe> findByIngredients(List<String> ingredients) {
        List<Recipe> recipes = List.of();

        final List<String> cleaned = cleanIngredients(ingredients);
        if (!cleaned.isEmpty()) {
            final Map<String, String> titleToThumb = new LinkedHashMap<>();
            final Map<String, String> titleToId = new LinkedHashMap<>();

            final Set<String> commonTitles = computeCommonTitles(cleaned, titleToThumb, titleToId);

            if (!commonTitles.isEmpty()) {
                recipes = buildRecipes(commonTitles, titleToId);
            }
        }

        return recipes;
    }

    /**
     * Cleans the input list of ingredients by trimming whitespace and removing duplicates or nulls.
     *
     * @param ingredients the raw list of ingredients.
     * @return a new list containing valid, unique ingredient strings.
     */
    private List<String> cleanIngredients(List<String> ingredients) {
        final List<String> cleaned = new ArrayList<>();

        if (ingredients != null) {
            for (final String raw : ingredients) {
                if (raw != null) {
                    final String trimmed = raw.trim();
                    if (!trimmed.isEmpty() && !cleaned.contains(trimmed)) {
                        cleaned.add(trimmed);
                    }
                }
            }
        }

        return cleaned;
    }

    /**
     * Computes the set of recipe titles that appear in the search results for every ingredient provided.
     *
     * @param cleaned      the list of cleaned ingredient names.
     * @param titleToThumb a map to populate with title-to-thumbnail URL mappings.
     * @param titleToId    a map to populate with title-to-ID mappings.
     * @return a set of titles that are common across all ingredient searches.
     */
    private Set<String> computeCommonTitles(List<String> cleaned,
                                            Map<String, String> titleToThumb,
                                            Map<String, String> titleToId) {

        Set<String> intersection = null;

        for (final String ingredient : cleaned) {
            if (intersection != null && intersection.isEmpty()) {
                break;
            }

            final Set<String> titlesForIngredient =
                    fetchTitlesForIngredient(ingredient, titleToThumb, titleToId);

            if (titlesForIngredient.isEmpty()) {
                intersection = new LinkedHashSet<>();
            }
            else if (intersection == null) {
                intersection = new LinkedHashSet<>(titlesForIngredient);
            }
            else {
                intersection.retainAll(titlesForIngredient);
            }
        }

        Set<String> result = new LinkedHashSet<>();
        if (intersection != null) {
            result = intersection;
        }

        return result;
    }

    /**
     * Loops through the final list of titles, uses the ID to fetch full details,
     * and returns fully populated Recipe objects.
     *
     * @param titles    the set of recipe titles to build.
     * @param titleToId a map containing the ID for each title.
     * @return a list of fully populated Recipe objects.
     */
    private List<Recipe> buildRecipes(Set<String> titles, Map<String, String> titleToId) {
        final List<Recipe> result = new ArrayList<>();

        for (final String title : titles) {
            final String id = titleToId.get(title);
            if (id != null) {
                final Recipe fullRecipe = fetchFullRecipeDetails(id);
                if (fullRecipe != null) {
                    result.add(fullRecipe);
                }
            }
        }
        return result;
    }

    /**
     * Fetches the full details of a recipe by ID using 'lookup.php'.
     * This is required to get the Ingredient list for filtering.
     *
     * @param id the unique MealDB ID of the recipe.
     * @return the Recipe object with details, or null if fetching failed.
     */
    private Recipe fetchFullRecipeDetails(String id) {
        Recipe recipe = null;
        try {
            final String json = get("lookup.php?i=" + id);
            if (json != null) {
                final JSONObject root = new JSONObject(json);
                final JSONArray meals = root.optJSONArray(MEALS_KEY);

                if (meals != null && !meals.isEmpty()) {
                    final JSONObject mealObj = meals.getJSONObject(0);

                    final String title = mealObj.optString(TITLE_KEY);
                    final String category = mealObj.optString("strCategory");
                    final String instructions = mealObj.optString("strInstructions");
                    final String thumb = mealObj.optString(THUMB_KEY);
                    final String youtube = mealObj.optString("strYoutube");

                    final List<Ingredient> ingredients = new ArrayList<>();
                    final int maxIngredients = 20;
                    for (int i = 1; i <= maxIngredients; i++) {
                        final String ingredientName = mealObj.optString("strIngredient" + i);
                        final String measure = mealObj.optString("strMeasure" + i);

                        if (ingredientName != null && !ingredientName.trim().isEmpty()) {
                            ingredients.add(new Ingredient(ingredientName.trim(), measure.trim()));
                        }
                    }

                    recipe = new Recipe(title, ingredients, instructions, thumb, youtube, category);
                }
            }
        }
        catch (IOException | JSONException exception) {
            LOGGER.log(Level.SEVERE, exception, () -> "Error fetching details for recipe ID " + id);
        }
        return recipe;
    }

    /**
     * Fetches titles of recipes containing a specific ingredient.
     *
     * @param ingredient   the ingredient name to search for.
     * @param titleToThumb map to populate with title-to-thumbnail data.
     * @param titleToId    map to populate with title-to-id data.
     * @return a set of titles found for this ingredient.
     */
    private Set<String> fetchTitlesForIngredient(String ingredient,
                                                 Map<String, String> titleToThumb,
                                                 Map<String, String> titleToId) {

        final Set<String> titles = new LinkedHashSet<>();

        try {
            final String normalized = ingredient.toLowerCase()
                    .trim()
                    .replace(" ", "_");

            final String json = get("filter.php?i=" + normalized);
            if (json != null) {
                final JSONObject root = new JSONObject(json);
                final JSONArray meals = root.optJSONArray(MEALS_KEY);

                if (meals != null) {
                    for (int index = 0; index < meals.length(); index++) {
                        final JSONObject meal = meals.getJSONObject(index);
                        final String title = meal.getString(TITLE_KEY);
                        final String thumb = meal.optString(THUMB_KEY, "");
                        final String id = meal.optString(ID_KEY, "");

                        titles.add(title);
                        titleToThumb.putIfAbsent(title, thumb);
                        titleToId.putIfAbsent(title, id);
                    }
                }
            }
        }
        catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, ioException.getMessage(), ioException);
        }

        return titles;
    }
}
