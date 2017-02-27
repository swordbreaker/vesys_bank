package bank.sockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {
    private bank.Bank bank = null;

    @Override
    public void connect(String[] args) {
        bank = new bank.sockets.Driver.Bank();
        System.out.println("connected...");
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("disconnected...");
    }

    @Override
    public bank.Bank getBank() {
        return bank;
    }

    static class Bank implements bank.Bank {

        private final Map<String, bank.sockets.Driver.Account> accounts = new HashMap<>();
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
            accounts.put(number, new bank.sockets.Driver.Account(owner, number));
            return number;
        }

        @Override
        public boolean closeAccount(String number) {
            if(!accounts.containsKey(number)) return false;
            bank.sockets.Driver.Account account = accounts.get(number);
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

    static class Account implements bank.Account {
        private String number;
        private String owner;
        private double balance;
        private boolean active = true;

        Account(String owner, String number) {
            this.owner = owner;
            this.number = number;
            active = true;
        }

        @Override
        public double getBalance() {
            return balance;
        }

        @Override
        public String getOwner() {
            return owner;
        }

        @Override
        public String getNumber() {
            return number;
        }

        @Override
        public boolean isActive() {
            return active;
        }

        @Override
        public void deposit(double amount) throws InactiveException {
            if(!active) throw new InactiveException();
            if(amount < 0) throw new IllegalArgumentException("Amount is negative");
            if(balance + amount < 0) throw new IllegalArgumentException("Overflow");
            balance += amount;
        }

        @Override
        public void withdraw(double amount) throws InactiveException, OverdrawException {
            if(!active) throw new InactiveException();
            if(amount < 0) throw new IllegalArgumentException("Amount is negative");
            if(balance - amount < 0) throw new OverdrawException("There is not enough balance on the account");
            balance -= amount;
        }

        @Override
        public boolean deactivate(){
            if(!active) return false;
            if(balance == 0){
                active = false;
            }
            return !active;
        }

    }

}