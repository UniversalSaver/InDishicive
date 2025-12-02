package logic.generate_recipe.view_recipe_details;

import entity.Recipe;

/**
 * Gateway for retrieving recipe details.
 */
public interface RecipeDetailsGateway {

    /**
     * Finds a recipe by its title.
     *
     * @param title the recipe title
     * @return the recipe with the given title, or null if not found
     */
    Recipe findByTitle(String title);
}
