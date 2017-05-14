package bank.rmi.Server;

import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.ServerBank;
import bank.rmi.ICommandSender;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CommandSender extends UnicastRemoteObject implements ICommandSender {

    private ServerBank bank;

    public CommandSender(int port, ServerBank bank) throws RemoteException {
        super(port);
        this.bank = bank;
    }

    @Override
    public <T extends Serializable> Response<T> sendCommand(ICommand command) throws RemoteException {
        Response<T> response = new Response<T>(null);
        try {
            response = command.apply(bank);
        } catch (Exception e) {
            response.setException(e);
            e.printStackTrace();
        }
        return response;
    }
}
