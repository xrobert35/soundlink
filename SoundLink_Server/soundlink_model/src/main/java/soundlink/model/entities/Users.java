package soundlink.model.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import soundlink.model.enums.UserRoleEnum;

/**
 * User Entity
 *
 * @author xrobert
 *
 */
@Entity
@Table(name = "users", schema = "public")
public class Users {

    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "picture")
    private String picture;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    private Set<UsersArtistes> favoritesArtistes = new HashSet<UsersArtistes>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    private Set<UsersAlbums> favoritesAlbums = new HashSet<UsersAlbums>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    private Set<UsersPlaylists> playlists = new HashSet<UsersPlaylists>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<UsersArtistes> getFavoritesArtistes() {
        return favoritesArtistes;
    }

    public void setFavoritesArtistes(Set<UsersArtistes> favoritesArtistes) {
        this.favoritesArtistes = favoritesArtistes;
    }

    public Set<UsersAlbums> getFavoritesAlbums() {
        return favoritesAlbums;
    }

    public void setFavoritesAlbums(Set<UsersAlbums> favoritesAlbums) {
        this.favoritesAlbums = favoritesAlbums;
    }

    public Set<UsersPlaylists> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<UsersPlaylists> playlists) {
        this.playlists = playlists;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Users)) {
            return false;
        }
        Users castOther = (Users) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(login, castOther.login) && Objects.equals(password, castOther.password)
                && Objects.equals(email, castOther.email) && Objects.equals(role, castOther.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role);
    }
}
