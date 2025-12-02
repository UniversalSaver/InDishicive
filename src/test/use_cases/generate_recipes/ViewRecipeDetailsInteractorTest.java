package use_cases.view_recipe_details;

import entity.Ingredient;
import entity.Recipe;
import logic.generate_recipe.view_recipe_details.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ViewRecipeDetailsInteractorTest {

    @Test
    void recipeFound_presentsFullDetails() {
        List<Ingredient> ingredients = List.of(
                new Ingredient("Cheese", "2 slices"),
                new Ingredient("Ham", "3 pieces")
        );
        Recipe fakeRecipe = new Recipe(
                "Pizza",
                ingredients,
                "Bake it in the oven.",
                "", "", ""
        );

        RecipeDetailsGateway gateway = new RecipeDetailsGateway() {
            @Override
            public Recipe findByTitle(String title) {
                Assertions.assertEquals("Pizza", title);
                return fakeRecipe;
            }
        };

        ViewRecipeDetailsOutputBoundary presenter = new ViewRecipeDetailsOutputBoundary() {
            @Override
            public void present(ViewRecipeDetailsOutputData outputData) {
                Assertions.assertEquals("Pizza", outputData.getTitle());

                List<String> expectedIngredients = List.of(
                        "Cheese: 2 slices",
                        "Ham: 3 pieces"
                );
                Assertions.assertEquals(expectedIngredients, outputData.getIngredients());

                Assertions.assertEquals("Bake it in the oven.", outputData.getInstructions());
            }
        };

        ViewRecipeDetailsInteractor interactor =
                new ViewRecipeDetailsInteractor(gateway, presenter);

        ViewRecipeDetailsInputData inputData =
                new ViewRecipeDetailsInputData("Pizza");
        interactor.execute(inputData);
    }

    @Test
    void recipeNotFound_presentsEmptyData() {
        RecipeDetailsGateway gateway = new RecipeDetailsGateway() {
            @Override
            public Recipe findByTitle(String title) {
                return null;
            }
        };

        ViewRecipeDetailsOutputBoundary presenter = new ViewRecipeDetailsOutputBoundary() {
            @Override
            public void present(ViewRecipeDetailsOutputData outputData) {
                Assertions.assertEquals("", outputData.getTitle());
                Assertions.assertTrue(outputData.getIngredients().isEmpty());
                Assertions.assertEquals("", outputData.getInstructions());
            }
        };

        ViewRecipeDetailsInteractor interactor =
                new ViewRecipeDetailsInteractor(gateway, presenter);

        ViewRecipeDetailsInputData inputData =
                new ViewRecipeDetailsInputData("Does Not Exist");
        interactor.execute(inputData);
    }
}