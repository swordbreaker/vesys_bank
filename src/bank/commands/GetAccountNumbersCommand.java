package bank.commands;


import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import java.io.IOException;
import java.util.HashSet;

public class GetAccountNumbersCommand implements ICommand{

    @Override
    public Response apply(Bank bank) throws IOException, InactiveException, OverdrawException {
        return new Response<>((HashSet<String>)bank.getAccountNumbers());
    }
}
