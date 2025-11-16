package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data_access.FavoriteDataAccessObject;
import interface_adapter.UserRecipesViewManagerModel;
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

        this.userRecipesView = new UserRecipesView();

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
