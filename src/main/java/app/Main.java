package app;

import javax.swing.*;

import databases.user_recipe.FileDataAccessObject;

public class Main {
    /**
     * Launches the Indishicive app.
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
                .addViewCreatorUseCase()

                .addViewRecipeDetailsUseCase()
                .addAddFavoriteUseCase()
                .addAddDietResUseCase()
                .addRemoveDietResUseCase()
                .addRemoveFavoriteUseCase()
                .addGenerateWithInventoryUseCase()

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
