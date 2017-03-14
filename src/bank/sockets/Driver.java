package bank.sockets;

import java.io.*;
import java.net.Socket;

import bank.commands.*;
import bank.common.LocalBank;
import bank.common.CommonClientDriver;

public class Driver extends CommonClientDriver {
    private bank.Bank bank = null;
    private static String server;
    private static int port;

    @Override
    public void connect(String[] args) {
        server = args[0];
        port = Integer.parseInt(args[1]);
        bank = new LocalBank(this);
        System.out.println("connected...");
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
        try (Socket s = new Socket(server, port)) {
            try (OutputStream out = s.getOutputStream(); InputStream in = s.getInputStream()) {
                try (ObjectOutputStream oos = new ObjectOutputStream(out); ObjectInputStream ois = new ObjectInputStream(in)) {
                    oos.writeObject(command);
                    oos.flush();
                    return (Response<T>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Response r = new Response<>(null);
                    r.setException(e);
                    return r;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Response r = new Response<>(null);
            r.setException(e);
            return r;
        }
    }
}