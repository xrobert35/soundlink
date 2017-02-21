package soundlink.service.websocket.handler;

import java.io.File;
import java.io.FileNotFoundException;

import soundlink.service.websocket.IWebSocketExecutor;

public interface IAudioStreamHandler extends IWebSocketExecutor {

    void init(File musicFile) throws FileNotFoundException;

    void kill();

    void play();

    void pause();
}
