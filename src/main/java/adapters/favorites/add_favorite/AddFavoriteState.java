package adapters.favorites.add_favorite;

/**
 * State object for the add favorite view model.
 * Holds the status and result of an add favorite operation.
 */
public class AddFavoriteState {

    private String statusMessage = "";
    private boolean isSuccess = false;

    /**
     * Gets the status message of the add favorite operation.
     *
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the status message of the add favorite operation.
     *
     * @param statusMessage the status message to set
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * Checks if the add favorite operation was successful.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Sets whether the add favorite operation was successful.
     *
     * @param success true if successful, false otherwise
     */
    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
