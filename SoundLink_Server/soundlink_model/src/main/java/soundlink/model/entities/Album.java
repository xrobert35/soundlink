package soundlink.model.entities;

import java.time.LocalDateTime;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Album Entity
 *
 * @author xrobert
 *
 */
@Entity
@Table(name = "album", schema = "public")
public class Album {

    @Id
    @SequenceGenerator(name = "album_id_seq", sequenceName = "album_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "artiste_id", nullable = false)
    private Artiste artiste;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "bit_rate")
    private String bitRate;

    @Column(name = "extension")
    private String extension;

    @Column(name = "nb_discs")
    private Integer nbDiscs;

    @Column(name = "album_directory")
    private String albumDirectory;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private Set<Music> musics = new HashSet<Music>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private Set<UsersAlbums> users = new HashSet<UsersAlbums>(0);

    @Column(name = "valide")
    private boolean valide = false;

    @Column(name = "integration_number")
    private Integer integrationNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAlbumDirectory() {
        return albumDirectory;
    }

    public void setAlbumDirectory(String albumDirectory) {
        this.albumDirectory = albumDirectory;
    }

    public Set<Music> getMusics() {
        return musics;
    }

    public void setMusics(Set<Music> musics) {
        this.musics = musics;
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

    public Integer getNbDiscs() {
        return nbDiscs;
    }

    public void setNbDiscs(Integer nbDiscs) {
        this.nbDiscs = nbDiscs;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<UsersAlbums> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersAlbums> users) {
        this.users = users;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Album)) {
            return false;
        }
        Album castOther = (Album) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(name, castOther.name) && Objects.equals(bitRate, castOther.bitRate)
                && Objects.equals(extension, castOther.extension) && Objects.equals(artiste, castOther.artiste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bitRate, extension);
    }
}
