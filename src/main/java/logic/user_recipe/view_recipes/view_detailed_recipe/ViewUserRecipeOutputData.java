package logic.user_recipe.view_recipes.view_detailed_recipe;

import java.util.List;

public class ViewUserRecipeOutputData {
    private final String title;
    private final String steps;
    private final List<String> ingredientNames;
    private final List<String> ingredientAmounts;

    public ViewUserRecipeOutputData(String title, String steps,
                                    List<String> ingredientNames, List<String> ingredientAmounts) {
        this.title = title;
        this.steps = steps;
        this.ingredientNames = ingredientNames;
        this.ingredientAmounts = ingredientAmounts;
    }

    public String getTitle() {
        return title;
    }

    public String getSteps() {
        return steps;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    public List<String> getIngredientAmounts() {
        return ingredientAmounts;
    }
}
