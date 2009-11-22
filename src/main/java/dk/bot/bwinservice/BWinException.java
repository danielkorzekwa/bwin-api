package dk.bot.bwinservice;

/**
 * 
 * @author daniel
 *
 */
public class BWinException extends RuntimeException{

	public BWinException(String message) {
		super(message);
	}
	
	public BWinException(String message,Throwable t) {
		super(message,t);
	}
	
	public BWinException(Throwable t) {
		super(t);
	}
}
