package interface_adapter.view_recipe_details;

import java.util.List;

public class ViewRecipeDetailsState {
    private String title = "";
    private List<String> ingredients = List.of();
    private String instructions = "";

    public String getTitle() { return title; }
    public List<String> getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }

    public void setTitle(String title) { this.title = title; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}