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
    public Response Apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>(bank.getAccount(number).deactivate());
    }
}
