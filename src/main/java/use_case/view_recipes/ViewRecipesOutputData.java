package use_case.view_recipes;

public class ViewRecipesOutputData {

    private final String title;
    private final String description;

    ViewRecipesOutputData(String title, String description) {
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
