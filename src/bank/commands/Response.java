package bank.commands;

import java.io.Serializable;

public class Response<T extends Serializable> implements Serializable {
    private T response;
    private Exception exception;

    public Response(T respondse) {
        this.response = respondse;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}