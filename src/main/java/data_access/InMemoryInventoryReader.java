package data_access;

import use_case.generate_with_inventory.InventoryReader;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//this is just used for implement usecase1 this time, once use cases related to inventory are completed.
//I will rewrite another one.
public class InMemoryInventoryReader implements InventoryReader {

    private final Set<String> ingredients = new HashSet<>();

    public void add(String ingredientName) {
        if (ingredientName != null) {
            ingredients.add(ingredientName.trim().toLowerCase());
        }
    }

    @Override
    public Set<String> getAll() {
        return Collections.unmodifiableSet(ingredients);
    }
}