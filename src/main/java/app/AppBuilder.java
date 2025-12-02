package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsController;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsPresenter;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsViewModel;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryPresenter;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;

import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsPresenter;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;

import adapters.inventory.add_ingredient.AddIngredientController;
import adapters.inventory.add_ingredient.AddIngredientPresenter;
import adapters.inventory.add_ingredient.AddIngredientViewModel;
import adapters.inventory.missing_ingredients.MissingIngredientsController;
import adapters.inventory.missing_ingredients.MissingIngredientsPresenter;
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
import adapters.user_recipe.delete_recipe.DeleteUserRecipeController;
import adapters.user_recipe.view_recipes.UserRecipeWindowModel;
import adapters.user_recipe.view_recipes.UserRecipesViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesController;
import adapters.user_recipe.view_recipes.ViewRecipesPresenter;
import adapters.user_recipe.view_recipes.view_detailed_recipe.UserRecipeDetailsViewModel;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsController;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsPresenter;

import adapters.generate_recipe.filter_by_cuisine.FilterByCuisineController;
import adapters.generate_recipe.filter_by_cuisine.FilterByCuisinePresenter;
import adapters.generate_recipe.get_available_cuisines.GetAvailableCuisinesController;
import adapters.generate_recipe.get_available_cuisines.GetAvailableCuisinesPresenter;

import databases.dietary_restriction.DietResDataAccessObject;
import databases.dietary_restriction.MealDbIngredientGateway;
import databases.favorites.FavoriteDataAccessObject;

import databases.generate_recipe.MealDbRecipeByIngredientsGateway;
import databases.generate_recipe.MealDbRecipeDetailsGateway;
import databases.generate_recipe.MealDbRecipeGateway;
import databases.generate_recipe.MealDbCuisineDataAccessObject; // new

import databases.generate_recipe.MealDbRandomRecipeGateway;

import databases.inventory.InventoryDataAccessObject;
import databases.inventory.MealDbIngredientDataAccess;

import databases.user_recipe.FileDataAccessObject;

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

import logic.generate_recipe.random_recipe.RandomRecipeGateway;
import logic.generate_recipe.random_recipe.RandomRecipeInputBoundary;
import logic.generate_recipe.random_recipe.RandomRecipeInteractor;
import logic.generate_recipe.random_recipe.RandomRecipeOutputBoundary;
import adapters.generate_recipe.random_recipe.RandomRecipeController;
import adapters.generate_recipe.random_recipe.RandomRecipePresenter;

import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInputBoundary;
import logic.generate_recipe.view_recipe_details.ViewRecipeDetailsInteractor;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsOutputBoundary;

import logic.inventory.add_ingredient.AddIngredientInteractor;
import logic.inventory.missing_ingredients.MissingIngredientsInteractor;
import logic.inventory.remove_ingredient.RemoveIngredientInteractor;
import logic.inventory.search_ingredients.SearchIngredientsInteractor;

import logic.user_recipe.add_recipe.AddRecipeInteractor;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientInteractor;
import logic.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInteractor;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInteractor;
import logic.user_recipe.view_recipes.ViewRecipesInteractor;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsInteractor;

import view.MainView;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;
import view.favorite_view.FavoriteView;
import view.generate_recipe_view.GenerateByIngredientsPanel;
import view.inventory.GenerateByInventoryPanel;
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
     * Start of the UserRecipe variables
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
     * End of UserRecipes Variables
     */

    /*
     * Start of Inventory Variables
     */
    private InventoryView inventoryView;
    private final InventoryDataAccessObject inventoryDao = new InventoryDataAccessObject();
    private final SearchIngredientsViewModel searchIngredientsViewModel = new SearchIngredientsViewModel();
    private final AddIngredientViewModel addIngredientViewModel = new AddIngredientViewModel();
    private final RemoveIngredientViewModel removeIngredientViewModel = new RemoveIngredientViewModel();
    private MissingIngredientsController missingIngredientsController;
    /*
     * End of Inventory Variables
     */

    /*
     * Start of Favorites variables
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
     * End of Favorites Variables
     */

    /*
     * Start of Random Recipe Variables
     */
    private RandomRecipeController randomRecipeController;
    /*
     * End of Random Recipe Variables
     */

    /*
     * Start of DietRes variables
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
     * End of DietRes variables
     */

    /*
     * Start of Generate By Ingredients Variables (UC2)
     */
    private GenerateByIngredientsViewModel generateByIngredientsViewModel;
    /*
     * End of Generate By Ingredients Variables
     */

    /** Adds the main view to the application builder. */
    public AppBuilder addMainView() {
        mainView = new MainView();
        this.mainWindow.addMainView(mainView);
        return this;
    }

    /** Adds the main window to the application builder. */
    public AppBuilder addMainWindow() {
        mainWindow = new MainWindow("Indishisive");
        return this;
    }

    /** Adds the profile menu to the application builder. */
    public AppBuilder addProfileMenu() {
        mainWindow.addProfileMenu();
        return this;
    }

    /** Adds the Indishisive DAO to the application builder. */
    public AppBuilder addIndishisiveDAO(final FileDataAccessObject memoryDataAccessObject) {
        this.fileDataAccessObject = memoryDataAccessObject;
        return this;
    }


    public AppBuilder addViewUserRecipeDetailsUseCase() {
        userRecipeDetailsViewModel = new UserRecipeDetailsViewModel();
        userRecipeDetailsWindow = new UserRecipeDetailsWindow(userRecipeDetailsViewModel);

        viewUserRecipeDetailsPresenter =
                new ViewUserRecipeDetailsPresenter(userRecipesViewModel, userRecipeDetailsViewModel);

        final ViewUserRecipeDetailsInteractor interactor =
                new ViewUserRecipeDetailsInteractor(fileDataAccessObject, viewUserRecipeDetailsPresenter,
                        viewRecipesInteractor);

        final ViewUserRecipeDetailsController controller =
                new ViewUserRecipeDetailsController(interactor);

        userRecipesView.addViewRecipeDetailsUseCase(controller);
        return this;
    }

    public AppBuilder addUserRecipesWindow() {
        this.userRecipeCardPanel.setLayout(userRecipeCardLayout);
        this.userRecipesViewManager = new UserRecipesViewManager(this.userRecipeCardLayout,
                this.userRecipeCardPanel, this.userRecipesViewManagerModel);
        this.userRecipeWindowModel = new UserRecipeWindowModel();
        userRecipesWindow = new UserRecipesWindow(userRecipeCardPanel);
        return this;
    }

    public AppBuilder addUserRecipesView() {
        this.userRecipesViewModel = new UserRecipesViewModel();
        userRecipesView = new UserRecipesView(userRecipesViewModel);
        this.userRecipeCardPanel.add(this.userRecipesView, userRecipesView.getViewName());
        this.userRecipeWindowModel.addPropertyChangeListener(this.userRecipesWindow);
        return this;
    }

    public AppBuilder addDeleteUserRecipeUseCase() {
        final DeleteUserRecipeInteractor interactor = new DeleteUserRecipeInteractor(
                viewRecipesInteractor, fileDataAccessObject, viewUserRecipeDetailsPresenter
        );
        final DeleteUserRecipeController controller = new DeleteUserRecipeController(interactor);
        userRecipesView.addDeleteRecipeUseCase(controller);
        return this;
    }

    public AppBuilder addRecipeIngredientUseCase() {
        final AddRecipeIngredientPresenter presenter =
                new AddRecipeIngredientPresenter(this.addRecipeViewModel);
        final AddRecipeIngredientInteractor interactor =
                new AddRecipeIngredientInteractor(new MealDbIngredientDataAccess(), presenter);
        final AddRecipeIngredientController controller = new AddRecipeIngredientController(interactor);
        this.addRecipeView.addIngredientUseCase(controller);
        return this;
    }

    public AppBuilder addAddRecipeUseCase() {
        final ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        final ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        final AddRecipePresenter addRecipePresenter =
                new AddRecipePresenter(viewRecipesController, addRecipeViewModel);
        final AddRecipeInteractor addRecipeInteractor =
                new AddRecipeInteractor(fileDataAccessObject, addRecipePresenter);
        final AddRecipeController addRecipeController = new AddRecipeController(addRecipeInteractor);

        this.addRecipeView.addAddRecipeUseCase(addRecipeController);
        return this;
    }

    public AppBuilder addUserRecipesCancelButtonUseCase() {
        final ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        final ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

        addRecipeView.addCancelButtonUseCase(viewRecipesController);
        return this;
    }

    public AppBuilder addViewRecipesUseCase() {
        final ViewRecipesPresenter viewRecipesPresenter = new ViewRecipesPresenter(
                this.userRecipeWindowModel, this.userRecipesViewManagerModel, this.userRecipesViewModel);

        viewRecipesInteractor =
                new ViewRecipesInteractor(viewRecipesPresenter, this.fileDataAccessObject);
        final ViewRecipesController viewRecipesController = new ViewRecipesController(viewRecipesInteractor);

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
        final ViewCreatorPresenter viewCreatorPresenter =
                new ViewCreatorPresenter(this.userRecipesViewManagerModel, this.addRecipeViewModel);
        final ViewCreatorInteractor interactor = new ViewCreatorInteractor(viewCreatorPresenter);
        final SwitchViewController controller = new SwitchViewController(interactor);
        userRecipesView.addViewCreatorUseCase(controller);
        return this;
    }


    public AppBuilder addInventoryView() {
        final MealDbIngredientDataAccess dataAccess = new MealDbIngredientDataAccess();

        final SearchIngredientsPresenter searchPresenter =
                new SearchIngredientsPresenter(searchIngredientsViewModel);
        final SearchIngredientsInteractor searchInteractor =
                new SearchIngredientsInteractor(searchPresenter, dataAccess);
        final SearchIngredientsController searchController =
                new SearchIngredientsController(searchInteractor);

        final AddIngredientPresenter addPresenter = new AddIngredientPresenter(addIngredientViewModel);
        final AddIngredientInteractor addInteractor =
                new AddIngredientInteractor(addPresenter, inventoryDao);
        final AddIngredientController addController = new AddIngredientController(addInteractor);

        final RemoveIngredientPresenter removePresenter =
                new RemoveIngredientPresenter(removeIngredientViewModel);
        final RemoveIngredientInteractor removeInteractor =
                new RemoveIngredientInteractor(removePresenter, inventoryDao);
        final RemoveIngredientController removeController = new RemoveIngredientController(removeInteractor);

        inventoryView = new InventoryView(
                searchController, addController, removeController, searchIngredientsViewModel, inventoryDao);

        mainView.addInventoryTab(inventoryView);

        return this;
    }


    public AppBuilder addAddFavoriteUseCase() {
        final AddFavoritePresenter addFavoritePresenter = new AddFavoritePresenter(this.addFavoriteViewModel);
        final MealDbRecipeDetailsGateway recipeDetailsGateway = new MealDbRecipeDetailsGateway();
        final AddFavoriteInteractor interactor = new AddFavoriteInteractor(
                this.favoriteDataAccess, this.favoriteDataAccess, addFavoritePresenter, recipeDetailsGateway);
        this.addFavoriteController = new AddFavoriteController(interactor);
        return this;
    }

    public AppBuilder addViewFavoritesUseCase() {
        final ViewFavoritePresenter presenter = new ViewFavoritePresenter(this.viewFavoriteViewModel);
        final ViewFavoriteInteractor interactor =
                new ViewFavoriteInteractor(this.favoriteDataAccess, presenter);
        this.viewFavoriteController = new ViewFavoriteController(interactor);
        return this;
    }

    public AppBuilder addRemoveFavoriteUseCase() {
        final RemoveFavoritePresenter presenter =
                new RemoveFavoritePresenter(this.removeFavoriteViewModel, this.viewFavoriteViewModel);
        final RemoveFavoriteInteractor interactor =
                new RemoveFavoriteInteractor(this.favoriteDataAccess, this.favoriteDataAccess, presenter);
        this.removeFavoriteController = new RemoveFavoriteController(interactor);
        return this;
    }

    public AppBuilder addFavoritesView() {
        this.favoriteView = new FavoriteView(
                this.viewFavoriteViewModel, this.viewRecipeDetailsController, this.removeFavoriteController);
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


    public AppBuilder addAddDietResUseCase() {
        final AddDietResPresenter presenter = new AddDietResPresenter(this.addDietResViewModel);
        final MealDbIngredientGateway ingredientGateway = new MealDbIngredientGateway();
        final AddDietResInteractor interactor =
                new AddDietResInteractor(this.restrictionDataAccess, presenter, ingredientGateway);
        this.addDietResController = new AddDietResController(interactor);
        return this;
    }

    public AppBuilder addRemoveDietResUseCase() {
        final RemoveDietResPresenter presenter = new RemoveDietResPresenter(this.removeDietResViewModel);
        final RemoveDietResInteractor interactor =
                new RemoveDietResInteractor(this.restrictionDataAccess, presenter);
        this.removeDietResController = new RemoveDietResController(interactor);
        return this;
    }

    public AppBuilder addDietResWindow() {
        this.dietResCardPanel.setLayout(dietResCardLayout);
        this.dietResViewManager = new DietResViewManager(
                this.dietResCardLayout, this.dietResCardPanel, this.dietResViewManagerModel);
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
        final ViewRestrictionsPresenter presenter = new ViewRestrictionsPresenter(
                this.dietResWindowModel, this.dietResViewManagerModel, this.dietResViewModel);
        final ViewRestrictionsInteractor interactor =
                new ViewRestrictionsInteractor(this.restrictionDataAccess, presenter);
        this.viewRestrictionsController = new ViewRestrictionsController(interactor);
        mainWindow.addViewRestrictionsUseCase(viewRestrictionsController);
        return this;
    }


    public JFrame build() {
        mainWindow.add(mainView);
        return mainWindow;
    }


    public AppBuilder addGenerateWithInventoryUseCase() {
        final MealDbRecipeGateway recipeGateway = new MealDbRecipeGateway();
        recipeGateway.preloadAllRecipes();

        final GenerateWithInventoryViewModel vm = new GenerateWithInventoryViewModel();

        final GenerateByInventoryPanel panel = getGenerateByInventoryPanel(
                vm,
                inventoryDao,
                recipeGateway
        );

        this.mainView.addGenerateByInventoryPanel(panel);
        return this;
    }

    private GenerateByInventoryPanel getGenerateByInventoryPanel(
            final GenerateWithInventoryViewModel vm,
            final InventoryReader inventoryReader,
            final RecipeGateway recipeGateway) {

        final GenerateWithInventoryOutputBoundary presenter = new GenerateWithInventoryPresenter(vm);
        final DietaryRestrictionCheckerInterface dietResChecker = new DietaryRestrictionChecker();

        final GenerateWithInventoryInputBoundary interactor =
                new GenerateWithInventoryInteractor(
                        inventoryReader,
                        recipeGateway,
                        presenter,
                        this.restrictionDataAccess,
                        dietResChecker
                );

        final GenerateWithInventoryController generateWithInventoryController =
                new GenerateWithInventoryController(interactor);

        final MealDbCuisineDataAccessObject cuisineDao = new MealDbCuisineDataAccessObject();

        final FilterByCuisinePresenter cuisinePresenter = new FilterByCuisinePresenter(vm);
        final logic.generate_recipe.filter_by_cuisine.FilterByCuisineInteractor cuisineInteractor =
                new logic.generate_recipe.filter_by_cuisine.FilterByCuisineInteractor(cuisineDao, cuisinePresenter);
        final FilterByCuisineController cuisineController = new FilterByCuisineController(cuisineInteractor);

        final GetAvailableCuisinesPresenter listPresenter = new GetAvailableCuisinesPresenter(vm);
        final logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesInteractor listInteractor =
                new logic.generate_recipe.get_available_cuisines.GetAvailableCuisinesInteractor(cuisineDao, listPresenter);
        final GetAvailableCuisinesController cuisinesController =
                new GetAvailableCuisinesController(listInteractor);

        final GenerateByInventoryPanel panel = new GenerateByInventoryPanel(
                generateWithInventoryController,
                vm,
                viewRecipeDetailsController,
                this.addFavoriteController,
                this.addFavoriteViewModel,
                cuisineController,
                this.randomRecipeController   // <-- new
        );

        cuisinesController.execute();

        return panel;
    }


    public AppBuilder addMissingIngredientsUseCase() {
        final MissingIngredientsPresenter presenter = new MissingIngredientsPresenter(recipeDetailsWindow);
        final MissingIngredientsInteractor interactor = new MissingIngredientsInteractor(inventoryDao, presenter);
        missingIngredientsController = new MissingIngredientsController(interactor);
        return this;
    }

    public AppBuilder addViewRecipeDetailsUseCase() {
        viewRecipeDetailsViewModel = new ViewRecipeDetailsViewModel();
        recipeDetailsWindow = new RecipeDetailsWindow(viewRecipeDetailsViewModel, missingIngredientsController);

        final ViewRecipeDetailsPresenter outputBoundary = new ViewRecipeDetailsPresenter(viewRecipeDetailsViewModel);
        final ViewRecipeDetailsInputBoundary interactor =
                new ViewRecipeDetailsInteractor(new MealDbRecipeDetailsGateway(), outputBoundary);

        viewRecipeDetailsController = new ViewRecipeDetailsController(interactor);
        return this;
    }


    public AppBuilder addRandomRecipeUseCase() {
        final RandomRecipeGateway randomGateway = new MealDbRandomRecipeGateway();
        final RandomRecipeOutputBoundary presenter = new RandomRecipePresenter(this.viewRecipeDetailsViewModel);
        final RandomRecipeInputBoundary interactor =
                new RandomRecipeInteractor(randomGateway, this.restrictionDataAccess, presenter);
        this.randomRecipeController = new RandomRecipeController(interactor);
        return this;
    }


    public AppBuilder addGenerateByIngredientsUseCase() {
        this.generateByIngredientsViewModel = new GenerateByIngredientsViewModel();

        final GenerateByIngredientsOutputBoundary presenter =
                new GenerateByIngredientsPresenter(this.generateByIngredientsViewModel);

        final RecipeByIngredientsGateway gateway = new MealDbRecipeByIngredientsGateway();

        final GenerateByIngredientsInputBoundary interactor =
                new GenerateByIngredientsInteractor(gateway, presenter, this.restrictionDataAccess);

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