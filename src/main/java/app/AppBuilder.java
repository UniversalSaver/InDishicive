package app;

import javax.swing.*;

import interface_adapter.DietResViewManagerModel;
import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.add_recipe.AddRecipeViewModel;
import interface_adapter.add_recipe.SwitchViewController;
import interface_adapter.add_recipe.ViewCreatorPresenter;
import interface_adapter.view_diet_res.DietResViewModel;
import interface_adapter.view_diet_res.DietResWindowModel;
import interface_adapter.view_diet_res.ViewRestrictionsController;
import interface_adapter.view_diet_res.ViewRestrictionsPresenter;
import interface_adapter.view_recipes.UserRecipesViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import interface_adapter.view_recipes.ViewRecipesPresenter;
import interface_adapter.view_recipes.UserRecipeWindowModel;
import use_case.view_recipe_creator.ViewCreatorInteractor;
import use_case.view_recipes.ViewRecipesInteractor;
import use_case.view_restrictions.ViewRestrictionsInteractor;
import use_case.view_restrictions.ViewRestrictionsOutputBoundary;
import view.*;
import view.user_recipe_view.AddRecipeView;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;
import view.user_recipe_view.UserRecipesView;
import view.user_recipe_view.UserRecipesViewManager;
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
    private UserRecipeWindowModel userRecipeWindowModel;


    private UserRecipesView userRecipesView;
    private UserRecipesViewModel userRecipesViewModel;

    private AddRecipeView addRecipeView;
    private AddRecipeViewModel addRecipeViewModel;


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

	public AppBuilder addAddRecipeView() {
		this.addRecipeViewModel = new AddRecipeViewModel();

		this.addRecipeView = new AddRecipeView(addRecipeViewModel);

		this.userRecipeCardPanel.add(this.addRecipeView, AddRecipeViewModel.VIEW_NAME);

		return this;
	}

	public AppBuilder addViewCreatorUseCase() {
		ViewCreatorPresenter viewCreatorPresenter = new ViewCreatorPresenter(this.userRecipesViewManagerModel,
				this.addRecipeViewModel);

		ViewCreatorInteractor viewCreatorInteractor = new ViewCreatorInteractor(viewCreatorPresenter);
		SwitchViewController switchViewController = new SwitchViewController(viewCreatorInteractor);

		userRecipesView.addViewCreatorUseCase(switchViewController);

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
