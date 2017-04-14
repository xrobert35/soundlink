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
@Table(name = "user_fav_albums", schema = "public")
public class UsersAlbums {

    @Id
    @SequenceGenerator(name = "user_albums_id_seq", sequenceName = "user_albums_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_albums_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "relation_date", nullable = false)
    private LocalDateTime relationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public LocalDateTime getRelationDate() {
        return relationDate;
    }

    public void setRelationDate(LocalDateTime relationDate) {
        this.relationDate = relationDate;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof UsersAlbums)) {
            return false;
        }
        UsersAlbums castOther = (UsersAlbums) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(relationDate, castOther.relationDate) && Objects.equals(album, castOther.album)
                && Objects.equals(user, castOther.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relationDate);
    }

}
