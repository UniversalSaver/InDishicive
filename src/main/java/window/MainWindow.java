package window;

import javax.swing.*;

import interface_adapter.favorites.view_favorite.ViewFavoriteController;
import interface_adapter.dietary_restriction.view_diet_res.ViewRestrictionsController;
import interface_adapter.user_recipe.view_recipes.ViewRecipesController;
import view.MainView;

public class MainWindow extends JFrame {

    private final JMenuBar optionsMenuBar;

    private ViewRecipesController viewRecipesController;
    private ViewRestrictionsController viewRestrictionsController;

    public MainWindow(String title) {
        super(title);
        optionsMenuBar = new JMenuBar();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(optionsMenuBar);
    }

    public void addViewRecipesUseCase(ViewRecipesController viewRecipesController) {
        this.viewRecipesController = viewRecipesController;
    }

    public void addViewRestrictionsUseCase(ViewRestrictionsController viewRestrictionsController) {
        this.viewRestrictionsController = viewRestrictionsController;
    }

    public void addProfileMenu() {
        JMenu profile = new JMenu("Profile");

        optionsMenuBar.add(profile);

        JMenuItem createRecipe = new JMenuItem("User Recipes");
        profile.add(createRecipe);

        createRecipe.addActionListener(
        evt -> {
                viewRecipesController.execute();
            }
        );


        JMenuItem dietRestrictionsMenu = new JMenuItem("Dietary Restrictions");
        profile.add(dietRestrictionsMenu);

        dietRestrictionsMenu.addActionListener(
                evt -> {
                    viewRestrictionsController.execute();
                }
        );
    }

    /**
     * Start of favorite use case
     */

    public void addViewFavoriteButton(ViewFavoriteController controller) {
        JMenu viewMenu = new JMenu("View");
        JMenuItem favoritesMenuItem = new JMenuItem("View Favorites");
        favoritesMenuItem.addActionListener(e -> controller.execute());

        viewMenu.add(favoritesMenuItem);
        optionsMenuBar.add(viewMenu);
    }
    /**
     * End of favorite use case
     */

    public void addMainView(MainView mainView) {
        this.add(mainView);
    }
}
