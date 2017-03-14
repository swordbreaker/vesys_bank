package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class DepositCommand extends AbstractAccountCommand {
    private double amount;

    public DepositCommand(String number, double amount) {
        super(number);
        this.amount = amount;
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        bank.getAccount(number).deposit(amount);
        return new Response<>(null);
    }
}
