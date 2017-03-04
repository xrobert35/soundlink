package fr.soundlink.service.async;

/**
 * Created by Ixoh on 22/04/2016.
 */
class AsyncResponse<T>{

    private boolean success;
    private T response;
    private Exception cause;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Exception getCause() {
        return cause;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }
}