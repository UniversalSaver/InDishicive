package logic.dietary_restriction;

import java.util.List;
import java.util.Objects;

import entity.Ingredient;
import entity.Recipe;

/**
 * Standard implementation of the DietaryRestrictionChecker.
 * Uses flexible string matching to find restricted ingredients.
 */
public class DietaryRestrictionChecker implements DietaryRestrictionCheckerInterface {

    @Override
    public boolean containsRestrictedIngredient(Recipe recipe, List<Ingredient> restrictions) {
        boolean foundRestriction = false;

        // Only proceed if there are restrictions to check
        if (restrictions != null && !restrictions.isEmpty() && recipe.getIngredients() != null) {

            // Check every ingredient in the recipe
            for (Ingredient recipeIngredient : recipe.getIngredients()) {
                if (recipeIngredient.getName() != null) {
                    final String ingredientName = recipeIngredient.getName().toLowerCase();

                    // Check against all restrictions
                    final boolean isMatch = restrictions.stream()
                            .map(Ingredient::getName)
                            .filter(Objects::nonNull)
                            .map(String::toLowerCase)
                            .anyMatch(restrictedName -> {
                                // Check if recipe ingredient contains the restriction
                                // (e.g. "Peanut Butter" contains "Peanut")
                                return ingredientName.contains(restrictedName)
                                        || restrictedName.contains(ingredientName);
                            });
                    if (isMatch) {
                        foundRestriction = true;
                        break;
                    }
                }
            }
        }

        return foundRestriction;
    }
}
