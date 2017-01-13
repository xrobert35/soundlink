package soundlink.dto;

import java.util.Objects;

/**
 * Music dto
 *
 * @author xrobert
 *
 */
public class MusicDto {

    private Integer id;

    private String title;

    private Long artisteId;

    private String artistName;

    private Long albumId;

    private String albumName;

    private Integer trackNumber = null;

    private Integer durationInSeconde = null;

    private String bitRate;

    private String musicFilePath;

    private Long musicFileSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDurationInSeconde(Integer durationInSeconde) {
        this.durationInSeconde = durationInSeconde;
    }

    public void setDurationInSeconde(int durationInSeconde) {
        this.durationInSeconde = durationInSeconde;
    }

    public int getDurationInSeconde() {
        return durationInSeconde;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getMusicFilePath() {
        return musicFilePath;
    }

    public void setMusicFilePath(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Long getMusicFileSize() {
        return musicFileSize;
    }

    public void setMusicFileSize(Long musicFileSize) {
        this.musicFileSize = musicFileSize;
    }

    public Long getArtisteId() {
        return artisteId;
    }

    public void setArtisteId(Long artisteId) {
        this.artisteId = artisteId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof MusicDto)) {
            return false;
        }
        MusicDto castOther = (MusicDto) other;
        return Objects.equals(id, castOther.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
