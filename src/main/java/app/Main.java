package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addMainWindow()
                .addUserRecipesWindow()

                .addProfileMenu()

                .addMainView()
                .addUserRecipesView()

                .addViewRecipeDetailsUseCase()
                .addGenerateWithInventoryUseCase()

                .addViewRecipesUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
