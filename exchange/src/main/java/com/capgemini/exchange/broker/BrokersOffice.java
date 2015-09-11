package com.capgemini.exchange.broker;

import java.util.Observable;
import java.util.Observer;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.wallet.MoneyWallet;

/**
 * Broker's office which earns commision for buying or selling shares.
 * 
 * @author KNIEMCZY
 */
public class BrokersOffice implements Observer {

	/**
	 * Default commision for any transactions (in %).
	 */
	public static final Double COMMISION_RATE = new Double(0.005); // 0.5%

	/**
	 * Was not specified, but is obvious that when earning commision the money
	 * is stored somewhere.
	 */
	private MoneyWallet moneyWallet;

	public BrokersOffice() {
		moneyWallet = new MoneyWallet(0.0);
	}

	public BrokersOffice(Double initialAmountInWallet) {
		moneyWallet = new MoneyWallet(initialAmountInWallet);
	}

	public MoneyWallet getMoneyWallet() {
		return moneyWallet;
	}

	public void earn(Double amount) {
		moneyWallet.earn(amount);
	}

	@Override
	public void update(Observable investor, Object amount) {
		if (investor instanceof Investor && amount instanceof Double) {
			Investor inv = (Investor) investor;
			Double amountSpent = (Double) amount;
			Double commisionToEarn = inv.getMoneyWallet().spend(amountSpent * COMMISION_RATE);
			earn(commisionToEarn);
		}
	}

}
