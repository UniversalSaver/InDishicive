package app;

import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import adapters.DietResViewManagerModel;
import adapters.dietary_restriction.add_diet_res.AddDietResController;
import adapters.dietary_restriction.add_diet_res.AddDietResPresenter;
import adapters.dietary_restriction.add_diet_res.AddDietResViewModel;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResController;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResPresenter;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResViewModel;
import adapters.dietary_restriction.view_diet_res.DietResViewModel;
import adapters.dietary_restriction.view_diet_res.DietResWindowModel;
import adapters.dietary_restriction.view_diet_res.ViewRestrictionsController;
import adapters.dietary_restriction.view_diet_res.ViewRestrictionsPresenter;
import adapters.favorites.add_favorite.AddFavoriteController;
import adapters.favorites.add_favorite.AddFavoritePresenter;
import adapters.favorites.add_favorite.AddFavoriteViewModel;
import adapters.favorites.remove_favorites.RemoveFavoriteController;
import adapters.favorites.remove_favorites.RemoveFavoritePresenter;
import adapters.favorites.remove_favorites.RemoveFavoriteViewModel;
import adapters.favorites.view_favorite.ViewFavoriteController;
import adapters.favorites.view_favorite.ViewFavoritePresenter;
import adapters.favorites.view_favorite.ViewFavoriteViewModel;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsController;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsPresenter;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsViewModel;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryPresenter;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsPresenter;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;
import adapters.user_recipe.add_recipe.*;
import adapters.user_recipe.add_recipe.add_ingredient.*;
import adapters.user_recipe.delete_recipe.DeleteUserRecipeController;
import adapters.inventory.add_ingredient.AddIngredientController;
import adapters.inventory.add_ingredient.AddIngredientPresenter;
import adapters.inventory.add_ingredient.AddIngredientViewModel;
import adapters.inventory.remove_ingredient.RemoveIngredientController;
import adapters.inventory.remove_ingredient.RemoveIngredientPresenter;
import adapters.inventory.remove_ingredient.RemoveIngredientViewModel;
import adapters.inventory.search_ingredients.SearchIngredientsController;
import adapters.inventory.search_ingredients.SearchIngredientsPresenter;
import adapters.inventory.search_ingredients.SearchIngredientsViewModel;
import adapters.user_recipe.add_recipe.AddRecipeController;
import adapters.user_recipe.add_recipe.AddRecipePresenter;
import adapters.user_recipe.add_recipe.AddRecipeViewModel;
import adapters.user_recipe.add_recipe.SwitchViewController;
import adapters.user_recipe.add_recipe.ViewCreatorPresenter;
import adapters.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientController;
import adapters.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientPresenter;
import adapters.user_recipe.view_recipes.UserRecipeWindowModel;
import adapters.user_recipe.view_recipes.UserRecipesViewModel;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsController;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsPresenter;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesController;
import adapters.user_recipe.view_recipes.ViewRecipesPresenter;
import adapters.user_recipe.view_recipes.view_detailed_recipe.UserRecipeDetailsViewModel;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsController;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsPresenter;
import databases.dietary_restriction.DietResDataAccessObject;
import databases.dietary_restriction.MealDbIngredientGateway;
import databases.generate_recipe.InventoryReaderFromInventory;
import logic.dietary_restriction.DietaryRestrictionChecker;
import logic.dietary_restriction.DietaryRestrictionCheckerInterface;
import logic.generate_recipe.generate_with_inventory.InventoryReader;
import databases.favorites.FavoriteDataAccessObject;
import databases.generate_recipe.InventoryReaderFromInventory;
import databases.generate_recipe.MealDbRecipeByIngredientsGateway;
import databases.generate_recipe.MealDbRecipeDetailsGateway;
import databases.generate_recipe.MealDbRecipeGateway;
import databases.inventory.InventoryDataAccessObject;
import databases.inventory.MealDBIngredientDataAccess;
import databases.test_DAO.FromMemoryMealRecipeDataAccessObject;
import databases.user_recipe.FileDataAccessObject;
import entity.Ingredient;
import entity.Inventory;
import logic.dietary_restriction.DietaryRestrictionChecker;
import logic.dietary_restriction.DietaryRestrictionCheckerInterface;
import logic.dietary_restriction.add_restrictions.AddDietResInteractor;
import logic.dietary_restriction.remove_restriction.RemoveDietResInteractor;
import logic.dietary_restriction.view_restrictions.ViewRestrictionsInteractor;
import logic.favorites.add_favorite.AddFavoriteInteractor;
import logic.favorites.remove_favorite.RemoveFavoriteInteractor;
import logic.favorites.view_favorite.ViewFavoriteInteractor;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInputBoundary;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInteractor;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsOutputBoundary;
import logic.generate_recipe.generate_by_ingredients.RecipeByIngredientsGateway;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInputBoundary;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInteractor;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import logic.generate_recipe.generate_with_inventory.InventoryReader;
import logic.generate_recipe.generate_with_inventory.RecipeGateway;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInteractor;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import logic.user_recipe.add_recipe.AddRecipeInteractor;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientInteractor;
import logic.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInteractor;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInteractor;
import logic.user_recipe.view_recipes.ViewRecipesInteractor;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsInteractor;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsOutputBoundary;
import view.inventory.GenerateByInventoryPanel;
import view.generate_recipe_view.GenerateByIngredientsPanel;
import view.MainView;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;
import view.favorite_view.FavoriteView;
import view.generate_recipe_view.GenerateByIngredientsPanel;
import view.inventory.GenerateByInventoryPanel;
import view.fav_view.FavoriteView;
import view.inventory.InventoryView;
import view.user_recipe_view.AddRecipeView;
import view.user_recipe_view.UserRecipesView;
import view.user_recipe_view.UserRecipesViewManager;
import window.DietResWindow;
import window.FavoriteWindow;
import window.MainWindow;
import window.RecipeDetailsWindow;
import window.UserRecipeDetailsWindow;
import window.UserRecipesWindow;

/**
 * An object that will build the app given what windows to include.
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

    private UserRecipeDetailsWindow userRecipeDetailsWindow;
    private UserRecipeDetailsViewModel userRecipeDetailsViewModel;

    private UserRecipesView userRecipesView;
    private UserRecipesViewModel userRecipesViewModel;

    private AddRecipeView addRecipeView;
    private AddRecipeViewModel addRecipeViewModel;

    private ViewRecipesInteractor viewRecipesInteractor;
    private ViewUserRecipeDetailsOutputBoundary viewUserRecipeDetailsPresenter;


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
    Start of Favorites variables
    */

    private final FavoriteDataAccessObject favoriteDataAccess = new FavoriteDataAccessObject();

    private final AddFavoriteViewModel addFavoriteViewModel = new AddFavoriteViewModel();
    private final ViewFavoriteViewModel viewFavoriteViewModel = new ViewFavoriteViewModel();

    private AddFavoriteController addFavoriteController;
    private ViewFavoriteController viewFavoriteController;

    private FavoriteView favoriteView;
    private FavoriteWindow favoriteWindow;

    private final RemoveFavoriteViewModel removeFavoriteViewModel = new RemoveFavoriteViewModel();
    private RemoveFavoriteController removeFavoriteController;

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

    /*
    Start of Generate By Ingredients Variables (UC2)
     */

    private GenerateByIngredientsViewModel generateByIngredientsViewModel;

    /*
    End of Generate By Ingredients Variables
     */

    /**
     * Adds the main view to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addMainView() {
        mainView = new MainView();
        this.mainWindow.addMainView(mainView);

        return this;
    }

    /**
     * Adds the main window to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addMainWindow() {
        mainWindow = new MainWindow("Indishisive");

        return this;
    }

    /**
     * Adds the profile menu to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addProfileMenu() {
        mainWindow.addProfileMenu();
        return this;
    }

    /**
     * Adds the Indishisive DAO to the application builder.
     *
     * @param memoryDataAccessObject the file data access object
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addIndishisiveDAO(FileDataAccessObject memoryDataAccessObject) {
        this.fileDataAccessObject = memoryDataAccessObject;
        return this;
    }

    /*
    Start of UserRecipe Methods
     */

    /**
     * Adds the view user recipe details use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewUserRecipeDetailsUseCase() {
        userRecipeDetailsViewModel = new UserRecipeDetailsViewModel();

        userRecipeDetailsWindow = new UserRecipeDetailsWindow(userRecipeDetailsViewModel);

        final ViewUserRecipeDetailsPresenter viewUserRecipeDetailsPresenter =
        viewUserRecipeDetailsPresenter =
                new ViewUserRecipeDetailsPresenter(userRecipesViewModel, userRecipeDetailsViewModel);

        final ViewUserRecipeDetailsInteractor viewUserRecipeDetailsInteractor =
                new ViewUserRecipeDetailsInteractor(fileDataAccessObject, viewUserRecipeDetailsPresenter,
                        viewRecipesInteractor);

        final ViewUserRecipeDetailsController viewUserRecipeDetailsController =
                new ViewUserRecipeDetailsController(viewUserRecipeDetailsInteractor);

        userRecipesView.addViewRecipeDetailsUseCase(viewUserRecipeDetailsController);

        return this;
    }

    /**
     * Adds the user recipes window to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addUserRecipesWindow() {
        this.userRecipeCardPanel.setLayout(userRecipeCardLayout);

        this.userRecipesViewManager = new UserRecipesViewManager(this.userRecipeCardLayout,
                this.userRecipeCardPanel, this.userRecipesViewManagerModel);

        this.userRecipeWindowModel = new UserRecipeWindowModel();

        userRecipesWindow = new UserRecipesWindow(userRecipeCardPanel
        );

        return this;
    }

    /**
     * Adds the user recipes view to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addUserRecipesView() {
        this.userRecipesViewModel = new UserRecipesViewModel();

        userRecipesView = new UserRecipesView(userRecipesViewModel);

        this.userRecipeCardPanel.add(this.userRecipesView, userRecipesView.getViewName());
        this.userRecipeWindowModel.addPropertyChangeListener(this.userRecipesWindow);

        return this;
    }

    public AppBuilder addDeleteUserRecipeUseCase() {
        DeleteUserRecipeInteractor deleteUserRecipeInteractor = new DeleteUserRecipeInteractor(
                viewRecipesInteractor, fileDataAccessObject, viewUserRecipeDetailsPresenter
        );

        DeleteUserRecipeController deleteUserRecipeController =
                new DeleteUserRecipeController(deleteUserRecipeInteractor);

        userRecipesView.addDeleteRecipeUseCase(deleteUserRecipeController);

        return this;
    }

    /**
     * Adds the recipe ingredient use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addRecipeIngredientUseCase() {
        final AddRecipeIngredientPresenter addIngredientPresenter =
                new AddRecipeIngredientPresenter(this.addRecipeViewModel);
        final AddRecipeIngredientInteractor addIngredientInteractor =
                new AddRecipeIngredientInteractor(new FromMemoryMealRecipeDataAccessObject(),
                        addIngredientPresenter);
        final AddRecipeIngredientController addIngredientController =
                new AddRecipeIngredientController(addIngredientInteractor);

        this.addRecipeView.addIngredientUseCase(addIngredientController);

        return this;
    }

    /**
     * Adds the add recipe use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addAddRecipeUseCase() {
        final ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        final ViewRecipesInteractor viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        final ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);
		viewRecipesInteractor =
				new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
		ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        final AddRecipePresenter addRecipePresenter =
                new AddRecipePresenter(viewRecipesController, addRecipeViewModel);
        final AddRecipeInteractor addRecipeInteractor =
                new AddRecipeInteractor(fileDataAccessObject, addRecipePresenter);
        final AddRecipeController addRecipeController = new AddRecipeController(addRecipeInteractor);

        this.addRecipeView.addAddRecipeUseCase(addRecipeController);

        return this;
    }

    /**
     * Adds the user recipes cancel button use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addUserRecipesCancelButtonUseCase() {
        final ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        final ViewRecipesInteractor viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        final ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);
        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        addRecipeView.addCancelButtonUseCase(viewRecipesController);

        return this;
    }

    /**
     * Adds the view recipes use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewRecipesUseCase() {
        final ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);
        viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        final ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        mainWindow.addViewRecipesUseCase(viewRecipesController);

        return this;
    }

    /**
     * Adds the add recipe view to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addAddRecipeView() {
        this.addRecipeViewModel = new AddRecipeViewModel();

        this.addRecipeView = new AddRecipeView(addRecipeViewModel);

        this.userRecipeCardPanel.add(this.addRecipeView, AddRecipeViewModel.VIEW_NAME);

        return this;
    }

    /**
     * Adds the view creator use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewCreatorUseCase() {
        final ViewCreatorPresenter viewCreatorPresenter = new ViewCreatorPresenter(
                this.userRecipesViewManagerModel, this.addRecipeViewModel);

        final ViewCreatorInteractor viewCreatorInteractor = new ViewCreatorInteractor(viewCreatorPresenter);
        final SwitchViewController switchViewController = new SwitchViewController(viewCreatorInteractor);

        userRecipesView.addViewCreatorUseCase(switchViewController);

        return this;
    }

    /*
    End of UserRecipe methods
     */

    /*
    Start of Inventory Methods
     */

    /**
     * Adds the inventory view to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addInventoryView() {
        final MealDBIngredientDataAccess dataAccess = new MealDBIngredientDataAccess();
        final InventoryDataAccessObject inventoryDataObject = new InventoryDataAccessObject(inventory);

        final SearchIngredientsPresenter searchPresenter =
                new SearchIngredientsPresenter(searchIngredientsViewModel);
        final SearchIngredientsInteractor searchInteractor =
                new SearchIngredientsInteractor(searchPresenter, dataAccess);
        final SearchIngredientsController searchController =
                new SearchIngredientsController(searchInteractor);

        final AddIngredientPresenter addPresenter = new AddIngredientPresenter(addIngredientViewModel);
        final AddIngredientInteractor addInteractor =
                new AddIngredientInteractor(addPresenter, inventoryDataObject);
        final AddIngredientController addController = new AddIngredientController(addInteractor);

        final RemoveIngredientPresenter removePresenter =
                new RemoveIngredientPresenter(removeIngredientViewModel);
        final RemoveIngredientInteractor removeInteractor =
                new RemoveIngredientInteractor(removePresenter, inventoryDataObject);
        final RemoveIngredientController removeController = new RemoveIngredientController(removeInteractor);

        inventoryView = new InventoryView(searchController, addController, removeController,
                searchIngredientsViewModel, inventory);

        mainView.addInventoryTab(inventoryView);

        return this;
    }

    /*
    End of Inventory Methods
    */

    /*
    Start of Favorites Methods
     */

    /**
     * Adds the add favorite use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addAddFavoriteUseCase() {
        final AddFavoritePresenter addFavoritePresenter = new AddFavoritePresenter(this.addFavoriteViewModel);

        final MealDbRecipeDetailsGateway recipeDetailsGateway = new MealDbRecipeDetailsGateway();

        final AddFavoriteInteractor addFavoriteInteractor = new AddFavoriteInteractor(
                this.favoriteDataAccess, this.favoriteDataAccess, addFavoritePresenter, recipeDetailsGateway);

        this.addFavoriteController = new AddFavoriteController(addFavoriteInteractor);

        return this;
    }

    /**
     * Adds the view favorites use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewFavoritesUseCase() {
        final ViewFavoritePresenter viewFavoritePresenter = new ViewFavoritePresenter(
                this.viewFavoriteViewModel);

        final ViewFavoriteInteractor viewFavoriteInteractor = new ViewFavoriteInteractor(
                this.favoriteDataAccess, viewFavoritePresenter);

        this.viewFavoriteController = new ViewFavoriteController(viewFavoriteInteractor);

        return this;
    }

    /**
     * Adds the remove favorite use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addRemoveFavoriteUseCase() {
        final RemoveFavoritePresenter removeFavoritePresenter = new RemoveFavoritePresenter(
                this.removeFavoriteViewModel, this.viewFavoriteViewModel);

        final RemoveFavoriteInteractor removeFavoriteInteractor = new RemoveFavoriteInteractor(
                this.favoriteDataAccess, this.favoriteDataAccess, removeFavoritePresenter);

        this.removeFavoriteController = new RemoveFavoriteController(removeFavoriteInteractor);

        return this;
    }

    /**
     * Adds the favorites view to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addFavoritesView() {
        this.favoriteView = new FavoriteView(this.viewFavoriteViewModel, this.viewRecipeDetailsController,
                this.removeFavoriteController);
        this.favoriteWindow = new FavoriteWindow(this.favoriteView, this.viewFavoriteViewModel);
        this.viewFavoriteViewModel.addPropertyChangeListener(this.favoriteWindow);

        return this;
    }

    /**
     * Adds the view favorites button to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewFavoritesButton() {
        mainWindow.addViewFavoriteButton(this.viewFavoriteController);
        return this;
    }

    /**
     * Gets the add favorite controller.
     *
     * @return the add favorite controller
     */
    public AddFavoriteController getAddFavoriteController() {
        return addFavoriteController;
    }

    /**
     * Gets the view favorites controller.
     *
     * @return the view favorites controller
     */
    public ViewFavoriteController getViewFavoritesController() {
        return viewFavoriteController;
    }

    /*
    End of Favorites Methods
     */

    /*
    Start of DietRes Methods
     */

    /**
     * Adds the add dietary restrictions use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addAddDietResUseCase() {
        final AddDietResPresenter addDietResPresenter = new AddDietResPresenter(this.addDietResViewModel);

        final MealDbIngredientGateway ingredientGateway = new MealDbIngredientGateway();

        final AddDietResInteractor addDietResInteractor = new AddDietResInteractor(
                this.restrictionDataAccess,
                addDietResPresenter,
                ingredientGateway
        );

        this.addDietResController = new AddDietResController(addDietResInteractor);

        return this;
    }

    /**
     * Adds the remove dietary restrictions use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addRemoveDietResUseCase() {
        final RemoveDietResPresenter removeDietResPresenter =
                new RemoveDietResPresenter(this.removeDietResViewModel);

        final RemoveDietResInteractor removeDietResInteractor = new RemoveDietResInteractor(
                this.restrictionDataAccess, removeDietResPresenter);

        this.removeDietResController = new RemoveDietResController(removeDietResInteractor);

        return this;
    }

    /**
     * Adds the dietary restrictions window to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addDietResWindow() {
        this.dietResCardPanel.setLayout(dietResCardLayout);

        this.dietResViewManager = new DietResViewManager(this.dietResCardLayout,
                this.dietResCardPanel, this.dietResViewManagerModel);

        this.dietResWindowModel = new DietResWindowModel();

        dietResWindow = new DietResWindow(dietResCardPanel, dietResWindowModel);

        return this;
    }

    /**
     * Adds the dietary restrictions view to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
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

    /**
     * Adds the view restrictions use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewRestrictionsUseCase() {
        final ViewRestrictionsPresenter viewRestrictionsPresenter = new ViewRestrictionsPresenter(
                this.dietResWindowModel, this.dietResViewManagerModel, this.dietResViewModel);

        final ViewRestrictionsInteractor viewRestrictionsInteractor =
                new ViewRestrictionsInteractor(this.restrictionDataAccess, viewRestrictionsPresenter);
        this.viewRestrictionsController = new ViewRestrictionsController(viewRestrictionsInteractor);

        mainWindow.addViewRestrictionsUseCase(viewRestrictionsController);

        return this;
    }
    /*
    End of DietRes methods
     */

    /**
     * Builds the application and returns the main window frame.
     *
     * @return the main window JFrame
     */
    public JFrame build() {
        mainWindow.add(mainView);

        return mainWindow;
    }

    /**
     * Adds the generate with inventory use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addGenerateWithInventoryUseCase() {
        final MealDbRecipeGateway recipeGateway = new MealDbRecipeGateway();
        recipeGateway.preloadAllRecipes();

        final InventoryReaderFromInventory inventoryReader =
                new InventoryReaderFromInventory(this.inventory);

        final GenerateWithInventoryViewModel generateWithInventoryViewModel =
                new GenerateWithInventoryViewModel();

        final GenerateByInventoryPanel panel = getGenerateByInventoryPanel(
                generateWithInventoryViewModel,
                inventoryReader,
                recipeGateway
        );

        this.mainView.addGenerateByInventoryPanel(panel);

        return this;
    }

    private GenerateByInventoryPanel getGenerateByInventoryPanel(
            GenerateWithInventoryViewModel generateWithInventoryViewModel,
            InventoryReader inventoryReader,
            RecipeGateway recipeGateway) {

        final GenerateWithInventoryOutputBoundary presenter =
                new GenerateWithInventoryPresenter(generateWithInventoryViewModel);

        final DietaryRestrictionCheckerInterface dietResChecker = new DietaryRestrictionChecker();

        final GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(inventoryReader,
                                                    recipeGateway,
                                                    presenter,
                                                    this.restrictionDataAccess,
                                                    dietResChecker);

        final GenerateWithInventoryController generateWithInventoryController =
                new GenerateWithInventoryController(interactor);

        return new GenerateByInventoryPanel(
                generateWithInventoryController,
                generateWithInventoryViewModel,
                viewRecipeDetailsController,
                this.addFavoriteController
        );
    }

    /**
     * Adds the view recipe details use case to the application builder.
     *
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addViewRecipeDetailsUseCase() {
        viewRecipeDetailsViewModel = new ViewRecipeDetailsViewModel();
        recipeDetailsWindow = new RecipeDetailsWindow(viewRecipeDetailsViewModel);

        final ViewRecipeDetailsOutputBoundary outputBoundary =
                new ViewRecipeDetailsPresenter(viewRecipeDetailsViewModel);

        final ViewRecipeDetailsInputBoundary interactor =
                new ViewRecipeDetailsInteractor(
                        new MealDbRecipeDetailsGateway(),
                        outputBoundary
                );

        viewRecipeDetailsController = new ViewRecipeDetailsController(interactor);

        return this;
    }

    /**
     * Connect the "generate recipes by ingredients" use case
     * into the application, and attaches its panel to the main view.
     *
     * @return this builder for chaining
     */
    public AppBuilder addGenerateByIngredientsUseCase() {
        this.generateByIngredientsViewModel = new GenerateByIngredientsViewModel();

        final GenerateByIngredientsOutputBoundary presenter =
                new GenerateByIngredientsPresenter(this.generateByIngredientsViewModel);

        final RecipeByIngredientsGateway gateway = new MealDbRecipeByIngredientsGateway();

        final GenerateByIngredientsInputBoundary interactor =
                new GenerateByIngredientsInteractor(gateway, presenter);

        final GenerateByIngredientsController controller =
                new GenerateByIngredientsController(interactor);

        final GenerateByIngredientsPanel panel =
                new GenerateByIngredientsPanel(
                        controller,
                        this.generateByIngredientsViewModel,
                        this.viewRecipeDetailsController,
                        this.addFavoriteController,
                        this.addFavoriteViewModel
                );

        this.mainView.addGenerateByIngredientsPanel(panel);

        return this;
    }
}
