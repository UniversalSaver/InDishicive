package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
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
                .addDietResView()

                .addViewRecipeDetailsUseCase()
                .addGenerateWithInventoryUseCase()

                .addViewRecipesUseCase()
                .addViewRestrictionsUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
