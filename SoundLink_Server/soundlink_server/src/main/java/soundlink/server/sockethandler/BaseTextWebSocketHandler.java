package soundlink.server.sockethandler;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import soundlink.dto.socket.WebSocketDto;

public abstract class BaseTextWebSocketHandler extends AbstractWebSocketHandler {

    protected static final Logger LOGGER = LogManager.getLogger(BaseTextWebSocketHandler.class);

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) {
        LOGGER.error("error occured at sender " + session, throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOGGER.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
        onClose(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOGGER.info("Connected ... " + session.getId());
        onConnect(session);
    }

    public Map<String, Object> getParameters(TextMessage jsonTextMessage) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        return springParser.parseMap(jsonTextMessage.getPayload());
    }

    protected void sendMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringParameter(Map<String, Object> parameters, String parameterName) {
        Object param = parameters.get(parameterName);
        if (param != null) {
            if (param instanceof String) {
                return (String) param;
            } else {
                throw new InvalidParameterException(parameterName + " must be a string !");
            }
        }
        return null;
    }

    public Integer getIntegerParameter(Map<String, Object> parameters, String parameterName) {
        Object param = parameters.get(parameterName);
        if (param != null) {
            if (param instanceof Integer) {
                return (Integer) param;
            } else if (param instanceof String) {
                return Integer.parseInt((String) param);
            } else {
                throw new InvalidParameterException(parameterName + " cannot be converted into Integer");
            }
        }
        return null;
    }

    public Boolean getBooleanParameter(Map<String, Object> parameters, String parameterName) {
        Object param = parameters.get(parameterName);
        if (param != null) {
            if (param instanceof Boolean) {
                return (Boolean) param;
            } else if (param instanceof String) {
                return Boolean.parseBoolean((String) param);
            } else {
                throw new InvalidParameterException(parameterName + " must be a string !");
            }
        }
        return null;
    }

    protected void sendMessage(String string, WebSocketSession... sessions) {
        sendMessage(new TextMessage(string), sessions);
    }

    protected void sendMessage(byte[] message, WebSocketSession... sessions) {
        sendMessage(new BinaryMessage(message), sessions);
    }

    public void sendMessage(WebSocketDto dto, WebSocketSession... sessions) {
        try {
            WebSocketMessage<?> message = new TextMessage(new ObjectMapper().writeValueAsBytes(dto));
            sendMessage(message, sessions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void sendMessage(WebSocketMessage<?> message, WebSocketSession... sessions) {
        Arrays.asList(sessions).forEach(session -> {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    protected abstract void onConnect(WebSocketSession session);

    protected void onClose(WebSocketSession session) {
    }
}
