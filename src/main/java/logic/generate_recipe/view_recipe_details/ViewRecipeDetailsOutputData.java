package logic.generate_recipe.view_recipe_details;

import java.util.List;

public class ViewRecipeDetailsOutputData {
    private final String title;
    private final List<String> ingredients;
    private final String instructions;

    public ViewRecipeDetailsOutputData(String title,
                                       List<String> ingredients,
                                       String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getTitle() { return title; }
    public List<String> getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
}