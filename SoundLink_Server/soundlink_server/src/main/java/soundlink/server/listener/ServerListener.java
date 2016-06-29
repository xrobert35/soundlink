package soundlink.server.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ServerListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(ServerListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Serveur started");
	}
}
