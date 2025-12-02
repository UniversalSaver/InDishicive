package databases.dietary_restriction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Ingredient;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

/**
 * Implementation of Dietary Restrictions Data Access Object.
 * Stores saved restricted ingredients in a JSON file.
 */
public class DietResDataAccessObject implements DietResDataAccessInterface {

    private final String filePath;
    private List<Ingredient> resIngredients;

    public DietResDataAccessObject() {
        this("restrictions.json");
    }

    public DietResDataAccessObject(String filePath) {
        this.filePath = filePath;
        this.resIngredients = new ArrayList<>();
        loadRestrictions();
    }

    @Override
    public void saveRestriction(Ingredient ingredient) {
        if (!isRestricted(ingredient)) {
            resIngredients.add(ingredient);
            saveRestrictionsToFile();
        }
    }

    /**
     * Removes an ingredient from the dietary restricted ingredients list.
     * @param ingredient the ingredient to remove
     */
    @Override
    public void removeRestriction(Ingredient ingredient) {
        final boolean removed = resIngredients.removeIf(existing ->
                existing.getName().equalsIgnoreCase(ingredient.getName())
        );

        if (removed) {
            saveRestrictionsToFile();
        }
    }

    @Override
    public List<Ingredient> getResIngredients() {
        return new ArrayList<>(resIngredients);
    }

    @Override
    public boolean isRestricted(Ingredient ingredient) {
        for (Ingredient restricted : resIngredients) {
            if (restricted.getName().equalsIgnoreCase(ingredient.getName())) {
                return true;
            }
        }
        return false;
    }

    private void loadRestrictions() {
        final File file = new File(filePath);

        if (!file.exists()) {
            resIngredients = new ArrayList<>();
            return;
        }

        try {
            final String jsonContent = Files.readString(file.toPath());
            if (jsonContent.isBlank()) {
                resIngredients = new ArrayList<>();
                return;
            }

            final JSONArray jsonArray = new JSONArray(jsonContent);
            resIngredients = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject ingredientObject = jsonArray.getJSONObject(i);
                resIngredients.add(jsonToIngredient(ingredientObject));
            }

        }
        catch (IOException | JSONException exception) {
            resIngredients = new ArrayList<>();
        }
    }

    private void saveRestrictionsToFile() {
        final int indentFactor = 4;
        try (FileWriter writer = new FileWriter(filePath)) {
            final JSONArray jsonArray = new JSONArray();

            for (Ingredient ingredient : resIngredients) {
                jsonArray.put(ingredientToJson(ingredient));
            }

            writer.write(jsonArray.toString(indentFactor));

        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to save restrictions to " + filePath, exception);
        }
    }

    private JSONObject ingredientToJson(Ingredient ingredient) {
        final JSONObject jsonObj = new JSONObject();
        jsonObj.put("Name", ingredient.getName());
        return jsonObj;
    }

    private Ingredient jsonToIngredient(JSONObject jsonObj) {
        final String name = jsonObj.getString("Name");
        return new Ingredient(name, "");
    }
}
