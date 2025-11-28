package adapters.inventory.add_ingredient;

import adapters.ViewModel;

/**
 * ViewModel for the Add Ingredient feature.
 * This ViewModel does not manage state, only fires property change events.
 * Uses Void as the generic type since no state management is needed.
 */
public class AddIngredientViewModel extends ViewModel<Void> {

    /** Property name for when an ingredient is successfully added */
    public static final String INGREDIENT_ADDED_PROPERTY = "ingredientAdded";

    public AddIngredientViewModel() {
        super("add_ingredient");
    }
}

