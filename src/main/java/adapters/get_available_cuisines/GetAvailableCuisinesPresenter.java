package adapters.get_available_cuisines;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import logic.get_available_cuisines.GetAvailableCuisinesOutputBoundary;
import logic.get_available_cuisines.GetAvailableCuisinesOutputData;

import java.util.ArrayList;
import java.util.List;

public class GetAvailableCuisinesPresenter implements GetAvailableCuisinesOutputBoundary {
    private final GenerateWithInventoryViewModel vm;

    public GetAvailableCuisinesPresenter(GenerateWithInventoryViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void present(GetAvailableCuisinesOutputData outputData) {
        List<String> list = new ArrayList<>();
        list.add("Any"); // always first
        if (outputData.getCuisines() != null) list.addAll(outputData.getCuisines());
        vm.setCuisines(list); // fires "cuisines" event
    }
}