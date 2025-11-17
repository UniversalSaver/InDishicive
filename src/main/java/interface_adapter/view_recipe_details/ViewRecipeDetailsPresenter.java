package interface_adapter.view_recipe_details;

import use_case.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import use_case.view_recipe_details.ViewRecipeDetailsOutputData;

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