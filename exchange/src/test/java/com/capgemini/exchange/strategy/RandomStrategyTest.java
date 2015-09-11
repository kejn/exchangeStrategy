package com.capgemini.exchange.strategy;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;

public class RandomStrategyTest {

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
		investor = new Investor(new BrokersOffice());
		spyInvestor = Mockito.spy(investor);
		stock.deleteObserver(investor);
		stock.addObserver(spyInvestor);
	}

	@Test
	public void testRandomStrategyShouldBuyShareInAnyAmount() {
		// when
		stock.updatePrices(sharePrices); // here investor only buys
		// then
		assertFalse(spyInvestor.getShareWallet().getShares().isEmpty());
		Mockito.verify(spyInvestor, Mockito.atLeastOnce()).buy(Mockito.any(Share.class), Mockito.anyInt());
	}

	@Test
	public void testRandomStrategyShouldBuyAndSellAnyShareInAnyAmount() {
		// when
		stock.updatePrices(sharePrices); // here investor only buys
		stock.updatePrices(sharePrices); // here investor buys and sells
		// then
		Mockito.verify(spyInvestor, Mockito.atLeastOnce()).buy(Mockito.any(Share.class), Mockito.anyInt());
		Mockito.verify(spyInvestor, Mockito.atLeastOnce()).sell(Mockito.any(Share.class), Mockito.anyInt());
	}

}
