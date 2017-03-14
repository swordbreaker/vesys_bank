package bank.commands;

import java.io.Serializable;

public class Response<T extends Serializable> implements Serializable {
    private T response;
    private Exception exception;

    public Response(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}