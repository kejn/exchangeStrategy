package com.capgemini.exchange.broker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.exchange.investor.Investor;
import com.capgemini.exchange.investor.MoneyWallet;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;

public class BrokersOfficeTest {

	@Before
	public void setUp() {
		Stock.getInstance().reset();
		Stock.getInstance().updatePricesFromFile();
	}

	@Test
	public void testBrokersOfficeEarnCommision() {
		// given
		BrokersOffice office = new BrokersOffice(0.0);
		Investor investor = new Investor(office);
		final MoneyWallet moneyWallet = office.getMoneyWallet();
		final Share share = Stock.chooseShare("TPSA");
		final int units = 100;
		final Double expected = new Double(BrokersOffice.COMMISION * share.getUnitPrice() * units); 
		// when
		investor.buy(share, units);
		// then
		assertEquals(expected, moneyWallet.balance());
	}

}
