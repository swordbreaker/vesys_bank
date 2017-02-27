package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;
import java.io.Serializable;

public interface ICommand extends Serializable {
    Response Apply(Bank bank) throws IOException, InactiveException, OverdrawException;
}
