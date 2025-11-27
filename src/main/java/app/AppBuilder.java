package app;

import java.awt.CardLayout;

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
import javax.swing.JFrame;
import javax.swing.JPanel;

import data_access.dietary_restriction.DietResDataAccessObject;
import data_access.favorites.FavoriteDataAccessObject;
import data_access.generate_recipe.MealDbRecipeDetailsGateway;
import data_access.generate_recipe.MealDbRecipeGateway;
import data_access.test_DAO.FromMemoryMealRecipeDataAccessObject;
import data_access.test_DAO.InMemoryInventoryReader;
import data_access.user_recipe.FileDataAccessObject;
import interface_adapter.DietResViewManagerModel;
import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.dietary_restriction.add_diet_res.AddDietResController;
import interface_adapter.dietary_restriction.add_diet_res.AddDietResPresenter;
import interface_adapter.dietary_restriction.add_diet_res.AddDietResViewModel;
import interface_adapter.favorites.add_favorite.AddFavoriteController;
import interface_adapter.favorites.add_favorite.AddFavoritePresenter;
import interface_adapter.favorites.add_favorite.AddFavoriteViewModel;
import interface_adapter.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import interface_adapter.generate_recipe.generate_with_inventory.GenerateWithInventoryPresenter;
import interface_adapter.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import interface_adapter.dietary_restriction.remove_diet_res.RemoveDietResController;
import interface_adapter.dietary_restriction.remove_diet_res.RemoveDietResPresenter;
import interface_adapter.dietary_restriction.remove_diet_res.RemoveDietResViewModel;
import interface_adapter.dietary_restriction.view_diet_res.DietResViewModel;
import interface_adapter.dietary_restriction.view_diet_res.DietResWindowModel;
import interface_adapter.dietary_restriction.view_diet_res.ViewRestrictionsController;
import interface_adapter.dietary_restriction.view_diet_res.ViewRestrictionsPresenter;
import interface_adapter.favorites.view_favorite.ViewFavoriteController;
import interface_adapter.favorites.view_favorite.ViewFavoritePresenter;
import interface_adapter.favorites.view_favorite.ViewFavoriteViewModel;
import interface_adapter.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import interface_adapter.generate_recipe.view_recipe_details.ViewRecipeDetailsPresenter;
import interface_adapter.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;
import interface_adapter.user_recipe.add_recipe.*;
import interface_adapter.user_recipe.add_recipe.add_ingredient.AddIngredientController;
import interface_adapter.user_recipe.add_recipe.add_ingredient.AddIngredientPresenter;
import interface_adapter.user_recipe.view_recipes.UserRecipeWindowModel;
import interface_adapter.user_recipe.view_recipes.UserRecipesViewModel;
import interface_adapter.user_recipe.view_recipes.ViewRecipesController;
import interface_adapter.user_recipe.view_recipes.ViewRecipesPresenter;
import use_case.user_recipe.add_recipe.add_ingredient.AddIngredientInteractor;
import use_case.dietary_restriction.add_restrictions.AddDietResInteractor;
import use_case.dietary_restriction.remove_restriction.RemoveDietResInteractor;
import use_case.user_recipe.add_recipe.AddRecipeInteractor;
import use_case.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInteractor;
import use_case.dietary_restriction.view_restrictions.ViewRestrictionsInteractor;
import view.user_recipe_view.AddRecipeView;
import use_case.favorites.add_favorite.AddFavoriteInteractor;
import use_case.generate_recipe.generate_with_inventory.GenerateWithInventoryInputBoundary;
import use_case.generate_recipe.generate_with_inventory.GenerateWithInventoryInteractor;
import use_case.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import use_case.generate_recipe.generate_with_inventory.RecipeGateway;
import use_case.favorites.view_favorite.ViewFavoriteInteractor;
import use_case.generate_recipe.view_recipe_details.ViewRecipeDetailsInputBoundary;
import use_case.generate_recipe.view_recipe_details.ViewRecipeDetailsInteractor;
import use_case.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import use_case.user_recipe.view_recipes.ViewRecipesInteractor;
import view.GenerateByInventoryPanel;
import view.MainView;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;
import view.fav_view.FavoriteView;
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
import java.util.ArrayList;
import window.DietResWindow;
import window.FavoriteWindow;
import window.MainWindow;
import window.RecipeDetailsWindow;
import window.UserRecipesWindow;

/**
 * An object that will build the app given what windows to include
 */
public class AppBuilder {

    private MainWindow mainWindow;

    private MainView mainView;

    private FileDataAccessObject fileDataAccessObject;

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
     
    /*
    Start of DietRes variables
    */
  
    /*
    Start of Favorites variables
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
    /*
    Start of DietRes variables
    */
    private DietResWindow dietResWindow;
    private DietResWindowModel dietResWindowModel;

    private DietResView dietResView;
    private final DietResViewModel dietResViewModel = new DietResViewModel();
    private final AddDietResViewModel addDietResViewModel = new AddDietResViewModel();
    private final RemoveDietResViewModel removeDietResViewModel = new RemoveDietResViewModel();

    private AddDietResController addDietResController;
    private ViewRestrictionsController viewRestrictionsController;
    private RemoveDietResController removeDietResController;

    private final DietResDataAccessObject restrictionDataAccess = new DietResDataAccessObject();

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

    public AppBuilder addIndishisiveDAO(FileDataAccessObject memoryDataAccessObject) {
        this.fileDataAccessObject = memoryDataAccessObject;
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

	public AppBuilder addIngredientUseCase() {
		AddIngredientPresenter addIngredientPresenter = new AddIngredientPresenter(this.addRecipeViewModel);
		AddIngredientInteractor addIngredientInteractor =
				new AddIngredientInteractor(new FromMemoryMealRecipeDataAccessObject(), addIngredientPresenter);
		AddIngredientController addIngredientController = new AddIngredientController(addIngredientInteractor);

		this.addRecipeView.addIngredientUseCase(addIngredientController);

		return this;
	}

	public AppBuilder addAddRecipeUseCase() {
		ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
				this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

		ViewRecipesInteractor viewRecipesInteractor =
				new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
		ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

		AddRecipePresenter addRecipePresenter = new AddRecipePresenter(viewRecipesController, addRecipeViewModel);
		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(fileDataAccessObject, addRecipePresenter);
		AddRecipeController addRecipeController = new AddRecipeController(addRecipeInteractor);

		this.addRecipeView.addAddRecipeUseCase(addRecipeController);

		return this;
	}

    public AppBuilder addUserRecipesCancelButtonUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        ViewRecipesInteractor viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        addRecipeView.addCancelButtonUseCase(viewRecipesController);

        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        ViewRecipesInteractor viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
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
    
    /**
    Start of Favorites Methods
     */


    public AppBuilder addAddFavoriteUseCase() {
        AddFavoritePresenter addFavoritePresenter = new AddFavoritePresenter(this.addFavoriteViewModel);

        MealDbRecipeDetailsGateway recipeDetailsGateway = new MealDbRecipeDetailsGateway();

        AddFavoriteInteractor addFavoriteInteractor = new AddFavoriteInteractor(
                this.favoriteDataAccess, addFavoritePresenter, recipeDetailsGateway);

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
        this.favoriteView = new FavoriteView(this.viewFavoriteViewModel, this.viewRecipeDetailsController);
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

     /*
    Start of DietRes Methods
     */
    //Adds "Add Dietary Restrictions" Use Case
    public AppBuilder addAddDietResUseCase() {
        AddDietResPresenter addDietResPresenter = new AddDietResPresenter(this.addDietResViewModel);

        AddDietResInteractor addDietResInteractor = new AddDietResInteractor(
                this.restrictionDataAccess, addDietResPresenter);

        this.addDietResController = new AddDietResController(addDietResInteractor);

        return this;
    }

    //Adds "Remove Dietary Restrictions" Use Case
    public AppBuilder addRemoveDietResUseCase() {
        RemoveDietResPresenter removeDietResPresenter = new RemoveDietResPresenter(this.removeDietResViewModel);

        RemoveDietResInteractor removeDietResInteractor = new RemoveDietResInteractor(
                this.restrictionDataAccess, removeDietResPresenter);

        this.removeDietResController = new RemoveDietResController(removeDietResInteractor);

        return this;
    }

    public AppBuilder addDietResWindow() {
        this.dietResCardPanel.setLayout(dietResCardLayout);

        this.dietResViewManager = new DietResViewManager(this.dietResCardLayout,
                this.dietResCardPanel, this.dietResViewManagerModel);

        this.dietResWindowModel = new DietResWindowModel();

        dietResWindow = new DietResWindow(dietResCardPanel, dietResWindowModel);

        return this;
    }

    public AppBuilder addDietResView() {
        dietResView = new DietResView(
                dietResViewModel,
                addDietResViewModel,
                removeDietResViewModel,
                addDietResController,
                viewRestrictionsController,
                removeDietResController
        );

        this.dietResCardPanel.add(this.dietResView, dietResView.getViewName());
        this.dietResWindowModel.addPropertyChangeListener(this.dietResWindow);

        return this;
    }

    public AppBuilder addViewRestrictionsUseCase() {
        ViewRestrictionsPresenter viewRestrictionsPresenter = new ViewRestrictionsPresenter(
                this.dietResWindowModel, this.dietResViewManagerModel, this.dietResViewModel);

        ViewRestrictionsInteractor viewRestrictionsInteractor = new ViewRestrictionsInteractor(this.restrictionDataAccess, viewRestrictionsPresenter);
        this.viewRestrictionsController = new ViewRestrictionsController(viewRestrictionsInteractor);

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
                viewRecipeDetailsController,
                this.addFavoriteController,
                this.addFavoriteViewModel
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