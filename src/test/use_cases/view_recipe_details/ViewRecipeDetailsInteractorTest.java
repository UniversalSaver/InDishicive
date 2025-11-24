package use_cases.view_recipe_details;

import entity.Ingredient;
import entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import use_case.view_recipe_details.ViewRecipeDetailsInputData;
import use_case.view_recipe_details.ViewRecipeDetailsOutputData;
import use_case.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import use_case.view_recipe_details.ViewRecipeDetailsInteractor;
import use_case.view_recipe_details.RecipeDetailsGateway;

class ViewRecipeDetailsInteractorTest {

    private static class RecordingGateway implements RecipeDetailsGateway {

        String lastTitle;
        Recipe recipeToReturn;

        @Override
        public Recipe findByTitle(String title) {
            this.lastTitle = title;
            return recipeToReturn;
        }
    }

    private static class RecordingPresenter implements ViewRecipeDetailsOutputBoundary {

        ViewRecipeDetailsOutputData lastOutput;
        int callCount = 0;

        @Override
        public void present(ViewRecipeDetailsOutputData outputData) {
            this.lastOutput = outputData;
            this.callCount++;
        }
    }


    @Test
    void execute_whenRecipeNotFound_callsPresenterWithEmptyOutput() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        ViewRecipeDetailsInteractor interactor =
                new ViewRecipeDetailsInteractor(gateway, presenter);

        ViewRecipeDetailsInputData input =
                new ViewRecipeDetailsInputData("Nonexistent title");

        interactor.execute(input);

        assertEquals("Nonexistent title", gateway.lastTitle);
        assertEquals(1, presenter.callCount);
        assertNotNull(presenter.lastOutput);

        ViewRecipeDetailsOutputData out = presenter.lastOutput;

        assertEquals("", out.getTitle());
        assertTrue(out.getIngredients().isEmpty());

    }

    @Test
    void execute_whenRecipeFound_buildsIngredientStringsAndPassesToPresenter() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        ViewRecipeDetailsInteractor interactor =
                new ViewRecipeDetailsInteractor(gateway, presenter);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Chicken", "200g"));
        ingredients.add(new Ingredient("Salt", "1 tsp"));

        String steps = "Step 1: Do something.\nStep 2: Eat.";
        gateway.recipeToReturn = new Recipe(
                "My Chicken Dish",
                ingredients,
                steps,
                "image-url",
                "youtube-url",
                "category"
        );

        ViewRecipeDetailsInputData input =
                new ViewRecipeDetailsInputData("My Chicken Dish");

        interactor.execute(input);

        assertEquals("My Chicken Dish", gateway.lastTitle);
        assertEquals(1, presenter.callCount);
        assertNotNull(presenter.lastOutput);

        ViewRecipeDetailsOutputData out = presenter.lastOutput;

        assertEquals("My Chicken Dish", out.getTitle());
        assertEquals(2, out.getIngredients().size());
        assertEquals("Chicken: 200g", out.getIngredients().get(0));
        assertEquals("Salt: 1 tsp", out.getIngredients().get(1));
    }
}