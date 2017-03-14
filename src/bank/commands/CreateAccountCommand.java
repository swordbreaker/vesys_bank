package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class CreateAccountCommand implements ICommand {
    private String owner;

    public CreateAccountCommand(String owner) {
        this.owner = owner;
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>(bank.createAccount(owner));
    }
}
