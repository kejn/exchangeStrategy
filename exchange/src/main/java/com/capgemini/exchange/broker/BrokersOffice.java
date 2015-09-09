package com.capgemini.exchange.broker;

import java.util.Observable;
import java.util.Observer;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.investor.MoneyWallet;

public class BrokersOffice implements Observer {

	public static final Double COMMISION = new Double(0.005); // 0.5%

	private MoneyWallet moneyWallet;

	public BrokersOffice() {
		moneyWallet = new MoneyWallet();
	}

	public BrokersOffice(Double initialAmountInWallet) {
		moneyWallet = new MoneyWallet(initialAmountInWallet);
	}

	public MoneyWallet getMoneyWallet() {
		return moneyWallet;
	}

	public void earn(Double amount) {
		moneyWallet.earn(amount*COMMISION);
	}
	
	@Override
	public void update(Observable investor, Object amount) {
		if(investor instanceof Investor && amount instanceof Double) {
			earn((Double)amount);
		}
	}

}
