package interface_adapter.dietery_restriction.add_diet_res;

public class AddDietResState {
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
