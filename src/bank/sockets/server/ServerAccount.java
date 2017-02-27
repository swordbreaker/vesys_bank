package bank.sockets.server;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class ServerAccount implements Account {
    private String number;
    private String owner;
    private double balance;
    private boolean active = true;

    ServerAccount(String owner, String number) {
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
