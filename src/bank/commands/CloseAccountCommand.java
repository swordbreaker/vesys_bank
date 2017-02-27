package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

/**
 * Created by tobia on 27.02.2017.
 */
public class CloseAccountCommand implements ICommand {
    private String number;

    public CloseAccountCommand(String number) {
        this.number = number;
    }

    @Override
    public Response Apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>(bank.closeAccount(number));
    }
}
