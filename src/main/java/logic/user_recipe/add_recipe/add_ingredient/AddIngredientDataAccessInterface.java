package logic.user_recipe.add_recipe.add_ingredient;

import java.util.List;

import entity.Ingredient;

/**
 * An interface that defines the ability to give back a sorted list of all ingredients.
 */
public interface AddIngredientDataAccessInterface {
	/**
	 * Returns a sorted list of all ingredients.
	 * @return a sorted ingredient list
	 */
	List<Ingredient> listPossibleIngredients();
}
