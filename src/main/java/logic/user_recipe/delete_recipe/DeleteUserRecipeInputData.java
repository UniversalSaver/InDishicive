package logic.user_recipe.delete_recipe;

public class DeleteUserRecipeInputData {
    private final String recipeTitle;

    public DeleteUserRecipeInputData(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }
}
