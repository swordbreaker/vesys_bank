package bank.rmi.Server;

import bank.common.ServerBank;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public static void main(String args[]) throws Exception {

        try {
            LocateRegistry.createRegistry(1099);
        }
        catch (RemoteException e) {
            System.out.println("registry could not be exported");
            System.out.println("probably another registry already runs on 1099");
        }
        
        ServerBank bank = new ServerBank();
        CommandSender sender = new CommandSender(7777 ,bank);

        Naming.rebind("rmi://localhost:1099/CommandSenderService", sender);
    }
}
