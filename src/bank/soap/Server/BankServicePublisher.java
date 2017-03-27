package bank.soap.Server;

import javax.xml.ws.Endpoint;

/**
 * Created by Yanick on 27.03.17.
 */
public class BankServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                "http://" + args[0] + ":" + args[1] + "/bank", // publication URI
                new BankServiceImplementation());
                System.out.println("service published");
    }
}
