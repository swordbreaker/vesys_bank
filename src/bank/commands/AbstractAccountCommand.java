package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

/**
 * Created by tobia on 27.02.2017.
 */
public abstract class AbstractAccountCommand implements ICommand{
    protected String number;

    public AbstractAccountCommand(String number) {
        this.number = number;
    }

    @Override
    public abstract Response Apply(Bank bank) throws IOException, InactiveException, OverdrawException;
}
