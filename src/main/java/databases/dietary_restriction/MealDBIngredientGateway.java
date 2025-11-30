package databases.dietary_restriction;

import logic.dietary_restriction.add_restrictions.IngredientGateway;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MealDBIngredientGateway implements IngredientGateway {
    private static final Logger logger = Logger.getLogger(MealDBIngredientGateway.class.getName());
    private static final String BASE_URL = "https://themealdb.com/api/json/v1/1/filter.php?i=";
    private final OkHttpClient client;

    public MealDBIngredientGateway() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .callTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Override
    public boolean isValidIngredient(String ingredient) {
        if (ingredient == null || ingredient.isBlank()) {
            return false;
        }

        try {
            String encodedIngredient = URLEncoder.encode(ingredient, StandardCharsets.UTF_8);
            String url = BASE_URL + encodedIngredient;

            Request request = new Request.Builder().url(url).get().build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    return false;
                }

                String responseBody = response.body().string();
                if (responseBody.isEmpty()) return false;

                JSONObject root = new JSONObject(responseBody);
                return !root.isNull("meals");
            }

        } catch (InterruptedIOException e) {
            // Log warning for timeouts
            logger.log(Level.WARNING, e, () -> "Timeout while checking ingredient: " + ingredient);
            return false;

        } catch (IOException | JSONException e) {
            // Log error for IO/JSON failures
            logger.log(Level.SEVERE, e, () -> "Error calling MealDB API: " + e.getMessage());
            return false;
        }
    }
}