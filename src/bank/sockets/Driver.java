package bank.sockets;

import java.io.*;
import java.net.Socket;

import bank.commands.*;
import bank.common.LocalBank;
import bank.common.CommonClientDriver;

public class Driver extends CommonClientDriver {
    private bank.Bank bank = null;
    private static String server;
    private static int port;

    @Override
    public void connect(String[] args) {
        server = args[0];
        port = Integer.parseInt(args[1]);
        bank = new LocalBank(this);
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

    public <T extends Serializable> Response<T> sendData(ICommand command) {
        try (Socket s = new Socket(server, port)) {
            try (OutputStream out = s.getOutputStream(); InputStream in = s.getInputStream()) {
                try (ObjectOutputStream oos = new ObjectOutputStream(out); ObjectInputStream ois = new ObjectInputStream(in)) {
                    oos.writeObject(command);
                    oos.flush();
                    return (Response<T>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Response r = new Response<>(null);
                    r.setException(e);
                    return r;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Response r = new Response<>(null);
            r.setException(e);
            return r;
        }
    }
//    static class LocalBank implements bank.LocalBank {
//        @Override
//        public Set<String> getAccountNumbers() throws IOException {
//            Response<HashSet<String>> response = Driver.sendData(new GetAccountNumbersCommand());
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//        @Override
//        public String createAccount(String owner) throws IOException {
//            Response<String> response = Driver.sendData(new CreateAccountCommand(owner));
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//        @Override
//        public boolean closeAccount(String number) throws IOException {
//            Response<Boolean> response = Driver.sendData(new CloseAccountCommand(number));
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//        @Override
//        public bank.LocalAccount getAccount(String number) throws IOException {
//            Response<Boolean> response = Driver.sendData(new GetAccountCommand(number));
//            if(response.getResponse()){
//                return new LocalAccount(number);
//            }
//            else{
//                return null;
//            }
//        }
//
//        @Override
//        public void transfer(bank.LocalAccount from, bank.LocalAccount to, double amount) throws IOException, InactiveException, OverdrawException {
//            if(amount < 0) throw new IllegalArgumentException("Amount is negative");
//
//            double removedFormA = 0;
//            double addedToB = 0;
//            try{
//                Response response = Driver.sendData(new TransferCommand(from, to, amount));
//                if(response.getException() != null){
//                    Exception exp = response.getException();
//                    if(exp instanceof  InactiveException){
//                        throw (InactiveException)exp;
//                    }
//                    else if (exp instanceof OverdrawException){
//                        throw (OverdrawException)exp;
//                    }
//                    else if (exp instanceof  IOException) {
//                        throw (IOException)exp;
//                    }
//                    else{
//                        throw new IOException(response.getException().getMessage());
//                    }
//                }
//                from.withdraw(amount);
//                removedFormA = amount;
//                to.deposit(amount);
//                addedToB = amount;
//            } catch (IOException | IllegalArgumentException | OverdrawException | InactiveException e){
//                from.deposit(removedFormA);
//                to.withdraw(addedToB);
//                throw e;
//            }
//        }
//    }

//    static class LocalAccount implements bank.LocalAccount {
//        private String number;
//
//        LocalAccount(String number) {
//            this.number = number;
//        }
//
//        @Override
//        public double getBalance() throws IOException {
//            Response<Double> response = sendData(new GetBalanceCommand(getNumber()));
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//        @Override
//        public String getOwner() throws IOException {
//            Response<String> response = sendData(new GetOwnerCommand(getNumber()));
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//        @Override
//        public String getNumber() {
//            return number;
//        }
//
//        @Override
//        public boolean isActive() throws IOException {
//            Response<Boolean> response = sendData(new IsActiveCommand(getNumber()));
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//        @Override
//        public void deposit(double amount) throws InactiveException, IOException {
//
//            if(amount < 0) throw new IllegalArgumentException("Amount is negative");
//
//            Response response = sendData(new DepositCommand(getNumber(), amount));
//            if(response.getException() != null){
//                Exception exp = response.getException();
//                if(exp instanceof InactiveException){
//                    throw (InactiveException)exp;
//                }
//                else if(exp instanceof  IOException){
//                    throw (IOException)exp;
//                }
//                else{
//                    exp.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        public void withdraw(double amount) throws InactiveException, OverdrawException, IOException {
//            if(amount < 0) throw new IllegalArgumentException("Amount is negative");
//            Response response = sendData(new WithdrawCommand(getNumber(), amount));
//            if(response.getException() != null){
//                Exception exp = response.getException();
//                if(exp instanceof  InactiveException)
//                {
//                    throw (InactiveException)exp;
//                }
//                else if(exp instanceof  OverdrawException){
//                    throw (OverdrawException)exp;
//                }
//                else if(exp instanceof IOException){
//                    throw (IOException)exp;
//                }
//                else{
//                    exp.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        public boolean deactivate() throws IOException {
//            Response<Boolean> response = sendData(new DeactivateCommand(getNumber()));
//            if(response.getException() != null){
//                throw new IOException(response.getException().getMessage());
//            }
//            return response.getResponse();
//        }
//
//    }

}