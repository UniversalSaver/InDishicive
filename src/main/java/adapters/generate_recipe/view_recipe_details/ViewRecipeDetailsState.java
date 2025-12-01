package adapters.generate_recipe.view_recipe_details;

import java.util.List;

public class ViewRecipeDetailsState {
    private String title = "";
    private List<String> ingredients = List.of();
    private String instructions = "";
    private String youtubeLink = "";

    public String getTitle() { return title; }
    public List<String> getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public String getYoutubeLink() {
        return youtubeLink;
    }


    public void setTitle(String title) { this.title = title; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
}