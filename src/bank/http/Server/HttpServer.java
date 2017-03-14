package bank.http.Server;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.commands.ICommand;
import bank.commands.Response;
import bank.common.ServerBank;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;


//@WebServlet(
//        name="LocalBank",
//        urlPatterns={"/bank"}
//)
@WebServlet("")
public class HttpServer extends HttpServlet {
    private static Bank bank = new ServerBank();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><pre>");
        out.println("<h1>Bank is running</h1>");
        out.println("</pre></body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse servletResponse) throws IOException {
        java.util.logging.Logger.getLogger(HttpServer.class.getName()).log(Level.WARNING, "doPost");
        try (ObjectOutputStream oos = new ObjectOutputStream(servletResponse.getOutputStream()); InputStream in = request.getInputStream()) {
            java.util.logging.Logger.getLogger(HttpServer.class.getName()).log(Level.WARNING, "A");
            Response response = new Response<>(null);
            try {
                java.util.logging.Logger.getLogger(HttpServer.class.getName()).log(Level.WARNING, "B");
                ObjectInputStream stream = new ObjectInputStream(in);
                java.util.logging.Logger.getLogger(HttpServer.class.getName()).log(Level.WARNING, "C");
                ICommand command = (ICommand) stream.readObject();
                response = command.apply(bank);
            } catch (InactiveException | OverdrawException e) {
                response.setException(e);
                e.printStackTrace();
            } finally {
                oos.writeObject(response);
                oos.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
            java.util.logging.Logger.getLogger(HttpServer.class.getName()).log(Level.WARNING, e.toString());
            throw new RuntimeException(e);
        }
    }
}
