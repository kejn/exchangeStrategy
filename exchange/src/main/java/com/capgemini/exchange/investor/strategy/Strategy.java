package com.capgemini.exchange.investor.strategy;

import java.util.Map.Entry;

import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.wallet.ShareWallet;

public interface Strategy {
	public Entry<Share,Integer> chooseShareToSell(ShareWallet fromWallet);
	public Entry<Share,Integer> chooseShareToBuy(ShareWallet fromWallet);
}
