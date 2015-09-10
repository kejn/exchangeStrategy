package com.capgemini.exchange.wallet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	private Map<Share, Integer> shares;

	public ShareWallet() {
		shares = new HashMap<>();
	}

	public ShareWallet(ShareWallet other) {
		shares = new HashMap<>(other.shares);
	}

	public Map<Share, Integer> getShares() {
		return shares;
	}

	/**
	 * @return overall count of shares within the ShareWallet.
	 */
	public Integer getAllSharesCount() {
		Integer count = 0;
		for (Entry<Share, Integer> entry : shares.entrySet()) {
			count += entry.getValue();
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
		Integer alreadyHad = shares.get(share);
		if (alreadyHad != null) {
			units += alreadyHad.intValue();
		}
		shares.put(share, units);
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
	public Entry<Share, Integer> get(Share share) {
		Entry<Share, Integer> result = null;

		for (Entry<Share, Integer> entry : shares.entrySet()) {
			if (entry.getKey().equals(share)) {
				result = entry;
				break;
			}
		}
		return result;
	}

	/**
	 * Removes given units of share form shares.
	 * 
	 * @param share to be removed (specified by {@link Share#companyName})
	 * @param units of specified share to be removed.
	 */
	public void remove(Share share, int units) throws IllegalArgumentException {
		Integer alreadyHad = shares.get(share);
		if (alreadyHad == null || alreadyHad.intValue() < units) {
			throw new IllegalArgumentException("No such share in shares or not enough units of given share in shares.");
		}
		units = alreadyHad.intValue() - units;
		if (units == 0) {
			shares.remove(share);
		} else {
			shares.put(share, units);
		}
	}

	/**
	 * @return number of companies in this ShareWallet
	 */
	public Integer getAllUniqueSharesCount() {
		return shares.size();
	}

}
