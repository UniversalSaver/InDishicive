package adapters.generate_recipe.filter_by_cuisine;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputBoundary;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputData;

import java.util.List;

public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private static final int PAGE_SIZE = 3;

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(final GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(final FilterByCuisineOutputData outputData) {
        final String cuisine = outputData.getCuisine();
        final List<String> titles = outputData.getRecipeTitles();

        if ("Any".equalsIgnoreCase(cuisine)) {
            final List<String> base = viewModel.getAllTitles();
            if (base.isEmpty()) {
                viewModel.setErrorMessage("Please add more ingredients!");
                viewModel.setState(List.of());
                viewModel.firePropertyChange("error");
            } else {
                viewModel.setErrorMessage("");
                viewModel.resetTitles(base);
                final List<String> page = viewModel.getNextPage(PAGE_SIZE);
                viewModel.setState(page);
                viewModel.firePropertyChange("recipes");
            }
        } else {
            if (titles == null || titles.isEmpty()) {
                viewModel.setErrorMessage("No recipes found for cuisine: " + cuisine);
                viewModel.setState(List.of());
                viewModel.firePropertyChange("error");
            } else {
                viewModel.setErrorMessage("");
                viewModel.resetTitles(titles);
                final List<String> page = viewModel.getNextPage(PAGE_SIZE);
                viewModel.setState(page);
                viewModel.firePropertyChange("recipes");
            }
        }
    }
}