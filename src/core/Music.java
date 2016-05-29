package core;

public class Music {
	boolean isMusic = false;
	String musicArtist;
	String musicName;
	String musicURL;
	String musicId;


	public Music(){
		
		
	}
	
	public Music(String musicName,String musicArtist,String musicURL) {
		setMusicName(musicName);
		setMusicArtist(musicArtist);
		setMusicURL(musicURL);
	}

	public String getMusicArtist() {
		return musicArtist;
	}

	public void setMusicArtist(String musicArtist) {
		this.musicArtist = musicArtist;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getMusicURL() {
		return musicURL;
	}

	public void setMusicURL(String musicURL) {
		this.musicURL = musicURL;
	}
	public boolean isMusic() {
		return isMusic;
	}
	public void setMusic(boolean isMusic) {
		this.isMusic = isMusic;
	}
	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

}
