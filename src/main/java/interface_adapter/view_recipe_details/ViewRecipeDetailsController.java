package interface_adapter.view_recipe_details;

import use_case.view_recipe_details.ViewRecipeDetailsInputBoundary;
import use_case.view_recipe_details.ViewRecipeDetailsInputData;

public class ViewRecipeDetailsController {

    private final ViewRecipeDetailsInputBoundary interactor;

    public ViewRecipeDetailsController(ViewRecipeDetailsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String title) {
        interactor.execute(new ViewRecipeDetailsInputData(title));
    }
}