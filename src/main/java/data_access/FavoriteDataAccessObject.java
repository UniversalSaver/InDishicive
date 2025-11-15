package data_access;

import entity.Recipe;
import entity.Ingredient;
import use_case.favorite_recipes.FavoriteDataAccessInterface;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * implementation of the Favorite Data Access Object.
 * Stores favorite recipes in a JSON file.
 */
public class FavoriteDataAccessObject implements FavoriteDataAccessInterface {
    /**
     * filePath is the file path where the favorite JSON is saved
     * favorites is a list of recipes
     */
    private final String filePath;
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
        File file = new File(filePath);
        
        // if file doesnt exist, create a new empty list
        if (!file.exists()) {
            favorites = new ArrayList<>();
            return;
        }
        
        try {
            String jsonContent = Files.readString(file.toPath());
            
            JSONArray jsonArray = new JSONArray(jsonContent);
            favorites = new ArrayList<>();
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeJson = jsonArray.getJSONObject(i);
                Recipe recipe = jsonToRecipe(recipeJson);
                favorites.add(recipe);
            }
            
        } catch (IOException e) {
            favorites = new ArrayList<>();
        }
    }
    
    /**
     * Saves favorites to the JSON file.
     */
    private void saveFavoritesToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            JSONArray jsonArray = new JSONArray();
            
            for (Recipe recipe : favorites) {
                JSONObject recipeJson = recipeToJson(recipe);
                jsonArray.put(recipeJson);
            }
            
            writer.write(jsonArray.toString(4));
            
        } catch (IOException e) {
            System.err.println("Error saving favorites to file: " + e.getMessage());
        }
    }
    
    /**
     * Converts a Recipe to JSON format.
     * @param recipe the recipe to convert
     * @return JSONObject representation of the recipe
     */
    private JSONObject recipeToJson(Recipe recipe) {
        JSONObject json = new JSONObject();
        
        // I only need the Name and Category for my use case. 

        json.put("title", recipe.getTitle());
        json.put("category", recipe.getCategory());
        
        return json;
    }
    
    /**
     * Converts JSON to a Recipe object.
     * Only title and category are stored. Other fields are left empty
     * @param json the JSON object to convert
     * @return Recipe object with title and category only
     */
    private Recipe jsonToRecipe(JSONObject json) {
        String title = json.getString("title");
        String category = json.getString("category");
        
        // Create recipe with only the required feels I need
        // steps, imageLink, youtubeLink, and ingredients will be empty!!!!!!!!
        // Use ViewRecipe use case to get complete recipe details.
        return new Recipe(
            title,
            new ArrayList<>(),  // empty ingredients list
            "",                  
            "",                  
            "",                  
            category
        );
    }
}