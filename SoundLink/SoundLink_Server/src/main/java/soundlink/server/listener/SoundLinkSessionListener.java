package soundlink.server.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SoundLinkSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// event.getSession().setMaxInactiveInterval(15 * 60); // Session de 15
		// minutes
		System.out.println("SESSION CREATE");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("SESSION DETROY");
	}
}