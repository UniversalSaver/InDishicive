package app;

import java.awt.CardLayout;
import java.util.ArrayList;

import adapters.user_recipe.view_recipes.view_detailed_recipe.UserRecipeDetailsViewModel;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsController;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsPresenter;
import databases.inventory.MealDBIngredientDataAccess;
import databases.inventory.InventoryDataAccessObject;
import entity.Ingredient;
import entity.Inventory;
import adapters.UserRecipesViewManagerModel;
import adapters.inventory.add_ingredient.AddIngredientController;
import adapters.inventory.add_ingredient.AddIngredientPresenter;
import adapters.inventory.add_ingredient.AddIngredientViewModel;
import adapters.inventory.remove_ingredient.RemoveIngredientController;
import adapters.inventory.remove_ingredient.RemoveIngredientPresenter;
import adapters.inventory.remove_ingredient.RemoveIngredientViewModel;
import adapters.inventory.search_ingredients.SearchIngredientsController;
import adapters.inventory.search_ingredients.SearchIngredientsPresenter;
import adapters.inventory.search_ingredients.SearchIngredientsViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesController;
import adapters.user_recipe.view_recipes.ViewRecipesPresenter;
import logic.inventory.add_ingredient.AddIngredientInteractor;
import logic.inventory.remove_ingredient.RemoveIngredientInteractor;
import logic.inventory.search_ingredients.SearchIngredientsInteractor;
import javax.swing.JFrame;
import javax.swing.JPanel;

import databases.dietary_restriction.DietResDataAccessObject;
import databases.favorites.FavoriteDataAccessObject;
import databases.generate_recipe.MealDbRecipeDetailsGateway;
import databases.generate_recipe.MealDbRecipeGateway;
import databases.test_DAO.FromMemoryMealRecipeDataAccessObject;
import databases.user_recipe.FileDataAccessObject;
import databases.generate_recipe.MealDbRecipeByIngredientsGateway;
import adapters.DietResViewManagerModel;
import adapters.UserRecipesViewManagerModel;
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
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryPresenter;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsPresenter;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;
import adapters.inventory.add_ingredient.AddIngredientController;
import adapters.inventory.add_ingredient.AddIngredientPresenter;
import adapters.inventory.add_ingredient.AddIngredientViewModel;
import adapters.inventory.remove_ingredient.RemoveIngredientController;
import adapters.inventory.remove_ingredient.RemoveIngredientPresenter;
import adapters.inventory.remove_ingredient.RemoveIngredientViewModel;
import adapters.inventory.search_ingredients.SearchIngredientsController;
import adapters.inventory.search_ingredients.SearchIngredientsPresenter;
import adapters.inventory.search_ingredients.SearchIngredientsViewModel;
import adapters.user_recipe.add_recipe.*;
import adapters.user_recipe.add_recipe.add_ingredient.*;
import adapters.user_recipe.view_recipes.UserRecipeWindowModel;
import adapters.user_recipe.view_recipes.UserRecipesViewModel;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsController;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsPresenter;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesController;
import adapters.user_recipe.view_recipes.ViewRecipesPresenter;
import databases.dietary_restriction.DietResDataAccessObject;
import databases.dietary_restriction.MealDbIngredientGateway;
import databases.favorites.FavoriteDataAccessObject;
import databases.generate_recipe.MealDbRecipeDetailsGateway;
import databases.generate_recipe.MealDbRecipeGateway;
import databases.inventory.InventoryDataAccessObject;
import databases.inventory.MealDBIngredientDataAccess;
import databases.test_DAO.FromMemoryMealRecipeDataAccessObject;
import databases.generate_recipe.InventoryReaderFromInventory;
import logic.dietary_restriction.DietaryRestrictionChecker;
import logic.dietary_restriction.DietaryRestrictionCheckerInterface;
import logic.generate_recipe.generate_with_inventory.InventoryReader;
import databases.user_recipe.FileDataAccessObject;
import entity.Ingredient;
import entity.Inventory;
import logic.dietary_restriction.add_restrictions.AddDietResInteractor;
import logic.dietary_restriction.remove_restriction.RemoveDietResInteractor;
import logic.dietary_restriction.view_restrictions.ViewRestrictionsInteractor;
import logic.favorites.add_favorite.AddFavoriteInteractor;
import logic.favorites.remove_favorite.RemoveFavoriteInteractor;
import logic.favorites.view_favorite.ViewFavoriteInteractor;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInputBoundary;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryInteractor;
import logic.generate_recipe.generate_with_inventory.GenerateWithInventoryOutputBoundary;
import logic.generate_recipe.generate_with_inventory.RecipeGateway;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInteractor;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsOutputBoundary;
import logic.inventory.add_ingredient.AddIngredientInteractor;
import logic.inventory.remove_ingredient.RemoveIngredientInteractor;
import logic.inventory.search_ingredients.SearchIngredientsInteractor;
import logic.user_recipe.add_recipe.AddRecipeInteractor;
import logic.user_recipe.add_recipe.add_ingredient.*;
import logic.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInteractor;
import logic.user_recipe.view_recipes.ViewRecipesInteractor;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInputBoundary;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsInteractor;
import logic.generate_recipe.generate_by_ingredients.GenerateByIngredientsOutputBoundary;
import logic.generate_recipe.generate_by_ingredients.RecipeByIngredientsGateway;
import view.inventory.GenerateByInventoryPanel;
import view.generate_recipe_view.GenerateByIngredientsPanel;
import view.MainView;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;
import view.fav_view.FavoriteView;
import view.inventory.GenerateByInventoryPanel;
import view.inventory.InventoryView;
import view.user_recipe_view.AddRecipeView;
import view.user_recipe_view.UserRecipesView;
import view.user_recipe_view.UserRecipesViewManager;
import window.DietResWindow;
import window.FavoriteWindow;
import window.MainWindow;
import window.RecipeDetailsWindow;
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
    private ViewRecipesInteractor viewRecipesInteractor;

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

    public AppBuilder addViewUserRecipeDetailsUseCase() {
        userRecipeDetailsViewModel = new UserRecipeDetailsViewModel();

        userRecipeDetailsWindow = new UserRecipeDetailsWindow(userRecipeDetailsViewModel);

        ViewUserRecipeDetailsPresenter viewUserRecipeDetailsPresenter =
                new ViewUserRecipeDetailsPresenter(userRecipesViewModel, userRecipeDetailsViewModel);

        ViewUserRecipeDetailsInteractor viewUserRecipeDetailsInteractor =
                new ViewUserRecipeDetailsInteractor(fileDataAccessObject, viewUserRecipeDetailsPresenter,
                        viewRecipesInteractor);

        ViewUserRecipeDetailsController viewUserRecipeDetailsController = new ViewUserRecipeDetailsController(
                viewUserRecipeDetailsInteractor
        );

        userRecipesView.addViewRecipeDetailsUseCase(viewUserRecipeDetailsController);

        return this;
    }

    public AppBuilder addUserRecipesWindow() {
        this.userRecipeCardPanel.setLayout(userRecipeCardLayout);

        this.userRecipesViewManager = new UserRecipesViewManager(this.userRecipeCardLayout,
                this.userRecipeCardPanel, this.userRecipesViewManagerModel);


        this.userRecipeWindowModel = new UserRecipeWindowModel();

        userRecipesWindow = new UserRecipesWindow(userRecipeCardPanel
        );


        return this;
    }

    public AppBuilder addUserRecipesView() {
        this.userRecipesViewModel = new UserRecipesViewModel();

        userRecipesView = new UserRecipesView(userRecipesViewModel);

        this.userRecipeCardPanel.add(this.userRecipesView, userRecipesView.getViewName());
        this.userRecipeWindowModel.addPropertyChangeListener(this.userRecipesWindow);

        return this;
    }

	public AppBuilder addRecipeIngredientUseCase() {
		AddRecipeIngredientPresenter addIngredientPresenter = new AddRecipeIngredientPresenter(this.addRecipeViewModel);
		AddRecipeIngredientInteractor addIngredientInteractor =
				new AddRecipeIngredientInteractor(new FromMemoryMealRecipeDataAccessObject(), addIngredientPresenter);
		AddRecipeIngredientController addIngredientController = new AddRecipeIngredientController(addIngredientInteractor);

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

        viewRecipesInteractor = new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
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
        InventoryDataAccessObject inventoryDataObject = new InventoryDataAccessObject(inventory);
        
        SearchIngredientsPresenter searchPresenter = new SearchIngredientsPresenter(searchIngredientsViewModel);
        SearchIngredientsInteractor searchInteractor = new SearchIngredientsInteractor(searchPresenter, dataAccess);
        SearchIngredientsController searchController = new SearchIngredientsController(searchInteractor);
        
        AddIngredientPresenter addPresenter = new AddIngredientPresenter(addIngredientViewModel);
        AddIngredientInteractor addInteractor = new AddIngredientInteractor(addPresenter, inventoryDataObject);
        AddIngredientController addController = new AddIngredientController(addInteractor);
        
        RemoveIngredientPresenter removePresenter = new RemoveIngredientPresenter(removeIngredientViewModel);
        RemoveIngredientInteractor removeInteractor = new RemoveIngredientInteractor(removePresenter, inventoryDataObject);
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

    public AppBuilder addRemoveFavoriteUseCase() {
        RemoveFavoritePresenter removeFavoritePresenter = new RemoveFavoritePresenter(
                this.removeFavoriteViewModel, this.viewFavoriteViewModel);

        RemoveFavoriteInteractor removeFavoriteInteractor = new RemoveFavoriteInteractor(
                this.favoriteDataAccess, removeFavoritePresenter);

        this.removeFavoriteController = new RemoveFavoriteController(removeFavoriteInteractor);

        return this;
    }

    public AppBuilder addFavoritesView() {
        this.favoriteView = new FavoriteView(this.viewFavoriteViewModel, this.viewRecipeDetailsController,
                this.removeFavoriteController);
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

        MealDbIngredientGateway ingredientGateway = new MealDbIngredientGateway();

        AddDietResInteractor addDietResInteractor = new AddDietResInteractor(
                this.restrictionDataAccess,
                addDietResPresenter,
                ingredientGateway
        );

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
        MealDbRecipeGateway recipeGateway = new MealDbRecipeGateway();
        recipeGateway.preloadAllRecipes();

        InventoryReaderFromInventory inventoryReader =
                new InventoryReaderFromInventory(this.inventory);

        GenerateWithInventoryViewModel generateWithInventoryViewModel =
                new GenerateWithInventoryViewModel();

        GenerateByInventoryPanel panel = getGenerateByInventoryPanel(
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

    /**
     * connect the \"generate recipes by ingredients\" use case
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
