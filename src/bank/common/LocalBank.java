package bank.common;

import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LocalBank implements bank.Bank {
    private final CommonClientDriver driver;

    public LocalBank(CommonClientDriver driver){
        this.driver = driver;
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {

        Response<HashSet<String>> response = driver.sendData(new GetAccountNumbersCommand());
        if(response.getException() != null){
            throw new IOException("get account numbers failed: " + response.getException().getMessage());
        }
        return response.getResponse();
    }

    @Override
    public String createAccount(String owner) throws IOException {
        Response<String> response = driver.sendData(new CreateAccountCommand(owner));
        if(response.getException() != null){
            throw new IOException("Create account failed: " + response.getException().getMessage());
        }
        return response.getResponse();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        Response<Boolean> response = driver.sendData(new CloseAccountCommand(number));
        if(response.getException() != null){
            throw new IOException("Close account failed: " + response.getException().getMessage());
        }
        return response.getResponse();
    }

    @Override
    public bank.Account getAccount(String number) throws IOException {
        Response<Boolean> response = driver.sendData(new GetAccountCommand(number));
        if(response.getResponse()){
            return new LocalAccount(number, driver);
        }
        else{
            return null;
        }
    }

    @Override
    public void transfer(bank.Account from, bank.Account to, double amount) throws IOException, InactiveException, OverdrawException {
        if(amount < 0) throw new IllegalArgumentException("Amount is negative");

        try{
            Response response = driver.sendData(new TransferCommand(from.getNumber(), to.getNumber(), amount));
            if(response.getException() != null){
                Exception exp = response.getException();
                if(exp instanceof  InactiveException){
                    throw (InactiveException)exp;
                }
                else if (exp instanceof OverdrawException){
                    throw (OverdrawException)exp;
                }
                else if (exp instanceof  IOException) {
                    throw (IOException)exp;
                }
                else{
                    throw new IOException("Transfer Failed: " + response.getException().getMessage());
                }
            }
            from.withdraw(amount);
            to.deposit(amount);
        } catch (IOException | IllegalArgumentException | OverdrawException | InactiveException e){
            throw e;
        }
    }
}