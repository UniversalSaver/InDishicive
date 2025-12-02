package databases.inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Ingredient;
import logic.generate_recipe.generate_with_inventory.InventoryReader;
import logic.inventory.add_ingredient.InventoryDataAccessInterface;
import logic.inventory.missing_ingredients.InventoryReaderInterface;
import logic.inventory.remove_ingredient.RemoveIngredientDataAccessInterface;

/**
 * Data Access Object for managing inventory operations with file persistence.
 * Stores inventory in a JSON file and automatically saves on add/remove operations.
 */
public class InventoryDataAccessObject implements InventoryDataAccessInterface,
        RemoveIngredientDataAccessInterface, InventoryReaderInterface, InventoryReader {

    private static final int JSON_INDENT = 4;

    private final String filePath;
    private List<Ingredient> ingredients;

    /**
     * Constructs an InventoryDataAccessObject with default file path.
     */
    public InventoryDataAccessObject() {
        this("inventory.json");
    }

    /**
     * Constructs an InventoryDataAccessObject with specified file path.
     * @param filePath the path to the JSON file
     */
    public InventoryDataAccessObject(String filePath) {
        this.filePath = filePath;
        this.ingredients = new ArrayList<>();
        loadInventory();
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        final boolean exists = ingredients.stream()
                .anyMatch(existing -> existing.getName().equalsIgnoreCase(ingredient.getName()));

        if (!exists) {
            ingredients.add(ingredient);
            saveInventoryToFile();
        }
    }

    @Override
    public boolean removeIngredient(Ingredient ingredient) {
        final boolean removed = ingredients.removeIf(existing ->
                existing.getName().equalsIgnoreCase(ingredient.getName())
        );

        if (removed) {
            saveInventoryToFile();
        }

        return removed;
    }

    @Override
    public Ingredient findIngredientByName(String name) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets all ingredients in the inventory.
     * @return a copy of the ingredients list
     */
    public List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public Set<String> getAll() {
        final Set<String> names = new HashSet<>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient != null && ingredient.getName() != null) {
                final String name = ingredient.getName().trim().toLowerCase();
                if (!name.isEmpty()) {
                    names.add(name);
                }
            }
        }
        return names;
    }

    /**
     * Loads inventory from the JSON file.
     * If the file doesn't exist, starts with an empty inventory.
     */
    private void loadInventory() {
        final File file = new File(filePath);

        if (!file.exists()) {
            ingredients = new ArrayList<>();
            return;
        }

        try {
            final String jsonContent = Files.readString(file.toPath());

            if (jsonContent.isBlank()) {
                ingredients = new ArrayList<>();
                return;
            }

            final JSONArray jsonArray = new JSONArray(jsonContent);
            ingredients = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject ingredientJson = jsonArray.getJSONObject(i);
                ingredients.add(jsonToIngredient(ingredientJson));
            }

        }
        catch (IOException | JSONException ex) {
            ingredients = new ArrayList<>();
        }
    }

    /**
     * Saves the entire inventory to the JSON file.
     * Rewrites the file completely.
     * @throws RuntimeException if saving fails
     */
    private void saveInventoryToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            final JSONArray jsonArray = new JSONArray();

            for (Ingredient ingredient : ingredients) {
                jsonArray.put(ingredientToJson(ingredient));
            }

            writer.write(jsonArray.toString(JSON_INDENT));

        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to save inventory to " + filePath, ex);
        }
    }

    /**
     * Converts an Ingredient to JSON format.
     * @param ingredient the ingredient to convert
     * @return JSONObject representation of the ingredient
     */
    private JSONObject ingredientToJson(Ingredient ingredient) {
        final JSONObject json = new JSONObject();
        json.put("name", ingredient.getName());
        json.put("amount", ingredient.getAmount());
        return json;
    }

    /**
     * Converts JSON to an Ingredient object.
     * @param json the JSON object to convert
     * @return Ingredient object
     */
    private Ingredient jsonToIngredient(JSONObject json) {
        final String name = json.getString("name");
        final String amount = json.getString("amount");
        return new Ingredient(name, amount);
    }
}
