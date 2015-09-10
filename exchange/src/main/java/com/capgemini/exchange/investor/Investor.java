package com.capgemini.exchange.investor;

import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.strategy.RandomStrategy;
import com.capgemini.exchange.investor.strategy.Strategy;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.stock.Stock;
import com.capgemini.exchange.wallet.MoneyWallet;
import com.capgemini.exchange.wallet.ShareWallet;

/**
 * Player who invests in shares.
 * 
 * @author KNIEMCZY
 */
public class Investor extends Observable implements Observer {

	private MoneyWallet moneyWallet;
	private ShareWallet shareWallet;
	private BrokersOffice office;
	private Strategy strategy;

	/**
	 * By default investor uses RandomStrategy
	 */
	public Investor(BrokersOffice office) {
		this.moneyWallet = new MoneyWallet();
		this.shareWallet = new ShareWallet();
		this.strategy = new RandomStrategy();
		this.office = office;
		this.addObserver(this.office);
		Stock.getInstance().addObserver(this);
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
		this.deleteObserver(this.office);
		this.office = office;
		this.addObserver(this.office);
	}

	public void buy(Share share, int units) throws NullPointerException, IllegalArgumentException {
		if (office == null) {
			throw new NullPointerException("Office field has not been assigned!");
		}
		if (Stock.getInstance().getCurrentPrices().get(share) == null) {
			throw new IllegalArgumentException("Stock has not announced such share: " + share.toString());
		}
		Double amount = share.getUnitPrice() * units;
		changeStateAndNotify(amount, true);
		shareWallet.put(share, units);
	}

	public void sell(Share share, int units) {
		if (office == null) {
			throw new NullPointerException("Office field has not been assigned!");
		}
		if (shareWallet.get(share) == null) {
			throw new IllegalArgumentException("Investor doesn't have such share in the shareWallet.");
		}
		Double amount = share.getUnitPrice() * units;
		changeStateAndNotify(amount, false);
		shareWallet.remove(share, units);
	}

	/**
	 * Notifies observers that shares were bought or sold.
	 * @param amount money to earn/spend.
	 * @param buying if is <code>true</code> money is spent, otherwise money is earned
	 */
	private void changeStateAndNotify(Double amount, boolean buying) {
		setChanged();
		if(buying) {
			notifyObservers(moneyWallet.spend(amount));
		} else {
			notifyObservers(moneyWallet.earn(amount));
		}
	}

	@Override
	public void update(Observable stock, Object prices) {
		if(stock instanceof Stock && prices instanceof ShareWallet) {
			Entry<Share, Integer> toBuy = strategy.chooseShareToBuy((ShareWallet)prices);
			Entry<Share, Integer> toSell = strategy.chooseShareToSell(shareWallet);

			buy(toBuy.getKey(), toBuy.getValue());
			if(toSell != null) {
				sell(toSell.getKey(), toSell.getValue());
			}
		}
	}

}
