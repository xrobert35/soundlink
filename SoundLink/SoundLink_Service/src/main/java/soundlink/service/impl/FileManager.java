package soundlink.service.impl;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import soundlink.service.IFileManager;

/**
 * Class used to manage file inside the application
 * 
 * @author xrobert
 */
@Service
public class FileManager implements IFileManager {

    @Value("${file.upload.path}")
    private String filePath;

    public static final String separator = "/";

    private static Logger logger = Logger.getLogger(FileManager.class);


    @Override
    public File saveFile(MultipartFile multiPartFile, String path, String filename) {
        if (!multiPartFile.isEmpty()) {
            File userFolder = new File(filePath + FileManager.separator + path);
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
        return new File(filePath + FileManager.separator + path);
    }
}
