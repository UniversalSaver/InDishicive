package logic.inventory.missing_ingredients;

import entity.Ingredient;
import java.util.List;

public interface InventoryReaderInterface {
    List<Ingredient> getAllIngredients();
}

