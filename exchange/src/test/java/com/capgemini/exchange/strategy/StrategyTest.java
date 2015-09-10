package com.capgemini.exchange.strategy;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;

public class StrategyTest {

	private Stock spyStock;
	private final static Share[] sharePrices = { Share.parse("PKOBP,20130102,37.35"),
			Share.parse("KGHM,20130102,193.10"), Share.parse("PGNIG,20130102,5.26"), Share.parse("TPSA,20130102,12.16"),
			Share.parse("JSW,20130102,94.60") };

	@Before
	public void setUp() {
		spyStock = Mockito.spy(Stock.getInstance());
	}

	@Test
	public void testRandomStrategyShouldBuyShareInAnyAmount() {
		// given
		Investor investor = new Investor(new BrokersOffice());
		// when
		Mockito.doCallRealMethod().when(spyStock).updatePrices(Mockito.<Share> any());
		spyStock.updatePrices(sharePrices); // here investor only buys
		// then
		assertFalse(investor.getShareWallet().getShares().isEmpty());
	}

	@Test
	public void testRandomStrategyShouldBuyAndSellAnyShareInAnyAmount() {
		// given
		Investor investor = new Investor(new BrokersOffice());
		// when
		Mockito.doCallRealMethod().when(spyStock).updatePrices(Mockito.<Share> any());
		spyStock.updatePrices(sharePrices); // here investor only buys
		spyStock.updatePrices(sharePrices); // here investor buys and sells
		// then
		assertFalse(investor.getShareWallet().getShares().isEmpty());
	}

}
