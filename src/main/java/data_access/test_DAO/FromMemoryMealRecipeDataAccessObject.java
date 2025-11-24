package data_access.test_DAO;

import entity.Ingredient;
import entity.Recipe;
import org.jetbrains.annotations.NotNull;
import use_case.user_recipe.add_recipe.add_ingredient.AddIngredientDataAccessInterface;
import use_case.generate_recipe.view_recipe_details.RecipeDetailsGateway;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FromMemoryMealRecipeDataAccessObject implements RecipeDetailsGateway, AddIngredientDataAccessInterface {
    List<Recipe> recipes = new ArrayList<>();
	List<Ingredient> ingredients = new ArrayList<>();

    public FromMemoryMealRecipeDataAccessObject() {

        recipes.add(new Recipe("Bolognese",
                createIngredients(new String[] {"Cheese", "5 tbsp"}, new String[] {"Beef", "5 lbs"}),
                "Turn on oven, and cook", "BologneseImage",
				"BologneseYoutube", "Italian"));
        recipes.add(new Recipe("Pizza",
                createIngredients(new String[] {"Dough", "1 handful"}, new String[] {"Tomato Sauce", "A good amount"}),
                "Spread Sauce on dough", "PizzaImage",
				"PizzaYoutube", "Italian"));

		ingredients.add(new Ingredient("Dough", "N/A"));
		ingredients.add(new Ingredient("Tomato Sauce", "N/A"));
		ingredients.add(new Ingredient("Cheese", "N/A"));
		ingredients.add(new Ingredient("Beef", "N/A"));
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

	@Override
	public List<Ingredient> listPossibleIngredients() {
		return getSortedIngredients();
	}

	@NotNull
	private List<Ingredient> getSortedIngredients() {
		return ingredients.stream().sorted(Comparator.comparing(Ingredient::getName)).toList();
	}
}
