package data_access;

import org.jetbrains.annotations.NotNull;
import use_case.search_ingredients.SearchIngredientsDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDBIngredientDataAccess implements SearchIngredientsDataAccessInterface {

    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";

    @Override
    public List<String> getAllIngredients() {
        List<String> ingredients = new ArrayList<>();
        
        try {
            URI uri = URI.create(API_URL);
            String jsonResponse = getString(uri);
            Pattern pattern = Pattern.compile("\"strIngredient\":\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(jsonResponse);
            
            while (matcher.find()) {
                String ingredientName = matcher.group(1);
                ingredients.add(ingredientName);
            }
            
        } catch (Exception e) {
            throw new APIFailException("Failed to fetch ingredients from API: " + e.getMessage());
        }
        
        return ingredients;
    }

    @NotNull
    private static String getString(URI uri) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    static class APIFailException extends RuntimeException {
        public APIFailException(String message) {
            super(message);
        }
    }
}

