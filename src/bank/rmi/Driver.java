/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.rmi;

import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.CommonClientDriver;
import bank.common.LocalBank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Driver extends CommonClientDriver {
    private bank.Bank bank = null;
    private ICommandSender commandSender;

    @Override
    public void connect(String[] args) {
        bank = new LocalBank(this);
        try {
            commandSender = (ICommandSender) Naming.lookup("rmi:/CommandSenderService");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
        try {
            return commandSender.sendCommand(command);
        } catch (RemoteException e) {
            System.out.println("cannot connect to the server");
            e.printStackTrace();
        }
        return null;
    }
}