package use_case.dietary_restriction.add_restrictions;

import entity.Ingredient;

public class AddDietResInputData {
    private final Ingredient ingredient;

    public AddDietResInputData(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public AddDietResInputData(String ingredientName) {
        this.ingredient = new Ingredient(ingredientName, "");
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
}
