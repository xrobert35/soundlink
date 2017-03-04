package soundlink.service.websocket.handler;

import java.io.File;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface IMusicFileProcessor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void processFile(File musicFile, Integer integrationNumber);

}
