package data_access_object;

import data_access.FromMemoryMealRecipeDataAccessObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class FromMemoryMealTest {
    @Test
    void existRecipeTest() {
        FromMemoryMealRecipeDataAccessObject db = new FromMemoryMealRecipeDataAccessObject();

        assertEquals("Pizza", db.findByTitle("Pizza").getTitle());
    }

    @Test
    void noRecipeTest() {
        FromMemoryMealRecipeDataAccessObject db = new FromMemoryMealRecipeDataAccessObject();

        assertNull(db.findByTitle("Escargot"));
    }
}
