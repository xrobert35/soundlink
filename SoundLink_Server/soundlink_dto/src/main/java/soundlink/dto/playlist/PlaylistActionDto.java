package soundlink.dto.playlist;

import javax.validation.constraints.NotNull;

public class PlaylistActionDto {

    @NotNull
    private Integer playlistId;

    @NotNull
    private Integer musicId;

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
}
