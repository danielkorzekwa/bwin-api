package dk.bot.bwinservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.bwinservice.model.BwinMarkets;
import dk.bot.bwinservice.model.BwinRegionEnum;
import dk.bot.bwinservice.model.BwinSportEnum;
import dk.bot.bwinservice.workers.RegionMarketsWorker;

public class BWinServiceImpl implements BWinService {

	private final Log log = LogFactory.getLog(BWinServiceImpl.class.getSimpleName());

	private final static String URL_SPORT = "https://www.bwin.com/betsnew.aspx?SportID=";

	private final static String URL_REGION = "https://www.bwin.com/betviewiframe.aspx?RegionID=";

	private HttpClient client;
	private ThreadPoolTaskExecutor executor;

	public BWinServiceImpl() {
		executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.initialize();
		
		MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
		httpConnectionManager.setMaxConnectionsPerHost(5);
		httpConnectionManager.setMaxTotalConnections(5);
		client = new HttpClient(httpConnectionManager);
		client.getParams().setSoTimeout(10000);
	}
	
	public BwinMarkets getBwinMarkets() {

		boolean currentExecutionSuccess = true;

		Set<BWinMarket> markets = new HashSet<BWinMarket>();
		
		for (BwinSportEnum sportEnum : BwinSportEnum.values()) {
			
			GetMethod method = new GetMethod(URL_SPORT + sportEnum.getSportId());

			try {
				client.executeMethod(method);

				List<BwinRegionEnum> sportRegions = BwinRegionEnum.getSportRegion(sportEnum);
				
				CountDownLatch doneSignal = new CountDownLatch(sportRegions.size());
				List<RegionMarketsWorker> workers = new ArrayList<RegionMarketsWorker>();
				for (BwinRegionEnum regionEnum : sportRegions) {
						RegionMarketsWorker worker = new RegionMarketsWorker(doneSignal, client, URL_REGION, sportEnum,regionEnum);
						workers.add(worker);
						executor.execute(worker);
				}
				try {
					doneSignal.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (RegionMarketsWorker worker : workers) {
					if(!worker.isExecutionSuccess()) {
						currentExecutionSuccess=false;
					}
					markets.addAll(worker.getRegionMarkets());
				}
				
			} catch (IOException e) {
				currentExecutionSuccess = false;
			} finally {
				// Release the connection.
				method.releaseConnection();
			}
		}

		return new BwinMarkets(markets,currentExecutionSuccess);
	}

}
