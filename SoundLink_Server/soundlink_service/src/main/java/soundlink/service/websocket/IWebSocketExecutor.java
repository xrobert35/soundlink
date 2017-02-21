package soundlink.service.websocket;

import org.springframework.web.socket.WebSocketSession;

public interface IWebSocketExecutor extends Runnable {

    void removeSession(WebSocketSession session);

    void addSession(WebSocketSession session);

    void setAutoClose(boolean autoClose);

    void execute();

}
