package adapters.dietary_restriction.remove_diet_res;

import entity.Ingredient;
import logic.dietary_restriction.remove_restriction.RemoveDietResInputBoundary;
import logic.dietary_restriction.remove_restriction.RemoveDietResInputData;

public class RemoveDietResController {
    private final RemoveDietResInputBoundary interactor;

    public RemoveDietResController(RemoveDietResInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the remove restriction Use Case.
     * @param ingredientName The name of the ingredient to remove.
     */
    public void execute(String ingredientName) {
        final Ingredient ingredient = new Ingredient(ingredientName, "");
        final RemoveDietResInputData inputData = new RemoveDietResInputData(ingredient);
        interactor.execute(inputData);
    }
}
