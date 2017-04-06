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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @Column(unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Users creator;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.ALL })
    @JoinTable(name = "music_playlists", schema = "public", inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id", columnDefinition = "long"), joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id", columnDefinition = "long"))
    private Set<Music> musics = new HashSet<Music>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "playlists")
    private Set<Users> users = new HashSet<Users>(0);

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

    public Set<Music> getMusics() {
        return musics;
    }

    public void setMusic(Set<Music> musics) {
        this.musics = musics;
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

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public void setMusics(Set<Music> musics) {
        this.musics = musics;
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
        return Objects.equals(id, castOther.id)
                || (Objects.equals(name, castOther.name) && Objects.equals(creator, castOther.creator));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
