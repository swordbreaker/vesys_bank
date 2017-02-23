package bank.local;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

import java.io.IOException;

public class LocalAccount implements Account {

    private String number;
    private String owner;
    private boolean isActive;
    private double balance;

    public LocalAccount(String owner, String number){
        this.owner = owner;
        this.number = number;
        isActive = true;
    }

    @Override
    public String getNumber() throws IOException {
        return number;
    }

    @Override
    public String getOwner() throws IOException {
        return owner;
    }

    public boolean deactivate(){
        if(!isActive) return false;
        if(balance == 0){
            isActive = false;
        }
        return !isActive;
    }

    @Override
    public boolean isActive() throws IOException {
        return isActive;
    }

    @Override
    public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
        if(!isActive) throw new InactiveException();
        if(amount < 0) throw new IllegalArgumentException("Amount is negative");
        if(balance + amount < 0) throw new IllegalArgumentException("Overflow");
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        if(!isActive) throw new InactiveException();
        if(amount < 0) throw new IllegalArgumentException("Amount is negative");
        if(balance - amount < 0) throw new OverdrawException("There is not enough balance on the account");
        balance -= amount;
    }

    @Override
    public double getBalance() throws IOException {
        return balance;
    }
}
