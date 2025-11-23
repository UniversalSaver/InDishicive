package use_case.add_restrictions;

import entity.Ingredient;

public class AddDietResInputData {
    private final Ingredient ingredient;

    public AddDietResInputData(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
}
