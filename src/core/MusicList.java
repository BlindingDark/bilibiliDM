package core;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author BlindingDark
 *	当前播放列表
 */
public class MusicList {

	int MUSICMAXNUM = 10; //最大歌曲数量
	MusicListBuffer musicListBuffer;
	ArrayList<Music> nowPlayMusic = new ArrayList<>();
	Music nextMusic;

	boolean isPlayingDefaultMusic = false;

	/**
	 * 清空列表
	 */
	public void cleanMusicList() {
		synchronized (nowPlayMusic) {
			int nowMusicCount = nowPlayMusic.size();
			for (int i = 0; i < nowMusicCount; i++) {
				nowPlayMusic.remove(0);
			}
		}
	}
	
	
	/**
	 * 设置是否在播放默认曲目
	 * @param isPlayingDefaultMusic
	 */
	public void setPlayingDefaultMusic(boolean isPlayingDefaultMusic) {
		this.isPlayingDefaultMusic = isPlayingDefaultMusic;
	}

	/**
	 * 得到是否在播放默认曲目
	 * @return
	 */
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

	/**
	 * 得到当前播放列表
	 */
	public void getMusicList() {
		// 末尾加入最新的歌曲
		synchronized (nowPlayMusic) {
			//从音乐列表缓冲中取
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
