package soundlink.model.entities;

import java.util.HashSet;
import java.util.Objects;
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
import javax.persistence.SequenceGenerator;
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
    @SequenceGenerator(name = "music_id_seq", sequenceName = "music_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "music_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private Integer trackNumber = null;

    private Integer durationInSeconde = null;

    private String bitRate;

    private String extension;

    private String musicFilePath;

    private Long musicFileSize;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "musics")
    private Set<Playlist> playlist = new HashSet<Playlist>(0);

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

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
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

    public Long getMusicFileSize() {
        return musicFileSize;
    }

    public void setMusicFileSize(Long musicFileSize) {
        this.musicFileSize = musicFileSize;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Music)) {
            return false;
        }
        Music castOther = (Music) other;
        return Objects.equals(id, castOther.id) || Objects.equals(title, castOther.title)
                && Objects.equals(album, castOther.album) && Objects.equals(trackNumber, castOther.trackNumber)
                && Objects.equals(durationInSeconde, castOther.durationInSeconde)
                && Objects.equals(bitRate, castOther.bitRate) && Objects.equals(extension, castOther.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, album, trackNumber, durationInSeconde, bitRate, extension);
    }
}
