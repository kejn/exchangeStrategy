package com.capgemini.exchange.investor;

/**
 * A wallet to store money. 
 * @author KNIEMCZY
 */
public class MoneyWallet {
	public static final Double MONEY_AT_START = 10e3;

	private Double balance;
	
	public MoneyWallet() {
		balance = new Double(MONEY_AT_START);
	}

	public Double balance() {
		return balance;
	}

	/**
	 * Subtract given <b>amount</b> of money from the wallet balance to spend it on something. 
	 * @param amount of money to be spent 
	 */
	public void spend(Double amount) {
		balance -= amount;
	}
	
}
