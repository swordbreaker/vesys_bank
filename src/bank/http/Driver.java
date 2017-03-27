/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.http;

import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.CommonClientDriver;
import bank.common.LocalBank;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Driver extends CommonClientDriver {
    private bank.Bank bank = null;
    private URL url;

    @Override
    public void connect(String[] args) {
        bank = new LocalBank(this);
        try {
            url = new URL("http://" + args[0] + ":" + args[1] + "/bank/bank");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connected...");
    }

    private HttpURLConnection GetConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.connect();
        return connection;
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("disconnected...");
    }

    @Override
    public bank.Bank getBank() {
        return bank;
    }

    public <T extends Serializable> Response<T> sendData(ICommand command) {
        Response r = new Response<>(null);
        try {
            HttpURLConnection connection = GetConnection();
            ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());

            oos.writeObject(command);
            oos.flush();
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
            r = (Response<T>)ois.readObject();
            ois.close();
        } catch (IOException e) {
            r.setException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return r;
    }
}