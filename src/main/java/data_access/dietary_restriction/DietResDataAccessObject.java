package data_access.dietary_restriction;

import entity.Ingredient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Dietary Restrictions Data Access Object.
 * Stores saved restricted ingredients in a JSON file.
 */
public class DietResDataAccessObject implements DietResDataAccessInterface {

    private final String fp;
    private List<Ingredient> resIngredients;

    public DietResDataAccessObject() {
        this("restrictions.json");
    }

    public DietResDataAccessObject(String fp) {
        this.fp = fp;
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
        boolean removed = resIngredients.removeIf(existing ->
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
        File file = new File(fp);

        if (!file.exists()) {
            resIngredients = new ArrayList<>();
            return;
        }

        try {
            String jsonContent = Files.readString(file.toPath());
            if (jsonContent.isBlank()){
                resIngredients = new ArrayList<>();
                return;
            }

            JSONArray jsonArray = new JSONArray(jsonContent);
            resIngredients = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ingredientObject = jsonArray.getJSONObject(i);
                resIngredients.add(jsonToIngredient(ingredientObject));
            }

        } catch (IOException | JSONException e) {
            resIngredients = new ArrayList<>();
        }
    }

    private void saveRestrictionsToFile() {
        try (FileWriter writer = new FileWriter(fp)) {
            JSONArray jsonArray = new JSONArray();

            for (Ingredient ingredient : resIngredients) {
                jsonArray.put(ingredientToJson(ingredient));
            }

            writer.write(jsonArray.toString(4));

        } catch (IOException e) {
            throw new RuntimeException("Failed to save restrictions to " + fp, e);
        }
    }

    private JSONObject ingredientToJson(Ingredient ingredient) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("Name", ingredient.getName());
        return jsonObj;
    }

    private Ingredient jsonToIngredient(JSONObject jsonObj) {
        String name = jsonObj.getString("Name");
        return new Ingredient(name, "");
    }
}