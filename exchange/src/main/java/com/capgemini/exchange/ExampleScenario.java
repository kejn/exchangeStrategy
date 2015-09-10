package com.capgemini.exchange;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.stock.Stock;

public class ExampleScenario {

	public static void main(String[] args) {
		Investor investor = new Investor(new BrokersOffice());
		int numberOfUpdates = 0;
		try {
			while(true) {
				Stock.getInstance().updatePricesFromFile();
				++numberOfUpdates;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " (after " + numberOfUpdates + " updates)");
			System.out.println("Investor's money wallet balance: " + investor.getMoneyWallet().balance());
		}
	}

}
