package use_cases.get_available_cuisines;

import adapters.get_available_cuisines.GetAvailableCuisinesPresenter;
import logic.get_available_cuisines.GetAvailableCuisinesDataAccessInterface;
import logic.get_available_cuisines.GetAvailableCuisinesInteractor;
import logic.get_available_cuisines.GetAvailableCuisinesOutputBoundary;
import logic.get_available_cuisines.GetAvailableCuisinesOutputData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetAvailableCuisinesInteractorTest {

    static class FakeDAO implements GetAvailableCuisinesDataAccessInterface {
        @Override public List<String> getAvailableCuisines() {
            return List.of("Italian", "Mexican");
        }
    }

    static class CapturingPresenter implements GetAvailableCuisinesOutputBoundary {
        GetAvailableCuisinesOutputData last;
        @Override public void present(GetAvailableCuisinesOutputData output) { last = output; }
    }

    @Test
    void interactorPassesRawListFromDao() {
        var dao = new FakeDAO();
        var pres = new CapturingPresenter();
        var interactor = new GetAvailableCuisinesInteractor(dao, pres);

        interactor.execute();

        assertNotNull(pres.last);
        assertEquals(List.of("Italian", "Mexican"), pres.last.getCuisines());
    }

    @Test
    void presenterAddsAnyAndFiresToVm() {
        var vm = new adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel();
        var presenter = new GetAvailableCuisinesPresenter(vm);

        presenter.present(new GetAvailableCuisinesOutputData(List.of("Italian", "Mexican")));

        assertEquals(List.of("Any", "Italian", "Mexican"), vm.getCuisines());
    }
}