package com.capgemini.exchange.player;

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
	 * Used for undefined shares.
	 */
	public Share(Double unitPrize) {
		this.unitPrize = unitPrize;
		companyName = UNDEFINED;
	}

	/**
	 * Used for anonymous shares.
	 */
	public Share(Double unitPrize, String companyName) {
		this.unitPrize = unitPrize;
		this.companyName = companyName;
	}

	public Double getUnitPrize() {
		return unitPrize;
	}

	public String getCompanyName() {
		return companyName;
	}

}
