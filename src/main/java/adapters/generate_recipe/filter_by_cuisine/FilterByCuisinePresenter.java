package adapters.generate_recipe.filter_by_cuisine;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputBoundary;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private static final int PAGE_SIZE = 3;

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(FilterByCuisineOutputData outputData) {
        String cuisine = outputData.getCuisine();

        List<String> base = viewModel.getAllTitles();

        if ("Any".equalsIgnoreCase(cuisine)) {
            if (base == null || base.isEmpty()) {
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

        // Otherwise intersect cuisine results with the base list (filter on top of generated)
        List<String> titlesFromCuisine = outputData.getRecipeTitles();
        if (titlesFromCuisine == null || titlesFromCuisine.isEmpty() || base == null || base.isEmpty()) {
            viewModel.setErrorMessage("No recipes found for cuisine: " + cuisine);
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
            return;
        }

        Set<String> baseSet = new HashSet<>(base);
        List<String> intersect = new ArrayList<>();
        for (String t : titlesFromCuisine) {
            if (baseSet.contains(t)) {
                intersect.add(t);
            }
        }

        if (intersect.isEmpty()) {
            viewModel.setErrorMessage("No matching recipes for your ingredients in: " + cuisine);
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
            return;
        }

        viewModel.setErrorMessage("");
        viewModel.resetTitles(intersect);
        List<String> page = viewModel.getNextPage(PAGE_SIZE);
        viewModel.setState(page);
        viewModel.firePropertyChange("recipes");
    }
}