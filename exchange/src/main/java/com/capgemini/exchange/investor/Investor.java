package com.capgemini.exchange.investor;

import java.util.Observable;
import java.util.Observer;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;

/**
 * Player who invests in shares.
 * 
 * @author KNIEMCZY
 */
public class Investor extends Observable implements Observer {

	private MoneyWallet moneyWallet;
	private ShareWallet shareWallet;
	private BrokersOffice office;

	public Investor() {
		moneyWallet = new MoneyWallet();
		shareWallet = new ShareWallet();
		Stock.getInstance().addObserver(this);
		office = null;
	}

	public Investor(BrokersOffice office) {
		moneyWallet = new MoneyWallet();
		shareWallet = new ShareWallet();
		Stock.getInstance().addObserver(this);
		this.office = office;
		addObserver(office);
	}

	public MoneyWallet getPLNWallet() {
		return moneyWallet;
	}

	public ShareWallet getShareWallet() {
		return shareWallet;
	}

	public BrokersOffice getOffice() {
		return office;
	}

	public void setOffice(BrokersOffice office) {
		this.office = office;
	}

	public void buy(Share share, int units) throws NullPointerException, IllegalArgumentException {
		if(office == null) {
			throw new NullPointerException("Office field has not been assigned!");
		}
		if(Stock.getInstance().getCurrentPrices().get(share.getCompanyName()) == null) {
			throw new IllegalArgumentException("Stock has not announced such share.");
		}
		Double amount = share.getUnitPrice() * units;
		changeStateAndNotify(amount);
		shareWallet.put(share, units);
	}
	
	/**
	 * Notifies observers that shares were bought.
	 */
	private void changeStateAndNotify(Double amount) {
		setChanged();
		notifyObservers(moneyWallet.spend(amount));
	}

	@Override
	public void update(Observable observable, Object notifyArgument) {
		// TODO: choose which Share to buy next | STRATEGY dependent
	}

}
