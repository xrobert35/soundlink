package soundlink.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import soundlink.server.sockethandler.AudioWebSocketHandler;
import soundlink.server.sockethandler.IntegrationSocketHandler;

@Configuration
@EnableWebSocket
public class BaseWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    protected AudioWebSocketHandler webSocketHandler;

    @Autowired
    private IntegrationSocketHandler integrationSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/music").setAllowedOrigins("*");
        registry.addHandler(integrationSocketHandler, "/ws/integration").setAllowedOrigins("*");
    }
}