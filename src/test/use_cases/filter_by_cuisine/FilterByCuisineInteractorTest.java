package use_cases.filter_by_cuisine;

import logic.generate_recipe.filter_by_cuisine.FilterByCuisineDataAccessInterface;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineInteractor;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputBoundary;
import logic.generate_recipe.filter_by_cuisine.FilterByCuisineOutputData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterByCuisineInteractorTest {

    static class FakeDAO implements FilterByCuisineDataAccessInterface {
        @Override public List<String> getRecipeTitlesByCuisine(String cuisine) {
            return List.of("A", "B", "C", "D");
        }
    }

    static class CapturingPresenter implements FilterByCuisineOutputBoundary {
        FilterByCuisineOutputData last;
        @Override public void present(FilterByCuisineOutputData outputData) { last = outputData; }
    }

    @Test
    void anyCuisineReturnsEmptyPayloadSoPresenterCanRestoreAll() {
        var dao = new FakeDAO();
        var pres = new CapturingPresenter();
        var interactor = new FilterByCuisineInteractor(dao, pres);

        interactor.execute("Any");

        assertNotNull(pres.last);
        assertEquals("Any", pres.last.getCuisine());
        assertTrue(pres.last.getRecipeTitles().isEmpty());
    }
}