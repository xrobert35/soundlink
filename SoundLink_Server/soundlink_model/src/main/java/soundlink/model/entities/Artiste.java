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

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artiste")
    private Set<Album> albums = new HashSet<Album>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artiste")
    private Set<UsersArtistes> users = new HashSet<UsersArtistes>(0);

    @Column
    private boolean valide = false;

    @Column(name = "integration_number")
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

    public Set<UsersArtistes> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersArtistes> users) {
        this.users = users;
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
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(name, castOther.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
