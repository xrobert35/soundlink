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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @Column(unique = true)
    private String login;
    @Column
    private String picture;

    @Column
    private String password;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.ALL })
    @JoinTable(name = "user_fav_artistes", schema = "public", inverseJoinColumns = @JoinColumn(name = "artiste_id", referencedColumnName = "id", columnDefinition = "long"), joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "long"))
    private Set<Artiste> favortiesArtistes = new HashSet<Artiste>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.ALL })
    @JoinTable(name = "user_fav_albums", schema = "public", inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", columnDefinition = "long"), joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "long"))
    private Set<Artiste> favoriteAlbums = new HashSet<Artiste>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.ALL })
    @JoinTable(name = "user_fav_playlists", schema = "public", inverseJoinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id", columnDefinition = "long"), joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "long"))
    private Set<Playlist> playlists = new HashSet<Playlist>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Artiste> getFavortiesArtistes() {
        return favortiesArtistes;
    }

    public void setFavortiesArtistes(Set<Artiste> favortiesArtistes) {
        this.favortiesArtistes = favortiesArtistes;
    }

    public Set<Artiste> getFavoriteAlbums() {
        return favoriteAlbums;
    }

    public void setFavoriteAlbums(Set<Artiste> favoriteAlbums) {
        this.favoriteAlbums = favoriteAlbums;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
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
        return Objects.equals(id, castOther.id) && Objects.equals(login, castOther.login)
                && Objects.equals(password, castOther.password) && Objects.equals(email, castOther.email)
                && Objects.equals(role, castOther.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role);
    }
}
