package dk.bot.bwinservice.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class BWinSport implements Serializable{

	private BwinSportEnum sport;
	
	/**key - region id*/
	private HashMap<BwinRegionEnum,List<BWinMarket>> markets = new HashMap<BwinRegionEnum, List<BWinMarket>>();

	public BwinSportEnum getSport() {
		return sport;
	}

	public BWinSport(BwinSportEnum sport, HashMap<BwinRegionEnum,List<BWinMarket>> markets) {
		this.sport = sport;
		this.markets = markets;
	}
	
	public void setSport(BwinSportEnum sport) {
		this.sport = sport;
	}

	public HashMap<BwinRegionEnum,List<BWinMarket>> getMarkets() {
		return markets;
	}

	public void setMarkets(HashMap<BwinRegionEnum,List<BWinMarket>> markets) {
		this.markets = markets;
	}
	
	
}
