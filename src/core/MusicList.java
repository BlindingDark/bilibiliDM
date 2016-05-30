package core;

import java.util.ArrayList;
import java.util.Scanner;

public class MusicList {

	int MUSICMAXNUM = 10;
	MusicListBuffer musicListBuffer;
	ArrayList<Music> nowPlayMusic = new ArrayList<>();
	Music nextMusic;

	boolean isPlayingDefaultMusic = false;

	public void cleanMusicList() {
		synchronized (nowPlayMusic) {
			int nowMusicCount = nowPlayMusic.size();
			for (int i = 0; i < nowMusicCount; i++) {
				nowPlayMusic.remove(0);
			}
		}
	}
	
	
	public void setPlayingDefaultMusic(boolean isPlayingDefaultMusic) {
		this.isPlayingDefaultMusic = isPlayingDefaultMusic;
	}

	public boolean isPlayingDefaultMusic() {		
		return isPlayingDefaultMusic;
	}

	
	
	public MusicList(MusicListBuffer _musicListBuffer) {
		this.musicListBuffer = _musicListBuffer;
	}
	
	public ArrayList<Music> getNowPlayMusic() {
		return nowPlayMusic;
	}


	
	public int getNowMusicCount() {
		synchronized (nowPlayMusic) {
			return nowPlayMusic.size();
		}
	}
	
	public Music getNextMusic() {
		synchronized (nowPlayMusic) {
			if (nowPlayMusic.size() != 0) {
				return nowPlayMusic.remove(0);
			}
		}
		return null;
	}

	public void getMusicList() {
		// 末尾加入最新的歌曲
		synchronized (nowPlayMusic) {
			nextMusic = musicListBuffer.getNextMusic();
			if ((nowPlayMusic.size() < MUSICMAXNUM) && (null != nextMusic)) {
				nowPlayMusic.add(nextMusic);
			}
			for (Music music : nowPlayMusic) {
				//TODO 写入文件
				System.out.println(music.getMusicName()+" : "+music.getMusicArtist());
			}
		}
	}
	

	
}
