package soundlink.service.filter;

import java.io.File;
import java.io.FileFilter;

import org.springframework.stereotype.Component;

import soundlink.model.enums.AudioTypeEnum;

/**
 * Filter used to get only music file or folder from file scanning
 *
 * @author xrobert
 *
 */
@Component
public class MusicFileFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || getFileAudioType(file) != null;
    }

    private AudioTypeEnum getFileAudioType(File file) {
        String path = file.getAbsolutePath();
        int lastIndexOf = path.lastIndexOf('.');
        if (lastIndexOf != -1) {
            String extensions = path.substring(lastIndexOf).toLowerCase();
            return AudioTypeEnum.getAudioTypeFromCode(extensions);
        }
        return null;
    }
}
