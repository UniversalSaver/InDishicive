package adapters.favorites.remove_favorites;

/**
 * State object for the remove favorite view model.
 * Holds the status and result of a remove favorite operation.
 */
public class RemoveFavoriteState {
    private String statusMessage = "";
    private boolean success = false;

    /**
     * Gets the status message of the remove favorite operation.
     *
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the status message of the remove favorite operation.
     *
     * @param statusMessage the status message to set
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * Checks if the remove favorite operation was successful.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets whether the remove favorite operation was successful.
     *
     * @param success true if successful, false otherwise
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
