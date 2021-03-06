package bank.commands;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

/**
 * Created by tobia on 27.02.2017.
 */
public class TransferCommand implements ICommand {

    private Account from;
    private Account to;
    private double amount;

    public TransferCommand(Account from, Account to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public Response Apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        bank.transfer(from, to, amount);
        return new Response<>(null);
    }
}
