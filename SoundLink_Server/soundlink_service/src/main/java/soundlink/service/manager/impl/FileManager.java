package soundlink.service.manager.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import soundlink.dto.UploadFileDto;
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

    @Override
    public UploadFileDto saveFile(MultipartFile multiPartFile, String path, String filename) {
        UploadFileDto dto = new UploadFileDto();
        if (!multiPartFile.isEmpty()) {
            try {
                byte[] bytes = multiPartFile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(path + FileManager.separator + filename)));
                stream.write(bytes);
                stream.close();
                dto.setMessage("You successfully uploaded " + filename);
            } catch (Exception e) {
                dto.setMessage("You failed to upload " + filename + " => " + e.getMessage());
            }
        } else {
            dto.setMessage("You failed to upload " + filename + " because the file was empty");
        }
        return dto;
    }

    @Override
    public File getFile(String path) {
        return new File(soundlinkFolder + FileManager.separator + path);
    }
}
