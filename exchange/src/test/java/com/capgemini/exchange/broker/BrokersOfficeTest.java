package com.capgemini.exchange.broker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;
import com.capgemini.exchange.wallet.MoneyWallet;

public class BrokersOfficeTest {

	@Before
	public void setUp() {
		Stock.getInstance().clearShareWallet();
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
		final Share share = Stock.chooseShare("TPSA");
		final int units = 100;
		// when
		assertNotNull(share);
		investor.buy(share, units);
		// then
		final Double expected = new Double(BrokersOffice.COMMISION_RATE * share.getUnitPrice() * units); 
		assertEquals(expected, moneyWallet.balance());
	}

}
