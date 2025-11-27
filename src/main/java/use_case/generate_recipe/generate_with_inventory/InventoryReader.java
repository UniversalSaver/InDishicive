package use_case.generate_recipe.generate_with_inventory;

import java.util.Set;
 //used to get the ingredients from inventory
 //It will change when use cases about inventory are completed

public interface InventoryReader {

    Set<String> getAll();
}
