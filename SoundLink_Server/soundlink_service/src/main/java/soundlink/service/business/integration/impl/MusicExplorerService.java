package soundlink.service.business.integration.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import javax.imageio.ImageIO;

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
import org.springframework.stereotype.Service;

import soundlink.dto.AlbumDto;
import soundlink.dto.ArtisteDto;
import soundlink.dto.FileIntegrationErrorDto;
import soundlink.dto.IntegrationDto;
import soundlink.dto.MusicDto;
import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;
import soundlink.service.business.integration.IMusicExplorerService;
import soundlink.service.converter.AlbumDtoConverter;
import soundlink.service.converter.ArtisteDtoConverter;
import soundlink.service.converter.MusicDtoConverter;
import soundlink.service.filter.MusicFileFilter;
import soundlink.service.manager.IAlbumManager;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IMusicManager;

@Service
public final class MusicExplorerService implements IMusicExplorerService {

    private static final Logger LOGGER = LogManager.getLogger(MusicExplorerService.class);

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

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
    private static final ThreadLocal<IntegrationDto> integrationDto = new ThreadLocal<IntegrationDto>() {
        @Override
        protected IntegrationDto initialValue() {
            return new IntegrationDto();
        }
    };

    @Override
    public IntegrationDto loadMusics() {
        filePath = soundlinkFolder + "/integration";

        File mainDirectory = new File(filePath);
        if (mainDirectory.isDirectory()) {
            exploreDirectory(mainDirectory);
        } else {
            throw new IllegalArgumentException("Root path must be a valide directory :" + filePath);
        }
        return integrationDto.get();
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

            // Check if the artiste already exist in database
            String artisteName = tag.getFirst(FieldKey.ARTIST);
            Artiste artiste = artisteManager.getArtisteByName(artisteName);
            ArtisteDto artisteDto = null;
            if (artiste == null) {
                artiste = createArtiste(artisteName);
                artisteDto = artisteDtoConverter.convertToDto(artiste);
                integrationDto.get().getArtistes().add(artisteDto);
            } else {
                artisteDto = getArtisteDto(artiste);
            }

            // Check if the album already exist in database
            String albumName = tag.getFirst(FieldKey.ALBUM);
            Album album = albumManager.findAlbumByNameAndArtisteName(albumName, artisteName);
            AlbumDto albumDto = null;
            if (album == null) {
                album = createAlbum(audioFile, artiste);
                albumDto = albumDtoConverter.convertToDto(album);
                artisteDto.getAlbums().add(albumDto);
            } else {
                albumDto = getAlbumDto(artisteDto, album);
            }

            String title = tag.getFirst(FieldKey.TITLE);
            Music music = musicManager.getMusicByTitle(title);

            long musicSizeInOctet = musicFile.length();

            MusicDto musicDto = null;
            if (music == null) {
                music = createMusic(audioFile, album);
                music.setMusicFileSize(musicSizeInOctet);
                musicDto = musicDtoConveter.convertToDto(music);
                albumDto.getMusics().add(musicDto);
            } else {
                musicDto = getMusicDto(albumDto, music);
            }
        } catch (Exception e) {
            manageErrorFile(musicFile, e.getMessage());
            LOGGER.error("Error while parsing " + musicFile.getAbsolutePath() + e.getMessage());
        }
    }

    private void manageErrorFile(File musicFile, String message) {
        FileIntegrationErrorDto errorDto = new FileIntegrationErrorDto();
        errorDto.setFilePath(musicFile.getAbsolutePath().substring(filePath.length() + 1));
        errorDto.setErrorMessage(message);
        integrationDto.get().getErrors().add(errorDto);
    }

    private MusicDto getMusicDto(AlbumDto albumDto, Music music) {
        Optional<MusicDto> possibleMusic = albumDto.getMusics().stream()
                .filter(musicToCheck -> musicToCheck.getTitle().equals(music.getTitle())).findFirst();
        if (possibleMusic.isPresent()) {
            return possibleMusic.get();
        } else {
            MusicDto musicDto = musicDtoConveter.convertToDto(music);
            albumDto.getMusics().add(musicDto);
            return musicDto;
        }
    }

    private AlbumDto getAlbumDto(ArtisteDto artisteDto, Album album) {
        Optional<AlbumDto> possibleAlbum = artisteDto.getAlbums().stream()
                .filter(albumToCheck -> albumToCheck.getName().equals(album.getName())).findFirst();
        if (possibleAlbum.isPresent()) {
            return possibleAlbum.get();
        } else {
            AlbumDto albumDto = albumDtoConverter.convertToDto(album);
            artisteDto.getAlbums().add(albumDto);
            return albumDto;
        }
    }

    private ArtisteDto getArtisteDto(Artiste artiste) {
        Optional<ArtisteDto> possibleArtiste = integrationDto.get().getArtistes().stream()
                .filter(artisteToCheck -> artisteToCheck.getName().equals(artiste.getName())).findFirst();
        if (possibleArtiste.isPresent()) {
            return possibleArtiste.get();
        } else {
            ArtisteDto artisteDto = artisteDtoConverter.convertToDto(artiste);
            integrationDto.get().getArtistes().add(artisteDto);
            return artisteDto;
        }
    }

    private Artiste createArtiste(String artisteName) {
        Artiste artiste = new Artiste();
        artiste.setName(artisteName);

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
        album.setArtiste(artiste);

        Artwork firstArtwork = tag.getFirstArtwork();
        if (firstArtwork != null) {
            BufferedImage coverImage = (BufferedImage) firstArtwork.getImage();
            Image scaledInstance = coverImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            BufferedImage resizedImage = new BufferedImage(200, 200, coverImage.getType());
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledInstance, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);

            String imageResized = new String(Base64.getEncoder().encode(baos.toByteArray()));
            album.setCover(imageResized);

            // albumDescriptor.setCoverGeneralColor(ImageUtils.getImageHexMainColor(coverImage));
        }
        album.setAlbumDirectory(audioFile.getFile().getParentFile().getAbsolutePath());

        LOGGER.debug("Creation of the album : " + album.getName());
        return albumManager.create(album);
    }

    private Music createMusic(AudioFile audioFile, Album album) {
        Music music = new Music();
        Tag tag = audioFile.getTag();
        AudioHeader audioHeader = audioFile.getAudioHeader();

        music.setMusicFilePath(audioFile.getFile().getAbsolutePath());
        music.setTrackNumber(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
        music.setTitle(tag.getFirst(FieldKey.TITLE));
        music.setDurationInSeconde(audioHeader.getTrackLength());
        music.setBitRate(audioHeader.getBitRate() + " kbps");
        music.setExtension(audioFile.getExt());

        music.setAlbum(album);

        LOGGER.debug("Creation of the music : " + music.getTitle());
        return musicManager.create(music);
    }
}
