package databases.generate_recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Recipe;
import logic.generate_recipe.generate_by_ingredients.RecipeByIngredientsGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MealDbRecipeByIngredientsGateway implements RecipeByIngredientsGateway {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String MEALS_KEY = "meals";
    private static final String TITLE_KEY = "strMeal";
    private static final String THUMB_KEY = "strMealThumb";

    private final OkHttpClient client = new OkHttpClient();

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

    @Override
    public List<Recipe> findByIngredients(List<String> ingredients) {
        List<Recipe> recipes = List.of();

        final List<String> cleaned = cleanIngredients(ingredients);
        if (!cleaned.isEmpty()) {
            final Map<String, String> titleToThumb = new LinkedHashMap<>();
            final Set<String> commonTitles = computeCommonTitles(cleaned, titleToThumb);
            if (!commonTitles.isEmpty()) {
                recipes = buildRecipes(commonTitles, titleToThumb);
            }
        }

        return recipes;
    }

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

    private Set<String> computeCommonTitles(List<String> cleaned,
                                            Map<String, String> titleToThumb) {

        Set<String> intersection = null;

        for (final String ingredient : cleaned) {
            final Set<String> titlesForIngredient =
                    fetchTitlesForIngredient(ingredient, titleToThumb);

            if (titlesForIngredient.isEmpty()) {
                intersection = new LinkedHashSet<>();
                break;
            }

            if (intersection == null) {
                intersection = new LinkedHashSet<>(titlesForIngredient);
            }
            else {
                intersection.retainAll(titlesForIngredient);
            }

            if (intersection.isEmpty()) {
                break;
            }
        }

        Set<String> result = new LinkedHashSet<>();
        if (intersection != null) {
            result = intersection;
        }

        return result;
    }

    private List<Recipe> buildRecipes(Set<String> titles,
                                      Map<String, String> titleToThumb) {

        final List<Recipe> result = new ArrayList<>();
        for (final String title : titles) {
            final String thumb = titleToThumb.getOrDefault(title, "");
            result.add(new Recipe(title, List.of(), "", thumb, "", ""));
        }
        return result;
    }

    private Set<String> fetchTitlesForIngredient(String ingredient,
                                                 Map<String, String> titleToThumb) {

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
                        titles.add(title);
                        titleToThumb.putIfAbsent(title, thumb);
                    }
                }
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return titles;
    }
}
