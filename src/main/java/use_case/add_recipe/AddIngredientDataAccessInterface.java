package use_case.add_recipe;

import entity.Ingredient;

import java.util.List;

/**
 * An interface that defines the ability to give back a sorted list of all ingredients.
 */
public interface AddIngredientDataAccessInterface {
	/**
	 * Returns a sorted list of all ingredients
	 * @return a sorted ingredient list
	 */
	List<Ingredient> listPossibleIngredients();
}
