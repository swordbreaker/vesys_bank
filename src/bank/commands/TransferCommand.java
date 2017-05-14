package bank.commands;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class TransferCommand implements ICommand {

    private String fromNumber;
    private String toNumber;
    private double amount;

    public TransferCommand(String fromNumber, String toNumber, double amount) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.amount = amount;
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        Account from = bank.getAccount(fromNumber);
        Account to = bank.getAccount(toNumber);
        bank.transfer(from, to, amount);
        return new Response<>(null);
    }
}
