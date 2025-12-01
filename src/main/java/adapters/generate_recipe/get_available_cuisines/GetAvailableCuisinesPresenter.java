package adapters.generate_recipe.get_available_cuisines;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesOutputBoundary;
import logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesOutputData;

import java.util.ArrayList;
import java.util.List;

public class GetAvailableCuisinesPresenter implements GetAvailableCuisinesOutputBoundary {
    private final GenerateWithInventoryViewModel vm;

    public GetAvailableCuisinesPresenter(GenerateWithInventoryViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void present(GetAvailableCuisinesOutputData output) {
        List<String> list = new ArrayList<>();
        list.add("Any");
        if (output.getCuisines() != null) list.addAll(output.getCuisines());
        vm.setCuisines(list);
    }
}