package soundlink.dto;

/**
 * PlayList dto
 *
 * @author xrobert
 *
 */
public class PlaylistDto {

    private String name;

    private String cover;

    private String creatorLogin;

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

    public String getCreatorId() {
        return creatorLogin;
    }

    public void setCreatorId(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }
}
