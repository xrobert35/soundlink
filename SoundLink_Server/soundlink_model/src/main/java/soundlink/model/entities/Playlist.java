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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
    @SequenceGenerator(name = "playlist_id_seq", sequenceName = "playlist_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Users creator;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    private Set<MusicsPlaylists> musics = new HashSet<MusicsPlaylists>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist")
    private Set<UsersPlaylists> users = new HashSet<UsersPlaylists>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<MusicsPlaylists> getMusics() {
        return musics;
    }

    public void setMusics(Set<MusicsPlaylists> musics) {
        this.musics = musics;
    }

    public Set<UsersPlaylists> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersPlaylists> users) {
        this.users = users;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Playlist)) {
            return false;
        }
        Playlist castOther = (Playlist) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return (Objects.equals(name, castOther.name) && Objects.equals(creator, castOther.creator));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
