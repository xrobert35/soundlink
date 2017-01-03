package soundlink.dto;

/**
 * Music dto
 *
 * @author xrobert
 *
 */
public class MusicDto {

    private Integer id;

    private String title;

    private String artistName;

    private String albumName;

    private Integer trackNumber = null;

    private Integer durationInSeconde = null;

    private String bitRate;

    private String musicFilePath;

    private String musicFileSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getMusicFilePath() {
        return musicFilePath;
    }

    public void setMusicFilePath(String musicFilePath) {
        this.musicFilePath = musicFilePath;
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

    public String getMusicFileSize() {
        return musicFileSize;
    }

    public void setMusicFileSize(String musicFileSize) {
        this.musicFileSize = musicFileSize;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumName == null) ? 0 : albumName.hashCode());
        result = prime * result + ((artistName == null) ? 0 : artistName.hashCode());
        result = prime * result + ((bitRate == null) ? 0 : bitRate.hashCode());
        result = prime * result + ((durationInSeconde == null) ? 0 : durationInSeconde.hashCode());
        result = prime * result + ((musicFilePath == null) ? 0 : musicFilePath.hashCode());
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
        if (bitRate == null) {
            if (other.bitRate != null) {
                return false;
            }
        } else if (!bitRate.equals(other.bitRate)) {
            return false;
        }
        if (durationInSeconde == null) {
            if (other.durationInSeconde != null) {
                return false;
            }
        } else if (!durationInSeconde.equals(other.durationInSeconde)) {
            return false;
        }
        if (musicFilePath == null) {
            if (other.musicFilePath != null) {
                return false;
            }
        } else if (!musicFilePath.equals(other.musicFilePath)) {
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
