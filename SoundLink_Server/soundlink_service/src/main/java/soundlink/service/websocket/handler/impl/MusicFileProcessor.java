package soundlink.service.websocket.handler.impl;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

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
import org.springframework.stereotype.Component;

import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IMusicManager;
import soundlink.service.websocket.handler.IMusicFileProcessor;

@Component
public class MusicFileProcessor implements IMusicFileProcessor {

    protected static final Logger LOGGER = LogManager.getLogger("soundlink_integration");

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private IMusicManager musicManager;

    @Override
    public void processFile(File musicFile, Integer integrationNumber) {
        try {

            AudioFile audioFile = AudioFileIO.read(musicFile);
            Tag tag = audioFile.getTag();
            if (tag == null) {
                throw new Exception("No tag found !");
            }
            // Check if the artiste already exist in database
            String artisteName = tag.getFirst(FieldKey.ALBUM_ARTIST);
            if (StringUtils.isBlank(artisteName)) {
                artisteName = tag.getFirst(FieldKey.ARTIST);
            }
            Artiste artiste = artisteManager.getArtisteByName(artisteName);
            if (artiste == null) {
                artiste = createArtiste(artisteName, integrationNumber);
            }

            // Check if the album already exist in database
            String albumName = tag.getFirst(FieldKey.ALBUM);
            Album album = albumManager.findAlbumByNameAndArtisteName(albumName, artisteName);
            if (album == null) {
                album = createAlbum(audioFile, artiste, integrationNumber);
            }

            String title = tag.getFirst(FieldKey.TITLE);
            Music music = musicManager.getMusicByTitleAlbumNameArtisteName(title, albumName, artisteName);

            long musicFileSizeMo = musicFile.length() / (1024 * 1024);

            if (music == null) {
                music = createMusic(audioFile, album, integrationNumber);
                music.setMusicFileSize(musicFileSizeMo);
            }
        } catch (Exception e) {
            manageErrorFile(musicFile, e.getMessage());
            LOGGER.error("Error while parsing " + musicFile.getAbsolutePath() + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void manageErrorFile(File musicFile, String message) {
        // FileIntegrationErrorDto errorDto = new FileIntegrationErrorDto();
        // errorDto.setFilePath(musicFile.getAbsolutePath().substring(filePath.length()
        // + 1));
        // errorDto.setErrorMessage(message);
    }

    private Artiste createArtiste(String artisteName, Integer integrationNumber) {
        Artiste artiste = new Artiste();
        artiste.setName(artisteName);
        artiste.setIntegrationNumber(integrationNumber);

        LOGGER.debug("Creation of the artiste : " + artisteName);
        return artisteManager.create(artiste);
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
        artiste.getAlbums().add(album);

        Artwork firstArtwork = tag.getFirstArtwork();
        if (firstArtwork != null) {
            BufferedImage coverImage = (BufferedImage) firstArtwork.getImage();
            int type = coverImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : coverImage.getType();
            coverImage = resizeImageWithHint(coverImage, type);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(coverImage, "png", baos);

            String imageResized = new String(Base64.getEncoder().encode(baos.toByteArray()));
            album.setCover(imageResized);
        }

        album.setAlbumDirectory(audioFile.getFile().getParentFile().getAbsolutePath());

        LOGGER.debug("Creation of the album : " + album.getName());
        return albumManager.create(album);
    }

    private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

        BufferedImage resizedImage = new BufferedImage(110, 110, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 110, 110, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private Music createMusic(AudioFile audioFile, Album album, Integer integrationNumber) {
        Music music = new Music();
        Tag tag = audioFile.getTag();
        AudioHeader audioHeader = audioFile.getAudioHeader();

        music.setMusicFilePath(audioFile.getFile().getAbsolutePath());
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

        String artisteName = tag.getFirst(FieldKey.ARTIST);
        Artiste artiste = artisteManager.getArtisteByName(artisteName);
        if (artiste == null) {
            artiste = createArtiste(artisteName, integrationNumber);
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

}
