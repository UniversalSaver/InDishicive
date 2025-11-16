package window;

import javax.swing.*;

import interface_adapter.view_favorite.ViewFavoriteController;
import interface_adapter.view_recipes.ViewRecipesController;
import view.MainView;

import java.awt.*;

public class MainWindow extends JFrame {

    private final JMenuBar optionsMenuBar;

    private ViewRecipesController viewRecipesController;

    public MainWindow(String title) {
        super(title);
        optionsMenuBar = new JMenuBar();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(optionsMenuBar);
    }

    public void addViewRecipesUseCase(ViewRecipesController viewRecipesController) {
        this.viewRecipesController = viewRecipesController;
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
    }

    /**
     * Start of favorite use case
     */

    public void addViewFavoriteButton(ViewFavoriteController controller) {
        JButton favoriteButton = new JButton("View Favorites");
        favoriteButton.addActionListener(e -> controller.execute());

        JPanel  favoritePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        favoritePanel.add(favoriteButton);

        add(favoritePanel, BorderLayout.NORTH);
    }
    /**
     * End of favorite use case
     */

    public void addMainView(MainView mainView) {
        this.add(mainView);
    }
}
