package app;

import data_access.MemoryDataAccessObject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        MemoryDataAccessObject memoryDataAccessObject = new MemoryDataAccessObject();

        memoryDataAccessObject.updateUserRecipes();

        JFrame application = appBuilder
                .addIndishisiveDAO(memoryDataAccessObject)
                .addMainWindow()
                .addUserRecipesWindow()
                .addDietResWindow()
                .addProfileMenu()

                .addMainView()
                .addUserRecipesView()
                .addAddRecipeView()
                .addDietResView()

                .addViewRecipeDetailsUseCase()
                .addGenerateWithInventoryUseCase()
                .addViewRecipesUseCase()
                .addAddFavoriteUseCase()
                .addViewFavoritesUseCase()
                .addFavoritesView()
                .addViewFavoritesButton()
				.addViewCreatorUseCase()
                .addViewRestrictionsUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }

}
