package interface_adapter.filter_by_cuisine;

import interface_adapter.generate_with_inventory.GenerateWithInventoryViewModel;
import use_case.filter_by_cuisine.FilterByCuisineOutputBoundary;
import use_case.filter_by_cuisine.FilterByCuisineOutputData;

import java.util.List;

public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private static final int PAGE_SIZE = 3;

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(FilterByCuisineOutputData outputData) {
        String cuisine = outputData.getCuisine();
        List<String> titles = outputData.getRecipeTitles();

        if ("Any".equalsIgnoreCase(cuisine)) {
            List<String> base = viewModel.getAllTitles();
            if (base.isEmpty()) {
                viewModel.setErrorMessage("Please add more ingredients!");
                viewModel.setState(List.of());
                viewModel.firePropertyChange("error");
                return;
            }
            viewModel.setErrorMessage("");
            viewModel.resetTitles(base);
            List<String> page = viewModel.getNextPage(PAGE_SIZE);
            viewModel.setState(page);
            viewModel.firePropertyChange("recipes");
            return;
        }

        if (titles == null || titles.isEmpty()) {
            viewModel.setErrorMessage("No recipes found for cuisine: " + cuisine);
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
            return;
        }

        viewModel.setErrorMessage("");
        viewModel.resetTitles(titles);
        List<String> page = viewModel.getNextPage(PAGE_SIZE);
        viewModel.setState(page);
        viewModel.firePropertyChange("recipes");
    }
}