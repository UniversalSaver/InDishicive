package interface_adapter.generate_recipe.generate_with_inventory;

import use_case.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import use_case.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputData;

import java.util.List;

public class GenerateWithInventoryPresenter implements GenerateWithInventoryOutputBoundary {

    private final GenerateWithInventoryViewModel viewModel;

    public GenerateWithInventoryPresenter(GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GenerateWithInventoryOutputData outputData) {
        List<String> allTitlesFromUseCase = outputData.getRecipeTitles();

        if (allTitlesFromUseCase.isEmpty()) {
            viewModel.resetTitles(List.of());
            viewModel.setErrorMessage("Please add more ingredients!");
            viewModel.setState(List.of());
            viewModel.firePropertyChange("error");
        }

        else {
            List<String> previousTitles = viewModel.getAllTitles();
            if (!previousTitles.equals(allTitlesFromUseCase)) {
                viewModel.resetTitles(allTitlesFromUseCase);
            }

            List<String> page = viewModel.getNextPage(3);

            if (page.isEmpty()) {
                viewModel.setErrorMessage("No more recipes to show. Please add more ingredients!");
                viewModel.setState(List.of());
                viewModel.firePropertyChange("error");
            } else {
                viewModel.setErrorMessage("");
                viewModel.setState(page);
                viewModel.firePropertyChange("recipes");
            }
        }
    }
}