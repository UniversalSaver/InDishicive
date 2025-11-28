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
    private final OkHttpClient client = new OkHttpClient();

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

    @Override
    public List<Recipe> findByInventory(Set<String> have) {
        Map<String, Recipe> unique = new LinkedHashMap<>();

        if (have == null || have.isEmpty()) {
            return List.of();
        }

        for (String raw : have) {
            if (raw == null) {
                continue;
            }
            String ingredient = raw.trim();
            if (ingredient.isEmpty()) {
                continue;
            }

            ingredient = ingredient.toLowerCase().replace(" ", "_");

            String url = BASE_URL + "filter.php?i=" + ingredient;

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
                    String title = m.getString("strMeal");
                    String thumb = m.optString("strMealThumb", "");

                    if (!unique.containsKey(title)) {
                        Recipe r = new Recipe(title, List.of(), "", thumb, "", "");
                        unique.put(title, r);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>(unique.values());
    }

    public Optional<Recipe> findByTitle(String title) {
        if (title == null || title.isBlank()) {
            return Optional.empty();
        }

        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String url = BASE_URL + "search.php?s=" + encodedTitle;

        try {
            String body = get(url);
            if (body == null) {
                return Optional.empty();
            }

            JSONObject root = new JSONObject(body);
            JSONArray meals = root.optJSONArray("meals");
            if (meals == null || meals.isEmpty()) {
                return Optional.empty();
            }

            JSONObject m = meals.getJSONObject(0);
            Recipe recipe = parseFullRecipe(m);
            return Optional.of(recipe);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Recipe parseFullRecipe(JSONObject m) {
        String title = m.optString("strMeal", "");
        String instructions = m.optString("strInstructions", "");
        String image = m.optString("strMealThumb", "");
        String youtube = m.optString("strYoutube", "");
        String category = m.optString("strCategory", "");

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ing = m.optString("strIngredient" + i, "").trim();
            String measure = m.optString("strMeasure" + i, "").trim();
            if (ing.isEmpty()) {
                continue;
            }
            ingredients.add(new Ingredient(ing, measure));
        }

        return new Recipe(title, ingredients, instructions, image, youtube, category);
    }
}