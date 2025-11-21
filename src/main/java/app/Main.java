package app;

import data_access.FileDataAccessObject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        FileDataAccessObject fileDataAccessObject = new FileDataAccessObject();

        fileDataAccessObject.updateUserRecipes();

        JFrame application = appBuilder
                .addIndishisiveDAO(fileDataAccessObject)

                .addMainWindow()
                .addUserRecipesWindow()
                .addDietResWindow()
                .addProfileMenu()

                .addMainView()
                .addUserRecipesView()
                .addAddRecipeView()

                .addUserRecipesCancelButtonUseCase()
				.addIngredientUseCase()
                .addViewRecipesUseCase()
                .addViewCreatorUseCase()
                .addDietResView()

                .addViewRecipeDetailsUseCase()
                .addGenerateWithInventoryUseCase()

                .addViewRecipesUseCase()

                .addAddFavoriteUseCase()
                .addViewFavoritesUseCase()
                .addFavoritesView()
                .addViewFavoritesButton()


                .addViewRestrictionsUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
