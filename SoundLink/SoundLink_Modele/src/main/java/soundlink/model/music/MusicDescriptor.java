package soundlink.model.music;

import java.io.File;
import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Music descriptor
 * 
 * @author xrobert
 *
 */
public class MusicDescriptor {

	private String title;
	
	private String artist;
	
	private String album;

	private Integer trackNumber = null;
	
	private Integer durationInSeconde = null;

	private String byteRate;

	@JsonIgnore
	private File file;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String musicName) {
		this.title = musicName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getByteRate() {
		return byteRate;
	}

	public void setByteRate(String byteRate) {
		this.byteRate = byteRate;
	}

	public String getDuration() {
		if (durationInSeconde != null) {
			float durationInMinute = (float) durationInSeconde / 60;
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(durationInMinute);
		}
		return null;
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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

}
