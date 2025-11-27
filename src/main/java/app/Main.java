package app;

import data_access.user_recipe.FileDataAccessObject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("user_recipes.tsv");

        JFrame application = appBuilder
                .addIndishisiveDAO(fileDataAccessObject)

                .addMainWindow()
//                .addUserRecipesWindow()

                .addProfileMenu()

                .addMainView()
//                .addUserRecipesView()

                .addInventoryView()
                .addUserRecipesWindow()
                .addDietResWindow()
                .addProfileMenu()

                .addMainView()
                .addUserRecipesView()
                .addAddRecipeView()

				.addAddRecipeUseCase()
                .addUserRecipesCancelButtonUseCase()
                .addIngredientUseCase()
                .addViewRecipesUseCase()
                .addViewCreatorUseCase()

                .addViewRecipeDetailsUseCase()
                .addAddFavoriteUseCase()
                .addAddDietResUseCase()
                .addRemoveDietResUseCase()
                .addGenerateWithInventoryUseCase()

                .addViewRecipesUseCase()

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