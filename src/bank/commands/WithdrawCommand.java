package bank.commands;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class WithdrawCommand extends AbstractAccountCommand {
    private double amount;

    public WithdrawCommand(String number, double amount) {
        super(number);
        this.amount = amount;
    }

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        bank.getAccount(number).withdraw(amount);
        return new Response<>(null);
    }
}
