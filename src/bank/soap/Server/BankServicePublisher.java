package bank.soap.Server;

import javax.xml.ws.Endpoint;

/**
 * Created by Yanick on 27.03.17.
 */
public class BankServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                "http://localhost:7890/bank", // publication URI
                new BankServiceImplementation());
                System.out.println("service published");
    }
}
