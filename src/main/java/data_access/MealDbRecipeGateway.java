package data_access;

import entity.Ingredient;
import entity.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.generate_with_inventory.RecipeGateway;
import use_case.view_recipe_details.RecipeDetailsGateway;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MealDbRecipeGateway implements RecipeGateway, RecipeDetailsGateway {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    // These five meals are the ONLY ones from TheMealDB that use fewer than four ingredients.
    // I list them manually so they automatically pass the hit-count check.
    // This avoids doing extra API calls for low-ingredient candidates.
    private static final Set<String> SMALL_INGREDIENT_MEALS = Set.of(
            "Padron peppers",
            "Ugali – Kenyan cornmeal",
            "Bread omelette",
            "Choripán",
            "Peanut Butter Cookies"
    );

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

    //UC1

    @Override
    public List<Recipe> findByInventory(Set<String> have) {
        if (have == null || have.isEmpty()) {
            return List.of();
        }

        Set<String> normalizedHave = new LinkedHashSet<>();
        for (String raw : have) {
            if (raw == null) {
                continue;
            }
            String norm = normalizeIngredientName(raw);
            if (!norm.isEmpty()) {
                normalizedHave.add(norm);
            }
        }

        if (normalizedHave.isEmpty()) {
            return List.of();
        }

        Map<String, Candidate> candidates = new LinkedHashMap<>();

        for (String normName : normalizedHave) {
            String param = normName.replace(" ", "_");
            String url = BASE_URL + "filter.php?i=" + param;

            try {
                String body = get(url);
                if (body == null) {
                    continue;
                }

                JSONObject root = new JSONObject(body);
                JSONArray meals = root.optJSONArray("meals");
                if (meals == null || meals.isEmpty()) {
                    continue;
                }

                for (int i = 0; i < meals.length(); i++) {
                    JSONObject m = meals.getJSONObject(i);
                    String title = m.optString("strMeal", "").trim();
                    if (title.isEmpty()) {
                        continue;
                    }

                    Candidate c = candidates.computeIfAbsent(title, Candidate::new);
                    c.hitCount++;
                }

            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to call TheMealDB API", e);
            }
        }
        Map<String, Recipe> uniqueRecipes = new LinkedHashMap<>();

        for (Candidate c : candidates.values()) {
            boolean shouldCheck =
                    c.hitCount >= 4 || SMALL_INGREDIENT_MEALS.contains(c.title);

            if (!shouldCheck) {
                continue;
            }

            Optional<Recipe> fullOpt = fetchRecipeByTitleInternal(c.title);
            if (fullOpt.isEmpty()) {
                continue;
            }

            Recipe fullRecipe = fullOpt.get();
            if (allIngredientsInInventory(fullRecipe, normalizedHave)) {
                uniqueRecipes.put(c.title, fullRecipe);
            }
        }

        return new ArrayList<>(uniqueRecipes.values());
    }

    private boolean allIngredientsInInventory(Recipe recipe, Set<String> normalizedHave) {
        for (Ingredient ing : recipe.getIngredients()) {
            String norm = normalizeIngredientName(ing.getName());
            if (norm.isEmpty()) {
                continue;
            }
            if (!normalizedHave.contains(norm)) {
                return false;
            }
        }
        return true;
    }

    private String normalizeIngredientName(String name) {
        if (name == null) {
            return "";
        }
        return name.trim().toLowerCase();
    }

    // UC3
    @Override
    public Recipe findByTitle(String title) {
        return fetchRecipeByTitleInternal(title).orElse(null);
    }

    private Optional<Recipe> fetchRecipeByTitleInternal(String title) {
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
            return Optional.empty();
        }
    }

    //helper function

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

    // used for uc1
    private static class Candidate {
        final String title;
        int hitCount;

        Candidate(String title) {
            this.title = title;
            this.hitCount = 0;
        }
    }
}