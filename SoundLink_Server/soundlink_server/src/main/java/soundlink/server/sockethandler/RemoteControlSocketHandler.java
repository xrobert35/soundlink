package soundlink.server.sockethandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import soundlink.dto.socket.SocketMessageType;
import soundlink.dto.socket.WebSocketDto;
import soundlink.server.configuration.SocketHandler;
import soundlink.server.utils.RemoteControlAction;

@SocketHandler("ws/remotecontrol")
public class RemoteControlSocketHandler extends BaseTextWebSocketHandler {

    // Map Login user / websocket session
    private Map<String, WebSocketSession> listeners = Collections
            .synchronizedMap(new HashMap<String, WebSocketSession>());

    private static final String NO_LISTENER = "NO_LISTENER";

    @Override
    protected void onConnect(WebSocketSession session) {
    }

    @Override
    protected void onClose(WebSocketSession session) {
        super.onClose(session);
        listeners.values().remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Map<String, Object> parameters = getParameters(message);

            // action_code:parameter
            String action = getStringParameter(parameters, "action");
            RemoteControlAction remoteAction = RemoteControlAction.valueOf(action.split(":")[0]);

            String loginUser = session.getPrincipal().getName();

            if (remoteAction == RemoteControlAction.REGISTER_LISTENER) {
                listeners.put(loginUser, session);
            } else if (remoteAction == RemoteControlAction.DEREGISTER_LISTENER) {
                listeners.values().remove(session);
            } else {
                WebSocketSession webSocketSession = listeners.get(loginUser);
                WebSocketDto webSocketDto = new WebSocketDto();
                if (webSocketSession != null) {
                    webSocketDto.setType(SocketMessageType.INFO);
                    webSocketDto.setCode(action);
                    this.sendMessage(webSocketDto, webSocketSession);
                } else {
                    // Envoi message
                    webSocketDto.setType(SocketMessageType.ERROR);
                    webSocketDto.setCode(NO_LISTENER);
                    this.sendMessage(webSocketDto, session);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}