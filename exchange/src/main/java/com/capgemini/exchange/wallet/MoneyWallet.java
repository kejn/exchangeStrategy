package com.capgemini.exchange.wallet;

import java.util.Locale;

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

	public MoneyWallet(Double balance) {
		this.balance = balance;
	}

	public Double balance() {
		return Double.parseDouble(String.format(Locale.US, "%.2f", balance));
	}

	/**
	 * Subtract given <b>amount</b> of money from the wallet {@link #balance}. 
	 * @param amount of money to be spent 
	 */
	public Double spend(Double amount) {
		balance -= amount;
		return amount;
	}

	/**
	 * Add given <b>amount</b> of money to the wallet {@link #balance}. 
	 * @param amount of money to be spent 
	 */
	public Double earn(Double amount) {
		balance += amount;
		return amount;
	}
	
}
