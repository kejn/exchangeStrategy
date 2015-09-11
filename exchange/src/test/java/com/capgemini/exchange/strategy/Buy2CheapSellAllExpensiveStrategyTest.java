package com.capgemini.exchange.strategy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.investor.strategy.Buy2CheapSellAllExpensiveStrategy;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;

public class Buy2CheapSellAllExpensiveStrategyTest {

	private Stock stock;
	private Investor investor;
	private Investor spyInvestor;
	private final static Share[] sharePrices = { Share.parse("PKOBP,20130102,37.35"),
			Share.parse("KGHM,20130102,193.10"), Share.parse("PGNIG,20130102,5.26"), Share.parse("TPSA,20130102,12.16"),
			Share.parse("JSW,20130102,94.60") };

	@Before
	public void setUp() {
		// given
		stock = Stock.getInstance();
		investor = new Investor(new Buy2CheapSellAllExpensiveStrategy(), new BrokersOffice());
		spyInvestor = Mockito.spy(investor);
		stock.deleteObserver(investor);
		stock.addObserver(spyInvestor);
	}
	
	@After
	public void tearDown() {
		Stock.getInstance().clearShareWallet();
		Stock.getInstance().deleteObservers();
	}

	@Test
	public void testBuy2CheapSellAllExpensiveStrategyShouldBuy2CheapestShares() {
		// when
		stock.updatePrices(sharePrices); // here investor only buys
		// then
		assertFalse(spyInvestor.getShareWallet().getShares().isEmpty());
		Mockito.verify(spyInvestor, Mockito.times(1)).buy(Share.parse("PGNIG,20130102,5.26"), 1);
		Mockito.verify(spyInvestor, Mockito.times(1)).buy(Share.parse("TPSA,20130102,12.16"), 1);
	}

	@Test
	public void testBuy2CheapSellAllExpensiveStrategyShouldSellAllMostExpensiveShares() {
		// when
		stock.updatePrices(sharePrices); // here investor only buys
		stock.updatePrices(sharePrices); // here investor buys and sells
		// then
		Mockito.verify(spyInvestor, Mockito.times(2)).buy(Share.parse("PGNIG,20130102,5.26"), 1);
		Mockito.verify(spyInvestor, Mockito.times(2)).buy(Share.parse("TPSA,20130102,12.16"), 1);
		Mockito.verify(spyInvestor, Mockito.times(1)).sell(Share.parse("TPSA,20130102,12.16"), 1);
	}
}
