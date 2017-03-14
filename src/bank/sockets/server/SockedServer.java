package bank.sockets.server;


import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.ICommand;
import bank.commands.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SockedServer implements Runnable{

    private static final int port = 6789;
    private Socket socket;
    private static Bank bank = new ServerBank();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Startet Server on port " + port);
            while (true) {
                Thread t = new Thread(new SockedServer(server.accept()));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SockedServer(Socket s) {
        this.socket= s;
    }

    @Override
    public void run() {
        System.out.println("connection from " + socket);

        try (Socket sock = socket; ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream()); InputStream in = socket.getInputStream()) {
            Response response = new Response<>(null);
            try{
                ObjectInputStream stream = new ObjectInputStream(in);
                ICommand command = (ICommand)stream.readObject();
                response = command.apply(bank);
            }
            catch (InactiveException | OverdrawException e) {
                response.setException(e);
                e.printStackTrace();
            }
            finally {
                oos.writeObject(response);
                oos.flush();
            }
        } catch(IOException | ClassNotFoundException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
