package use_cases.diet_res;

import entity.Ingredient;
import entity.Recipe;
import logic.dietary_restriction.DietaryRestrictionChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DietaryRestrictionCheckerTest {

    private DietaryRestrictionChecker checker;

    @BeforeEach
    void setUp() {
        checker = new DietaryRestrictionChecker();
    }

    @Test
    void containsRestrictedIngredient_shouldReturnFalse_whenNoRestrictions() {
        Recipe recipe = new Recipe("Pasta", List.of(new Ingredient("Gluten", "100g")), "steps", "img", "yt", "cat");

        // Test nulls and empty lists
        assertFalse(checker.containsRestrictedIngredient(recipe, null));
        assertFalse(checker.containsRestrictedIngredient(recipe, Collections.emptyList()));
    }

    @Test
    void containsRestrictedIngredient_shouldReturnTrue_whenPartialMatchFound() {
        // Restriction is "Peanut"
        List<Ingredient> restrictions = List.of(new Ingredient("Peanut", ""));

        // Recipe has "Roasted Peanuts"
        Recipe recipe = new Recipe("Pad Thai", List.of(new Ingredient("Roasted Peanuts", "1 cup")), "steps", "img", "yt", "cat");

        // Should return TRUE now (it used to be false with equalsIgnoreCase)
        assertTrue(checker.containsRestrictedIngredient(recipe, restrictions), "Should detect 'Peanut' inside 'Roasted Peanuts'");
    }

    @Test
    void containsRestrictedIngredient_shouldReturnTrue_whenReversePartialMatchFound() {
        // Restriction is "Eggs"
        List<Ingredient> restrictions = List.of(new Ingredient("Eggs", ""));

        // Recipe has "Egg"
        Recipe recipe = new Recipe("Omelette", List.of(new Ingredient("Egg", "1")), "steps", "img", "yt", "cat");

        assertTrue(checker.containsRestrictedIngredient(recipe, restrictions), "Should detect 'Egg' inside 'Eggs'");
    }

    @Test
    void containsRestrictedIngredient_shouldReturnFalse_whenIngredientsAreDifferent() {
        Recipe recipe = new Recipe("Salad", List.of(new Ingredient("Lettuce", "1 head")), "steps", "img", "yt", "cat");
        List<Ingredient> restrictions = List.of(new Ingredient("Steak", ""));

        assertFalse(checker.containsRestrictedIngredient(recipe, restrictions));
    }
}