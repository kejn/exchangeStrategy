package com.capgemini.exchange.wallet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.capgemini.exchange.share.Pair;
import com.capgemini.exchange.share.Share;

/**
 * A wallet to store shares.
 * 
 * @author KNIEMCZY
 */
public class ShareWallet {
	public static final Integer OVERALL_SHARE_AT_START = 0;

	/**
	 * Map of shares in the ShareWallet Key is the company name. Value is count
	 * of shares of that specific company
	 */
	private Map<String, Pair<Share, Integer>> shares;

	public ShareWallet() {
		shares = new HashMap<>();
	}

	public ShareWallet(ShareWallet other) {
		shares = new HashMap<>();
		other.shares.values().forEach(pair -> {
			shares.put(pair.first.getCompanyName(), pair);
		});
	}

	public Map<String, Pair<Share, Integer>> getShares() {
		return shares;
	}

	/**
	 * @return overall count of shares within the ShareWallet.
	 */
	public Integer getAllSharesCount() {
		Integer count = 0;
		for (Entry<String, Pair<Share, Integer>> entry : shares.entrySet()) {
			count += entry.getValue().second;
		}
		return count;
	}

	/**
	 * Adds new share in amount of <b>units</b> to {@link #shares} or increases
	 * one by <b>units</b> if it already existed in ShareWallet.
	 * 
	 * @param share
	 *            to be added to the {@link #shares}.
	 * @param units
	 *            of the share to be added.
	 */
	public void put(Share share, int units) {
		Pair<Share, Integer> alreadyHad = shares.get(share.getCompanyName());
		if (alreadyHad != null) {
			units += alreadyHad.second.intValue();
		}
		shares.put(share.getCompanyName(), new Pair<Share, Integer>(share, units));
	}

	/**
	 * Looks for a share in shares which company name equals to this in
	 * <b>share</b> parameter.
	 * 
	 * @param share
	 *            with defined at least {@link Share#companyName} field.
	 * @return fully described share from shares if found or <b>null</b>
	 *         otherwise.
	 */
	public Entry<String, Pair<Share, Integer>> get(Share share) {
		Entry<String, Pair<Share, Integer>> result = null;

		for (Entry<String, Pair<Share, Integer>> entry : shares.entrySet()) {
			if (entry.getKey().equals(share.getCompanyName())) {
				result = entry;
				break;
			}
		}
		return result;
	}

	/**
	 * Removes given units of share form shares.
	 * 
	 * @param share
	 *            to be removed (specified by {@link Share#companyName})
	 * @param units
	 *            of specified share to be removed.
	 */
	public void remove(Share share, int units) throws IllegalArgumentException {
		Pair<Share, Integer> alreadyHad = shares.get(share.getCompanyName());
		if (alreadyHad == null || alreadyHad.second.intValue() < units) {
			throw new IllegalArgumentException("No such share in shares or not enough units of given share in shares."
					+ alreadyHad==null?"":" Wanted to remove " + units + ", but had only " + alreadyHad.second);
		}
		units = alreadyHad.second.intValue() - units;
		if (units == 0) {
			shares.remove(share.getCompanyName());
		} else {
			shares.put(share.getCompanyName(), new Pair<Share, Integer>(share, units));
		}
	}

	/**
	 * @return number of companies in this ShareWallet
	 */
	public Integer getAllUniqueSharesCount() {
		return shares.size();
	}

}
