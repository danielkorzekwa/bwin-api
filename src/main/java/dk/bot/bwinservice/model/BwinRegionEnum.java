package dk.bot.bwinservice.model;

import java.util.ArrayList;
import java.util.List;

public enum BwinRegionEnum {

	SOCCER_EUROPE_INTERTOTTO(BwinSportEnum.SOCCER, "7"),

	SOCCER_WORLD_WOMAN_INTERNATIONAL(BwinSportEnum.SOCCER, "6"),

	SOCCER_UK(BwinSportEnum.SOCCER, "14"), SOCCER_GERMANY(BwinSportEnum.SOCCER, "17"), SOCCER_ITALY(
			BwinSportEnum.SOCCER, "20"), SOCCER_SPAIN(BwinSportEnum.SOCCER, "28"), SOCCER_AUSTRIA(BwinSportEnum.SOCCER,
			"8"), SOCCER_FINLAND(BwinSportEnum.SOCCER, "15"), SOCCER_FRANCE(BwinSportEnum.SOCCER, "16"), SOCCER_DENMARK(
			BwinSportEnum.SOCCER, "13"), SOCCER_GREECE(BwinSportEnum.SOCCER, "18"), SOCCER_HUNGARY(
			BwinSportEnum.SOCCER, "19"), SOCCER_NORWAY(BwinSportEnum.SOCCER, "21"), SOCCER_POLAND(BwinSportEnum.SOCCER,
			"22"), SOCCER_REPIRELAND(BwinSportEnum.SOCCER, "23"), SOCCER_ROMANIA(BwinSportEnum.SOCCER, "24"), SOCCER_RUSSIA(
			BwinSportEnum.SOCCER, "25"), SOCCER_SCOTLAND(BwinSportEnum.SOCCER, "26"), SOCCER_CZECH(
			BwinSportEnum.SOCCER, "12"), SOCCER_SWEDEN(BwinSportEnum.SOCCER, "29"), SOCCER_SWISS(BwinSportEnum.SOCCER,
			"30"), SOCCER_TURKEY(BwinSportEnum.SOCCER, "31"), SOCCER_BRAZIL(BwinSportEnum.SOCCER, "33"), SOCCER_BELGIUM(
			BwinSportEnum.SOCCER, "35"), SOCCER_NETHERLANDS(BwinSportEnum.SOCCER, "36"), SOCCER_PORTUGAL(
			BwinSportEnum.SOCCER, "37"), SOCCER_ARGENTINA(BwinSportEnum.SOCCER, "38"), SOCCER_USA(BwinSportEnum.SOCCER,
			"39"), SOCCER_MEXICO(BwinSportEnum.SOCCER, "43"), SOCCER_CROATIA(BwinSportEnum.SOCCER, "50"), SOCCER_SLOVAKIA(
			BwinSportEnum.SOCCER, "51"), SOCCER_JAPAN(BwinSportEnum.SOCCER, "52"), SOCCER_ICELAND(BwinSportEnum.SOCCER,
			"49"),

	BASKETBALL_NBA(BwinSportEnum.BASKETBALL, "9"), BASKETBALL_WNBA(BwinSportEnum.BASKETBALL, "9"), BASKETBALL_TURKEY(
			BwinSportEnum.BASKETBALL, "31"), BASKETBALL_ITALY(BwinSportEnum.BASKETBALL, "20"),
	// BASKETBALL_FRANCE(BwinSportEnum.BASKETBALL,"9"),
	BASKETBALL_POLAND(BwinSportEnum.BASKETBALL, "22"),
	// BASKETBALL_GREECE(BwinSportEnum.BASKETBALL,"9"),
	BASKETBALL_SPAIN(BwinSportEnum.BASKETBALL, "28"),

	TENNIS_WORLD(BwinSportEnum.TENNIS, "6"),

	VOLLEYBALL_WORLD_MEN_WORLD_LEAGUE_2008(BwinSportEnum.VOLLEYBALL, "6"),

	BASEBALL_NORTH_AMERICA_MLB2008(BwinSportEnum.BASEBALL, "9");

	private final String bWin;
	private final BwinSportEnum sport;

	BwinRegionEnum(BwinSportEnum sport, String bWin) {

		this.sport = sport;
		this.bWin = bWin;
	}

	public String getBWin() {
		return bWin;
	}

	public BwinSportEnum getSport() {
		return sport;
	}

	public static List<BwinRegionEnum> getSportRegion(BwinSportEnum sport) {
		ArrayList<BwinRegionEnum> regions = new ArrayList<BwinRegionEnum>();

		for (BwinRegionEnum regionEnum : BwinRegionEnum.values()) {
			if (regionEnum.getSport().equals(sport)) {
				regions.add(regionEnum);
			}
		}
		return regions;
	}

}
