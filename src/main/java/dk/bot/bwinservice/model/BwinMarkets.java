package dk.bot.bwinservice.model;

import java.util.Set;


/**
 * Markets obtained from Bwin.com .
 * 
 * @author daniel
 * 
 */
public class BwinMarkets {

	private final Set<BWinMarket> markets;

	/**
	 * True if the execution finished successfully (data for all markets was
	 * obtained without any exception)
	 */
	private final boolean status;

	
	public BwinMarkets(Set<BWinMarket> markets, boolean status) {
		this.markets = markets;
		this.status = status;
	}


	public boolean getStatus() {
		return status;
	}


	public Set<BWinMarket> getMarkets() {
		return markets;
	}

}
