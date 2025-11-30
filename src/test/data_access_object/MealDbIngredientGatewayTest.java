package data_access_object;

import databases.dietary_restriction.MealDbIngredientGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Tests for MealDbIngredientGateway.
 * Requirements:
 * 1. Active Internet connection.
 * 2. TheMealDB API must be up and running.
 */
@Tag("Integration")
class MealDbIngredientGatewayIntegrationTest {

    private MealDbIngredientGateway gateway;

    @BeforeEach
    void setUp() {
        // This will attempt to connect to https://themealdb.com/
        gateway = new MealDbIngredientGateway();
    }

    @Test
    void isValidIngredient_shouldReturnTrue_forKnownValidIngredient() {
        // "Garlic" is a standard ingredient that should always exist in the DB
        boolean result = gateway.isValidIngredient("Garlic");
        assertTrue(result, "The API should return true for 'Garlic'");
    }

    @Test
    void isValidIngredient_shouldReturnTrue_forIngredientWithSpaces() {
        // "Chicken Breast" needs URL encoding to work
        boolean result = gateway.isValidIngredient("Chicken Breast");
        assertTrue(result, "The API should return true for 'Chicken Breast' (testing URL encoding)");
    }

    @Test
    void isValidIngredient_shouldReturnFalse_forNonExistentIngredient() {
        // Random string, not a valid ingredient
        String fakeIngredient = "FakeIngredient";
        boolean result = gateway.isValidIngredient(fakeIngredient);
        assertFalse(result, "The API should return false for a non-existent ingredient");
    }

    @Test
    void isValidIngredient_shouldReturnFalse_forNullInput() {
        boolean result = gateway.isValidIngredient(null);
        assertFalse(result);
    }
}