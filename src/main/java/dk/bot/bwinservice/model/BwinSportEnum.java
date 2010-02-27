package dk.bot.bwinservice.model;

public enum BwinSportEnum {

	SOCCER("4","<!-- entry -->","<td class='unselected result"),
	BASKETBALL("7","<nobr> <a class='more' title='","<td class='unselected result"),
	TENNIS("5","<!-- entry -->","<td class='unselected result"),
	BASEBALL("23","<!-- entry -->","<td class='unselected result"),
	VOLLEYBALL("18","<!-- entry -->","<td class='unselected result");
	
	private final String sportId;
	private final String marketsSplit;
	private final String teamsSplit;

	BwinSportEnum(String sportId,String marketsSplit,String teamsSplit) {
		this.sportId = sportId;
		this.marketsSplit = marketsSplit;
		this.teamsSplit = teamsSplit;
	}

	public String getSportId() {
		return sportId;
	}

	public String getMarketsSplit() {
		return marketsSplit;
	}

	public String getTeamsSplit() {
		return teamsSplit;
	}
	
}
