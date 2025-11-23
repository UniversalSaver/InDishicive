package use_case.add_restrictions;

import entity.Ingredient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.diet_res_ingredients.DietResDataAccessInterface;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class AddDietResInteractor implements AddDietResInputBoundary {
    private final DietResDataAccessInterface dietResDataAccessInterface;
    private final AddDietResOutputBoundary addDietResOutputBoundary;

    private static final String BASE_URL = "https://themealdb.com/api/json/v1/1/filter.php?i=";
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)   // time to establish connection
            .readTimeout(30, TimeUnit.SECONDS)      // time between bytes when reading responses
            .writeTimeout(30, TimeUnit.SECONDS)     // time between bytes when sending request body
            .callTimeout(60, TimeUnit.SECONDS)      // overall timeout for the entire call
            .retryOnConnectionFailure(true)
            .build();

    public AddDietResInteractor(DietResDataAccessInterface dietResDataAccessInterface,
                                AddDietResOutputBoundary addDietResOutputBoundary) {
        this.dietResDataAccessInterface = dietResDataAccessInterface;
        this.addDietResOutputBoundary = addDietResOutputBoundary;
    }

    @Override
    public void execute(AddDietResInputData inputData) {
        Ingredient ingredient = inputData.getIngredient();
        String ingredientName = ingredient.getName();

        if (!isValidIngredient(ingredientName)) {
            addDietResOutputBoundary.prepareFailView("Ingredient not found in database");
            return;
        }

        if (dietResDataAccessInterface.isRestricted(ingredient)) {
            addDietResOutputBoundary.prepareFailView("Already in Dietary Restricted Ingredients List");
            return;
        }

        dietResDataAccessInterface.saveRestriction(ingredient);

        addDietResOutputBoundary.prepareSuccessView();
    }

    /**
     * Helper method to check if an ingredient exists via MealDB API.
     * I cannot remove this method until "Filter by Main Ingredient" use case is done
     */
    private boolean isValidIngredient(String ingredient) {
        if (ingredient == null || ingredient.isBlank()) {
            return false;
        }

        String encodedIngredient = URLEncoder.encode(ingredient, StandardCharsets.UTF_8);
        String url = BASE_URL + encodedIngredient;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return false;
            }

            String responseBody = response.body().string();
            JSONObject root = new JSONObject(responseBody);

            return !root.isNull("meals");

        } catch (IOException | JSONException e) {
            System.err.println("Error calling MealDB API: " + e.getMessage());
            return false;
        }
    }
}
