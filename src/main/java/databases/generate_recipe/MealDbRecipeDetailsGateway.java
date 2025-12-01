package databases.generate_recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Ingredient;
import entity.Recipe;
import logic.generate_recipe.view_recipe_details.RecipeDetailsGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Gateway for retrieving recipe details from TheMealDB API.
 */
public class MealDbRecipeDetailsGateway implements RecipeDetailsGateway {

    private static final String API_BASE =
            "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    private static final int MAX_INGREDIENTS = 20;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Recipe findByTitle(String title) {
        final String url = API_BASE + title.replace(" ", "%20");
        final Request request = new Request.Builder().url(url).build();

        Recipe result = null;

        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful() && response.body() != null) {

                final String body = response.body().string();
                final JSONObject root = new JSONObject(body);

                if (!root.isNull("meals")) {

                    final JSONArray meals = root.getJSONArray("meals");

                    if (!meals.isEmpty()) {

                        final JSONObject meal = meals.getJSONObject(0);

                        final String mealTitle = meal.optString("strMeal", "");
                        final String instructions = meal.optString("strInstructions", "");
                        final String image = meal.optString("strMealThumb", "");
                        final String youtube = meal.optString("strYoutube", "");
                        final String category = meal.optString("strCategory", "");

                        final List<Ingredient> ingredients = new ArrayList<>();

                        for (int index = 1; index <= MAX_INGREDIENTS; index++) {
                            final String ingName =
                                    meal.optString("strIngredient" + index, "").trim();
                            final String ingMeasure =
                                    meal.optString("strMeasure" + index, "").trim();

                            if (ingName.isEmpty()) {
                                break;
                            }

                            ingredients.add(new Ingredient(ingName, ingMeasure));
                        }

                        result = new Recipe(
                                mealTitle, ingredients, instructions, image, youtube, category
                        );
                    }
                }
            }

        }
        catch (IOException exception) {
            result = null;
        }

        return result;
    }
}
