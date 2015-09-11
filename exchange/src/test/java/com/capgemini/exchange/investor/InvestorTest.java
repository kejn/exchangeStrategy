package com.capgemini.exchange.investor;

import static org.junit.Assert.assertEquals;

import java.util.Observable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;
import com.capgemini.exchange.wallet.MoneyWallet;
import com.capgemini.exchange.wallet.ShareWallet;

public class InvestorTest {
	private Investor investor;
	private Investor spyInvestor;
	
	@Before
	public void setUp() {
		// given..
		investor = new Investor(new BrokersOffice());
		spyInvestor = Mockito.spy(investor);
		Stock.getInstance().deleteObserver(investor);
		Stock.getInstance().addObserver(spyInvestor);
	}
	
	@After
	public void tearDown() {
		Stock.getInstance().clearShareWallet();
		Stock.getInstance().deleteObservers();
	}

	@Test
	public void testInvestorHas10kPLNAtStart() {
		// then
		assertEquals(MoneyWallet.MONEY_AT_START, spyInvestor.getMoneyWallet().balance());
	}

	@Test
	public void testInvestorHas0SharesAtStart() {
		// then
		assertEquals(ShareWallet.OVERALL_SHARE_AT_START, spyInvestor.getShareWallet().getAllSharesCount());
	}
	
	@Test
	public void testInvestorBought3SharesFor100() {
		// ..given
		final Share share = new Share(100.0);
		final Integer unitsToBuy = new Integer(3);
		final Double buyingCost = share.getUnitPrice() * unitsToBuy;
		final Double commision = buyingCost * BrokersOffice.COMMISION_RATE;
		final Double expectedBalance = MoneyWallet.MONEY_AT_START - buyingCost - commision;
		// when
		Mockito.doNothing().when(spyInvestor).update(Mockito.<Observable>any(), Mockito.anyObject());
		Stock.getInstance().updatePrices(share);
		spyInvestor.buy(share, unitsToBuy);
		// then
		Mockito.verify(spyInvestor, Mockito.atLeastOnce()).update(Mockito.<Observable>any(), Mockito.anyObject());
		assertEquals(expectedBalance, spyInvestor.getMoneyWallet().balance());
		assertEquals(new Integer(3), spyInvestor.getShareWallet().getAllSharesCount());
	}

	@Test
	public void testInvestorBought3SharesOf2DifferentCompanies() {
		// ..given
		final Share share1 = new Share(100.0, "Company 1");
		final Share share2 = new Share(50.0, "Company 2");
		final Integer unitsToBuy1 = new Integer(1);
		final Integer unitsToBuy2 = new Integer(2);
		final Double buyingCost = share1.getUnitPrice() * unitsToBuy1 + share2.getUnitPrice() * unitsToBuy2;
		final Double commision = buyingCost * BrokersOffice.COMMISION_RATE;
		final Double expectedBalance = MoneyWallet.MONEY_AT_START - buyingCost - commision;
		// when
		Mockito.doNothing().when(spyInvestor).update(Mockito.<Observable>any(), Mockito.anyObject());
		Stock.getInstance().updatePrices(share1, share2);
		spyInvestor.buy(share1, unitsToBuy1);
		spyInvestor.buy(share2, unitsToBuy2);
		// then
		Mockito.verify(spyInvestor, Mockito.atLeastOnce()).update(Mockito.<Observable>any(), Mockito.anyObject());
		assertEquals(expectedBalance, spyInvestor.getMoneyWallet().balance());
		assertEquals(new Integer(3), spyInvestor.getShareWallet().getAllSharesCount());
		assertEquals(new Integer(2), spyInvestor.getShareWallet().getAllUniqueSharesCount());
	}
	
}
