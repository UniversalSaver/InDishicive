package adapters.inventory.remove_ingredient;

import adapters.ViewModel;

/**
 * ViewModel for the Remove Ingredient feature.
 * This ViewModel does not manage state, only fires property change events.
 * Uses Void as the generic type since no state management is needed.
 */
public class RemoveIngredientViewModel extends ViewModel<Void> {

    /** Property name for when an ingredient is successfully removed */
    public static final String INGREDIENT_REMOVED_PROPERTY = "ingredientRemoved";

    public RemoveIngredientViewModel() {
        super("remove_ingredient");
    }
}

