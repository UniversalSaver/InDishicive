package app;

import javax.swing.JFrame;

import databases.user_recipe.FileDataAccessObject;

public class Main {
    /**
     * Launches the Indishicive app.
     *
     * @param args is an array of Strings that contains command-line arguments passed (sources: Google).
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        final FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("user_recipes.tsv");

        final JFrame application = appBuilder
                .addIndishisiveDAO(fileDataAccessObject)

                .addMainWindow()

                .addProfileMenu()

                .addUserRecipesWindow()
                .addDietResWindow()

                .addMainView()
                .addInventoryView()
                .addUserRecipesView()
                .addAddRecipeView()

                .addAddRecipeUseCase()
                .addUserRecipesCancelButtonUseCase()
                .addRecipeIngredientUseCase()
                .addViewRecipesUseCase()
                .addViewUserRecipeDetailsUseCase()
                .addViewCreatorUseCase()
                .addDeleteUserRecipeUseCase()

                .addViewRecipeDetailsUseCase()
                .addAddFavoriteUseCase()
                .addAddDietResUseCase()
                .addRemoveDietResUseCase()
                .addRandomRecipeUseCase()
                .addRemoveFavoriteUseCase()
                .addGenerateWithInventoryUseCase()
                .addGenerateByIngredientsUseCase()

                .addViewFavoritesUseCase()
                .addFavoritesView()
                .addViewFavoritesButton()

                .addViewRestrictionsUseCase()
                .addDietResView()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
