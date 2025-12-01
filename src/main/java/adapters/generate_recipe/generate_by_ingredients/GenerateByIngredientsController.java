package adapters.generate_recipe.generate_by_ingredients;

import java.util.List;

import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInputBoundary;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInputData;

public class GenerateByIngredientsController {

    private final GenerateByIngredientsInputBoundary interactor;

    public GenerateByIngredientsController(GenerateByIngredientsInputBoundary interactor) {
        this.interactor = interactor;
    }
    /**
     * Executes the use case using the provided ingredient names.
     *
     * @param ingredientNames the list of ingredient names entered by the user
     */

    public void execute(List<String> ingredientNames) {
        final GenerateByIngredientsInputData inputData =
                new GenerateByIngredientsInputData(ingredientNames);
        interactor.execute(inputData);
    }
}
