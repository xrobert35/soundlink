package soundlink.server.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import soundlink.datastore.manager.DataStoreFileManager;

@Component
public class ServerListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private DataStoreFileManager dataStoreManager;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			dataStoreManager.initDataStoreFromPath("D:/Musique/Test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
