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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * PlayList Entity
 *
 * @author xrobert
 *
 */
@Entity
@Table(name = "playlist", schema = "public")
public class Playlist {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.ALL })
    @JoinTable(name = "music_playlists", schema = "public", inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id", columnDefinition = "long"), joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id", columnDefinition = "long"))
    private Set<Music> musics = new HashSet<Music>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "playlists")
    private Set<Users> users = new HashSet<Users>(0);

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

    public Set<Music> getMusics() {
        return musics;
    }

    public void setMusic(Set<Music> musics) {
        this.musics = musics;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        if (!(obj instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) obj;
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
