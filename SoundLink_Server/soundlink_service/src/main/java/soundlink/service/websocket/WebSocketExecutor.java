package soundlink.service.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import soundlink.dto.socket.WebSocketDto;

public abstract class WebSocketExecutor implements Runnable {

    private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

    private boolean autoClose = false;

    @Transactional
    @Override
    public void run() {
        try {
            execute();
            if (autoClose) {
                closeSessions();
            }
        } catch (Exception e) {
            closeSessions();
            e.printStackTrace();
        }
    }

    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    public void addSession(WebSocketSession session) {
        this.sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        if (sessions.contains(session)) {
            sessions.remove(session);
        }
    }

    protected void closeSessions() {
        this.sessions.stream().forEach(session -> {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.sessions.clear();
    }

    protected void sendMessage(String string) {
        sendMessage(new TextMessage(string));
    }

    protected void sendMessage(byte[] message) {
        sendMessage(new BinaryMessage(message));
    }

    public void sendMessage(WebSocketDto dto) {
        try {
            WebSocketMessage<?> message = new TextMessage(new ObjectMapper().writeValueAsBytes(dto));
            sendMessage(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void sendMessage(WebSocketMessage<?> message) {
        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                } else {
                    iterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    session.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                iterator.remove();
            }
        }
    }

    public abstract void execute();
}
