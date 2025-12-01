package logic.generate_recipe.generate_by_ingredients;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import entity.Ingredient;
import entity.Recipe;
import logic.dietary_restriction.DietaryRestrictionChecker;
import logic.dietary_restriction.DietaryRestrictionCheckerInterface;
import logic.dietary_restriction.diet_res_ingredients.DietResDataAccessInterface;

public class GenerateByIngredientsInteractor implements GenerateByIngredientsInputBoundary {

    private final RecipeByIngredientsGateway gateway;
    private final GenerateByIngredientsOutputBoundary presenter;
    private final DietResDataAccessInterface dietResDataAccessInterface;
    private final DietaryRestrictionCheckerInterface dietaryRestrictionChecker;

    public GenerateByIngredientsInteractor(RecipeByIngredientsGateway gateway,
                                           GenerateByIngredientsOutputBoundary presenter,
                                           DietResDataAccessInterface dietResDataAccessInterface) {
        this.gateway = gateway;
        this.presenter = presenter;
        this.dietResDataAccessInterface = dietResDataAccessInterface;
        this.dietaryRestrictionChecker = new DietaryRestrictionChecker();
    }

    @Override
    public void execute(GenerateByIngredientsInputData inputData) {
        final List<String> cleaned = getStrings(inputData);

        final List<Recipe> recipes = gateway.findByIngredients(cleaned);

        final List<Ingredient> restrictions = dietResDataAccessInterface.getResIngredients();

        final List<String> titles = recipes.stream()
                .filter(recipe -> !dietaryRestrictionChecker.containsRestrictedIngredient(recipe, restrictions))
                .map(Recipe::getTitle)
                .toList();

        final GenerateByIngredientsOutputData out =
                new GenerateByIngredientsOutputData(titles);

        presenter.present(out);
    }

    private static List<String> getStrings(GenerateByIngredientsInputData inputData) {
        List<String> rawIngredients = null;
        if (inputData != null) {
            rawIngredients = inputData.getIngredients();
        }

        final Set<String> unique = new LinkedHashSet<>();
        if (rawIngredients != null) {
            for (String raw : rawIngredients) {
                if (raw == null) {
                    continue;
                }
                final String trimmed = raw.trim();
                if (!trimmed.isEmpty()) {
                    unique.add(trimmed);
                }
            }
        }

        return new ArrayList<>(unique);
    }
}
