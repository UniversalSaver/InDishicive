package use_case.view_recipe_details;

import entity.Recipe;

public interface RecipeDetailsGateway {
    Recipe findByTitle(String title);
}