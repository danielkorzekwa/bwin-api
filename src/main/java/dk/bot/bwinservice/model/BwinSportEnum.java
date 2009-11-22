package dk.bot.bwinservice.model;

public enum BwinSportEnum {

	SOCCER("4","<!-- entry -->","<td class='unselected'"),
	BASKETBALL("7","<nobr> <a class='more' title='","<td class='unselected'"),
	TENNIS("5","<!-- entry -->","<td class='unselected'"),
	BASEBALL("23","<!-- entry -->","<td class='unselected'"),
	VOLLEYBALL("18","<!-- entry -->","<td class='unselected'");
	
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
