package com.capgemini.exchange.share;

import java.time.LocalDate;
import java.util.Locale;

/**
 * A company share.
 * 
 * @author KNIEMCZY
 */
public class Share {

	/**
	 * Replacement for unspecified {@link #companyName}.
	 */
	public static final String UNDEFINED = "?null?";

	/**
	 * Company which this share refers to. It mustn't be changed!
	 */
	private final String companyName;

	/**
	 * Prize per unit of this Share.
	 */
	private Double unitPrize;

	/**
	 * Date when the {@link #unitPrize} was updated.
	 */
	private LocalDate date;

	/**
	 * Used for undefined shares.
	 */
	public Share(Double unitPrize) {
		this.unitPrize = unitPrize;
		this.companyName = UNDEFINED;
		this.date = LocalDate.now();
	}

	/**
	 * Used for choosing share to sell/buy.
	 */
	public Share(String companyName) {
		this.unitPrize = 0.0;
		this.companyName = companyName;
		this.date = LocalDate.now();
	}

	/**
	 * Used for observing current situation at the market.
	 */
	public Share(Double unitPrize, String companyName) {
		this.unitPrize = unitPrize;
		this.companyName = companyName;
		this.date = LocalDate.now();
	}

	/**
	 * Used for enterprise usage like predicting future share unit prices.
	 */
	public Share(Double unitPrize, String companyName, LocalDate date) {
		this.unitPrize = unitPrize;
		this.companyName = companyName;
		this.date = date;
	}

	public Double getUnitPrice() {
		return unitPrize;
	}

	public String getCompanyName() {
		return companyName;
	}

	public LocalDate getDate() {
		return date;
	}

	/**
	 * Parse a share from String in format:
	 * <b>companyName,dateString,unitPrice</b>, where <b>dateString</b> is in
	 * format <code>yyyymmdd</code>.
	 * 
	 * @return converted share object
	 * @throws IllegalArgumentException
	 *             when <b>line</b> did not contain 2 commas separating required
	 *             data.
	 */
	public static Share parse(String line) throws IllegalArgumentException {
		String[] data = line.split(",");
		if (data.length < 3) {
			throw new IllegalArgumentException("Provided String did not contain 2 commas");
		}
		String companyName = data[0];
		int year = Integer.parseInt(data[1].substring(0, 4));
		int month = Integer.parseInt(data[1].substring(4, 6));
		int day = Integer.parseInt(data[1].substring(6, 8));
		Double unitPrice = Double.parseDouble(data[2]);
		return new Share(unitPrice, companyName, LocalDate.of(year, month, day));
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Share) && (((Share) obj).companyName).equals(companyName)) {
			return true;
		}
		return false;
	}

	/**
	 * The output format is:</p>
	 * <code>companyName,dateString,unitPrice</code></p>where <b>dateString</b> is in
	 * format: <code>yyyymmdd</code>.</p>
	 */
	@Override
	public String toString() {
		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();

		return companyName + String.format(Locale.US, ",%4d%02d%02d,%.2f", year, month, day, unitPrize);
	}

}
