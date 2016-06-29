package soundlink.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Music Entity
 *
 * @author xrobert
 *
 */
@Entity
@Table(name = "music", schema = "public")
public class Music {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private Integer trackNumber = null;

    private Integer durationInSeconde = null;

    private String bitRate;

    private String extension;

    private String musicFilePath;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "musics")
    private Set<Playlist> playlist = new HashSet<Playlist>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Integer getDurationInSeconde() {
        return durationInSeconde;
    }

    public void setDurationInSeconde(Integer durationInSeconde) {
        this.durationInSeconde = durationInSeconde;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String byteRate) {
        this.bitRate = byteRate;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Set<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Set<Playlist> playlist) {
        this.playlist = playlist;
    }

    public String getMusicFilePath() {
        return musicFilePath;
    }

    public void setMusicFilePath(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((album == null) ? 0 : album.hashCode());
        result = prime * result + ((bitRate == null) ? 0 : bitRate.hashCode());
        result = prime * result + ((durationInSeconde == null) ? 0 : durationInSeconde.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof Music)) {
            return false;
        }
        Music other = (Music) obj;
        if (album == null) {
            if (other.album != null) {
                return false;
            }
        } else if (!album.equals(other.album)) {
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
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
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
