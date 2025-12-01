package databases.generate_recipe;

import entity.Ingredient;
import entity.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import logic.generate_recipe.view_recipe_details.RecipeDetailsGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MealDbRecipeDetailsGateway implements RecipeDetailsGateway {

    private static final String API_BASE =
            "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Recipe findByTitle(String title) {
        final String url = API_BASE + title.replace(" ", "%20");
        final Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }
            final String body = response.body().string();
            final JSONObject root = new JSONObject(body);
            if (root.isNull("meals")) {
                return null;
            }
            final JSONArray meals = root.getJSONArray("meals");
            if (meals.isEmpty()) {
                return null;
            }
            final JSONObject meal = meals.getJSONObject(0);

            final String mealTitle = meal.getString("strMeal");
            final String instructions = meal.optString("strInstructions", "");
            final String image = meal.optString("strMealThumb", "");
            final String youtube = meal.optString("strYoutube", "");
            final String category = meal.optString("strCategory", "");

            final List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                final String ingName = meal.optString("strIngredient" + i, "").trim();
                final String ingMeasure = meal.optString("strMeasure" + i, "").trim();
                if (ingName.isEmpty()) break;
                ingredients.add(new Ingredient(ingName, ingMeasure));
            }

            return new Recipe(mealTitle, ingredients, instructions, image, youtube, category);

        }
        catch (IOException exception) {
            return null;
        }
    }
}
