package bank.common;

import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class LocalAccount implements bank.Account {
    private final String number;
    private final CommonClientDriver driver;

    LocalAccount(String number, CommonClientDriver driver) {
        this.number = number;
        this.driver = driver;

        ArrayList<Integer> bla = new ArrayList<Integer>();
        Stream<String> a =  bla.stream().map(i -> i.toString());

    }

    @Override
    public double getBalance() throws IOException {
        Response<Double> response = driver.sendData(new GetBalanceCommand(getNumber()));
        if(response.getException() != null){
            throw new IOException("Get balance failed: " + response.getException().getMessage());
        }
        return response.getResponse();
    }

    @Override
    public String getOwner() throws IOException {
        Response<String> response = driver.sendData(new GetOwnerCommand(getNumber()));
        if(response.getException() != null){
            throw new IOException("Get owner failed: " + response.getException().getMessage());
        }
        return response.getResponse();
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() throws IOException {
        Response<Boolean> response = driver.sendData(new IsActiveCommand(getNumber()));
        if(response.getException() != null){
            throw new IOException("isActive failed: " + response.getException().getMessage());
        }
        return response.getResponse();
    }

    @Override
    public void deposit(double amount) throws InactiveException, IOException {

        if(amount < 0) throw new IllegalArgumentException("Amount is negative");

        Response response = driver.sendData(new DepositCommand(getNumber(), amount));
        if(response.getException() != null){
            Exception exp = response.getException();
            if(exp instanceof InactiveException){
                throw (InactiveException)exp;
            }
            else if(exp instanceof  IOException){
                throw (IOException)exp;
            }
            else{
                exp.printStackTrace();
            }
        }
    }

    @Override
    public void withdraw(double amount) throws InactiveException, OverdrawException, IOException {
        if(amount < 0) throw new IllegalArgumentException("Amount is negative");
        Response response = driver.sendData(new WithdrawCommand(getNumber(), amount));
        if(response.getException() != null){
            Exception exp = response.getException();
            if(exp instanceof  InactiveException)
            {
                throw (InactiveException)exp;
            }
            else if(exp instanceof  OverdrawException){
                throw (OverdrawException)exp;
            }
            else if(exp instanceof IOException){
                throw (IOException)exp;
            }
            else{
                exp.printStackTrace();
            }
        }
    }

//    @Override
//    public boolean deactivate() throws IOException {
//        Response<Boolean> response = driver.sendData(new DeactivateCommand(getNumber()));
//        if(response.getException() != null){
//            throw new IOException("deactivate failed: " + response.getException().getMessage());
//        }
//        return response.getResponse();
//    }
}