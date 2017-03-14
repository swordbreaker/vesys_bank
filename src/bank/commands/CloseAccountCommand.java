package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;


public class CloseAccountCommand extends AbstractAccountCommand {
    public CloseAccountCommand(String number) {
        super(number);
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>(bank.closeAccount(number));
    }
}
