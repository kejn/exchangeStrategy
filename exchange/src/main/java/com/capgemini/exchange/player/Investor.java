package com.capgemini.exchange.player;

/**
 * Player who invests in shares.
 * 
 * @author KNIEMCZY
 */
public class Investor {

	private PLNWallet plnWallet;
	private ShareWallet shareWallet;

	public Investor() {
		plnWallet = new PLNWallet();
		shareWallet = new ShareWallet();
	}

	public PLNWallet getPLNWallet() {
		return plnWallet;
	}

	public ShareWallet getShareWallet() {
		return shareWallet;
	}

	public void buy(Share share, int units) {
		plnWallet.spend(share.getUnitPrize() * units);
		shareWallet.put(share, units);
	}

}
