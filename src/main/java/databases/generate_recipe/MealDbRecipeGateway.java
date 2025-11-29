package databases.generate_recipe;

import entity.Ingredient;
import entity.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import logic.generate_recipe.generate_with_inventory.RecipeGateway;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MealDbRecipeGateway implements RecipeGateway {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final int MAX_INGREDIENTS = 20;

    private final OkHttpClient client = new OkHttpClient();

    private final Map<String, Recipe> cache = new LinkedHashMap<>();
    private boolean cacheLoaded = false;

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }
            return response.body().string();
        }
    }

    public void preloadAllRecipes() {
        if (cacheLoaded) {
            return;
        }
        loadAllRecipesIntoCache();
    }

    private void ensureCacheLoaded() {
        if (!cacheLoaded) {
            loadAllRecipesIntoCache();
        }
    }

    private void loadAllRecipesIntoCache() {
        if (cacheLoaded) {
            return;
        }

        for (char c = 'a'; c <= 'z'; c++) {
            String url = BASE_URL + "search.php?f=" + c;
            try {
                String body = get(url);
                if (body == null) {
                    continue;
                }

                JSONObject root = new JSONObject(body);
                JSONArray meals = root.optJSONArray("meals");
                if (meals == null) {
                    continue;
                }

                for (int i = 0; i < meals.length(); i++) {
                    JSONObject m = meals.getJSONObject(i);
                    Recipe recipe = parseFullRecipe(m);

                    String title = recipe.getTitle();
                    if (title == null || title.isBlank()) {
                        continue;
                    }

                    cache.putIfAbsent(title, recipe);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cacheLoaded = true;
    }

    @Override
    public List<Recipe> findByInventory(Set<String> have) {
        if (have == null || have.isEmpty()) {
            return List.of();
        }

        ensureCacheLoaded();
        Set<String> available = new HashSet<>();
        for (String raw : have) {
            if (raw == null) {
                continue;
            }
            String normalized = raw.trim().toLowerCase();
            if (!normalized.isEmpty()) {
                available.add(normalized);
            }
        }

        if (available.isEmpty()) {
            return List.of();
        }

        List<Recipe> result = new ArrayList<>();

        for (Recipe recipe : cache.values()) {
            if (allIngredientsInInventory(recipe, available)) {
                result.add(recipe);
            }
        }

        return result;
    }

    private Recipe parseFullRecipe(JSONObject m) {
        String title = m.optString("strMeal", "");
        String instructions = m.optString("strInstructions", "");
        String image = m.optString("strMealThumb", "");
        String youtube = m.optString("strYoutube", "");
        String category = m.optString("strCategory", "");

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 1; i <= MAX_INGREDIENTS; i++) {
            String ing = m.optString("strIngredient" + i, "").trim();
            String measure = m.optString("strMeasure" + i, "").trim();
            if (ing.isEmpty()) {
                continue;
            }
            ingredients.add(new Ingredient(ing, measure));
        }

        return new Recipe(title, ingredients, instructions, image, youtube, category);
    }

    private boolean allIngredientsInInventory(Recipe recipe, Set<String> available) {
        for (Ingredient ing : recipe.getIngredients()) {
            if (ing == null || ing.getName() == null) {
                return false;
            }
            String name = ing.getName().trim().toLowerCase();
            if (name.isEmpty()) {
                continue;
            }
            if (!available.contains(name)) {
                return false;
            }
        }
        return true;
    }
}