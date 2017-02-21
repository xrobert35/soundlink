package soundlink.service.websocket.handler.impl;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Base64;

import javax.annotation.PostConstruct;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import soundlink.dto.socket.IntegrationProgressDto;
import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;
import soundlink.service.filter.MusicFileFilter;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IIntegrationManager;
import soundlink.service.manager.IMusicManager;
import soundlink.service.websocket.WebSocketExecutor;
import soundlink.service.websocket.handler.IMusicIntegrationExecutor;

@Component
public class MusicIntegrationExecutor extends WebSocketExecutor implements IMusicIntegrationExecutor {

    protected static final Logger LOGGER = LogManager.getLogger("soundlink_integration");

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

    @Autowired
    private MusicFileFilter musicFileFilter;

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private IMusicManager musicManager;

    @Autowired
    private IIntegrationManager integrationManager;

    private Integer integrationNumber;

    private File integrationFolder;

    private int totalFiles = 0;

    private int fileTraited = 0;

    @PostConstruct
    public void initBean() {
        integrationFolder = new File(soundlinkFolder);
        if (!integrationFolder.isDirectory()) {
            throw new IllegalArgumentException(
                    "Root path must be a valide directory : " + integrationFolder.getAbsolutePath());
        }
    }

    @Override
    public void execute() {
        integrationNumber = integrationManager.getNextIntegrationNumber();

        fileTraited = 0;
        totalFiles = countFilesInDirectory(integrationFolder);

        sendProgressMessage("Start integrating " + totalFiles + " musics ", 0, false);

        exploreDirectory(integrationFolder);

        sendProgressMessage("Integration ended", 100, true);
    }

    private void sendProgressMessage(String message, Integer progress, boolean end) {
        IntegrationProgressDto dto = new IntegrationProgressDto();
        dto.setMessage(message);
        dto.setProgress(progress);
        dto.setEnd(end);
        sendMessage(dto);
    }

    public static int countFilesInDirectory(File directory) {
        int count = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                count++;
            }
            if (file.isDirectory()) {
                count += countFilesInDirectory(file);
            }
        }
        return count;
    }

    /**
     * Method used to explore a directory. It will analyse each music file (see
     * MusicFileFilter) from the directory
     * 
     * @param directory
     */
    private void exploreDirectory(File directory) {
        LOGGER.debug("Explore directory : " + directory.getAbsolutePath());
        File[] listFiles = directory.listFiles(musicFileFilter);
        for (File file : listFiles) {
            if (!file.isDirectory()) {
                LOGGER.debug(">>> analyse file : " + file.getAbsolutePath());
                processFile(file);
            } else {
                exploreDirectory(file);
            }
        }
    }

    private void processFile(File musicFile) {
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
                artiste = createArtiste(artisteName);
            }

            // Check if the album already exist in database
            String albumName = tag.getFirst(FieldKey.ALBUM);
            Album album = albumManager.findAlbumByNameAndArtisteName(albumName, artisteName);
            if (album == null) {
                album = createAlbum(audioFile, artiste);
                sendProgressMessage(artiste.getName() + "/" + album.getName(), null, false);
            }

            String title = tag.getFirst(FieldKey.TITLE);
            Music music = musicManager.getMusicByTitleAlbumNameArtisteName(title, albumName, artisteName);

            long musicFileSizeMo = musicFile.length() / (1024 * 1024);

            if (music == null) {
                music = createMusic(audioFile, album);
                music.setMusicFileSize(musicFileSizeMo);
            }
        } catch (Exception e) {
            manageErrorFile(musicFile, e.getMessage());
            LOGGER.error("Error while parsing " + musicFile.getAbsolutePath() + " " + e.getMessage());
            e.printStackTrace();
        }
        fileTraited++;
        sendProgressMessage(null, getPourcent(totalFiles, fileTraited), false);
    }

    private int getPourcent(int total, int done) {
        if (total != 0) {
            return new BigDecimal(done).divide(new BigDecimal(total), 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100)).intValue();
        } else {
            return 0;
        }
    }

    private void manageErrorFile(File musicFile, String message) {
        // FileIntegrationErrorDto errorDto = new FileIntegrationErrorDto();
        // errorDto.setFilePath(musicFile.getAbsolutePath().substring(filePath.length()
        // + 1));
        // errorDto.setErrorMessage(message);
    }

    private Artiste createArtiste(String artisteName) {
        Artiste artiste = new Artiste();
        artiste.setName(artisteName);
        artiste.setIntegrationNumber(integrationNumber);

        LOGGER.debug("Creation of the artiste : " + artisteName);
        return artisteManager.create(artiste);
    }

    private Album createAlbum(AudioFile audioFile, Artiste artiste) throws IOException {
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

    private Music createMusic(AudioFile audioFile, Album album) {
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
            artiste = createArtiste(artisteName);
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
