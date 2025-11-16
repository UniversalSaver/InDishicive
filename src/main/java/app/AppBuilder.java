package app;

import javax.swing.*;

import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.ViewRecipesViewModel;
import use_case.view_recipes.ViewRecipesInteractor;
import view.*;
import window.*;

//Favorites use case
import data_access.FavoriteDataAccessObject;
import interface_adapter.add_favorite.AddFavoriteController;
import interface_adapter.add_favorite.AddFavoritePresenter;
import interface_adapter.add_favorite.AddFavoriteViewModel;
import interface_adapter.view_favorite.ViewFavoriteController;
import interface_adapter.view_favorite.ViewFavoritePresenter;
import interface_adapter.view_favorite.ViewFavoriteViewModel;
import use_case.add_favorite.AddFavoriteInteractor;
import use_case.view_favorite.ViewFavoritesInteractor;
import view.FavoriteView;
import window.FavoriteWindow;
// end


import java.awt.*;

/**
 * An object that will build the app given what windows to include
 */
public class AppBuilder {

    private MainWindow mainWindow;

    private MainView mainView;


    /*
    Start of the UserRecipe variables
     */

    private UserRecipesWindow userRecipesWindow;


    private UserRecipesView userRecipesView;
    private final ViewRecipesViewModel viewRecipesViewModel = new ViewRecipesViewModel();

    private final UserRecipesViewManagerModel userRecipesViewManagerModel = new UserRecipesViewManagerModel();
    private UserRecipesViewManager userRecipesViewManager;

    private final JPanel userRecipeCardPanel = new JPanel();
    private final CardLayout userRecipeCardLayout = new CardLayout();

    /*
    End of UserRecipes Variables
     */


    public AppBuilder addMainView() {
        mainView = new MainView();
        this.mainWindow.addMainView(mainView);

        return this;
    }

    public AppBuilder addMainWindow() {
        mainWindow = new MainWindow("Indishisive");

        return this;
    }

    public AppBuilder addProfileMenu() {
        mainWindow.addProfileMenu();
        return this;
    }

    /*
    Start of UserRecipe Methods
     */

    public AppBuilder addUserRecipesWindow() {
        this.userRecipeCardPanel.setLayout(userRecipeCardLayout);

        this.userRecipesViewManager = new UserRecipesViewManager(this.userRecipeCardLayout,
                this.userRecipeCardPanel, this.userRecipesViewManagerModel);


        userRecipesWindow = new UserRecipesWindow(userRecipeCardPanel, userRecipeCardLayout,
                userRecipesViewManager, userRecipesViewManagerModel);

        return this;
    }

    public AppBuilder addUserRecipesView() {
        this.userRecipeCardPanel.add(this.userRecipesView);
        this.viewRecipesViewModel.addPropertyChangeListener(this.userRecipesWindow);

        userRecipesWindow.addUserRecipesView(userRecipesView, viewRecipesViewModel);
        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.viewRecipesViewModel, this.userRecipesViewManagerModel);

        ViewRecipesInteractor viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter);
        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        mainWindow.addViewRecipesUseCase(viewRecipesController);

        return this;
    }

    /*
    End of UserRecipe methods
     */

    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }
}
