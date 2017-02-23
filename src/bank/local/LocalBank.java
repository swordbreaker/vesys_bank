package bank.local;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tobia on 23.02.2017.
 */
public class LocalBank implements Bank {

    private final Map<String, bank.Account> accounts = new HashMap<>();
    private final String accountPrefix = "A";
    private int idCounter;

    @Override
    public String createAccount(String owner) throws IOException {
        String number = accountPrefix + idCounter++;
        accounts.put(number, new LocalAccount(owner, number));
        return number;
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        if(!accounts.containsKey(number)) return false;
        LocalAccount account = (LocalAccount) accounts.get(number);
        return account.deactivate();
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return accounts.entrySet().stream().filter(stringAccountEntry -> {
            try {
                return stringAccountEntry.getValue().isActive();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    @Override
    public Account getAccount(String number) throws IOException {
        if(!accounts.containsKey(number)) return null;
        return accounts.get(number);
    }

    @Override
    public void transfer(Account a, Account b, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        if(amount < 0) throw new IllegalArgumentException("Amount is negative");
        if(!a.isActive() || !b.isActive()) throw new InactiveException("One of the Accounts is inactive");
        if(a.getBalance() - amount < 0) throw new OverdrawException("Account a has not enough money");

        double removedFormA = 0;
        double addedToB = 0;
        try{
            a.withdraw(amount);
            removedFormA = amount;
            b.deposit(amount);
            addedToB = amount;
        } catch (IOException | IllegalArgumentException | OverdrawException | InactiveException e){
            a.deposit(removedFormA);
            b.withdraw(addedToB);
            throw e;
        }
    }
}
