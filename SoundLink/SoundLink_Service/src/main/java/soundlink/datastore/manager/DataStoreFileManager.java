package soundlink.datastore.manager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import image.ImageUtils;
import soundlink.datastore.AlbumDataStore;
import soundlink.model.constant.AudioTypeEnum;
import soundlink.model.music.AlbumDescriptor;
import soundlink.model.music.MusicDescriptor;

@Service
public final class DataStoreFileManager {

	private static final Logger LOGGER = Logger.getLogger(DataStoreFileManager.class);

	@Autowired
	private AlbumDataStore albumDatastore;

	public void initDataStoreFromPath(String rootPath) throws Exception {
		File mainDirectory = new File(rootPath);
		if (mainDirectory.isDirectory()) {
			discoverDirectory(mainDirectory);
		} else {
			throw new Exception("Root path must be a valide directory ! ");
		}
	}

	private void discoverDirectory(File directory) {
		File[] listFiles = directory.listFiles(new MusicFileFilter());
		if (listFiles != null) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					discoverDirectory(file);
				} else {
					albumDatastore.addAlbum(createAlbum(file));
					break;
				}
			}
		}
	}

	private AlbumDescriptor createAlbum(File oneMusicFileFromAlbum) {
		AlbumDescriptor albumDescriptor = new AlbumDescriptor();

		try {
			AudioFile audioFile = AudioFileIO.read(oneMusicFileFromAlbum);
			Tag tag = audioFile.getTag();
			AudioHeader audioHeader = audioFile.getAudioHeader();
			Artwork firstArtwork = tag.getFirstArtwork();

			albumDescriptor.setArtisteName(tag.getFirst(FieldKey.ARTIST));
			albumDescriptor.setAlbumName(tag.getFirst(FieldKey.ALBUM));
			if (firstArtwork != null) {
				BufferedImage coverImage = (BufferedImage) firstArtwork.getImage();
				coverImage = ImageUtils.scalrResize(coverImage, 150);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(coverImage, "jpg", baos);
				byte[] dataAfter = baos.toByteArray();
				albumDescriptor.setCover(dataAfter);
				albumDescriptor.setCoverGeneralColor(ImageUtils.getImageHexMainColor(coverImage));
			}
			albumDescriptor.setBitRate(audioHeader.getBitRate());
			albumDescriptor.setExtension(audioFile.getExt());
			albumDescriptor.setAlbumDirectory(oneMusicFileFromAlbum.getParentFile());
		} catch (Exception e) {
			LOGGER.error("Error while parsing " + oneMusicFileFromAlbum.getAbsolutePath() + e.getMessage());
		}
		return albumDescriptor;
	}

	public static AudioTypeEnum getFileAudioType(File file) {
		String path = file.getAbsolutePath();
		int lastIndexOf = path.lastIndexOf('.');
		if (lastIndexOf != -1) {
			String extensions = path.substring(lastIndexOf).toLowerCase();
			return AudioTypeEnum.getAudioTypeFromCode(extensions);
		}
		return null;

	}

	public void initAlbumMusics(AlbumDescriptor album) {
		if (album.getMusics() == null) {
			HashSet<MusicDescriptor> musics = new HashSet<MusicDescriptor>();
			File albumDirectory = album.getAlbumDirectory();
			for (File musicFile : albumDirectory.listFiles(new MusicFileFilter())) {
				if (!musicFile.isDirectory()) {
					musics.add(createMusic(musicFile));
				}
			}
			album.setMusics(musics);
		}
	}

	private MusicDescriptor createMusic(File musicFile) {
		MusicDescriptor musicDescriptor = new MusicDescriptor();
		try {
			AudioFile audioFile = AudioFileIO.read(musicFile);
			Tag tag = audioFile.getTag();
			AudioHeader audioHeader = audioFile.getAudioHeader();

			musicDescriptor.setFile(musicFile);
			musicDescriptor.setTrackNumber(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
			musicDescriptor.setTitle(tag.getFirst(FieldKey.TITLE));
			musicDescriptor.setAlbum(tag.getFirst(FieldKey.ALBUM));
			musicDescriptor.setArtist(tag.getFirst(FieldKey.ARTIST));

			musicDescriptor.setDurationInSeconde(audioHeader.getTrackLength());
		} catch (Exception e) {
			LOGGER.error("Error while parsing " + musicFile.getAbsolutePath() + e.getMessage());
			try {
				throw new tataBibicheException();
			} catch (tataBibicheException e1) {
				e1.printStackTrace();
				System.out.println(
						"Je suis un bouc sorti du néant issu du fion, à laquelle participe le blaireau de copain imaginaire de yohann le soulard!");
			}
		}
		return musicDescriptor;
	}
}
