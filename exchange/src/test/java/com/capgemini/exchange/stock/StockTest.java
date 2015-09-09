package com.capgemini.exchange.stock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.Observable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.share.ShareMap;

public class StockTest {
	
	private Stock stock = Stock.getInstance();

	@Before
	public void setUp() {
		stock.reset();
	}
	
	@Test
	public void testStockLoadedShareDailyPrizes() {
		// given
		ShareMap prices = stock.getCurrentPrices();
		// when
		stock.updatePrices(new Share(1.0));
		ShareMap result = stock.getCurrentPrices();
		// then
		assertFalse(result.isEmpty());
		assertNotEquals(prices, result);
		System.out.println(result);
	}

	@Test
	public void testStockLoadedShareDailyPrizesFromDefaultFile() {
		// given
		ShareMap prices = stock.getCurrentPrices();
		// when
		stock.updatePricesFromFile();
		ShareMap result = stock.getCurrentPrices();
		// then
		assertFalse(result.isEmpty());
		assertNotEquals(prices, result);
		System.out.println(result);
	}
	
	@Test
	public void testInvestorWasNotifiedAboutPriceUpdate() {
		// given
		Investor investor = Mockito.mock(Investor.class);
		// when
		stock.addObserver(investor);
		stock.updatePrices(new Share(1.0));
		ShareMap result = stock.getCurrentPrices();
		// then
		Mockito.verify(investor).update(Mockito.<Observable>any(), Mockito.anyObject());
		System.out.println(result);
	}

	@Test
	public void testInvestorWasNotifiedAboutPriceUpdateFromDefaultFile() {
		// given
		Investor investor = Mockito.mock(Investor.class);
		// when
		stock.addObserver(investor);
		stock.updatePricesFromFile();
		ShareMap result = stock.getCurrentPrices();
		// then
		Mockito.verify(investor).update(Mockito.<Observable>any(), Mockito.anyObject());
		System.out.println(result);
	}
	
	

}
