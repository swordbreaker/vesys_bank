/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.local;

import bank.common.ServerBank;

public class Driver implements bank.BankDriver {
	private bank.Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new ServerBank();
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
}