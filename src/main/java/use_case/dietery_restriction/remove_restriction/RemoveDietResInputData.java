package use_case.dietery_restriction.remove_restriction;

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