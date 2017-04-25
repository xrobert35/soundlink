package soundlink.server.configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import soundlink.server.sockethandler.RemoteControlSocketHandler;

@Configuration
@EnableWebSocket
public class BaseWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    protected RemoteControlSocketHandler webSocketHandler;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        Map<String, Object> socketHandlersMap = applicationContext.getBeansWithAnnotation(SocketHandler.class);
        socketHandlersMap.values().forEach(socketHandler -> {
            SocketHandler socketHandlerAnnotation = socketHandler.getClass().getAnnotation(SocketHandler.class);
            registry.addHandler((AbstractWebSocketHandler) socketHandler, socketHandlerAnnotation.value())
                    .setAllowedOrigins("*");
        });
    }
}