package soundlink.service.business.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import soundlink.dto.AlbumDto;
import soundlink.dto.ArtisteDto;
import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;
import soundlink.service.business.IMusicExplorerService;
import soundlink.service.converter.AlbumDtoConverter;
import soundlink.service.converter.ArtisteDtoConverter;
import soundlink.service.converter.MusicDtoConverter;
import soundlink.service.filter.MusicFileFilter;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IMusicManager;

@Service
public final class MusicExplorerService implements IMusicExplorerService {

    private static final Logger LOGGER = Logger.getLogger(MusicExplorerService.class);

    @Value("${file.upload.path}")
    private String filePath;

    @Autowired
    private MusicFileFilter musicFileFilter;

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private IAlbumManager albumManager;

    @Autowired
    private IMusicManager musicManager;

    @Autowired
    private AlbumDtoConverter albumDtoConverter;

    @Autowired
    private ArtisteDtoConverter artisteDtoConverter;

    @Autowired
    private MusicDtoConverter musicDtoConveter;

    // Returned object
    private static final ThreadLocal<List<ArtisteDto>> artistesDto = new ThreadLocal<List<ArtisteDto>>() {
        @Override
        protected List<ArtisteDto> initialValue() {
            return new ArrayList<ArtisteDto>();
        }
    };

    @Override
    public void loadMusics() throws Exception {
        File mainDirectory = new File(filePath);
        if (mainDirectory.isDirectory()) {
            exploreDirectory(mainDirectory);
        } else {
            throw new IllegalArgumentException("Root path must be a valide directory !");
        }
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

            String artisteName = tag.getFirst(FieldKey.ARTIST);

            // Check if the artiste already exist in database
            Artiste artiste = artisteManager.getArtisteByName(artisteName);
            if (artiste == null) {
                artiste = createArtiste(artisteName);
            }
            ArtisteDto artisteDto = artistesDto.get().stream()
                    .filter(artisteToCheck -> artisteToCheck.getName().equals(artisteName)).findFirst().get();

            if (artisteDto == null) {
                artisteDto = artisteDtoConverter.convertToDto(artiste);
            }

            // Check if the album already exist in database
            String albumName = tag.getFirst(FieldKey.ALBUM);
            Album album = albumManager.findAlbumByNameAndArtisteName(albumName, artisteName);
            if (album == null) {
                album = createAlbum(audioFile, artiste);
            }

            AlbumDto albumDto = artisteDto.getAlbums().stream()
                    .filter(albumToCheck -> albumToCheck.getName().equals(albumName)).findFirst().get();

            if (albumDto == null) {
                albumDto = albumDtoConverter.convertToDto(album);
                artisteDto.getAlbums().add(albumDto);
            }

            String title = tag.getFirst(FieldKey.TITLE);
            Music music = musicManager.getMusicByTitle(title);
            if (music == null) {
                createMusic(audioFile, album);
            }
            albumDto.getMusics().add(musicDtoConveter.convertToDto(music));
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

        LOGGER.debug("Creation of the album : " + album.getName());
        return albumManager.create(album);
    }

    private Artiste createArtiste(String artisteName) {
        LOGGER.debug("Creation of the artiste : " + artisteName);
        Artiste artiste = new Artiste();
        artiste.setName(artisteName);
        artisteManager.create(artiste);
        return artiste;
    }

    private Music createMusic(AudioFile audioFile, Album album) {
        Music music = new Music();
        try {
            Tag tag = audioFile.getTag();
            AudioHeader audioHeader = audioFile.getAudioHeader();

            music.setMusicFilePath(audioFile.getFile().getAbsolutePath());
            music.setTrackNumber(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
            music.setTitle(tag.getFirst(FieldKey.TITLE));
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
