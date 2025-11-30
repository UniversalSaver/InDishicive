package logic.dietary_restriction;

import java.util.List;

import entity.Ingredient;
import entity.Recipe;

/**
 * Domain Service responsible for checking if items violate dietary restrictions.
 * Located in logic.dietary_restriction so it can be shared across different Use Cases.
 */
public class DietaryRestrictionChecker {

    /**
     * Checks if a recipe contains any ingredient from the list of restrictions.
     * Uses case-insensitive comparison on ingredient names.
     *
     * @param recipe       The recipe to check.
     * @param restrictions The list of restricted ingredients.
     * @return true if the recipe contains a restricted ingredient, false otherwise.
     */
    public boolean containsRestrictedIngredient(Recipe recipe, List<Ingredient> restrictions) {
        boolean foundRestriction = false;

        // Only proceed if there are actual restrictions to check against
        if (restrictions != null && !restrictions.isEmpty()) {

            // Check every ingredient in the recipe
            for (Ingredient recipeIngredient : recipe.getIngredients()) {

                // See if this ingredient exists in the restrictions list
                final boolean isMatch = restrictions.stream()
                        .anyMatch(restricted -> restricted.getName().equalsIgnoreCase(recipeIngredient.getName()));

                if (isMatch) {
                    foundRestriction = true;
                    break;
                }
            }
        }

        return foundRestriction;
    }
}
