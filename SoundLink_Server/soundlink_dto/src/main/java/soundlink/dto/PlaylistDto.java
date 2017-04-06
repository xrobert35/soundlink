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

    private Integer creatorId;

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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}
