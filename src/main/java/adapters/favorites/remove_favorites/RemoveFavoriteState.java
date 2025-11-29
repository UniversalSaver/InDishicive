package adapters.favorites.remove_favorites;

public class RemoveFavoriteState {
    private String statusMessage = "";
    private boolean success = false;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
