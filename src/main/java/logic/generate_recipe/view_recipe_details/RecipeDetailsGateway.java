package logic.generate_recipe.view_recipe_details;

import entity.Recipe;

public interface RecipeDetailsGateway {
    Recipe findByTitle(String title);
}