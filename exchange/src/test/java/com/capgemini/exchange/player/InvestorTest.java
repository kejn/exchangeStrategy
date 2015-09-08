package com.capgemini.exchange.player;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class InvestorTest {
	private Investor investor;
	
	@Before
	public void setUp() {
		// given..
		investor = new Investor();
	}

	@Test
	public void testInvestorHas10kPLNAtStart() {
		// then
		assertEquals(PLNWallet.MONEY_AT_START, investor.getPLNWallet().balance());
	}

	@Test
	public void testInvestorHas0SharesAtStart() {
		// then
		assertEquals(ShareWallet.OVERALL_SHARE_AT_START, investor.getShareWallet().getAllSharesCount());
	}
	
	@Test
	public void testInvestorBought3Shares1For100and2for50PLN() {
		// ..given
		Share share1 = new Share(100.0);
		Share share2 = new Share(50.0);
		// when
		investor.buy(share1, 1);
		investor.buy(share2, 2);
		// then
		assertEquals(new Double(9800), investor.getPLNWallet().balance());
		assertEquals(new Integer(3), investor.getShareWallet().getAllSharesCount());
	}

	@Test
	public void testInvestorBought3SharesOf2DifferentCompanies() {
		// ..given
		Share share1 = new Share(100.0, "Company 1");
		Share share2 = new Share(50.0, "Company 2");
		// when
		investor.buy(share1, 1);
		investor.buy(share2, 2);
		// then
		assertEquals(new Double(9800), investor.getPLNWallet().balance());
		assertEquals(new Integer(3), investor.getShareWallet().getAllSharesCount());
		assertEquals(new Integer(2), investor.getShareWallet().getAllUniqueSharesCount());
	}

}
