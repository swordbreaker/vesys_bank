package bank.soap.Server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface BankService {
    byte[] action(@WebParam(name = "data") byte[] data);
}
