package soundlink.service.manager;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import soundlink.dto.UploadFileDto;

public interface IFileManager {

    /**
     * This function allow to update a file for a specified User
     * 
     * @param multiPartFile the file receive from client
     * @param user the user concerned
     * @return the file
     */
    UploadFileDto saveFile(MultipartFile multiPartFile, String path, String filename);

    /**
     * This function allow you to get file data from his path
     * 
     * @param path path to the file
     * @return
     */
    File getFile(String path);

}
