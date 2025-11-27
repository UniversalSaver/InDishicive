package adapters.generate_recipe.view_recipe_details;

import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputData;

public class ViewRecipeDetailsPresenter implements ViewRecipeDetailsOutputBoundary {

    private final ViewRecipeDetailsViewModel viewModel;

    public ViewRecipeDetailsPresenter(ViewRecipeDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewRecipeDetailsOutputData outputData) {
        ViewRecipeDetailsState state = new ViewRecipeDetailsState();
            state.setTitle(outputData.getTitle());
            state.setIngredients(outputData.getIngredients());
            state.setInstructions(outputData.getInstructions());


        viewModel.setState(state);
        viewModel.firePropertyChange(ViewRecipeDetailsViewModel.DETAILS_PROPERTY);
    }
}