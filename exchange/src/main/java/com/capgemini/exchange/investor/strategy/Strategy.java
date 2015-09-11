package com.capgemini.exchange.investor.strategy;

import com.capgemini.exchange.wallet.ShareWallet;

public interface Strategy {
	public ShareWallet chooseShareToSell(ShareWallet fromWallet);
	public ShareWallet chooseShareToBuy(ShareWallet fromWallet);
}
