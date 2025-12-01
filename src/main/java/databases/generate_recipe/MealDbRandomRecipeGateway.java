package databases.generate_recipe;

import entity.Ingredient;
import entity.Recipe;
import logic.generate_recipe.random_recipe.RandomRecipeGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealDbRandomRecipeGateway implements RandomRecipeGateway {
    private static final String RANDOM_URL = "https://www.themealdb.com/api/json/v1/1/random.php";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Optional<Recipe> getRandomRecipe() {
        Request request = new Request.Builder().url(RANDOM_URL).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return Optional.empty();
            }

            String body = response.body().string();
            return getRecipeFromBody(body);

        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Recipe> getRecipeFromBody(String body) {
        Optional<Recipe> result = Optional.empty();
        JSONObject root = new JSONObject(body);

        if (!root.isNull("meals")) {
            JSONArray meals = root.getJSONArray("meals");
            if (!meals.isEmpty()) {
                result = Optional.of(parseRecipe(meals.getJSONObject(0)));
            }
        }
        return result;
    }

    private Recipe parseRecipe(JSONObject m) {
        String title = m.optString("strMeal", "");
        String instructions = m.optString("strInstructions", "");
        String image = m.optString("strMealThumb", "");
        String youtube = m.optString("strYoutube", "");
        String category = m.optString("strCategory", "");

        List<Ingredient> ingredients = getIngredients(m);

        return new Recipe(title, ingredients, instructions, image, youtube, category);
    }

    private List<Ingredient> getIngredients(JSONObject m) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ing = m.optString("strIngredient" + i, "").trim();
            String measure = m.optString("strMeasure" + i, "").trim();
            if (!ing.isEmpty()) {
                ingredients.add(new Ingredient(ing, measure));
            }
        }
        return ingredients;
    }
}