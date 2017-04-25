package soundlink.server.sockethandler;

import java.util.Map;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void init() {
        musicIntegrationExecutor.setAutoClose(true);
    }

    @Override
    protected void onConnect(WebSocketSession session) {
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
            startIntegration(session);
            break;
        }
    }

    public void startIntegration(WebSocketSession session) {
        new Thread(musicIntegrationExecutor).run();
    }
}