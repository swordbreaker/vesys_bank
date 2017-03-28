package bank.soap;

import bank.Bank;
import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.CommonClientDriver;
import bank.common.LocalBank;

import java.io.*;
import java.net.URL;

/**
 * Created by Yanick on 27.03.17.
 */
public class Driver  extends CommonClientDriver {
    private bank.Bank bank = null;
    private URL url;
    private BankServiceImplementationService service;

    @Override
    public <T extends Serializable> Response<T> sendData(ICommand command) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        Response response = null;
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOutputStream);
            out.writeObject(command);
            out.flush();
            out.close();
            byte[] responseData = service.getBankServiceImplementationPort().action(byteOutputStream.toByteArray());
            byteOutputStream.close();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(responseData);
            ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
            response = (Response<T>) in.readObject();
            in.close();
            byteArrayInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void connect(String[] args) throws IOException {
        bank = new LocalBank(this);
        try {
            url = new URL("http://" + args[0] + ":" + args[1] + "/bank");
        } catch (IOException e) {
            e.printStackTrace();
        }
        service = new BankServiceImplementationService();
        System.out.println("connected...");
    }

    @Override
    public void disconnect() throws IOException {
        bank = null;
        System.out.println("disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }
}
