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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;


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
        ObjectOutputStream out = new ObjectOutputStream(servletResponse.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(request.getInputStream());
        Response response = new Response<>(null);
        try {
            ICommand command = (ICommand) in.readObject();
            response = command.apply(bank);
        } catch (ClassNotFoundException | InactiveException | OverdrawException e) {
            response.setException(e);
            java.util.logging.Logger.getLogger(HttpServer.class.getName()).log(Level.WARNING, e.getMessage());
        }
        out.writeObject(response);
    }
}
