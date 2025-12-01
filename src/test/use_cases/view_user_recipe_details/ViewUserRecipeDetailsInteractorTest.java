package use_cases.view_user_recipe_details;

import entity.Ingredient;
import entity.UserRecipe;
import logic.user_recipe.view_recipes.ViewRecipesInputBoundary;
import logic.user_recipe.view_recipes.view_detailed_recipe.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ViewUserRecipeDetailsInteractorTest {

    @Test
    void foundRecipeTest() {
        String inputTitle = "Pizza";
        String expectedOutput = "Pizza";

        ViewUserRecipeDetailsOutputBoundary presenter = new ViewUserRecipeDetailsOutputBoundary() {
            @Override
            public void presentSuccessView(ViewUserRecipeOutputData outputData) {
                assertEquals(expectedOutput, inputTitle);
            }

            @Override
            public void presentFailureView() {
                fail("Should not present failure view.");
            }
        };

        ViewRecipesInputBoundary viewRecipesInteractor = () -> fail("Should not present failure view.");

        ViewUserRecipeDetailsDataAccessInterface database = recipeTitle ->
                new UserRecipe(recipeTitle, (Arrays.stream(new Ingredient[] {
                new Ingredient("Mac", "5 lbs")
        }).toList()), "Boil Mac", "A nonsense recipe");

        ViewUserRecipeDetailsInteractor viewUserRecipeDetailsInteractor = new ViewUserRecipeDetailsInteractor(
                database, presenter, viewRecipesInteractor
        );

        viewUserRecipeDetailsInteractor.execute(new ViewUserRecipeDetailsInputData(inputTitle));
    }

    @Test
    void recipeNotFoundTest() {
        String inputTitle = "Pizza";

        ViewUserRecipeDetailsOutputBoundary presenter = new ViewUserRecipeDetailsOutputBoundary() {
            @Override
            public void presentSuccessView(ViewUserRecipeOutputData outputData) {
                fail("Should not present failure view.");
            }
            @Override
            public void presentFailureView() {
                // Pass, as this should happen
            }
        };
        ViewUserRecipeDetailsDataAccessInterface database = recipeTitle -> {
            // Simulates a couldn't find recipe.
            return null;
        };
        ViewRecipesInputBoundary viewRecipesInteractor = () -> {
            // Pass, as this should happen
        };

        ViewUserRecipeDetailsInteractor viewUserRecipeDetailsInteractor = new ViewUserRecipeDetailsInteractor(
                database, presenter, viewRecipesInteractor
        );

        viewUserRecipeDetailsInteractor.execute(new ViewUserRecipeDetailsInputData(inputTitle));
    }
}
