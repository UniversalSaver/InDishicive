package app;

import javax.swing.*;

import interface_adapter.DietResViewManagerModel;
import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.view_diet_res.DietResViewModel;
import interface_adapter.view_diet_res.DietResWindowModel;
import interface_adapter.view_diet_res.ViewRestrictionsController;
import interface_adapter.view_diet_res.ViewRestrictionsPresenter;
import interface_adapter.view_recipes.UserRecipesViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.UserRecipeWindowModel;
import use_case.view_recipes.ViewRecipesInteractor;
import use_case.view_restrictions.ViewRestrictionsInteractor;
import use_case.view_restrictions.ViewRestrictionsOutputBoundary;
import view.*;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;
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
    Start of DietRes variables
     */
    private DietResWindow dietResWindow;
    private DietResWindowModel dietResWindowModel;


    private DietResView dietResView;
    private DietResViewModel dietResViewModel;

    private final DietResViewManagerModel dietResViewManagerModel = new DietResViewManagerModel();
    private DietResViewManager dietResViewManager;

    private final JPanel dietResCardPanel = new JPanel();
    private final CardLayout dietResCardLayout = new CardLayout();

    /*
    End of DietRes variables
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

    /*
    Start of DietRes Methods
     */
    public AppBuilder addDietResWindow() {
        this.dietResCardPanel.setLayout(dietResCardLayout);

        this.dietResViewManager = new DietResViewManager(this.dietResCardLayout,
                this.dietResCardPanel, this.dietResViewManagerModel);

        this.dietResWindowModel = new DietResWindowModel();

        dietResWindow = new DietResWindow(dietResCardPanel, dietResCardLayout,
                dietResViewManager, dietResViewManagerModel, dietResWindowModel);


        return this;
    }

    public AppBuilder addDietResView() {
        this.dietResViewModel = new DietResViewModel();

        dietResView = new DietResView(dietResViewModel);

        this.dietResCardPanel.add(this.dietResView, dietResView.getViewName());
        this.dietResWindowModel.addPropertyChangeListener(this.dietResWindow);

        dietResWindow.addDietResView(dietResView, dietResViewModel);
        return this;
    }

    public AppBuilder addViewRestrictionsUseCase() {
        ViewRestrictionsPresenter viewRestrictionsPresenter = new ViewRestrictionsPresenter(
                this.dietResWindowModel, this.dietResViewManagerModel, this.dietResViewModel);

        ViewRestrictionsInteractor viewRestrictionsInteractor = new ViewRestrictionsInteractor(viewRestrictionsPresenter);
        ViewRestrictionsController viewRestrictionsController = new ViewRestrictionsController(viewRestrictionsInteractor);

        mainWindow.addViewRestrictionsUseCase(viewRestrictionsController);

        return this;
    }
    /*
    End of DietRes methods
     */

    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }
}
