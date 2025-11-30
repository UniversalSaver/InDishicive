package interface_adapter.remove_diet_res;

import entity.Ingredient;
import use_case.remove_restriction.RemoveDietResInputBoundary;
import use_case.remove_restriction.RemoveDietResInputData;

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
        Ingredient ingredient = new Ingredient(ingredientName, "");
        RemoveDietResInputData inputData = new RemoveDietResInputData(ingredient);
        interactor.execute(inputData);
    }
}