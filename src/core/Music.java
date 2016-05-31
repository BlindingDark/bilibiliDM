package core;

import cloudmusic.GetMusicURLByName;

/**
 * @author BlindingDark 
 * 音乐信息
 */
public class Music {
	boolean isMusic = false;// 是否是可播放的音乐
	String musicArtist;// 艺术家
	String musicName;// 歌曲名
	String musicURL;// 歌曲播放链接
	String musicId;// 歌曲ID
	int duration;// 歌曲持续时长

	public Music(String musicName, String musicArtist, String musicURL) {
		setMusicName(musicName);
		setMusicArtist(musicArtist);
	}

	public Music() {

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
