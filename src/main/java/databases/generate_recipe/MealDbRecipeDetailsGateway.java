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
        String url = API_BASE + title.replace(" ", "%20");
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }
            String body = response.body().string();
            JSONObject root = new JSONObject(body);
            if (root.isNull("meals")) {
                return null;
            }
            JSONArray meals = root.getJSONArray("meals");
            if (meals.isEmpty()) {
                return null;
            }
            JSONObject meal = meals.getJSONObject(0);

            String mealTitle = meal.getString("strMeal");
            String instructions = meal.optString("strInstructions", "");
            String image = meal.optString("strMealThumb", "");
            String youtube = meal.optString("strYoutube", "");
            String category = meal.optString("strCategory", "");

            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                String ingName = meal.optString("strIngredient" + i, "").trim();
                String ingMeasure = meal.optString("strMeasure" + i, "").trim();
                if (ingName.isEmpty()) break;
                ingredients.add(new Ingredient(ingName, ingMeasure));
            }

            return new Recipe(mealTitle, ingredients, instructions, image, youtube, category);

        } catch (IOException e) {
            return null;
        }
    }
}