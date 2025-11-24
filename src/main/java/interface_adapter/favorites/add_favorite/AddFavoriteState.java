package interface_adapter.favorites.add_favorite;

/**
 * State for the Add Favorite view.
 */
public class AddFavoriteState {

    private String statusMessage = "";
    private boolean isSuccess = false;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
