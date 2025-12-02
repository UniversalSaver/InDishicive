package adapters.generate_recipe.view_recipe_details;

import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputData;

/**
 * Presenter for the view recipe details use case.
 */
public class ViewRecipeDetailsPresenter implements ViewRecipeDetailsOutputBoundary {

    private final ViewRecipeDetailsViewModel viewModel;

    /**
     * Creates a presenter with the given view model.
     *
     * @param viewModel the view model for this use case
     */
    public ViewRecipeDetailsPresenter(ViewRecipeDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewRecipeDetailsOutputData outputData) {
        final ViewRecipeDetailsState state = new ViewRecipeDetailsState();
        state.setTitle(outputData.getTitle());
        state.setIngredients(outputData.getIngredients());
        state.setInstructions(outputData.getInstructions());

        viewModel.setState(state);
        viewModel.firePropertyChange(ViewRecipeDetailsViewModel.DETAILS_PROPERTY);
    }
}