package soundlink.server.sockethandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import soundlink.service.manager.IMusicManager;
import soundlink.service.websocket.handler.IAudioStreamHandler;

@Component
public class AudioWebSocketHandler extends BaseTextWebSocketHandler {

    @Autowired
    private IMusicManager musicManager;

    @Autowired
    private ApplicationContext applicationContext;

    private static final String STREAMER = "streamer";

    @Override
    protected void onConnect(WebSocketSession session) {
        IAudioStreamHandler bean = applicationContext.getBean(IAudioStreamHandler.class);
        bean.setAutoClose(false);
        bean.addSession(session);
        session.getAttributes().put(STREAMER, bean);

    }

    @Override
    protected void onClose(WebSocketSession session) {
        IAudioStreamHandler streamer = getStreamer(session);
        streamer.kill();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Map<String, Object> parameters = getParameters(message);

            Integer musicId = getIntegerParameter(parameters, "fileName");
            Boolean resume = getBooleanParameter(parameters, "resume");
            Boolean pause = getBooleanParameter(parameters, "pause");

            IAudioStreamHandler streamer = getStreamer(session);

            if (musicId != null) {
                File musicFile = musicManager.getMusicFile(musicId);

                TextMessage textMessage = new TextMessage("{ \"fileSize\" : " + musicFile.length() + "}");
                sendMessage(session, textMessage);

                try {
                    streamer.init(musicFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (Boolean.TRUE.equals(resume)) {
                new Thread(streamer).start();
                streamer.play();
            } else if (Boolean.TRUE.equals(pause)) {
                streamer.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IAudioStreamHandler getStreamer(WebSocketSession session) {
        return (IAudioStreamHandler) session.getAttributes().get(STREAMER);
    }
}