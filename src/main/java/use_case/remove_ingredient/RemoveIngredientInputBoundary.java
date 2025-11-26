package use_case.remove_ingredient;

/**
 * Input boundary for the Remove Ingredient use case.
 * Defines the interface for executing the remove ingredient operation.
 */
public interface RemoveIngredientInputBoundary {

    /**
     * Executes the remove ingredient use case.
     * @param ingredientName the name of the ingredient to remove
     */
    void execute(String ingredientName);
}

