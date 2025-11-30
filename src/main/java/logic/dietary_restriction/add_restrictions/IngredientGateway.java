package logic.dietary_restriction.add_restrictions;

public interface IngredientGateway {
    /**
     * Checks if the given ingredient exists in MealDB.
     * @param ingredientName the name of the ingredient to validate
     * @return true if the ingredient is found and valid, false otherwise
     */
    boolean isValidIngredient(String ingredientName);
}
