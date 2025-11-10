package window;

import javax.swing.*;

import interface_adapter.view_recipes.ViewRecipesController;
import view.MainView;
import view.UserRecipesView;

public class MainWindow extends JFrame {

    private final JMenuBar optionsMenuBar;

    private ViewRecipesController viewRecipesController;

    private UserRecipesWindow userRecipesWindow;

    public MainWindow(String title) {
        super(title);
        optionsMenuBar = new JMenuBar();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(optionsMenuBar);
    }

    public void addUserRecipesWindow(UserRecipesWindow userRecipesWindow) {
        this.userRecipesWindow = userRecipesWindow;
    }

    public void addViewRecipesUseCase(ViewRecipesController viewRecipesController) {
        this.viewRecipesController = viewRecipesController;
    }

    public void addProfileMenu(UserRecipesView userRecipesView) {
        JMenu profile = new JMenu("Profile");

        optionsMenuBar.add(profile);

        JMenuItem createRecipe = new JMenuItem("User Recipes");
        profile.add(createRecipe);

        createRecipe.addActionListener(
        evt -> {
                viewRecipesController.execute();
            }
        );
    }

    public void addMainView(MainView mainView) {
        this.add(mainView);
    }
}
