package bank.common;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
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
}