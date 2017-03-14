package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import java.io.IOException;

public class GetBalanceCommand extends AbstractAccountCommand {

    public GetBalanceCommand(String number) {
        super(number);
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>(bank.getAccount(number).getBalance());
    }
}
