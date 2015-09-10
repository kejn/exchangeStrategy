package com.capgemini.exchange.stock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.Observable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.wallet.ShareWallet;

public class StockTest {
	
	private Stock spyStock;
	private final static Share[] sharePrices = { Share.parse("PKOBP,20130102,37.35"),
			Share.parse("KGHM,20130102,193.10"), Share.parse("PGNIG,20130102,5.26"), Share.parse("TPSA,20130102,12.16"),
			Share.parse("JSW,20130102,94.60") };
	private final Answer<Void> answer = new Answer<Void>() {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			spyStock.updatePrices(sharePrices);
			return null;
		}
	};

	@Before
	public void setUp() {
		spyStock = Mockito.spy(Stock.getInstance());
		Mockito.doAnswer(answer).when(spyStock).updatePricesFromFile();
		Mockito.doAnswer(answer).when(spyStock).updatePrices(Mockito.<Share>anyObject());
	}
	
	@Test
	public void testStockLoadedShareDailyPrizes() {
		// given
		ShareWallet prices = spyStock.getCurrentPrices();
		// when
		spyStock.updatePrices(new Share(1.0));
		ShareWallet result = spyStock.getCurrentPrices();
		// then
		assertFalse(result.getShares().isEmpty());
		assertNotEquals(prices.getShares(), result.getShares());
	}

	@Test
	public void testStockLoadedShareDailyPrizesFromFile() {
		// given
		ShareWallet prices = spyStock.getCurrentPrices();
		// when
		spyStock.updatePricesFromFile();
		ShareWallet result = spyStock.getCurrentPrices();
		// then
		assertFalse(result.getShares().isEmpty());
		assertNotEquals(prices, result);
	}
	
	@Test
	public void testInvestorWasNotifiedAboutPriceUpdate() {
		// given
		Investor investor = Mockito.mock(Investor.class);
		// when
		spyStock.addObserver(investor);
		spyStock.updatePrices(new Share(1.0));
		// then
		Mockito.verify(investor).update(Mockito.<Observable>any(), Mockito.anyObject());
	}

	@Test
	public void testInvestorWasNotifiedAboutPriceUpdateFromFile() {
		// given
		Investor investor = Mockito.mock(Investor.class);
		// when
		spyStock.addObserver(investor);
		spyStock.updatePricesFromFile();
		// then
		Mockito.verify(investor).update(Mockito.<Observable>any(), Mockito.anyObject());
	}
	
	

}
