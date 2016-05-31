package core;

import tools.Mp3Player;

/**
 * @author BlindingDark
 *	音乐播放线程
 */
public class MusicPlayer implements Runnable {
	MusicList nowPlayList;
	Mp3Player mp3Player;
	boolean isPlaying;
	
	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public MusicPlayer(Mp3Player _mp3Player, MusicList _nowPlayList) {
		this.nowPlayList = _nowPlayList;
		this.mp3Player = _mp3Player;
	}

	/**
	 * 播放歌曲的线程
	 */
	@Override
	public void run() {
		while (true) {
			Music nextMusic= nowPlayList.getNextMusic();
			if (null != nextMusic) {
				System.out.println("+++++++++++++++");
				nowPlayList.getMusicList();
				System.out.println("+++++++++++++++");
				this.setPlaying(true);
				mp3Player.play(nextMusic);
				this.setPlaying(false);
			}else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		
	}

}
