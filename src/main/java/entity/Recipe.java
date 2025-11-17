package entity;

import java.util.List;
import java.util.Objects;

/**
 * An entity representing a recipe from MealDB. Uses only possible attributes from an API call
 */
public class Recipe {
    private final String title;
    private final List<Ingredient> ingredients;
    private final String steps;
    private final String imageLink;
    private final String youtubeLink;
    private final String category;

    /**
     * Creates a recipe given all the information from it
     * @param title the name of the dish
     * @param ingredients a list of the ingredients
     * @param steps a paragraph string of the steps required
     * @param imageLink a string of the image of the dish
     * @param youtubeLink a string of the link to a Youtube tutorial
     * @param category a string of the category of the Recipe.
     */
    public Recipe(String title, List<Ingredient> ingredients, String steps, String imageLink, String youtubeLink, String category) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imageLink = imageLink;
        this.youtubeLink = youtubeLink;
        this.category = category;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) obj;
        return Objects.equals(title, recipe.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
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
