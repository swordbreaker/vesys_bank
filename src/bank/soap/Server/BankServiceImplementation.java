package bank.soap.Server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class BankServiceImplementation implements BankService {

    @Override
    public String action(@WebParam(name = "object") String object) {
        /*
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
        */
        return object;
    }
}
