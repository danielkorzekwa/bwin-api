package dk.bot.bwinservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author daniel
 *
 */
public class BWinMarket implements Serializable{

	
	private final BwinSportEnum sport;
	private final BwinRegionEnum region;
	private final List<BWinTeam> teams;
	private final Date marketTime;

	public BWinMarket(BwinSportEnum sport,BwinRegionEnum region,List<BWinTeam> teams,Date marketTime) {
		this.sport = sport;
		this.region = region;
		this.teams = teams;
		this.marketTime = marketTime;
	}
	
	public BwinSportEnum getSport() {
		return sport;
	}
	public BwinRegionEnum getRegion() {
		return region;
	}
	public List<BWinTeam> getTeams() {
		return teams;
	}
	public Date getMarketTime() {
		return marketTime;
	}

	/** null if runner not found */
	public Double getRunnerOdd(String runnerName) {

		if (runnerName!=null && runnerName.equals("The Draw")) {
			runnerName = "X";
		}

		for (BWinTeam team : teams) {
			if (compare(runnerName,team.getName())) {
				return team.getOdd();
			}
		}

		return null;

	}

	public static boolean compare(String betFair, String bWin) {
		String mapping = getTeamMap().get(bWin);
		if (mapping != null) {
			bWin = mapping;
		}

		betFair = betFair.replaceAll("[^a-zA-Z0-9\\s]", "_");
		bWin = bWin.replaceAll("[^a-zA-Z0-9\\s]", "_");

		if (betFair.toLowerCase().startsWith(bWin.toLowerCase()) || bWin.toLowerCase().startsWith(betFair.toLowerCase())) {
			return true;
		}

		String[] aWords = betFair.split(" ");
		String[] bWords = bWin.split(" ");
		
		boolean matched = compare(aWords, bWords);
		if(matched) {
			return true;
		}

		matched = compare(bWords,aWords);
		if(matched) {
			return true;
		}
		
		/**dot as separator*/
		aWords = betFair.split(".");
		bWords = bWin.split(".");

		matched = compare(aWords, bWords);
		if(matched) {
			return true;
		}

		matched = compare(bWords,aWords);
		if(matched) {
			return true;
		}
	
		return false;
	}
	
	private static boolean compare(String[] aWords,String[] bWords) {
		
		boolean matched=false;
		
		for (String aWord : aWords) {
			if (aWord.length() > 3) {
				matched=false;
				for (String bWord : bWords) {
					if (bWord.length() > 3 && aWord.toLowerCase().startsWith(bWord.toLowerCase())) {
						matched=true;
						break;
					}
				}
				if(!matched) {
					break;
				}
			}
		}
		
		return matched;
	}

	@Override
	public String toString() {
		StringBuffer text = new StringBuffer();

		for (int i = 0; i < teams.size(); i++) {
			text.append(teams.get(i).getName());
			if (i < teams.size() - 1) {
				text.append(":");
			}
		}

		return text.toString();
	}

	private static HashMap<String, String> getTeamMap() {
		HashMap<String, String> map = new HashMap<String, String>();

		// germany
		map.put("Karlsruher SC", "Karlsruhe");
		map.put("SC Freiburg", "Freiburg");
		map.put("Borussia Mönchengladbach", "Mgladbach");

		// greece
		map.put("AEK Athens", "AEK");
		map.put("Panseraikos", "Panserraikos");
		map.put("Haidari", "Chaidari");
		map.put("Ilysiakos", "Ilisiakos");
		map.put("Ionikos Nikeas", "Ionikos");
		map.put("Kerkyra (Corfu)", "Kerkira");

		// england
		map.put("Manchester United", "Man Utd");
		map.put("Wigan Athletic", "Wigan");

		// denmark
		map.put("FC Nordsjælland", "FC Nordsj");
		map.put("Randers FC", "Randers");
		map.put("Silkeborg IF", "Silkeborg");
		map.put("Næstved", "Naestved");

		// portugal
		map.put("Sporting Clube de Portugal", "Sp Lisbon");
		map.put("Boavista Porto", "Boavista");

		//baskep poland
		map.put("Polpak S.A. Świecie", "Poplak");
		return map;
	}

}
