package com.capgemini.exchange.investor.strategy;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.capgemini.exchange.share.Pair;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.wallet.ShareWallet;

public class RandomStrategy implements Strategy {

	private int entryIndex;
	
	private int randomIndex(int maxIndex) {
		return new Long(Math.round(Math.random() * maxIndex)).intValue();
	}

	private Entry<String, Pair<Share,Integer>> chooseRandomly(ShareWallet fromWallet) {
		Set<Entry<String, Pair<Share,Integer>>> set = fromWallet.getShares().entrySet();
		Iterator<Entry<String, Pair<Share,Integer>>> iter = set.iterator();
		entryIndex = randomIndex(set.size());

		Entry<String, Pair<Share,Integer>> entry = null;
		while(entryIndex >= 0 && iter.hasNext()) {
			entry = iter.next();
			--entryIndex;
		}
		return entry;
	}
	
	@Override
	public Entry<String, Pair<Share,Integer>> chooseShareToBuy(ShareWallet fromWallet) {
		return chooseRandomly(fromWallet);
	}
	

	@Override
	public Entry<String, Pair<Share,Integer>> chooseShareToSell(ShareWallet fromWallet) {
		return chooseRandomly(fromWallet);
	}

}
