package adapters.generate_recipe.filter_by_cuisine;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputBoundary;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("checkstyle:ClassCanBeRecord")
public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private static final int PAGE_SIZE = 3;

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(FilterByCuisineOutputData outputData) {
        String cuisine = outputData.getCuisine();

        if ("Any".equalsIgnoreCase(cuisine)) {
            List<String> base = viewModel.getBaseTitles();
            if (base.isEmpty()) {
                viewModel.setErrorMessage("Please add more ingredients!");
                viewModel.setState(List.of());
                viewModel.firePropertyChange("error");
                return;
            }
            List<String> previous = viewModel.getAllTitles();
            if (!previous.equals(base)) {
                viewModel.resetTitles(base);
            }
            showNextPageOrError();
            return;
        }

        List<String> base = viewModel.getBaseTitles();
        if (base.isEmpty()) {
            viewModel.setErrorMessage("Please add more ingredients!");
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
            return;
        }

        Set<String> baseSet = new HashSet<>(base);
        List<String> fromApi = outputData.getRecipeTitles();
        List<String> filtered = new ArrayList<>();
        if (fromApi != null) {
            for (String t : fromApi) {
                if (baseSet.contains(t)) {
                    filtered.add(t);
                }
            }
        }

        if (filtered.isEmpty()) {
            viewModel.setErrorMessage("No recipes found for cuisine: " + cuisine);
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
            return;
        }

        List<String> previous = viewModel.getAllTitles();
        if (!previous.equals(filtered)) {
            viewModel.resetTitles(filtered);
        }
        showNextPageOrError();
    }

    private void showNextPageOrError() {
        List<String> page = viewModel.getNextPage(PAGE_SIZE);
        if (page.isEmpty()) {
            viewModel.setErrorMessage("No more recipes to show.");
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
        } else {
            viewModel.setErrorMessage("");
            viewModel.setState(page);
            viewModel.firePropertyChange("recipes");
        }
    }
}