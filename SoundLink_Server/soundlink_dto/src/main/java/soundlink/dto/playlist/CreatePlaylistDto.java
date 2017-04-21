package soundlink.dto.playlist;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * PlayList dto
 *
 * @author xrobert
 *
 */
public class CreatePlaylistDto {

    @NotNull
    private String name;

    private String cover;

    private boolean isPublic = false;

    private String description;

    private List<Integer> musicsId = new ArrayList<>();

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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getMusicsId() {
        return musicsId;
    }

    public void setMusicsId(List<Integer> musicsId) {
        this.musicsId = musicsId;
    }
}
