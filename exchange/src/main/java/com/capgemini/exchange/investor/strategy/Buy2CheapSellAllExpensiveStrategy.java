package com.capgemini.exchange.investor.strategy;

import java.util.Comparator;

import com.capgemini.exchange.share.Pair;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.wallet.ShareWallet;

public class Buy2CheapSellAllExpensiveStrategy implements Strategy {

	private Comparator<Pair<Share,Integer>> cmp = new Comparator<Pair<Share,Integer>>() {
		@Override
		public int compare(Pair<Share, Integer> o1, Pair<Share, Integer> o2) {
			return Math.toIntExact(Math.round((o1.first.getUnitPrice() - o2.first.getUnitPrice())*100));
		}
	};

	@Override
	public ShareWallet chooseShareToSell(ShareWallet fromWallet) {
		if (fromWallet.getShares().isEmpty()) {
			return new ShareWallet();
		}
		return mostExpensiveShares(fromWallet);
	}

	private ShareWallet mostExpensiveShares(ShareWallet fromWallet) {
		ShareWallet shareWallet = new ShareWallet();
		Pair<Share,Integer> pair = fromWallet.getShares().values().stream().max(cmp).get(); 
		shareWallet.put(pair.first, pair.second);
		return shareWallet;
	}

	@Override
	public ShareWallet chooseShareToBuy(ShareWallet fromWallet) {
		return cheapestTwoShares(fromWallet);
	}

	private ShareWallet cheapestTwoShares(ShareWallet fromWallet) {
		ShareWallet shareWallet = new ShareWallet();
		ShareWallet from = new ShareWallet(fromWallet);
		for (int i = 0; i < 2; ++i) {
			Pair<Share,Integer> pair = from.getShares().values().stream().min(cmp).get();
			shareWallet.put(pair.first, 1);
			from.remove(pair.first, pair.second);
		}
		return shareWallet;
	}

}
