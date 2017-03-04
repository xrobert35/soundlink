package soundlink.service.websocket.handler.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

import soundlink.service.websocket.WebSocketExecutor;
import soundlink.service.websocket.handler.IAudioStreamHandler;

@Component
@Scope("prototype")
public class AudioStreamHandler extends WebSocketExecutor implements IAudioStreamHandler {

    private boolean playing = true;
    private boolean alive = true;
    private RandomAccessFile fileReader = null;

    public void init(File musicFile) throws FileNotFoundException {
        playing = false;
        fileReader = new RandomAccessFile(musicFile, "r");
    }

    @Override
    public void play() {
        playing = true;
    }

    @Override
    public void pause() {
        playing = false;
    }

    public void kill() {
        alive = false;
    }

    @Override
    public void execute() {
        while (alive) {
            if (playing) {
                byte[] data = new byte[512000];
                try {
                    if (fileReader.read(data) != -1) {
                        BinaryMessage binaryMessage = new BinaryMessage(data);
                        sendMessage(binaryMessage);
                    } else {
                        playing = false;
                        TextMessage textMessage = new TextMessage("{ \"end\" : true }");
                        sendMessage(textMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
