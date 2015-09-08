package com.capgemini.exchange.player;

/**
 * A wallet to store money in PLN. 
 * @author KNIEMCZY
 */
public class PLNWallet {
	public static final Double MONEY_AT_START = 10e3;

	private Double balance;
	
	public PLNWallet() {
		balance = new Double(MONEY_AT_START);
	}

	public Double balance() {
		return balance;
	}

	/**
	 * Substract given <b>amount</b> of money from the wallet balance and spend it on something. 
	 * @param amount of money to be spent 
	 */
	public void spend(Double amount) {
		balance -= amount;
	}
	
}
