/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.http;

import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.CommonClientDriver;
import bank.common.LocalBank;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
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
            url = new URL("http://" + args[0] + ":" + args[1] + "/bank");
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
        try {
            HttpURLConnection connection = GetConnection();

        try (OutputStream out = connection.getOutputStream(); InputStream in = connection.getInputStream()) {
            return sendData(out, in, command);
        } catch (IOException e) {
            e.printStackTrace();
            Response r = new Response<>(null);
            r.setException(e);
            connection.disconnect();
            return r;
        }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}