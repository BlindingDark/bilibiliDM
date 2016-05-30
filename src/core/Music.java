package core;

import cloudmusic.GetMusicURLByName;

public class Music {
	boolean isMusic = false;
	String musicArtist;
	String musicName;
	String musicURL;
	String musicId;
	int duration;



	public Music(){
		
		
	}
	
	public Music(String musicName,String musicArtist,String musicURL) {
		setMusicName(musicName);
		setMusicArtist(musicArtist);
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
		return GetMusicURLByName.getMusicURL(this.getMusicId());
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
