package soundlink.model.entities;

import java.util.HashSet;
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
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
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
        if (!(obj instanceof Users)) {
            return false;
        }
        Users other = (Users) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (role == null) {
            if (other.role != null) {
                return false;
            }
        } else if (!role.equals(other.role)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        return true;
    }
}
