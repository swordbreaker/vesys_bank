package bank.commands;

import java.io.Serializable;

public class Response<T extends Serializable> implements Serializable {
    private T respondse;

    public Response(T respondse) {
        this.respondse = respondse;
    }

    public T getRespondse() {
        return respondse;
    }

    public void setRespondse(T respondse) {
        this.respondse = respondse;
    }
}
