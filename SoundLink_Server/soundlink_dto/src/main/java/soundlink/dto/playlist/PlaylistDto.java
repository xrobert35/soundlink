package soundlink.dto.playlist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import soundlink.dto.MusicDto;

public class PlaylistDto {

    private Integer id;

    private String name;

    private String description;

    private String creatorLogin;

    private String isPublic;

    private LocalDate creationDate;

    private List<MusicDto> musics = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<MusicDto> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicDto> musics) {
        this.musics = musics;
    }
}
