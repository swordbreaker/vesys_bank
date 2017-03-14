package bank.common;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.*;

public abstract class CommonClientDriver implements bank.BankDriver {
    public abstract <T extends Serializable> Response<T> sendData(ICommand command);

    protected  <T extends Serializable> Response<T> sendData(OutputStream out, InputStream in, ICommand command){
        try (ObjectOutputStream oos = new ObjectOutputStream(out); ObjectInputStream ois = new ObjectInputStream(in)) {
            oos.writeObject(command);
            oos.flush();
            Response<T> response = (Response<T>) ois.readObject();
            return (Response<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Response r = new Response<>(null);
            r.setException(e);
            return r;
        }
    }
}