package bank.soap.Server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface BankService {
    String action(@WebParam(name = "object") String object);
}
