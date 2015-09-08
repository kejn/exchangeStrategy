package com.capgemini.exchange.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A wallet to store shares.
 * @author KNIEMCZY
 */
public class ShareWallet {
	public static final Integer OVERALL_SHARE_AT_START = 0;

	/**
	 * Map of shares in the ShareWallet Key is the company name. Value is count
	 * of shares of that specific company
	 */
	private Map<String, Integer> shares;

	public ShareWallet() {
		shares = new HashMap<>();
	}

	/**
	 * @return overall count of shares within the ShareWallet.
	 */
	public Integer getAllSharesCount() {
		Integer count = 0;
		for (Entry<String, Integer> entry : shares.entrySet()) {
			count += entry.getValue();
		}
		return count;
	}

	/**
	 * Adds new share in amount of <b>units</b> to {@link #shares} or increments
	 * units of a share if it already existed in ShareWallet.
	 * 
	 * @param share
	 *            to be added to the {@link #shares}.
	 * @param units
	 *            of the share to be added.
	 */
	public void put(Share share, int units) {
		Integer alreadyHad = shares.get(share.getCompanyName());
		if (alreadyHad != null) {
			units += alreadyHad.intValue();
		}
		shares.put(share.getCompanyName(), units);
	}

	/**
	 * @return number of companies in this ShareWallet
	 */
	public Integer getAllUniqueSharesCount() {
		return shares.size();
	}
}
