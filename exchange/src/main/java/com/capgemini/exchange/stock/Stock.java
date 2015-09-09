package com.capgemini.exchange.stock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import com.capgemini.exchange.share.Share;
import com.capgemini.exchange.share.ShareMap;

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
	private ShareMap prices;

	/**
	 * List of prices loaded from file.
	 */
	private List<Share> pricesFromFile;

	private Stock() {
		prices = new ShareMap();
		loadPricesFromFile();
	}

	/**
	 * Load all share prices from file {@value #INPUT_FILE_PATH} into
	 * {@link #pricesFromFile} list and sorts it by {@link Share#date} field.
	 */
	private void loadPricesFromFile() {
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

	public ShareMap getCurrentPrices() {
		return prices.copy();
	}

	public void updatePrices(Share... shares) {
		for (Share share : shares) {
			prices.put(share);
		}
		changeStateAndNotify();
	}

	/**
	 * Notifies observers that prices were updated.
	 */
	private void changeStateAndNotify() {
		setChanged();
		notifyObservers();
	}

	/**
	 * IMPORTANT: sort {@link #pricesFromFile} before any invocation of this
	 * method!
	 */
	public void updatePricesFromFile() {
		int prevSize = prices.size();
		for (int added=0; added < pricesFromFile.size(); ++added) {
			Share share = pricesFromFile.get(0);
			if ((prevSize == 0) && (added >= prevSize) && (prices.get(share) != null)) {
				System.out.println(added);
				break;
			}
			pricesFromFile.remove(0);
			prices.put(share);
		}
		changeStateAndNotify();
	}

	/**
	 * Clears list of registered prices.
	 */
	public void reset() {
		prices.clear();
	}

}
