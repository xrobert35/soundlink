package soundlink.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Album dto
 *
 * @author xrobert
 *
 */
public class AlbumDto {

    private Long id;

    private String artisteName;

    private String name;

    private String bitRate;

    private String extension;

    private String coverGeneralColor;

    private byte[] cover;

    private File albumDirectory;

    private List<MusicDto> musics = new ArrayList<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtisteName() {
        return artisteName;
    }

    public void setArtisteName(String artisteName) {
        this.artisteName = artisteName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MusicDto> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicDto> musics) {
        this.musics = musics;
    }

    public File getAlbumDirectory() {
        return albumDirectory;
    }

    public void setAlbumDirectory(File albumDirectory) {
        this.albumDirectory = albumDirectory;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverGeneralColor() {
        return coverGeneralColor;
    }

    public void setCoverGeneralColor(String coverGeneralColor) {
        this.coverGeneralColor = coverGeneralColor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((artisteName == null) ? 0 : artisteName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AlbumDto)) {
            return false;
        }
        AlbumDto other = (AlbumDto) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (artisteName == null) {
            if (other.artisteName != null) {
                return false;
            }
        } else if (!artisteName.equals(other.artisteName)) {
            return false;
        }
        return true;
    }
}
