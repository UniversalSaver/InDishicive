package adapters.generate_recipe.generate_with_inventory;

import java.util.List;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputData;

public class GenerateWithInventoryPresenter implements GenerateWithInventoryOutputBoundary {

    private static final int PAGE_SIZE = 3;

    private final GenerateWithInventoryViewModel viewModel;

    public GenerateWithInventoryPresenter(final GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(final GenerateWithInventoryOutputData outputData) {
        final List<String> allTitlesFromUseCase = outputData.getRecipeTitles();

        if (allTitlesFromUseCase.isEmpty()) {
            viewModel.resetTitles(List.of());
            viewModel.setBaseTitles(List.of());
            viewModel.setErrorMessage("Please add more ingredients!");
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
        } else {
            viewModel.setBaseTitles(allTitlesFromUseCase);

            final List<String> previous = viewModel.getAllTitles();
            if (!previous.equals(allTitlesFromUseCase)) {
                viewModel.resetTitles(allTitlesFromUseCase);
            }

            final List<String> page = viewModel.getNextPage(PAGE_SIZE);
            if (page.isEmpty()) {
                viewModel.setErrorMessage(
                        "No more recipes to show. Please add more ingredients!");
                viewModel.setState(List.of());
                viewModel.firePropertyChange("error");
            }
            else {
                viewModel.setErrorMessage("");
                viewModel.setState(page);
                viewModel.firePropertyChange("recipes");
            }
        }
    }
}
