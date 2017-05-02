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
@Table(name = "music_playlists", schema = "public")
public class MusicsPlaylists {

    @Id
    @SequenceGenerator(name = "musics_playlists_id_seq", sequenceName = "musics_playlists_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "musics_playlists_id_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "relation_date", nullable = false)
    private LocalDateTime relationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Column(name = "playlist_id", updatable = false, insertable = false)
    private Integer playlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    private Music music;

    @Column(name = "music_id", updatable = false, insertable = false)
    private Integer musicId;

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

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof MusicsPlaylists)) {
            return false;
        }
        MusicsPlaylists castOther = (MusicsPlaylists) other;
        if (id != null) {
            return Objects.equals(id, castOther.id);
        }
        return Objects.equals(relationDate, castOther.relationDate) && Objects.equals(playlist, castOther.playlist)
                && Objects.equals(music, castOther.music);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relationDate, playlist, music);
    }

}
