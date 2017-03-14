package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class GetAccountCommand implements ICommand{
    private String number;

    public GetAccountCommand(String number) {
        this.number = number;
    }


    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>(bank.getAccount(number) != null);
    }
}
