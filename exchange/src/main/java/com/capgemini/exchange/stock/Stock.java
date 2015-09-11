package com.capgemini.exchange.stock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;

import com.capgemini.exchange.share.Pair;
import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.wallet.ShareWallet;

/**
 * Single instance of stock exchange which notifies Investors about share price
 * changes.
 * 
 * @author KNIEMCZY
 */
public class Stock extends Observable {

	private static final String INPUT_FILE_PATH = "src/main/resources/com/capgemini/exchange/data/dane.csv";

	/**
	 * To implement Stock as singleton.
	 */
	private static Stock instance;

	/**
	 * Shares' prices announced recently by Stock
	 */
	private ShareWallet prices;

	/**
	 * List of prices loaded from file.
	 */
	private List<Share> pricesFromFile = null;

	private Stock() {
		prices = new ShareWallet();
		loadPricesFromFile();
	}

	/**
	 * Load all share prices from file {@value #INPUT_FILE_PATH} into
	 * {@link #pricesFromFile} list and sorts it by {@link Share#date} field.
	 */
	private void loadPricesFromFile() {
		if(pricesFromFile != null) {
			System.err.println("Prices have already been loaded");
			return;
		}
		pricesFromFile = new ArrayList<>();
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(new File(INPUT_FILE_PATH)));
			String line;
			while ((line = fileReader.readLine()) != null) {
				Share share = Share.parse(line);
				pricesFromFile.add(share);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		pricesFromFile.sort(new Comparator<Share>() {
			@Override
			public int compare(Share o1, Share o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
	}

	public static Stock getInstance() {
		if (instance == null) {
			instance = new Stock();
		}
		return instance;
	}

	public ShareWallet getCurrentPrices() {
		return new ShareWallet(prices);
	}

	public void updatePrices(Share... shares) {
		ShareWallet oldPrices = new ShareWallet(prices);
		clearShareWallet();
		for (Share share : shares) {
			prices.put(share, 1);
		}
		retrieveNotUpdatedPrices(oldPrices);
		changeStateAndNotify();
	}

	/**
	 * Used by Investor to choose which Share to buy.
	 */
	public static Share chooseShare(String companyName) throws ExceptionInInitializerError {
		if (getInstance().getCurrentPrices().getShares().isEmpty()) {
			throw new ExceptionInInitializerError("Stock haven't announced any prices yet!");
		}
		return getInstance().prices.getShares().get(companyName).first;
	}

	/**
	 * Notifies observers that prices were updated.
	 */
	public void changeStateAndNotify() {
		setChanged();
		notifyObservers(getCurrentPrices());
	}

	/**
	 * IMPORTANT: sort {@link #pricesFromFile} before any invocation of this
	 * method!
	 * @throws Exception if {@link #pricesFromFile} is empty.
	 */
	public void updatePricesFromFile() throws Exception {
		if(pricesFromFile.isEmpty()) {
			throw new Exception("No more prices to load from file.");
		}
		ShareWallet oldPrices = new ShareWallet(prices);
		clearShareWallet();

		// load new prices
		while(!pricesFromFile.isEmpty()) {
			Share share = pricesFromFile.get(0);
			if (priceExists(share)) {
				break;
			}
			pricesFromFile.remove(0);
			prices.put(share, 1);
		}
		
		retrieveNotUpdatedPrices(oldPrices);
		changeStateAndNotify();
	}
	
	private boolean priceExists(Share nextShareToBeAdded) {
		if(prices.getShares().containsKey(nextShareToBeAdded.getCompanyName())) {
			return true;
		}
		return false;
	}

	// if some prices were not updated put their old values back to prices
	private void retrieveNotUpdatedPrices(ShareWallet oldPrices) {
		for (Entry<String, Pair<Share,Integer>> entry : oldPrices.getShares().entrySet()) {
			Share share = entry.getValue().first;
			if(!priceExists(share)) {
				System.out.println("put back old price!");
				prices.put(share, 1);
			}
		}
	}
	
	/**
	 * Clears list of registered prices in ShareWallet.
	 */
	public void clearShareWallet() {
		prices.getShares().clear();
	}

}
