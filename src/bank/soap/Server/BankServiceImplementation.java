package bank.soap.Server;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.ServerBank;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.*;
import java.util.logging.Level;

@WebService
public class BankServiceImplementation implements BankService {
    private static Bank bank = new ServerBank();

    @Override
    public byte[] action(@WebParam(name = "data") byte[] data) {
        Response response = new Response<>(null);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
            byte[] responseData;
            ICommand command = (ICommand) in.readObject();
            response = command.apply(bank);
            out.writeObject(response);
            responseData = byteArrayOutputStream.toByteArray();
            return responseData;
        } catch (IOException e) {
            response.setException(e);
            java.util.logging.Logger.getLogger(BankServiceImplementation.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (ClassNotFoundException | InactiveException | OverdrawException e) {
            response.setException(e);
            java.util.logging.Logger.getLogger(BankServiceImplementation.class.getName()).log(Level.WARNING, e.getMessage());
        }

        return createErrorResponse(response);
    }

    private byte[] createErrorResponse(Response response){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
