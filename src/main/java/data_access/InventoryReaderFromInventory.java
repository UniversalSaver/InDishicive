package data_access;

import entity.Inventory;
import entity.Ingredient;
import use_case.generate_with_inventory.InventoryReader;

import java.util.Set;
import java.util.stream.Collectors;

public class InventoryReaderFromInventory implements InventoryReader {

    private final Inventory inventory;

    public InventoryReaderFromInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Set<String> getAll() {
        return inventory.getIngredients().stream()
                .map(Ingredient::getName)
                .collect(Collectors.toSet());
    }
}