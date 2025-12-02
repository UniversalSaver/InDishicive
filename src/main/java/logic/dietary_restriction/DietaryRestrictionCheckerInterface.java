package logic.dietary_restriction;

import java.util.List;

import entity.Ingredient;
import entity.Recipe;

public interface DietaryRestrictionCheckerInterface {
    /**
     * Checks if a recipe contains any ingredient from the list of restrictions.
     *
     * @param recipe       The recipe to check.
     * @param restrictions The list of restricted ingredients.
     * @return true if the recipe contains a restricted ingredient, false otherwise.
     */
    boolean containsRestrictedIngredient(Recipe recipe, List<Ingredient> restrictions);
}
