package logic.generate_recipe.generate_by_ingredients;

import java.util.List;

public class GenerateByIngredientsOutputData {

    private final List<String> recipeTitles;

    public GenerateByIngredientsOutputData(List<String> recipeTitles) {
        this.recipeTitles = recipeTitles;
    }

    public List<String> getRecipeTitles() {
        return recipeTitles;
    }
}