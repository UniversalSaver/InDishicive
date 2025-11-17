package app;

import javax.swing.*;

import data_access.MealDBIngredientDataAccess;
import entity.Ingredient;
import entity.Inventory;
import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.add_ingredient.AddIngredientController;
import interface_adapter.add_ingredient.AddIngredientPresenter;
import interface_adapter.add_ingredient.AddIngredientViewModel;
import interface_adapter.remove_ingredient.RemoveIngredientController;
import interface_adapter.remove_ingredient.RemoveIngredientPresenter;
import interface_adapter.remove_ingredient.RemoveIngredientViewModel;
import interface_adapter.search_ingredients.SearchIngredientsController;
import interface_adapter.search_ingredients.SearchIngredientsPresenter;
import interface_adapter.search_ingredients.SearchIngredientsViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.ViewRecipesViewModel;
import use_case.add_ingredient.AddIngredientInteractor;
import use_case.remove_ingredient.RemoveIngredientInteractor;
import use_case.search_ingredients.SearchIngredientsInteractor;
import use_case.view_recipes.ViewRecipesInteractor;
import view.*;
import window.*;

import java.awt.*;
import java.util.ArrayList;

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
    Start of Inventory Variables
     */

    private InventoryView inventoryView;
    private final Inventory inventory = new Inventory(new ArrayList<Ingredient>());
    private final SearchIngredientsViewModel searchIngredientsViewModel = new SearchIngredientsViewModel();
    private final AddIngredientViewModel addIngredientViewModel = new AddIngredientViewModel();
    private final RemoveIngredientViewModel removeIngredientViewModel = new RemoveIngredientViewModel();

    /*
    End of Inventory Variables
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

    /*
    Start of Inventory Methods
     */

    public AppBuilder addInventoryView() {
        MealDBIngredientDataAccess dataAccess = new MealDBIngredientDataAccess();
        
        SearchIngredientsPresenter searchPresenter = new SearchIngredientsPresenter(searchIngredientsViewModel);
        SearchIngredientsInteractor searchInteractor = new SearchIngredientsInteractor(searchPresenter, dataAccess);
        SearchIngredientsController searchController = new SearchIngredientsController(searchInteractor);
        
        AddIngredientPresenter addPresenter = new AddIngredientPresenter(addIngredientViewModel);
        AddIngredientInteractor addInteractor = new AddIngredientInteractor(addPresenter, inventory);
        AddIngredientController addController = new AddIngredientController(addInteractor);
        
        RemoveIngredientPresenter removePresenter = new RemoveIngredientPresenter(removeIngredientViewModel);
        RemoveIngredientInteractor removeInteractor = new RemoveIngredientInteractor(removePresenter, inventory);
        RemoveIngredientController removeController = new RemoveIngredientController(removeInteractor);
        
        inventoryView = new InventoryView(searchController, addController, removeController, 
                                          searchIngredientsViewModel, inventory);
        
        mainView.addInventoryTab(inventoryView);
        
        return this;
    }

    /*
    End of Inventory Methods
     */

    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }
}
