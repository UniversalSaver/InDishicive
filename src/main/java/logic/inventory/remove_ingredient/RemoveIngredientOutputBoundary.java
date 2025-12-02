package logic.inventory.remove_ingredient;

/**
 * Output boundary for the Remove Ingredient use case.
 * Defines the interface for preparing the view after ingredient removal.
 */
public interface RemoveIngredientOutputBoundary {

    /**
     * Prepares the success view after successful ingredient removal.
     */
    void prepareSuccessView();
}

