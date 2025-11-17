package app;

import javax.swing.*;

import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.ViewRecipesViewModel;
import use_case.view_recipes.ViewRecipesInteractor;
import view.*;
import window.*;
import data_access.MealDbRecipeDetailsGateway;
import data_access.InMemoryInventoryReader;
import data_access.MealDbRecipeGateway;
import interface_adapter.generate_with_inventory.GenerateWithInventoryController;
import interface_adapter.generate_with_inventory.GenerateWithInventoryPresenter;
import interface_adapter.generate_with_inventory.GenerateWithInventoryViewModel;
import use_case.generate_with_inventory.GenerateWithInventoryInputBoundary;
import use_case.generate_with_inventory.GenerateWithInventoryInteractor;
import use_case.generate_with_inventory.RecipeGateway;
import use_case.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import interface_adapter.view_recipe_details.*;
import use_case.view_recipe_details.*;

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
    private ViewRecipeDetailsViewModel viewRecipeDetailsViewModel;
    private ViewRecipeDetailsController viewRecipeDetailsController;
    private RecipeDetailsWindow recipeDetailsWindow;

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

    public AppBuilder addGenerateWithInventoryUseCase() {
        RecipeGateway recipeGateway = new MealDbRecipeGateway();

        // Used for testing UC1; replace with real inventory once implemented.
        InMemoryInventoryReader inMemoryInventoryReader = new InMemoryInventoryReader();
        inMemoryInventoryReader.add("");
        inMemoryInventoryReader.add("cheese");

        GenerateWithInventoryViewModel generateWithInventoryViewModel = new GenerateWithInventoryViewModel();

        GenerateByInventoryPanel panel = getGenerateByInventoryPanel(generateWithInventoryViewModel, inMemoryInventoryReader, recipeGateway);

        this.mainView.addGenerateByInventoryPanel(panel);

        return this;
    }

    private GenerateByInventoryPanel getGenerateByInventoryPanel(
            GenerateWithInventoryViewModel generateWithInventoryViewModel,
            InMemoryInventoryReader inMemoryInventoryReader,
            RecipeGateway recipeGateway) {

        GenerateWithInventoryOutputBoundary presenter =
                new GenerateWithInventoryPresenter(generateWithInventoryViewModel);

        GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(inMemoryInventoryReader, recipeGateway, presenter);

        GenerateWithInventoryController generateWithInventoryController =
                new GenerateWithInventoryController(interactor);

        return new GenerateByInventoryPanel(
                generateWithInventoryController,
                generateWithInventoryViewModel,
                viewRecipeDetailsController
        );
    }

    public AppBuilder addViewRecipeDetailsUseCase() {
        viewRecipeDetailsViewModel = new ViewRecipeDetailsViewModel();
        recipeDetailsWindow = new RecipeDetailsWindow(viewRecipeDetailsViewModel);

        ViewRecipeDetailsOutputBoundary outputBoundary =
                new ViewRecipeDetailsPresenter(viewRecipeDetailsViewModel);

        ViewRecipeDetailsInputBoundary interactor =
                new ViewRecipeDetailsInteractor(
                        new MealDbRecipeDetailsGateway(),
                        outputBoundary
                );

        viewRecipeDetailsController = new ViewRecipeDetailsController(interactor);

        return this;
    }
}
