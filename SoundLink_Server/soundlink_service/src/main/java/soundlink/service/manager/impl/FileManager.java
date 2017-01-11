package soundlink.service.manager.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import soundlink.service.manager.IFileManager;

/**
 * Class used to manage file inside the application
 * 
 * @author xrobert
 */
@Service
public class FileManager implements IFileManager {

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

    public static final String separator = "/";

    private static Logger logger = LogManager.getLogger(FileManager.class);

    @Override
    public File saveFile(MultipartFile multiPartFile, String path, String filename) {
        if (!multiPartFile.isEmpty()) {
            File userFolder = new File(soundlinkFolder + FileManager.separator + path);
            if (!userFolder.exists()) {
                userFolder.mkdirs();
            }
            File userFile = new File(userFolder + FileManager.separator + filename);
            try {
                byte[] bytes = multiPartFile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(userFile));
                stream.write(bytes);
                stream.close();
                logger.debug("You successfully uploaded file : " + userFile.getAbsolutePath());
                return userFile;
            } catch (Exception e) {
                logger.error("You failed to upload " + userFile.getAbsolutePath() + " => " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public File getFile(String path) {
        return new File(soundlinkFolder + FileManager.separator + path);
    }
}
