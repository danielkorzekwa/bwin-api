package dk.bot.bwinservice.workers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.bwinservice.model.BWinTeam;
import dk.bot.bwinservice.model.BwinRegionEnum;
import dk.bot.bwinservice.model.BwinSportEnum;
import dk.bot.bwinservice.util.ParseHelper;

/**
 * Gets markets for bwin region
 * 
 * @author daniel
 * 
 */
public class RegionMarketsWorker implements Runnable {

	private final Log log = LogFactory.getLog(RegionMarketsWorker.class.getSimpleName());

	private final static String DAY_REGEX = ".+, (.+) (\\d+), (\\d+)";
	private final static String TEAM_REGEX = ".*>(.*)</td><td class='odd'>.*";
	private final static String ODD_REGEX = ".*</td><td class='odd'>(.*)</td></tr></table>.*";

	private final CountDownLatch doneSignal;
	private final HttpClient client;
	private final String url;
	private final BwinSportEnum sport;
	private final BwinRegionEnum region;

	private boolean executionSuccess = true;
	private List<BWinMarket> regionMarkets = new ArrayList<BWinMarket>();

	/**
	 * 
	 * @param doneSignal
	 * @param client
	 * @param url
	 *            Bwin Url to obtain region markets
	 * @param region
	 */
	public RegionMarketsWorker(CountDownLatch doneSignal, HttpClient client, String url, BwinSportEnum sport,
			BwinRegionEnum region) {
		this.doneSignal = doneSignal;
		this.client = client;
		this.url = url;
		this.sport = sport;
		this.region = region;
	}

	public void run() {
		try {
			doWork();
		} catch (Exception e) {
			executionSuccess = false;
		} finally {
			doneSignal.countDown();
		}
	}

	private void doWork() {
		// Create a method instance.
		GetMethod method = new GetMethod(url + region.getBWin());

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				executionSuccess = false;
				/** use method.getStatusLine()); for details */
				return;
			}

			// Read the response body.
			String responseBody = method.getResponseBodyAsString();
			regionMarkets = parseMarkets(responseBody, region);

		} catch (ParseException e) {
			executionSuccess = false;
			log.error("Bwin parse error: + " + e.getMessage());
			return;
		} catch (HttpException e) {
			executionSuccess = false;
			return;
		} catch (IOException e) {
			executionSuccess = false;
			return;
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

	}

	private List<BWinMarket> parseMarkets(String responseBody, BwinRegionEnum region) throws ParseException {

		List<BWinMarket> markets = new ArrayList<BWinMarket>();

		String[] days = responseBody.split("<div class=\"controlHeaderNoShadow\">");
		for (int i = 1; i < days.length; i++) {
			String day = days[i];

			Date marketsTime = formatBwinDate(day);

			String[] marketsStrings = day.split(region.getSport().getMarketsSplit());

			for (int j = 1; j < marketsStrings.length; j++) {

				String[] odds = marketsStrings[j].split(region.getSport().getTeamsSplit());

				List<BWinTeam> teams = new ArrayList<BWinTeam>(odds.length - 1);
				for (int k = 1; k < odds.length; k++) {
					BWinTeam team = new BWinTeam();
					team.setName(ParseHelper.findRegex(TEAM_REGEX, odds[k], 1).trim());
					String odd = ParseHelper.findRegex(ODD_REGEX, odds[k], 1);
					
					try {
						team.setOdd(Double.parseDouble(odd));
					} catch (NumberFormatException e) {
						try {
							String[] oddArray = odd.split("/");
							team.setOdd((Double.parseDouble(oddArray[0]) + Double.parseDouble(oddArray[1]))
									/ Double.parseDouble(oddArray[1]));

						} catch (NumberFormatException e1) {
							team.setOdd(1.01d);
						}
					}
					teams.add(team);
				}

				BWinMarket market = new BWinMarket(sport, region, teams, marketsTime);
				markets.add(market);
			}

		}

		return markets;

	}

	private Date formatBwinDate(String day) throws ParseException {
		String shortDay = day.substring(0, day.indexOf("</span>")).split("<span class=\"spanInnerLeft\">")[1];
		String bWinMonth = ParseHelper.findRegex(DAY_REGEX, shortDay, 1);
		String bWinDay = ParseHelper.findRegex(DAY_REGEX, shortDay, 2);
		String bWinYear = ParseHelper.findRegex(DAY_REGEX, shortDay, 3);

		SimpleDateFormat bwinDateFormat = new SimpleDateFormat("MMM d yyyy");
		return bwinDateFormat.parse(bWinMonth + " " + bWinDay + " " + bWinYear);
	}

	public boolean isExecutionSuccess() {
		return executionSuccess;
	}

	public List<BWinMarket> getRegionMarkets() {
		return regionMarkets;
	}

}
