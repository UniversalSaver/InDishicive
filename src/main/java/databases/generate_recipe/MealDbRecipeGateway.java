package databases.generate_recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Ingredient;
import entity.Recipe;
import logic.generate_recipe.generate_with_inventory.RecipeGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Gateway for retrieving recipes from TheMealDB and
 * filtering them based on the user's inventory.
 */

public class MealDbRecipeGateway implements RecipeGateway {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private static final int MAX_INGREDIENTS = 20;

    private final OkHttpClient client = new OkHttpClient();

    private final Map<String, Recipe> cache = new LinkedHashMap<>();

    private boolean cacheLoaded;

    /**
     * Loads all recipes from the remote API into a local cache.
     * This method may be called multiple times but only loads once.
     */
    public void preloadAllRecipes() {
        if (!cacheLoaded) {
            loadAllRecipesIntoCache();
        }
    }

    @Override
    public List<Recipe> findByInventory(Set<String> have) {
        List<Recipe> result = List.of();

        if (have != null && !have.isEmpty()) {
            ensureCacheLoaded();

            final Set<String> available = new HashSet<>();
            for (String raw : have) {
                if (raw != null) {
                    final String normalized = raw.trim().toLowerCase();
                    if (!normalized.isEmpty()) {
                        available.add(normalized);
                    }
                }
            }

            if (!available.isEmpty()) {
                final List<Recipe> collected = new ArrayList<>();
                for (Recipe recipe : cache.values()) {
                    if (allIngredientsInInventory(recipe, available)) {
                        collected.add(recipe);
                    }
                }
                result = collected;
            }
        }

        return result;
    }

    private String get(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        String responseBody = null;

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                responseBody = response.body().string();
            }
        }

        return responseBody;
    }

    private void ensureCacheLoaded() {
        if (!cacheLoaded) {
            loadAllRecipesIntoCache();
        }
    }

    private void loadAllRecipesIntoCache() {
        if (!cacheLoaded) {
            for (char character = 'a'; character <= 'z'; character++) {
                final String url = BASE_URL + "search.php?f=" + character;
                try {
                    final String body = get(url);
                    if (body == null) {
                        continue;
                    }

                    final JSONObject root = new JSONObject(body);
                    final JSONArray meals = root.optJSONArray("meals");
                    if (meals == null) {
                        continue;
                    }

                    for (int index = 0; index < meals.length(); index++) {
                        final JSONObject mealJson = meals.getJSONObject(index);
                        final Recipe recipe = parseFullRecipe(mealJson);

                        final String title = recipe.getTitle();
                        if (title == null || title.isBlank()) {
                            continue;
                        }

                        cache.putIfAbsent(title, recipe);
                    }
                }
                catch (IOException exception) {
                    // ignore and continue loading others
                }
            }

            cacheLoaded = true;
        }
    }

    private Recipe parseFullRecipe(JSONObject mealJson) {
        final String title = mealJson.optString("strMeal", "");
        final String instructions = mealJson.optString("strInstructions", "");
        final String image = mealJson.optString("strMealThumb", "");
        final String youtube = mealJson.optString("strYoutube", "");
        final String category = mealJson.optString("strCategory", "");

        final List<Ingredient> ingredients = new ArrayList<>();

        for (int index = 1; index <= MAX_INGREDIENTS; index++) {
            final String ingredientName =
                    mealJson.optString("strIngredient" + index, "").trim();
            final String measure =
                    mealJson.optString("strMeasure" + index, "").trim();

            if (ingredientName.isEmpty()) {
                continue;
            }

            ingredients.add(new Ingredient(ingredientName, measure));
        }

        return new Recipe(title, ingredients, instructions, image, youtube, category);
    }

    private boolean allIngredientsInInventory(Recipe recipe, Set<String> available) {
        boolean allPresent = true;

        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient == null || ingredient.getName() == null) {
                allPresent = false;
                break;
            }

            final String name = ingredient.getName().trim().toLowerCase();
            if (name.isEmpty()) {
                continue;
            }

            if (!available.contains(name)) {
                allPresent = false;
                break;
            }
        }

        return allPresent;
    }
}
