package logic.generate_recipe.random_recipe;

import entity.Recipe;
import java.util.Optional;

public interface RandomRecipeGateway {
    // Returns a random recipe from the API
    Optional<Recipe> getRandomRecipe();
}