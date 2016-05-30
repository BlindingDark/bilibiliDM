package core;

import cloudmusic.GetMusicURLByName;
import tools.Mp3Player;

public class MusicPlayer implements Runnable {
	MusicList nowPlayList;
	Mp3Player mp3Player;
	
	public MusicPlayer(Mp3Player _mp3Player, MusicList _nowPlayList) {
		this.nowPlayList = _nowPlayList;
		this.mp3Player = _mp3Player;
	}

	@Override
	public void run() {
		while (true) {

			Music nextMusic= nowPlayList.getNextMusic();
			if (null != nextMusic) {
				System.out.println("+++++++++++++++");
				nowPlayList.getMusicList();
				System.out.println("+++++++++++++++");
				mp3Player.play(nextMusic);
			}
		}
		
	}

}
