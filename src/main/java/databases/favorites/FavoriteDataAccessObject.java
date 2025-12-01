package databases.favorites;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;

/**
 * Implementation of the Favorite Data Access Object.
 * Stores favorite recipes in a JSON file.
 */
public class FavoriteDataAccessObject implements FavoriteDataAccessInterface {
    /**
     * The file path where the favorite JSON is saved.
     */
    private final String filePath;
    /**
     * A list of favorite recipes.
     */
    private List<Recipe> favorites;

    /**
     * Creates a FavoriteDataAccessObject with default file path.
     */
    public FavoriteDataAccessObject() {
        this("favorites.json");
    }

    /**
     * Creates a FavoriteDataAccessObject with specified file path.
     * @param filePath the path to the JSON file
     */
    public FavoriteDataAccessObject(String filePath) {
        this.filePath = filePath;
        this.favorites = new ArrayList<>();
        loadFavorites();
    }
    
    @Override
    public void saveFavorite(Recipe recipe) {
        if (!isFavorite(recipe)) {
            favorites.add(recipe);
            saveFavoritesToFile();
        }
    }
    
    @Override
    public List<Recipe> getFavorites() {
        return new ArrayList<>(favorites);
    }
    
    @Override
    public boolean isFavorite(Recipe recipe) {
        return favorites.contains(recipe);
    }

    /**
     * Loads favorites from the JSON file.
     */
    private void loadFavorites() {
        final File file = new File(filePath);

        // If file doesnt exist, create a new empty list
        if (!file.exists()) {
            favorites = new ArrayList<>();
        }
        else {
            try {
                final String jsonContent = Files.readString(file.toPath());

                if (jsonContent.isEmpty()) {
                    favorites = new ArrayList<>();
                }
                else {
                    final JSONArray jsonArray = new JSONArray(jsonContent);
                    favorites = parseFavoritesFromJson(jsonArray);
                }

            }
            catch (IOException ioException) {
                favorites = new ArrayList<>();
            }
        }
    }

    /**
     * Parses recipes from a JSON array.
     *
     * @param jsonArray the JSON array containing recipe objects
     * @return list of parsed Recipe objects
     */
    private List<Recipe> parseFavoritesFromJson(JSONArray jsonArray) {
        final List<Recipe> parsedFavorites = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject recipeJson = jsonArray.getJSONObject(i);
            final Recipe recipe = jsonToRecipe(recipeJson);
            parsedFavorites.add(recipe);
        }
        return parsedFavorites;
    }

    /**
     * Saves favorites to the JSON file.
     */
    private void saveFavoritesToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            final JSONArray jsonArray = new JSONArray();

            for (Recipe recipe : favorites) {
                final JSONObject recipeJson = recipeToJson(recipe);
                jsonArray.put(recipeJson);
            }

            final int indentSpaces = 4;
            writer.write(jsonArray.toString(indentSpaces));

        }
        catch (IOException ioException) {
            System.err.println("Error saving favorites to file: " + ioException.getMessage());
        }
    }

    /**
     * Removes favorite from file.
     *
     * @param recipe the recipe to remove from favorites
     */
    @Override
    public void removeFavorite(Recipe recipe) {
        favorites.removeIf(recipeItem -> recipeItem.getTitle().equals(recipe.getTitle()));
        saveFavoritesToFile();
    }

    /**
     * Converts a Recipe to JSON format.
     * @param recipe the recipe to convert
     * @return JSONObject representation of the recipe
     */
    private JSONObject recipeToJson(Recipe recipe) {
        final JSONObject json = new JSONObject();

        // I only need the Name and Category for my use case.

        json.put("title", recipe.getTitle());
        json.put("category", recipe.getCategory());

        return json;
    }

    /**
     * Converts JSON to a Recipe object.
     * Only title and category are stored. Other fields are left empty.
     * @param json the JSON object to convert
     * @return Recipe object with title and category only
     */
    private Recipe jsonToRecipe(JSONObject json) {
        final String title = json.getString("title");
        final String category = json.getString("category");

        // Create recipe with only the required fields I need.
        // Steps, imageLink, youtubeLink, and ingredients will be empty.
        // Use ViewRecipe use case to get complete recipe details.
        return new Recipe(
            title,
            new ArrayList<>(),
            "",
            "",
            "",
            category
        );
    }
}
