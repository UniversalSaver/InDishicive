package use_case.dietary_restriction.remove_restriction;

import entity.Ingredient;

public class RemoveDietResInputData {
    private final Ingredient ingredient;

    public RemoveDietResInputData(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}