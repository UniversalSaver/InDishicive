package logic.generate_recipe.random_recipe;

import entity.Recipe;
import java.util.Optional;

/**
 * An interface defining the expected methods of a database for the random recipe use case.
 */
public interface RandomRecipeGateway {
    /**
     * Returns a random recipe from the database
     * @return random recipe
     */
    Optional<Recipe> getRandomRecipe();
}