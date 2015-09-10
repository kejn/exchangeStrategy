package com.capgemini.exchange.investor;

import static org.junit.Assert.assertEquals;

import java.util.Observable;

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

	@Test
	public void testInvestorHas10kPLNAtStart() {
		// then
		assertEquals(MoneyWallet.MONEY_AT_START, investor.getMoneyWallet().balance());
	}

	@Test
	public void testInvestorHas0SharesAtStart() {
		// then
		assertEquals(ShareWallet.OVERALL_SHARE_AT_START, investor.getShareWallet().getAllSharesCount());
	}
	
	@Test
	public void testInvestorBought3Shares1For100and2for50() {
		// ..given
		Share share1 = new Share(100.0);
		Share share2 = new Share(50.0);
		// when
		Mockito.doNothing().when(spyInvestor).update(Mockito.<Observable>any(), Mockito.anyObject());
		Stock.getInstance().updatePrices(share1, share2);
		investor.buy(share1, 1);
		investor.buy(share2, 2);
		// then
		assertEquals(new Double(9800), investor.getMoneyWallet().balance());
		assertEquals(new Integer(3), investor.getShareWallet().getAllSharesCount());
	}

	@Test
	public void testInvestorBought3SharesOf2DifferentCompanies() {
		// ..given
		Share share1 = new Share(100.0, "Company 1");
		Share share2 = new Share(50.0, "Company 2");
		// when
		Mockito.doNothing().when(spyInvestor).update(Mockito.<Observable>any(), Mockito.anyObject());
		Stock.getInstance().updatePrices(share1, share2);
		investor.buy(share1, 1);
		investor.buy(share2, 2);
		// then
		assertEquals(new Double(9800), investor.getMoneyWallet().balance());
		assertEquals(new Integer(3), investor.getShareWallet().getAllSharesCount());
		assertEquals(new Integer(2), investor.getShareWallet().getAllUniqueSharesCount());
	}
	
}
