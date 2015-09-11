package com.capgemini.exchange.investor.strategy;

import java.util.Iterator;
import java.util.Map.Entry;

import com.capgemini.exchange.share.Pair;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.wallet.ShareWallet;

public class RandomStrategy implements Strategy {
	/**
	 * @param maxIndex
	 * @return random int from 0 (including) to <b>maxIndex</b> (excluding)
	 */
	private int randomIndex(int maxIndex) {
		return new Double(Math.random() * maxIndex).intValue();
	}

	private ShareWallet chooseRandomly(ShareWallet fromWallet) {
		ShareWallet result = new ShareWallet(fromWallet);
		int numberOfEntriesToReturn = randomIndex(result.getShares().size() - 1) + 1;
		System.out.print(numberOfEntriesToReturn + " <");

		while (result.getShares().size() > numberOfEntriesToReturn) {
			int indexToRemove = randomIndex(result.getShares().size());
			Iterator<Entry<String, Pair<Share, Integer>>> iter = result.getShares().entrySet().iterator();
			Entry<String, Pair<Share, Integer>> entry = null;
			System.out.print(indexToRemove + ",");
			while (indexToRemove >= 0 && iter.hasNext()) {
				entry = iter.next();
				--indexToRemove;
			}
			result.remove(entry.getValue().first, 1);

		}
		System.out.println(">");
		return result;
	}

	@Override
	public ShareWallet chooseShareToBuy(ShareWallet fromWallet) {
		return chooseRandomly(fromWallet);
	}

	@Override
	public ShareWallet chooseShareToSell(ShareWallet fromWallet) {
		return chooseRandomly(fromWallet);
	}

}
