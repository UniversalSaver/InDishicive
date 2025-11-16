package app;

import javax.swing.*;

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
/**
 * An object that will build the app given what windows to include
 */
public class AppBuilder {

    private MainWindow mainWindow;
    private UserRecipesWindow userRecipesWindow;

    private MainView mainView;
    private UserRecipesView userRecipesView;
    private ViewRecipesViewModel viewRecipesViewModel;

    private ViewRecipeDetailsViewModel viewRecipeDetailsViewModel;
    private ViewRecipeDetailsController viewRecipeDetailsController;
    private RecipeDetailsWindow recipeDetailsWindow;

    public AppBuilder addUserRecipesView() {
        userRecipesView = new UserRecipesView();
        viewRecipesViewModel = new ViewRecipesViewModel("view recipes");
        userRecipesWindow = new UserRecipesWindow(this.viewRecipesViewModel);

        userRecipesWindow.addUserRecipesView(userRecipesView);

        return this;
    }

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
        mainWindow.addProfileMenu(userRecipesView);
        return this;
    }

    public AppBuilder addUserRecipesWindow() {
        mainWindow.addUserRecipesWindow(userRecipesWindow);

        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipesWindow, this.viewRecipesViewModel);

        ViewRecipesInteractor viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter);

        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        mainWindow.addViewRecipesUseCase(viewRecipesController);

        return this;
    }

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
