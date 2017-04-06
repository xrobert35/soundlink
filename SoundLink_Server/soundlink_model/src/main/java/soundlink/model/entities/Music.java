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
    @JoinColumn(name = "artiste_id", nullable = false)
    private Artiste artiste;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @Column(name = "track_number")
    private Integer trackNumber = null;

    @Column(name = "duration_in_seconde")
    private Integer durationInSeconde = null;

    @Column(name = "bit_rate")
    private String bitRate;

    @Column
    private String extension;

    @Column(name = "music_file_path")
    private String musicFilePath;

    @Column(name = "music_file_size")
    private Long musicFileSize;

    @Column(name = "disc_number")
    private Integer discNumber;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "musics")
    private Set<Playlist> playlist = new HashSet<Playlist>(0);

    @Column
    private boolean valide = false;

    @Column(name = "integration_number")
    private Integer integrationNumber;

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

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
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

    public Integer getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
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

    public Integer getIntegrationNumber() {
        return integrationNumber;
    }

    public void setIntegrationNumber(Integer integrationNumber) {
        this.integrationNumber = integrationNumber;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
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
