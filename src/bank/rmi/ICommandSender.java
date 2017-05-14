package bank.rmi;

import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.ICommand;
import bank.commands.Response;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by tobia on 14.05.2017.
 */
public interface ICommandSender extends Remote {
    <T extends Serializable> Response sendCommand(ICommand command) throws RemoteException;
}
