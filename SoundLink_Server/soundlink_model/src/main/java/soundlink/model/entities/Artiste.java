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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Artiste Entity
 *
 * @author xrobert
 *
 */
@Entity
@Table(name = "artiste", schema = "public")
public class Artiste {

    @Id
    @SequenceGenerator(name = "artiste_id_seq", sequenceName = "artiste_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artiste_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String cover;

    // @Cascade({ CascadeType.ALL })
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artiste")
    private Set<Album> albums = new HashSet<Album>(0);

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favortiesArtistes")
    private Set<Users> users = new HashSet<Users>(0);

    private boolean valide = false;

    private Integer integrationNumber;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public Integer getIntegrationNumber() {
        return integrationNumber;
    }

    public void setIntegrationNumber(Integer integrationNumber) {
        this.integrationNumber = integrationNumber;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<Users> getUsers() {
        return users;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Artiste)) {
            return false;
        }
        Artiste castOther = (Artiste) other;
        return Objects.equals(id, castOther.id) || Objects.equals(name, castOther.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
