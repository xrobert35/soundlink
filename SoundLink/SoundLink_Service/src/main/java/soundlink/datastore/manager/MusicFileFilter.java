package soundlink.datastore.manager;

import java.io.File;
import java.io.FileFilter;

public class MusicFileFilter implements FileFilter {

	@Override
	public boolean accept(File file) {
		return file.isDirectory() || DataStoreFileManager.getFileAudioType(file) != null;
	}
}
