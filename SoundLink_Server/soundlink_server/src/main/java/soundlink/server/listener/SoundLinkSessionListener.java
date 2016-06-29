package soundlink.server.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SoundLinkSessionListener implements HttpSessionListener {

	private static final Logger LOGGER = Logger.getLogger(SoundLinkSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.debug("SESSION CREATE");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOGGER.debug("SESSION DETROY");
	}
}