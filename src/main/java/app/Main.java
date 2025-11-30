package app;

import databases.user_recipe.FileDataAccessObject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("user_recipes.tsv");

        JFrame application = appBuilder
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

                .addMissingIngredientsUseCase()
                .addViewRecipeDetailsUseCase()
                .addAddFavoriteUseCase()
                .addAddDietResUseCase()
                .addRemoveDietResUseCase()
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