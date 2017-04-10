package soundlink.service.websocket.handler.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;
import soundlink.service.constant.SoundlinkConstant;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IMusicManager;
import soundlink.service.websocket.handler.IMusicFileProcessor;

@Component
public class MusicFileProcessor implements IMusicFileProcessor {

    protected static final Logger LOGGER = LogManager.getLogger("soundlink_integration");

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private IMusicManager musicManager;

    @Override
    public void processFile(File musicFile, Integer integrationNumber) throws Exception {
        AudioFile audioFile = AudioFileIO.read(musicFile);
        Tag tag = audioFile.getTag();
        if (tag == null) {
            throw new Exception("No tag found !");
        }
        // Check if the artiste already exist in database
        String artisteName = getArtisteName(tag, FieldKey.ALBUM_ARTIST);
        if (StringUtils.isBlank(artisteName)) {
            artisteName = getArtisteName(tag, FieldKey.ARTIST);
        }
        Artiste artiste = artisteManager.getArtisteByName(artisteName);
        if (artiste == null) {
            artiste = createArtiste(audioFile, artisteName, integrationNumber);
        }

        // Check if the album already exist in database
        String albumName = tag.getFirst(FieldKey.ALBUM);
        Album album = albumManager.findAlbumByNameAndArtisteName(albumName, artisteName);
        if (album == null) {
            album = createAlbum(audioFile, artiste, integrationNumber);
        }

        String title = tag.getFirst(FieldKey.TITLE);
        Music music = musicManager.getMusicByTitleAlbumNameArtisteName(title, albumName, artisteName);

        String cleanArtisteName = artiste.getName().replaceAll("[^a-zA-Z0-9. -]", "_");
        String cleanAlbumName = album.getName().replaceAll("[^a-zA-Z0-9. -]", "_");
        String musicFilePath = SoundlinkConstant.MUSICS_STORAGE_FOLDER + cleanArtisteName + "/" + cleanAlbumName;

        String cleanTitle = null;
        if (music == null) {
            music = createMusic(audioFile, album, integrationNumber);
            long musicFileSizeMo = musicFile.length() / (1024 * 1024);
            music.setMusicFileSize(musicFileSizeMo);
            cleanTitle = music.getTitle().replaceAll("[^a-zA-Z0-9. -]", "_");
            music.setMusicFilePath(musicFilePath + "/" + cleanTitle);
        }
        moveToMusicsFolder(musicFile, musicFilePath, cleanTitle);
    }

    private void moveToMusicsFolder(File musicFile, String newPath, String title) {
        File newFileFolder = new File(soundlinkFolder + newPath);
        if (!newFileFolder.exists()) {
            newFileFolder.mkdirs();
        }

        String extensions = musicFile.getAbsolutePath().substring(musicFile.getAbsolutePath().lastIndexOf('.'))
                .toLowerCase();
        musicFile.renameTo(new File(newFileFolder.getPath() + "/" + title + extensions));
    }

    private Artiste createArtiste(AudioFile audioFile, String artisteName, Integer integrationNumber)
            throws IOException {
        Artiste artiste = new Artiste();
        artiste.setName(artisteName);
        artiste.setIntegrationNumber(integrationNumber);

        LOGGER.debug("Creation of the artiste : " + artisteName);
        artiste = artisteManager.create(artiste);

        // Id is needed
        if (audioFile != null) {
            Tag tag = audioFile.getTag();
            saveArtisteCover(artiste, tag);
        }

        return artiste;
    }

    private Album createAlbum(AudioFile audioFile, Artiste artiste, Integer integrationNumber) throws IOException {
        AudioHeader audioHeader = audioFile.getAudioHeader();
        Tag tag = audioFile.getTag();

        Album album = new Album();
        album.setName(tag.getFirst(FieldKey.ALBUM));
        album.setBitRate(audioHeader.getBitRate());
        album.setExtension(audioFile.getExt());
        album.setIntegrationNumber(integrationNumber);
        album.setArtiste(artiste);

        String albumPath = audioFile.getFile().getParentFile().getAbsolutePath().substring(soundlinkFolder.length());
        album.setAlbumDirectory(albumPath);

        artiste.getAlbums().add(album);

        album.setAlbumDirectory(audioFile.getFile().getParentFile().getAbsolutePath());

        LOGGER.debug("Creation of the album : " + album.getName());
        album = albumManager.create(album);

        // Id is needed
        saveAlbumCover(album, tag);

        return album;
    }

    private void saveAlbumCover(Album album, Tag tag) throws IOException {
        byte[] coverFromTag = exactCoverFromTag(tag);
        if (coverFromTag != null) {
            File artisteCover = new File(soundlinkFolder + SoundlinkConstant.ALBUMS_COVERS_FOLDER + album.getId());
            FileUtils.writeByteArrayToFile(artisteCover, coverFromTag);
        }
    };

    private void saveArtisteCover(Artiste artiste, Tag tag) throws IOException {
        byte[] coverFromTag = exactCoverFromTag(tag);
        if (coverFromTag != null) {
            File artisteCover = new File(soundlinkFolder + SoundlinkConstant.ARTISTES_COVERS_FOLDER + artiste.getId());
            FileUtils.writeByteArrayToFile(artisteCover, coverFromTag);
        }
    };

    private byte[] exactCoverFromTag(Tag tag) throws IOException {
        Artwork firstArtwork = tag.getFirstArtwork();
        if (firstArtwork != null) {
            BufferedImage coverImage = (BufferedImage) firstArtwork.getImage();
            int type = coverImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : coverImage.getType();
            coverImage = resizeImageWithHint(coverImage, type);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(coverImage, "png", baos);
            return baos.toByteArray();
        }
        return null;
    }

    private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {
        return Scalr.resize(originalImage, Method.QUALITY, Mode.FIT_EXACT, 110, 110);
    }

    private Music createMusic(AudioFile audioFile, Album album, Integer integrationNumber) throws IOException {
        Music music = new Music();
        Tag tag = audioFile.getTag();
        AudioHeader audioHeader = audioFile.getAudioHeader();

        music.setIntegrationNumber(integrationNumber);

        String track = tag.getFirst(FieldKey.TRACK);
        if (!StringUtils.isEmpty(track)) {
            if (track.contains("/")) {
                track = track.substring(0, track.indexOf("/"));
            }
            music.setTrackNumber(Integer.valueOf(track));
        }

        String disc = tag.getFirst(FieldKey.DISC_NO);
        if (!StringUtils.isEmpty(disc)) {
            if (disc.contains("/")) {
                disc = disc.substring(0, disc.indexOf("/"));
            }
            music.setDiscNumber(Integer.valueOf(disc));
        }

        music.setTitle(tag.getFirst(FieldKey.TITLE));

        String artisteName = getArtisteName(tag, FieldKey.ARTIST);
        Artiste artiste = artisteManager.getArtisteByName(artisteName);
        if (artiste == null) {
            artiste = createArtiste(null, artisteName, integrationNumber);
        }
        music.setArtiste(artiste);

        music.setDurationInSeconde(audioHeader.getTrackLength());
        music.setBitRate(audioHeader.getBitRate() + " kbps");
        music.setExtension(audioFile.getExt());

        music.setAlbum(album);
        album.getMusics().add(music);

        LOGGER.debug("Creation of the music : " + music.getTitle());
        return musicManager.create(music);
    }

    private String getArtisteName(Tag tag, FieldKey fieldKey) {
        String artisteName = tag.getFirst(fieldKey);
        if (artisteName != null && artisteName.endsWith(";")) {
            artisteName = artisteName.substring(0, artisteName.length() - 1);
        }
        return artisteName;
    }
}
