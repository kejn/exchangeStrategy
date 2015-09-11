package com.capgemini.exchange;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.investor.strategy.Buy2CheapSellAllExpensiveStrategy;
import com.capgemini.exchange.investor.strategy.RandomStrategy;
import com.capgemini.exchange.investor.strategy.Strategy;
import com.capgemini.exchange.stock.Stock;

public class ExampleScenario {

	public static void main(String[] args) {
		Investor investor = new Investor(new BrokersOffice());
		Strategy strategy1 = new Buy2CheapSellAllExpensiveStrategy();
		Strategy strategy2 = new RandomStrategy(); // Default
		int numberOfUpdates = 0;
		try {
			while(true) {
				Stock.getInstance().updatePricesFromFile();
				if(Math.random() < Math.random()) { // fate will choose :)
					investor.setStrategy(strategy1);
				} else {
					investor.setStrategy(strategy2);
				}
				++numberOfUpdates;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " (after " + numberOfUpdates + " updates)");
			investor.sellAll();
			System.out.println("Investor's money wallet balance: " + investor.getMoneyWallet().balance());
		}
	}

}
