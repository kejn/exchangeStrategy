package com.capgemini.exchange.investor;

import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import com.capgemini.exchange.broker.BrokersOffice;
import com.capgemini.exchange.investor.strategy.RandomStrategy;
import com.capgemini.exchange.investor.strategy.Strategy;
import com.capgemini.exchange.share.Pair;
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

	public Investor(Strategy strategy, BrokersOffice office) {
		this.moneyWallet = new MoneyWallet();
		this.shareWallet = new ShareWallet();
		this.strategy = strategy;
		this.office = office;
		this.addObserver(this.office);
		Stock.getInstance().addObserver(this);
	}

	public MoneyWallet getMoneyWallet() {
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
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public Strategy getStrategy() {
		return strategy;
	}

	public void buy(Share share, int units) throws NullPointerException, IllegalArgumentException {
		if (office == null) {
			throw new NullPointerException("Office field has not been assigned!");
		}
		if (!Stock.getInstance().getCurrentPrices().getShares().containsKey(share.getCompanyName())) {
			throw new IllegalArgumentException("Stock has not announced such share: " + share.toString());
		}
		Double amount = share.getUnitPrice() * units;
		changeStateAndNotify(amount, true);
		shareWallet.put(share, units);
		System.out.println("- " + amount + " (" + share.toString() + " ; " + units + ")");
	}

	public void sell(Share share, int units) {
		if (office == null) {
			throw new NullPointerException("Office field has not been assigned!");
		}
		if (!shareWallet.getShares().containsKey(share.getCompanyName())) {
			throw new IllegalArgumentException("Investor doesn't have such share in the shareWallet.");
		}
		Double amount = share.getUnitPrice() * units;
		changeStateAndNotify(amount, false);
		shareWallet.remove(share, units);
		System.out.println("+ " + amount + " (" + share.toString() + " ; " + units + ")");
	}

	/**
	 * Notifies observers that shares were bought or sold.
	 * 
	 * @param amount
	 *            money to earn/spend.
	 * @param buying
	 *            if is <code>true</code> money is spent, otherwise money is
	 *            earned
	 */
	private void changeStateAndNotify(Double amount, boolean buying) {
		setChanged();
		if (buying) {
			notifyObservers(moneyWallet.spend(amount));
		} else {
			notifyObservers(moneyWallet.earn(amount));
		}
	}

	@Override
	public void update(Observable stock, Object prices) {
		if (stock instanceof Stock && prices instanceof ShareWallet) {
			updateShareWallet((ShareWallet) prices);
			ShareWallet toBuy = strategy.chooseShareToBuy((ShareWallet) prices);
			ShareWallet toSell = strategy.chooseShareToSell(shareWallet);

			if (!toBuy.getShares().isEmpty()) {
				toBuy.getShares().entrySet().forEach(entry -> {
					Share share = entry.getValue().first;
					Integer units = entry.getValue().second;
					if(haveEnoughMoney(share.getUnitPrice(), units)){
						buy(share, units);
					}
				});
			}
			if (!toSell.getShares().isEmpty()) {
				toSell.getShares().entrySet().forEach(entry -> {
					Share share = entry.getValue().first;
					Integer units = entry.getValue().second;
					sell(share, Math.min(units, this.shareWallet.get(share).getValue().second));
				});
			}
			System.out.println("\n\n\n");
		}
	}

	private void updateShareWallet(ShareWallet prices) {
		for (Entry<String, Pair<Share, Integer>> newEntry : prices.getShares().entrySet()) {
			Share newShare = newEntry.getValue().first;
			for (Entry<String, Pair<Share, Integer>> oldEntry : shareWallet.getShares().entrySet()) {
				Share oldShare = oldEntry.getValue().first;
				if (oldShare.equals(newShare)) {
					shareWallet.put(newShare, 0);
				}
			}

		}
	}

	private boolean haveEnoughMoney(Double shareUnitPrice, Integer units) {
		return moneyWallet.balance() >= shareUnitPrice * units;
	}

	public void sellAll() {
		ShareWallet shareWallet = new ShareWallet(this.shareWallet);
		shareWallet.getShares().entrySet().forEach(entry -> {
			Share share = entry.getValue().first;
			Integer units = entry.getValue().second;
			sell(share, units);
		});
	}

}
