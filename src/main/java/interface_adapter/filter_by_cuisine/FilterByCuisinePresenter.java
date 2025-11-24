package interface_adapter.filter_by_cuisine;

import interface_adapter.generate_with_inventory.GenerateWithInventoryViewModel;
import use_case.filter_by_cuisine.FilterByCuisineOutputBoundary;
import use_case.filter_by_cuisine.FilterByCuisineOutputData;

import java.util.List;

public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(FilterByCuisineOutputData outputData) {
        List<String> titles = outputData.getRecipeTitles();
        String cuisine = outputData.getCuisine();

        if ("Any".equalsIgnoreCase(cuisine)) {
            viewModel.setErrorMessage("");
            viewModel.setRecipes(viewModel.getAllTitles());
            return;
        }

        if (titles == null || titles.isEmpty()) {
            viewModel.setErrorMessage("No recipes found for cuisine: " + cuisine);
            viewModel.setRecipes(List.of());
        } else {
            viewModel.setErrorMessage("");
            viewModel.setRecipes(titles);
        }
    }
}