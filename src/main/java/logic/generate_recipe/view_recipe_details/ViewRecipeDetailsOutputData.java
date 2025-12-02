package logic.generate_recipe.view_recipe_details;

import java.util.List;

/**
 * Output data for viewing recipe details.
 */
public class ViewRecipeDetailsOutputData {

    private final String title;
    private final List<String> ingredients;
    private final String instructions;
    private final String youtubeLink;

    public ViewRecipeDetailsOutputData(String title,
                                       List<String> ingredients,
                                       String instructions,
                                       String youtubeLink) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.youtubeLink = youtubeLink;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
  
    public String getYoutubeLink() { 
        return youtubeLink; 
    }
}
