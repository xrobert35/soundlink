package soundlink.dto;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Music dto
 *
 * @author xrobert
 *
 */
public class MusicDto {

    private Long id;

    private String title;

    private String artistName;

    private String albumName;

    private Integer trackNumber = null;

    private Integer durationInSeconde = null;

    private String byteRate;

    private File file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDurationInSeconde(Integer durationInSeconde) {
        this.durationInSeconde = durationInSeconde;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getByteRate() {
        return byteRate;
    }

    public void setByteRate(String byteRate) {
        this.byteRate = byteRate;
    }

    public String getDuration() {
        if (durationInSeconde != null) {
            float durationInMinute = (float) durationInSeconde / 60;
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(durationInMinute);
        }
        return null;
    }

    public void setDurationInSeconde(int durationInSeconde) {
        this.durationInSeconde = durationInSeconde;
    }

    public int getDurationInSeconde() {
        return durationInSeconde;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumName == null) ? 0 : albumName.hashCode());
        result = prime * result + ((artistName == null) ? 0 : artistName.hashCode());
        result = prime * result + ((byteRate == null) ? 0 : byteRate.hashCode());
        result = prime * result + ((durationInSeconde == null) ? 0 : durationInSeconde.hashCode());
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((trackNumber == null) ? 0 : trackNumber.hashCode());
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
        if (!(obj instanceof MusicDto)) {
            return false;
        }
        MusicDto other = (MusicDto) obj;
        if (albumName == null) {
            if (other.albumName != null) {
                return false;
            }
        } else if (!albumName.equals(other.albumName)) {
            return false;
        }
        if (artistName == null) {
            if (other.artistName != null) {
                return false;
            }
        } else if (!artistName.equals(other.artistName)) {
            return false;
        }
        if (byteRate == null) {
            if (other.byteRate != null) {
                return false;
            }
        } else if (!byteRate.equals(other.byteRate)) {
            return false;
        }
        if (durationInSeconde == null) {
            if (other.durationInSeconde != null) {
                return false;
            }
        } else if (!durationInSeconde.equals(other.durationInSeconde)) {
            return false;
        }
        if (file == null) {
            if (other.file != null) {
                return false;
            }
        } else if (!file.equals(other.file)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        if (trackNumber == null) {
            if (other.trackNumber != null) {
                return false;
            }
        } else if (!trackNumber.equals(other.trackNumber)) {
            return false;
        }
        return true;
    }
}
