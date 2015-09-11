package com.capgemini.exchange.broker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;
import com.capgemini.exchange.wallet.MoneyWallet;

public class BrokersOfficeTest {
	
	private final Share share = Share.parse("TPSA,20130102,12.16");

	@Before
	public void setUp() {
		Stock.getInstance().clearShareWallet();
		Stock.getInstance().updatePrices(share);
		try {
			Stock.getInstance().updatePricesFromFile();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBrokersOfficeEarnCommision() {
		// given
		BrokersOffice office = new BrokersOffice();
		Investor investor = new Investor(office);
		final MoneyWallet moneyWallet = office.getMoneyWallet();
		final int unitsToBuy = 100;
		final Double unitPrice = share.getUnitPrice();
		final Double expectedBalance = moneyWallet.balance() + BrokersOffice.COMMISION_RATE * unitPrice * unitsToBuy; 
		// when
		assertNotNull(share);
		investor.buy(share, unitsToBuy);
		// then
		assertEquals(expectedBalance, moneyWallet.balance());
	}

}
