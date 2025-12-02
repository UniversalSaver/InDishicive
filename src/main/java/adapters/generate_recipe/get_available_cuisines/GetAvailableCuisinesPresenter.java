package adapters.generate_recipe.get_available_cuisines;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesOutputBoundary;
import logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesOutputData;

import java.util.ArrayList;
import java.util.List;

public class GetAvailableCuisinesPresenter implements GetAvailableCuisinesOutputBoundary {

    private final GenerateWithInventoryViewModel viewModel;

    public GetAvailableCuisinesPresenter(final GenerateWithInventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(final GetAvailableCuisinesOutputData output) {
        final List<String> list = new ArrayList<>();
        list.add("Any");

        if (output.getCuisines() != null) {
            list.addAll(output.getCuisines());
        }

        viewModel.setCuisines(list);
    }
}