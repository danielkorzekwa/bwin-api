package dk.bot.bwinservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import dk.bot.bwinservice.model.BWinMarket;

public class BWinServiceImplIntegrationTest {

	@Test
	public void testGetMarkets() {
		
		BWinServiceImpl service = new BWinServiceImpl();

		long now = System.currentTimeMillis();
		Collection<BWinMarket> markets = service.getBwinMarkets().getMarkets();
		System.out.println(System.currentTimeMillis()-now);
		assertTrue(markets.size() > 0);
		
	}
	
	@Test
	public void testCompare() {
		assertTrue(BWinMarket.compare("Cheltenham Town", "Cheltenham"));
		assertTrue(BWinMarket.compare("Nottingham Forest", "Nott Forest"));
		assertTrue(BWinMarket.compare("Manchester City", "Man city"));
		assertTrue(BWinMarket.compare("aaabbb", "aaabbb"));
		
		assertFalse(BWinMarket.compare("Harrogate Town", "Alfreton Town"));
		
		assertFalse(BWinMarket.compare("Rennes", "Amiens SCF"));
		assertFalse(BWinMarket.compare("Rennes", "X"));
		assertFalse(BWinMarket.compare("Rennes", "ES Troyes AC"));
		assertFalse(BWinMarket.compare("G Furth", "Jahn Regensburg  (U19)"));
		
		// Racing B:Gijon B:The Draw:1 10 2008:Racing Santander:X:Sporting Gijon:9 10 2008
	}

}
