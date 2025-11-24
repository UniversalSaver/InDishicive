package interface_adapter.generate_recipe.filter_by_cuisine;

import interface_adapter.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import use_case.generate_recipe.filter_by_cuisine.FilterByCuisineOutputBoundary;
import use_case.generate_recipe.filter_by_cuisine.FilterByCuisineOutputData;

import java.util.List;

public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(FilterByCuisineOutputData outputData) {
        List<String> titles = outputData.getRecipeTitles();

        if (titles == null || titles.isEmpty()) {
            viewModel.setErrorMessage("No recipes found for cuisine: " + outputData.getCuisine());
            viewModel.setRecipes(List.of());
        } else {
            viewModel.setErrorMessage("");
            viewModel.setRecipes(titles);
        }
    }
}