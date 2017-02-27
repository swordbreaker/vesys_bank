package bank.sockets.server;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.sockets.Driver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tobia on 27.02.2017.
 */
public class ServerBank implements Bank {
    private final Map<String, ServerAccount> accounts = new HashMap<>();
    private final String accountPrefix = "A";
    private int idCounter;

    @Override
    public Set<String> getAccountNumbers() {
        return accounts.entrySet().stream()
                .filter(stringAccountEntry -> stringAccountEntry.getValue().isActive())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public String createAccount(String owner) {
        String number = accountPrefix + idCounter++;
        accounts.put(number, new ServerAccount(owner, number));
        return number;
    }

    @Override
    public boolean closeAccount(String number) {
        if(!accounts.containsKey(number)) return false;
        ServerAccount account = accounts.get(number);
        return account.deactivate();
    }

    @Override
    public bank.Account getAccount(String number) {
        return accounts.get(number);
    }

    @Override
    public void transfer(bank.Account from, bank.Account to, double amount) throws IOException, InactiveException, OverdrawException {
        if(amount < 0) throw new IllegalArgumentException("Amount is negative");
        if(!from.isActive() || !to.isActive()) throw new InactiveException("One of the Accounts is inactive");
        if(from.getBalance() - amount < 0) throw new OverdrawException("Account a has not enough money");

        double removedFormA = 0;
        double addedToB = 0;
        try{
            from.withdraw(amount);
            removedFormA = amount;
            to.deposit(amount);
            addedToB = amount;
        } catch (IOException | IllegalArgumentException | OverdrawException | InactiveException e){
            from.deposit(removedFormA);
            to.withdraw(addedToB);
            throw e;
        }
    }
}
