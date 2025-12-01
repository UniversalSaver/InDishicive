package logic.generate_recipe.generate_by_ingredients;

import java.util.List;

import entity.Recipe;

public interface RecipeByIngredientsGateway {

    /**
     * Finds recipes that contain all of the given ingredients.
     *
     * @param ingredients list of ingredient names
     * @return recipes that contain ALL of these ingredients
     */
    List<Recipe> findByIngredients(List<String> ingredients);
}
