package use_cases.view_recipe;

import data_access.FromMemoryMealRecipeDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import use_case.view_recipe_details.*;

import java.util.List;

class ViewRecipeDetailsInteractorTest {

    @Test
    void noRecipeTest() {
        ViewRecipeDetailsInputData viewRecipeDetailsInputData = new ViewRecipeDetailsInputData("Stroganoff");

        ViewRecipeDetailsOutputBoundary successPresenter = outputData -> {
            Assertions.assertEquals("", outputData.getTitle());
            Assertions.assertEquals(List.of(), outputData.getIngredients());
            Assertions.assertEquals("", outputData.getInstructions());
        };

        ViewRecipeDetailsInteractor interactor = new ViewRecipeDetailsInteractor(new FromMemoryMealRecipeDataAccessObject(), successPresenter);

        interactor.execute(viewRecipeDetailsInputData);
    }

    @Test
    void existsRecipeTest() {
        ViewRecipeDetailsInputData viewRecipeDetailsInputData = new ViewRecipeDetailsInputData("Pizza");

        String expectedName = "Pizza";
        String expectedInstructions = "Spread Sauce on dough";
        List<String> expectedIngredients = List.of("Dough: 1 handful", "Tomato Sauce: A good amount");

        ViewRecipeDetailsOutputBoundary successPresenter = outputData -> {
            Assertions.assertEquals(expectedName, outputData.getTitle());
            Assertions.assertEquals(expectedIngredients.get(0), outputData.getIngredients().get(0));
            Assertions.assertEquals(expectedIngredients.get(1), outputData.getIngredients().get(1));
            Assertions.assertEquals(expectedInstructions, outputData.getInstructions());
        };

        ViewRecipeDetailsInteractor interactor = new ViewRecipeDetailsInteractor(new FromMemoryMealRecipeDataAccessObject(), successPresenter);

        interactor.execute(viewRecipeDetailsInputData);
    }
}
