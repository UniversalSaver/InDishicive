package adapters.generate_recipe.view_recipe_details;

import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputData;

/**
 * Controller for the "view recipe details" use case.
 */
public class ViewRecipeDetailsController {

    private final ViewRecipeDetailsInputBoundary interactor;

    /**
     * Creates a controller with the given interactor.
     *
     * @param interactor the use case interactor
     */
    public ViewRecipeDetailsController(ViewRecipeDetailsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the use case for the recipe with the given title.
     *
     * @param title the title of the recipe to view
     */
    public void execute(String title) {
        interactor.execute(new ViewRecipeDetailsInputData(title));
    }
}
