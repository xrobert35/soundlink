package soundlink.server.sockethandler;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import soundlink.server.configuration.SocketHandler;
import soundlink.service.websocket.handler.IMusicIntegrationExecutor;

@SocketHandler("ws/integration")
public class IntegrationSocketHandler extends BaseTextWebSocketHandler {

    @Autowired
    private IMusicIntegrationExecutor musicIntegrationExecutor;

    protected static final Logger LOGGER = LogManager.getLogger(IntegrationSocketHandler.class);

    private Thread integrationThread = null;

    @PostConstruct
    public void init() {
        musicIntegrationExecutor.setAutoClose(true);
    }

    @Override
    protected void onConnect(WebSocketSession session) {
        LOGGER.info("Client for integration connected !");
        musicIntegrationExecutor.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> parameters = getParameters(message);

        String action = getStringParameter(parameters, "action");

        switch (action) {
        case "start":
            LOGGER.info("Client call start");
            startIntegration(session);
            break;
        }
    }

    public void startIntegration(WebSocketSession session) {
        if (integrationThread == null || !integrationThread.isAlive()) {
            LOGGER.info("Starting integration");
            integrationThread = new Thread(musicIntegrationExecutor);
            integrationThread.run();
        } else {
            LOGGER.info("Integration already running");
        }
    }
}