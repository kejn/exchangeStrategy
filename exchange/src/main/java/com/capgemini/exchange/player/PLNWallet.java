package com.capgemini.exchange.player;

public class PLNWallet {
	public static final double MONEY_AT_START = 10e3;

	private Double value;
	
	public PLNWallet() {
		value = new Double(MONEY_AT_START);
	}

	public Double value() {
		return value;
	}
	
}
