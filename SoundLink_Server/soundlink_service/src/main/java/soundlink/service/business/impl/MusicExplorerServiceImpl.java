package soundlink.service.business.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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

import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;
import soundlink.service.business.MusicExplorerService;
import soundlink.service.filter.MusicFileFilter;
import soundlink.service.manager.AlbumManager;
import soundlink.service.manager.ArtisteManager;
import soundlink.service.manager.MusicManager;

@Service
public final class MusicExplorerServiceImpl implements MusicExplorerService {

    private static final Logger LOGGER = Logger.getLogger(MusicExplorerServiceImpl.class);

    @Autowired
    private MusicFileFilter musicFileFilter;

    @Autowired
    private AlbumManager albumManager;

    @Autowired
    private ArtisteManager artisteManager;

    @Autowired
    private MusicManager musicManager;

    @Override
    public void loadMusicsFromPath(String rootPath) throws Exception {
        File mainDirectory = new File(rootPath);
        if (mainDirectory.isDirectory()) {
            discoverDirectory(mainDirectory);
        } else {
            throw new Exception("Root path must be a valide directory ! ");
        }
    }

    private void discoverDirectory(File directory) {
        File[] listFiles = directory.listFiles(musicFileFilter);
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    processFile(file);
                }
            }
        }
    }

    private void processFile(File musicFile) {
        try {
            AudioFile audioFile = AudioFileIO.read(musicFile);
            Tag tag = audioFile.getTag();

            String artisteName = tag.getFirst(FieldKey.ARTIST);
            String albumName = tag.getFirst(FieldKey.ALBUM);

            // Check if the artiste already exist in database
            Artiste artiste = artisteManager.getArtisteByName(artisteName);
            if (artiste == null) {
                artiste = createArtiste(artisteName);
            }
            // Check if the album already exist in database
            Album album = albumManager.findAlbumByNameAndArtisteName(albumName, artisteName);
            if (album == null) {
                album = createAlbum(audioFile, artiste);
            }

            // TODO check if music already exist
            createMusic(audioFile, album);
        } catch (Exception e) {
            LOGGER.error("Error while parsing " + musicFile.getAbsolutePath() + e.getMessage());
        }
    }

    private Album createAlbum(AudioFile audioFile, Artiste artiste) throws IOException {
        AudioHeader audioHeader = audioFile.getAudioHeader();
        Tag tag = audioFile.getTag();

        Album album = new Album();
        album.setName(tag.getFirst(FieldKey.ALBUM));
        album.setBitRate(audioHeader.getBitRate());
        album.setExtension(audioFile.getExt());
        album.setArtiste(artiste);

        Artwork firstArtwork = tag.getFirstArtwork();
        if (firstArtwork != null) {
            BufferedImage coverImage = (BufferedImage) firstArtwork.getImage();
            // coverImage = ImageUtils.scalrResize(coverImage, 150);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(coverImage, "jpg", baos);
            byte[] dataAfter = baos.toByteArray();
            album.setCover(dataAfter);
            // albumDescriptor.setCoverGeneralColor(ImageUtils.getImageHexMainColor(coverImage));
        }
        album.setAlbumDirectory(audioFile.getFile().getParentFile().getAbsolutePath());
        return albumManager.create(album);
    }

    private Artiste createArtiste(String artisteName) {
        Artiste artiste = new Artiste();
        artiste.setName(artisteName);
        return artisteManager.create(artiste);
    }

    private Music createMusic(AudioFile audioFile, Album album) {
        Music music = new Music();
        try {
            Tag tag = audioFile.getTag();
            AudioHeader audioHeader = audioFile.getAudioHeader();

            music.setMusicFilePath(audioFile.getFile().getAbsolutePath());
            music.setTrackNumber(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
            music.setName(tag.getFirst(FieldKey.TITLE));
            music.setDurationInSeconde(audioHeader.getTrackLength());
            music.setBitRate(audioHeader.getBitRate());
            music.setExtension(audioFile.getExt());

            music.setAlbum(album);

            musicManager.create(music);
        } catch (Exception e) {
            LOGGER.error("Error while parsing " + audioFile.getFile().getAbsolutePath() + e.getMessage());
        }
        return music;
    }
}
