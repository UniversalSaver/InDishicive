package app;

import data_access.MemoryDataAccessObject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        MemoryDataAccessObject memoryDataAccessObject = new MemoryDataAccessObject();

        JFrame application = appBuilder
                .addIndishisiveDAO(memoryDataAccessObject)

                .addMainWindow()
                .addUserRecipesWindow()
                .addDietResWindow()
                .addProfileMenu()

                .addMainView()
                .addUserRecipesView()
                .addAddRecipeView()

                .addUserRecipesCancelButtonUseCase()
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
