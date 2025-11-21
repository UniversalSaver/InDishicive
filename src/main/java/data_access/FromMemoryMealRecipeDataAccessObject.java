package data_access;

import entity.Ingredient;
import entity.Recipe;
import org.jetbrains.annotations.NotNull;
import use_case.view_recipe_details.RecipeDetailsGateway;

import java.util.ArrayList;
import java.util.List;

public class FromMemoryMealRecipeDataAccessObject implements RecipeDetailsGateway {
    List<Recipe> recipes = new ArrayList<>();

    public FromMemoryMealRecipeDataAccessObject() {

        recipes.add(new Recipe("Bolognese",
                createIngredients(new String[] {"Cheese", "5 tbsp"}, new String[] {"Beef", "5 lbs"}),
                "Turn on oven, and cook", "BologneseImage",
				"BologneseYoutube", "Italian"));
        recipes.add(new Recipe("Pizza",
                createIngredients(new String[] {"Dough", "1 handful"}, new String[] {"Tomato Sauce", "A good amount"}),
                "Spread Sauce on dough", "PizzaImage", "PizzaYoutube", "Italian"));
    }

    @NotNull
    private static List<Ingredient> createIngredients(String[] ingredient1, String[] ingredient2) {
        List<Ingredient> result = new ArrayList<>();

        result.add(new Ingredient(ingredient1[0], ingredient1[1]));
        result.add(new Ingredient(ingredient2[0], ingredient2[1]));
        return result;
    }


    @Override
    public Recipe findByTitle(String title) {
        for (Recipe recipe : recipes) {
            if (recipe.getTitle().equals(title)) {
                return recipe;
            }
        }
        return null;
    }
}
