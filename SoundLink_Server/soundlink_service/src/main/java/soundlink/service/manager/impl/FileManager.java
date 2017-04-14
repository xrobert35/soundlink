package soundlink.service.manager.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public static final Logger LOGGER = LogManager.getLogger(FileManager.class);

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

    @Override
    public boolean unzip(String path, String filename, boolean delete) {
        File zipFile = new File(path + FileManager.separator + filename);

        File unzipDirectory = new File(path + FileManager.separator + filename.substring(0, filename.lastIndexOf(".")));
        if (!unzipDirectory.exists()) {
            unzipDirectory.mkdir();
        }

        ZipInputStream zis = null;
        ZipEntry zipEntry = null;
        try {
            zis = new ZipInputStream(new FileInputStream(zipFile));
            zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String zipfileName = zipEntry.getName();
                File unzipFile = new File(unzipDirectory + File.separator + zipfileName);

                if (zipEntry.isDirectory()) {
                    unzipFile.mkdirs();
                } else {
                    extractFile(zis, unzipFile);
                }

                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            zis.close();
        } catch (IOException e) {
            LOGGER.error("Error while trying to unzip " + filename + " because ", e);
            try {
                zis.close();
            } catch (IOException e1) {
            }
            unzipDirectory.delete();
            return false;
        }

        // File is unzip , we delete him
        if (delete) {
            zipFile.delete();
        }

        return true;
    }

    private void extractFile(ZipInputStream zipIn, File unzipFile) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFile));
        byte[] bytesIn = new byte[2048];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
