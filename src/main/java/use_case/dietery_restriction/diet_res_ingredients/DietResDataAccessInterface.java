package use_case.dietery_restriction.diet_res_ingredients;

import entity.Ingredient;

import java.util.List;

public interface DietResDataAccessInterface {
    /**
     * Saves an ingredient to the dietary restricted ingredients list.
     * @param ingredient the ingredient to save
     */
    void saveRestriction(Ingredient ingredient);

    /**
     * Removes an ingredient from the dietary restricted ingredients list.
     * @param ingredient the ingredient to remove
     */
    void removeRestriction(Ingredient ingredient);

    /**
     * Retrieves dietary restricted ingredients list.
     * @return list of all dietary restricted ingredients
     */
    List<Ingredient> getResIngredients();

    /**
     * Checks if an ingredient is already saved as restricted.
     * @param ingredient the ingredient to check if restricted
     * @return true if the ingredient is in restricted ingredients list, false otherwise
     */
    boolean isRestricted(Ingredient ingredient);
}