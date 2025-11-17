package entity;

import java.util.List;

/**
 * An entity representing a recipe from MealDB. Uses only possible attributes from an API call
 */
public class Recipe {
    private final String title;
    private final List<Ingredient> ingredients;
    private final String steps;
    private final String imageLink;
    private final String youtubeLink;

    /**
     * Creates a recipe given all the information from it
     * @param title the name of the dish
     * @param ingredients a list of the ingredients
     * @param steps a paragraph string of the steps required
     * @param imageLink a string of the image of the dish
     * @param youtubeLink a string of the link to a Youtube tutorial
     */
    public Recipe(String title, List<Ingredient> ingredients, String steps, String imageLink, String youtubeLink) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imageLink = imageLink;
        this.youtubeLink = youtubeLink;
    }
    public String getTitle() {
        return title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}
