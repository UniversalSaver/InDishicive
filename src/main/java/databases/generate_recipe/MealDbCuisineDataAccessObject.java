package databases.generate_recipe;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineDataAccessInterface;
import logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesDataAccessInterface;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MealDbCuisineDataAccessObject
        implements FilterByCuisineDataAccessInterface, GetAvailableCuisinesDataAccessInterface {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<String> getRecipeTitlesByCuisine(String cuisine) {
        List<String> titles = new ArrayList<>();

        if (cuisine == null || cuisine.isBlank()) {
            return titles;
        }

        String encodedCuisine = URLEncoder.encode(cuisine, StandardCharsets.UTF_8);
        String path = "filter.php?a=" + encodedCuisine;

        JSONArray meals = fetchMealsArray(
                path,
                // keep same failure prefix
                "Error calling MealDB API: "                            // keep same exception prefix
        );
        if (meals == null) {
            return titles;
        }

        for (int i = 0; i < meals.length(); i++) {
            JSONObject meal = meals.getJSONObject(i);
            String title = meal.optString("strMeal", "").trim();
            if (!title.isEmpty()) {
                titles.add(title);
            }
        }
        return titles;
    }

    @Override
    public List<String> getAvailableCuisines() {
        List<String> cuisines = new ArrayList<>();
        String path = "list.php?a=list";

        JSONArray meals = fetchMealsArray(
                path,
                // keep same failure prefix
                "Error calling MealDB API (getAvailableCuisines): "     // keep same exception prefix
        );
        if (meals == null) {
            return cuisines;
        }

        for (int i = 0; i < meals.length(); i++) {
            JSONObject areaObj = meals.getJSONObject(i);
            String area = areaObj.optString("strArea", "").trim();
            if (!area.isEmpty()) {
                cuisines.add(area);
            }
        }
        return cuisines;
    }

    /**
     * Fetches the "meals" array from a MealDB endpoint, or returns null if unavailable.
     * Keeps error messages identical to the original methods via the provided prefixes.
     */
    private JSONArray fetchMealsArray(String path, String exceptionPrefix) {
        String url = BASE_URL + path;
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                System.err.println("MealDB request failed: " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            JSONObject root = new JSONObject(responseBody);

            // When no meals, MealDB returns {"meals": null}
            if (root.isNull("meals")) {
                return null;
            }
            return root.getJSONArray("meals");

        } catch (IOException | JSONException e) {
            System.err.println(exceptionPrefix + e.getMessage());
            return null;
        }
    }
}