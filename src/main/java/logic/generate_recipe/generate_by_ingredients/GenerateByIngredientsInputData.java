package logic.generate_recipe.generate_by_ingredients;

import java.util.List;

public class GenerateByIngredientsInputData {

    private final List<String> ingredients;

    public GenerateByIngredientsInputData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}