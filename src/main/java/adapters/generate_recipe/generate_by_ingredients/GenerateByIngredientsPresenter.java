package adapters.generate_recipe.generate_by_ingredients;

import java.util.List;

import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsOutputBoundary;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsOutputData;

public class GenerateByIngredientsPresenter implements GenerateByIngredientsOutputBoundary {

    private final GenerateByIngredientsViewModel viewModel;

    public GenerateByIngredientsPresenter(GenerateByIngredientsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GenerateByIngredientsOutputData outputData) {
        final List<String> titles = outputData.getRecipeTitles();
        viewModel.setState(titles);
        viewModel.firePropertyChange(GenerateByIngredientsViewModel.SET_TITLES);
    }
}
