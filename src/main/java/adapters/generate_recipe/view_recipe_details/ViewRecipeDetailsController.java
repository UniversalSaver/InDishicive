package adapters.generate_recipe.view_recipe_details;

import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputData;

public class ViewRecipeDetailsController {

    private final ViewRecipeDetailsInputBoundary interactor;

    public ViewRecipeDetailsController(ViewRecipeDetailsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String title) {
        interactor.execute(new ViewRecipeDetailsInputData(title));
    }
}