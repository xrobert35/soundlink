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
@Table(name = "user_fav_artistes", schema = "public")
public class UsersArtistes {

    @Id
    @SequenceGenerator(name = "user_artistes_id_seq", sequenceName = "user_artistes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_artistes_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "relation_date", nullable = false)
    private LocalDateTime relationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artiste_id", nullable = false)
    private Artiste artiste;

    @Column(name = "artiste_id", insertable = false, updatable = false)
    private Integer artisteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

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

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArtisteId() {
        return artisteId;
    }

    public void setArtisteId(Integer artisteId) {
        this.artisteId = artisteId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof UsersArtistes)) {
            return false;
        }
        UsersArtistes castOther = (UsersArtistes) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(relationDate, castOther.relationDate) && Objects.equals(artiste, castOther.artiste)
                && Objects.equals(user, castOther.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relationDate);
    }

}
