package use_cases.delete_recipe;

import logic.user_recipe.delete_recipe.DeleteUserRecipeDataAccessInterface;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInputData;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInteractor;
import logic.user_recipe.view_recipes.ViewRecipesInputBoundary;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsOutputBoundary;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeOutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the delete recipe interactor
 */
class DeleteRecipeInteractorTest {
    /**
     * Tests the interactor with a mock success case.
     */
    @Test
    void deleteRecipeSuccessTest() {
        ViewRecipesInputBoundary successBoundary = () -> {
            // Pass, as this is what should run.
        };
        DeleteUserRecipeDataAccessInterface database = title -> DeleteUserRecipeDataAccessInterface.DELETED;
        ViewUserRecipeDetailsOutputBoundary failureBoundary = new ViewUserRecipeDetailsOutputBoundary() {

            @Override
            public void presentSuccessView(ViewUserRecipeOutputData outputData) {
                fail("Should not be calling this ever");
            }

            @Override
            public void presentFailureView() {
                fail("Should not be called on success");
            }
        };

        DeleteUserRecipeInteractor interactor = new DeleteUserRecipeInteractor(
                successBoundary, database, failureBoundary);

        interactor.execute(new DeleteUserRecipeInputData("Test"));
    }

    /**
     * Tests the interactor with a mock failure
     */
    @Test
    void deleteRecipeFailureTest() {
        ViewRecipesInputBoundary successBoundary = () -> fail("Should not be called on failure");
        DeleteUserRecipeDataAccessInterface database = title -> "Failed for some reason";
        ViewUserRecipeDetailsOutputBoundary failureBoundary = new ViewUserRecipeDetailsOutputBoundary() {

            @Override
            public void presentSuccessView(ViewUserRecipeOutputData outputData) {
                fail("Should not be calling this ever");
            }

            @Override
            public void presentFailureView() {
                // Pass, as this is what is desired
            }
        };

        DeleteUserRecipeInteractor interactor = new DeleteUserRecipeInteractor(
                successBoundary, database, failureBoundary);

        interactor.execute(new DeleteUserRecipeInputData("Test"));

    }
}
