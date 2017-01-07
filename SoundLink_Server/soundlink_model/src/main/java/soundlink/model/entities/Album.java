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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    private String name;

    private String bitRate;

    private String extension;

    private String coverGeneralColor;

    private String cover;

    private String albumDirectory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    @Cascade({ CascadeType.ALL })
    private Set<Music> musics = new HashSet<Music>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteAlbums")
    private Set<Users> users = new HashSet<Users>(0);

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

    public String getCoverGeneralColor() {
        return coverGeneralColor;
    }

    public void setCoverGeneralColor(String coverGeneralColor) {
        this.coverGeneralColor = coverGeneralColor;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
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
        return Objects.equals(id, castOther.id)
                || Objects.equals(artiste, castOther.artiste) && Objects.equals(name, castOther.name)
                        && Objects.equals(bitRate, castOther.bitRate) && Objects.equals(extension, castOther.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artiste, name, bitRate, extension);
    }
}
