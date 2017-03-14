package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class DeactivateCommand extends AbstractAccountCommand {
    public DeactivateCommand(String number) {
        super(number);
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        if(bank.getAccount(number) == null) return new Response<>(false);
        return new Response<>(bank.getAccount(number).deactivate());
    }
}
