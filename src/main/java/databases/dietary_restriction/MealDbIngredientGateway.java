package databases.dietary_restriction;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import logic.dietary_restriction.add_restrictions.IngredientGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MealDbIngredientGateway implements IngredientGateway {
    private static final Logger LOGGER = Logger.getLogger(MealDbIngredientGateway.class.getName());
    private static final String BASE_URL = "https://themealdb.com/api/json/v1/1/filter.php?i=";
    private final OkHttpClient client;

    public MealDbIngredientGateway() {
        // Fix for magic numbers
        final int connectTimeoutVal = 5;
        final int readTimeoutVal = 5;
        final int writeTimeoutVal = 5;
        final int callTimeoutVal = 5;

        this.client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeoutVal, TimeUnit.SECONDS)
                .readTimeout(readTimeoutVal, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutVal, TimeUnit.SECONDS)
                .callTimeout(callTimeoutVal, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Override
    public boolean isValidIngredient(String ingredient) {
        boolean isValid = false;

        if (ingredient != null && !ingredient.isBlank()) {

            isValid = checkIngredientWithApi(ingredient);
        }

        return isValid;
    }

    private boolean checkIngredientWithApi(String ingredient) {
        boolean isFound = false;

        try {
            final String encodedIngredient = URLEncoder.encode(ingredient, StandardCharsets.UTF_8);
            final String url = BASE_URL + encodedIngredient;
            final Request request = new Request.Builder().url(url).get().build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    final String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        final JSONObject root = new JSONObject(responseBody);
                        isFound = !root.isNull("meals");
                    }
                }
            }
        }
        catch (InterruptedIOException exception) {
            LOGGER.log(Level.WARNING, exception, () -> "Timeout while checking ingredient: " + ingredient);
        }
        catch (IOException | JSONException exception) {
            LOGGER.log(Level.SEVERE, exception, () -> "Error calling MealDB API: " + exception.getMessage());
        }

        return isFound;
    }
}
