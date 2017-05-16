package soundlink.service.websocket.handler.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import soundlink.service.utils.ImageUtils;
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

        String cleanArtisteName = artiste.getName().replaceAll("[^a-zA-Z0-9.-]", "_");
        String cleanAlbumName = album.getName().replaceAll("[^a-zA-Z0-9.-]", "_");
        String musicFilePath = SoundlinkConstant.MUSICS_STORAGE_FOLDER + cleanArtisteName + "/" + cleanAlbumName;

        String cleanTitle = null;
        if (music == null) {
            music = createMusic(audioFile, album, integrationNumber);
            long musicFileSizeMo = musicFile.length() / (1024 * 1024);
            music.setMusicFileSize(musicFileSizeMo);
            cleanTitle = music.getTitle().replaceAll("[^a-zA-Z0-9.-]", "_");
            cleanTitle += musicFile.getAbsolutePath().substring(musicFile.getAbsolutePath().lastIndexOf('.'))
                    .toLowerCase();
            music.setMusicFilePath(musicFilePath + "/" + cleanTitle);
        } else {
            throw new Exception(
                    "Music already existe " + artiste.getName() + " - " + album.getName() + " - " + music.getTitle());
        }
        moveToMusicsFolder(musicFile, musicFilePath, cleanTitle);
    }

    private void moveToMusicsFolder(File musicFile, String newPath, String title) {
        File newFileFolder = new File(soundlinkFolder + newPath);
        if (!newFileFolder.exists()) {
            newFileFolder.mkdirs();
        }

        musicFile.renameTo(new File(newFileFolder.getPath() + "/" + title));
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
        album.setYear(tag.getFirst(FieldKey.YEAR));

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
        BufferedImage extractedCoverImage = extractBufferedImage(tag);
        byte[] coverFromTag = extractCoverFromTag(extractedCoverImage);
        byte[] smallCoverFromTag = extractSmallCoverFromTag(extractedCoverImage);
        byte[] blurrifiedCoverFromTag = extractBlurrifiedImageCoverFromTag(extractedCoverImage);
        if (coverFromTag != null) {
            File albumCover = new File(soundlinkFolder + SoundlinkConstant.ALBUMS_COVERS_FOLDER + album.getId());
            FileUtils.writeByteArrayToFile(albumCover, coverFromTag);
            File smallAlbumCover = new File(
                    soundlinkFolder + SoundlinkConstant.ALBUMS_COVERS_FOLDER + "small-" + album.getId());
            FileUtils.writeByteArrayToFile(smallAlbumCover, smallCoverFromTag);
            File burrifiedAlbumCover = new File(
                    soundlinkFolder + SoundlinkConstant.ALBUMS_COVERS_FOLDER + "blur-" + album.getId());
            FileUtils.writeByteArrayToFile(burrifiedAlbumCover, blurrifiedCoverFromTag);
        }
    };

    private void saveArtisteCover(Artiste artiste, Tag tag) throws IOException {
        BufferedImage extractBufferedImage = extractBufferedImage(tag);
        byte[] coverFromTag = extractCoverFromTag(extractBufferedImage);
        byte[] smallCoverFromTag = extractSmallCoverFromTag(extractBufferedImage);
        if (coverFromTag != null) {
            File artisteCover = new File(soundlinkFolder + SoundlinkConstant.ARTISTES_COVERS_FOLDER + artiste.getId());
            FileUtils.writeByteArrayToFile(artisteCover, coverFromTag);
            File smallArtisteCover = new File(
                    soundlinkFolder + SoundlinkConstant.ARTISTES_COVERS_FOLDER + "small-" + artiste.getId());
            FileUtils.writeByteArrayToFile(smallArtisteCover, smallCoverFromTag);
        }
    };

    private BufferedImage extractBufferedImage(Tag tag) throws IOException {
        Artwork firstArtwork = tag.getFirstArtwork();
        BufferedImage coverImage = null;
        if (firstArtwork != null) {
            coverImage = (BufferedImage) firstArtwork.getImage();
        }
        return coverImage;
    }

    private byte[] extractCoverFromTag(BufferedImage coverImage) throws IOException {
        return ImageUtils.reduceAndCreateJpgImage(coverImage, 150, 150, true);
    }

    private byte[] extractSmallCoverFromTag(BufferedImage coverImage) throws IOException {
        return ImageUtils.reduceAndCreateJpgImage(coverImage, 50, 50, true);
    }

    private byte[] extractBlurrifiedImageCoverFromTag(BufferedImage coverImage) throws IOException {
        return ImageUtils.reduceAndCreateBlurrifiedJpgImage(coverImage, 50, 50, true);
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
        music.setYear(tag.getFirst(FieldKey.YEAR));

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
        return artisteName.trim();
    }
}
