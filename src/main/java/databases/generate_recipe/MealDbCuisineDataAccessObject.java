package databases.generate_recipe;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineDataAccessInterface;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MealDbCuisineDataAccessObject implements FilterByCuisineDataAccessInterface {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<String> getRecipeTitlesByCuisine(String cuisine) {
        List<String> titles = new ArrayList<>();

        if (cuisine == null || cuisine.isBlank()) {
            return titles;
        }

        String encodedCuisine = URLEncoder.encode(cuisine, StandardCharsets.UTF_8);
        String url = BASE_URL + "filter.php?a=" + encodedCuisine;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful() || response.body() == null) {
                System.err.println("MealDB request failed: " + response.code());
                return titles;
            }

            String responseBody = response.body().string();
            JSONObject root = new JSONObject(responseBody);

            // When no meals, MealDB returns {"meals": null}
            if (root.isNull("meals")) {
                return titles;
            }

            JSONArray meals = root.getJSONArray("meals");

            for (int i = 0; i < meals.length(); i++) {
                JSONObject meal = meals.getJSONObject(i);
                String title = meal.optString("strMeal", "").trim();
                if (!title.isEmpty()) {
                    titles.add(title);
                }
            }

        } catch (IOException | JSONException e) {
            System.err.println("Error calling MealDB API: " + e.getMessage());
        }

        return titles;
    }
}