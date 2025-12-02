package adapters.generate_recipe.filter_by_cuisine;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import java.util.ArrayList;
import java.util.List;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputBoundary;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputData;

public class FilterByCuisinePresenter implements FilterByCuisineOutputBoundary {

    private static final int PAGE_SIZE = 3;

    private final GenerateWithInventoryViewModel viewModel;

    public FilterByCuisinePresenter(final GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(final FilterByCuisineOutputData output) {
        final String cuisine = output.getCuisine();
        final List<String> base = viewModel.getBaseTitles();

        if (base.isEmpty()) {
            viewModel.setErrorMessage("Please add more ingredients!");
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
            return;
        }

        final List<String> working = filtered(cuisine, base, output);

        if (!viewModel.getAllTitles().equals(working)) {
            viewModel.resetTitles(working);
        }

        final List<String> page = viewModel.getNextPage(PAGE_SIZE);
        if (page.isEmpty()) {
            viewModel.setErrorMessage("No recipes match this cuisine.");
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
        } else {
            viewModel.setErrorMessage("");
            viewModel.setState(page);
            viewModel.firePropertyChange("recipes");
        }
    }

    private static List<String> filtered(final String cuisine,
                                         final List<String> base,
                                         final FilterByCuisineOutputData output) {
        if (cuisine == null || cuisine.isBlank() || "Any".equalsIgnoreCase(cuisine)) {
            return base;
        }
        final List<String> daoTitles = output.getRecipeTitles();
        if (daoTitles == null || daoTitles.isEmpty()) {
            return List.of();
        }
        final List<String> filtered = new ArrayList<>();
        for (final String title : base) {
            if (title != null && daoTitles.contains(title)) {
                filtered.add(title);
            }
        }
        return filtered;
    }
}