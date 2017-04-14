package soundlink.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_fav_playlists", schema = "public")
public class UsersPlaylists {

    @Id
    @SequenceGenerator(name = "user_playlists_id_seq", sequenceName = "user_playlists_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_playlists_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "relation_date", nullable = false)
    private LocalDateTime relationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getRelationDate() {
        return relationDate;
    }

    public void setRelationDate(LocalDateTime relationDate) {
        this.relationDate = relationDate;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof UsersPlaylists)) {
            return false;
        }
        UsersPlaylists castOther = (UsersPlaylists) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(relationDate, castOther.relationDate) && Objects.equals(playlist, castOther.playlist)
                && Objects.equals(user, castOther.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relationDate);
    }

}
