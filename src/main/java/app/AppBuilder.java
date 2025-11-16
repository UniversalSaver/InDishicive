package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data_access.FavoriteDataAccessObject;
import interface_adapter.UserRecipesViewManagerModel;

//favorites
import interface_adapter.add_favorite.AddFavoriteController;
import interface_adapter.add_favorite.AddFavoritePresenter;
import interface_adapter.add_favorite.AddFavoriteViewModel;
import interface_adapter.view_favorite.ViewFavoriteController;
import interface_adapter.view_favorite.ViewFavoritePresenter;
import interface_adapter.view_favorite.ViewFavoriteViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.ViewRecipesViewModel;
import use_case.add_favorite.AddFavoriteInteractor;
import use_case.view_favorite.ViewFavoriteInteractor;
import use_case.view_recipes.ViewRecipesInteractor;
import view.FavoriteView;
import view.MainView;
import view.UserRecipesView;
import view.UserRecipesViewManager;
import window.FavoriteWindow;
import window.MainWindow;
import window.UserRecipesWindow;

//View recipe
import interface_adapter.view_recipes.UserRecipesViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.UserRecipeWindowModel;
import use_case.view_recipes.ViewRecipesInteractor;
import view.*;
import view.user_recipe_view.UserRecipesView;
import view.user_recipe_view.UserRecipesViewManager;
import window.*;

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
    private UserRecipeWindowModel userRecipeWindowModel;


    private UserRecipesView userRecipesView;
    private UserRecipesViewModel userRecipesViewModel;

    private final UserRecipesViewManagerModel userRecipesViewManagerModel = new UserRecipesViewManagerModel();
    private UserRecipesViewManager userRecipesViewManager;

    private final JPanel userRecipeCardPanel = new JPanel();
    private final CardLayout userRecipeCardLayout = new CardLayout();

    /*
    End of UserRecipes Variables
     */

    /*
    Start of Favorites Variables
     */

    private final FavoriteDataAccessObject favoriteDataAccess = new FavoriteDataAccessObject();

    private final AddFavoriteViewModel addFavoriteViewModel = new AddFavoriteViewModel();
    private final ViewFavoriteViewModel viewFavoriteViewModel = new ViewFavoriteViewModel();

    private AddFavoriteController addFavoriteController;
    private ViewFavoriteController viewFavoriteController;

    private FavoriteView favoriteView;
    private FavoriteWindow favoriteWindow;

    /*
    End of Favorites Variables
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


        this.userRecipeWindowModel = new UserRecipeWindowModel();

        userRecipesWindow = new UserRecipesWindow(userRecipeCardPanel, userRecipeCardLayout,
                userRecipesViewManager, userRecipesViewManagerModel, userRecipeWindowModel);


        return this;
    }

    public AppBuilder addUserRecipesView() {
        this.userRecipesViewModel = new UserRecipesViewModel();

        userRecipesView = new UserRecipesView(userRecipesViewModel);

        this.userRecipeCardPanel.add(this.userRecipesView, userRecipesView.getViewName());
        this.userRecipeWindowModel.addPropertyChangeListener(this.userRecipesWindow);

        userRecipesWindow.addUserRecipesView(userRecipesView, userRecipesViewModel);
        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        ViewRecipesInteractor viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter);
        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        mainWindow.addViewRecipesUseCase(viewRecipesController);

        return this;
    }

    /*
    End of UserRecipe methods
     */



    /**
    Start of Favorites Methods
     */


    public AppBuilder addAddFavoriteUseCase() {
        AddFavoritePresenter addFavoritePresenter = new AddFavoritePresenter(this.addFavoriteViewModel);

        AddFavoriteInteractor addFavoriteInteractor = new AddFavoriteInteractor(
                this.favoriteDataAccess, addFavoritePresenter);

        this.addFavoriteController = new AddFavoriteController(addFavoriteInteractor);

        return this;
    }


    public AppBuilder addViewFavoritesUseCase() {
        ViewFavoritePresenter viewFavoritePresenter = new ViewFavoritePresenter(
                this.viewFavoriteViewModel);

        ViewFavoriteInteractor viewFavoriteInteractor = new ViewFavoriteInteractor(
                this.favoriteDataAccess, viewFavoritePresenter);

        this.viewFavoriteController = new ViewFavoriteController(viewFavoriteInteractor);

        return this;
    }


    public AppBuilder addFavoritesView() {
        this.favoriteView = new FavoriteView(this.viewFavoriteViewModel);
        this.favoriteWindow = new FavoriteWindow(this.favoriteView, this.viewFavoriteViewModel);
        this.viewFavoriteViewModel.addPropertyChangeListener(this.favoriteWindow);

        return this;
    }


    public AppBuilder addViewFavoritesButton() {
        mainWindow.addViewFavoriteButton(this.viewFavoriteController);
        return this;
    }


    public AddFavoriteController getAddFavoriteController() {
        return addFavoriteController;
    }


    public ViewFavoriteController getViewFavoritesController() {
        return viewFavoriteController;
    }

    /**
    End of Favorites Methods
     */


    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }
}
