package adapters.favorites.remove_favorites;

/**
 * State object for the remove favorite view model.
 * Holds the status and result of a remove favorite operation.
 */
public class RemoveFavoriteState {
    private boolean success;

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
