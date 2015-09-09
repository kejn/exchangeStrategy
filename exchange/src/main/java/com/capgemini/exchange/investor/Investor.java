package com.capgemini.exchange.investor;

import java.util.Observable;
import java.util.Observer;

import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;

/**
 * Player who invests in shares.
 * 
 * @author KNIEMCZY
 */
public class Investor implements Observer {

	private MoneyWallet plnWallet;
	private ShareWallet shareWallet;

	public Investor() {
		plnWallet = new MoneyWallet();
		shareWallet = new ShareWallet();
		Stock.getInstance().addObserver(this);
	}

	public MoneyWallet getPLNWallet() {
		return plnWallet;
	}

	public ShareWallet getShareWallet() {
		return shareWallet;
	}

	public void buy(Share share, int units) {
		plnWallet.spend(share.getUnitPrice() * units);
		shareWallet.put(share, units);
	}

	@Override
	public void update(Observable observable, Object notifyArgument) {
		
	}

}
