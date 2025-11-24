package interface_adapter.user_recipe.view_recipes;

public class RecipeSummary {
    private final String title;
    private final String description;

    RecipeSummary(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
