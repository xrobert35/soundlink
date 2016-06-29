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
import javax.persistence.OneToMany;
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
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artiste_id")
    private Artiste artiste;

    private String name;

    private String bitRate;

    private String extension;

    private String coverGeneralColor;

    private byte[] cover;

    private String albumDirectory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    @Cascade({ CascadeType.ALL })
    private Set<Music> musics = new HashSet<Music>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteAlbums")
    private Set<Users> users = new HashSet<Users>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumDirectory == null) ? 0 : albumDirectory.hashCode());
        result = prime * result + ((artiste == null) ? 0 : artiste.hashCode());
        result = prime * result + ((bitRate == null) ? 0 : bitRate.hashCode());
        result = prime * result + ((extension == null) ? 0 : extension.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof Album)) {
            return false;
        }
        Album other = (Album) obj;
        if (albumDirectory == null) {
            if (other.albumDirectory != null) {
                return false;
            }
        } else if (!albumDirectory.equals(other.albumDirectory)) {
            return false;
        }
        if (artiste == null) {
            if (other.artiste != null) {
                return false;
            }
        } else if (!artiste.equals(other.artiste)) {
            return false;
        }
        if (bitRate == null) {
            if (other.bitRate != null) {
                return false;
            }
        } else if (!bitRate.equals(other.bitRate)) {
            return false;
        }
        if (extension == null) {
            if (other.extension != null) {
                return false;
            }
        } else if (!extension.equals(other.extension)) {
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
        return true;
    }
}
