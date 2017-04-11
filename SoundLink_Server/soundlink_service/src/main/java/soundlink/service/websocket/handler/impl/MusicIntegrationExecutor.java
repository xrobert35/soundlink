package soundlink.service.websocket.handler.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import soundlink.dto.socket.IntegrationProgressDto;
import soundlink.service.constant.SoundlinkConstant;
import soundlink.service.filter.MusicFileFilter;
import soundlink.service.manager.IIntegrationManager;
import soundlink.service.websocket.WebSocketExecutor;
import soundlink.service.websocket.handler.IMusicFileProcessor;
import soundlink.service.websocket.handler.IMusicIntegrationExecutor;

@Component
public class MusicIntegrationExecutor extends WebSocketExecutor implements IMusicIntegrationExecutor {

    protected static final Logger LOGGER = LogManager.getLogger("soundlink_integration");

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolderPath;

    @Autowired
    private MusicFileFilter musicFileFilter;

    @Autowired
    private IIntegrationManager integrationManager;

    @Autowired
    private IMusicFileProcessor musicFileProcessor;

    private Integer integrationNumber;

    private File musicsInputFolder;

    private File musicsStorageFolder;

    private File musicsErrorFolder;

    private int totalFiles = 0;

    private int fileTraited = 0;

    @PostConstruct
    public void initBean() {
        File soundlinkFolder = new File(soundlinkFolderPath);
        if (!soundlinkFolder.isDirectory()) {
            throw new IllegalArgumentException(
                    "Root path must be a valide directory : " + soundlinkFolder.getAbsolutePath());
        }

        File albumCoverFolder = new File(soundlinkFolder + SoundlinkConstant.ALBUMS_COVERS_FOLDER);
        if (!albumCoverFolder.exists()) {
            albumCoverFolder.mkdirs();
        }

        File artistesCoverFolder = new File(soundlinkFolder + SoundlinkConstant.ARTISTES_COVERS_FOLDER);
        if (!artistesCoverFolder.exists()) {
            artistesCoverFolder.mkdirs();
        }

        musicsInputFolder = new File(soundlinkFolder + SoundlinkConstant.MUSICS_INPUT_FOLDER);
        if (!musicsInputFolder.exists()) {
            musicsInputFolder.mkdirs();
        }

        musicsStorageFolder = new File(soundlinkFolder + SoundlinkConstant.MUSICS_STORAGE_FOLDER);
        if (!musicsStorageFolder.exists()) {
            musicsStorageFolder.mkdirs();
        }

        musicsErrorFolder = new File(soundlinkFolder + SoundlinkConstant.MUSICS_ERROR_FOLDER);
        if (!musicsErrorFolder.exists()) {
            musicsErrorFolder.mkdirs();
        }
    }

    @Override
    public void execute() {
        integrationNumber = integrationManager.getNextIntegrationNumber();

        fileTraited = 0;
        totalFiles = countFilesInDirectory(musicsInputFolder);

        sendProgressMessage("Start integrating " + totalFiles + " musics ", 0, false);

        exploreDirectory(musicsInputFolder);

        sendProgressMessage("Integration ended", 100, true);
    }

    private void sendProgressMessage(String message, Integer progress, boolean end) {
        IntegrationProgressDto dto = new IntegrationProgressDto();
        dto.setMessage(message);
        dto.setProgress(progress);
        dto.setEnd(end);
        sendMessage(dto);
    }

    public int countFilesInDirectory(File directory) {
        int count = 0;
        for (File file : directory.listFiles(musicFileFilter)) {
            if (file.isFile()) {
                count++;
            }
            if (file.isDirectory()) {
                count += countFilesInDirectory(file);
            }
        }
        return count;
    }

    /**
     * Method used to explore a directory. It will analyse each music file (see
     * MusicFileFilter) from the directory
     * 
     * @param directory
     */
    private void exploreDirectory(File directory) {
        LOGGER.debug("Explore directory : " + directory.getAbsolutePath());
        File[] listFiles = directory.listFiles(musicFileFilter);
        for (File file : listFiles) {
            if (!file.isDirectory()) {
                LOGGER.debug(">>> analyse file : " + file.getAbsolutePath());
                processFile(file);
            } else {
                exploreDirectory(file);
            }
        }
        // delete input directory
        directory.delete();
    }

    private void processFile(File musicFile) {
        try {
            musicFileProcessor.processFile(musicFile, integrationNumber);
        } catch (Exception e) {
            manageErrorFile(musicFile, e);
        }

        fileTraited++;
        sendProgressMessage(musicFile.getName(), getPourcent(totalFiles, fileTraited), false);
    }

    private void manageErrorFile(File musicFile, Exception exception) {
        LOGGER.error("Error while parsing " + musicFile.getAbsolutePath() + " " + exception.getMessage());
        musicFile.renameTo(new File(musicsErrorFolder.getPath() + musicFile.getName()));
    }

    private int getPourcent(int total, int done) {
        if (total != 0) {
            return new BigDecimal(done).divide(new BigDecimal(total), 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100)).intValue();
        } else {
            return 0;
        }
    }
}
