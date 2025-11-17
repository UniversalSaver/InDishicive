package use_case.view_recipe_details;

public class ViewRecipeDetailsInputData {
    private final String title;

    public ViewRecipeDetailsInputData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}