package logic.user_recipe.view_recipes.view_detailed_recipe;

import java.util.ArrayList;
import java.util.List;

import entity.Ingredient;

/**
 * A builder for the output data, because I find the creation to be ugly otherwise.
 */
public class OutputDataBuilder {
    private final List<String> ingredientAmounts = new ArrayList<>();
    private final List<String> ingredientNames = new ArrayList<>();
    private String title;
    private String steps;

    /**
     * Adds a title to the builder.
     * @param givenTitle title to be given
     * @return the builder
     */
    OutputDataBuilder addTitle(String givenTitle) {
        title = givenTitle;
        return this;
    }

    /**
     * Adds an ingredient to the builder. This guarantees the order is maintained.
     * @param ingredient ingredient to be added
     * @return the builder
     */
    OutputDataBuilder addIngredient(Ingredient ingredient) {
        ingredientNames.add(ingredient.getName());
        ingredientAmounts.add(ingredient.getAmount());

        return this;
    }

    /**
     * Adds the steps to the builder.
     * @param givenSteps steps to be added
     * @return the builder
     */
    OutputDataBuilder addSteps(String givenSteps) {
        steps = givenSteps;

        return this;
    }

    ViewUserRecipeOutputData build() {
        return new ViewUserRecipeOutputData(title, steps, ingredientNames, ingredientAmounts);
    }
}
