package core;

import java.util.ArrayList;
import java.util.Scanner;

import tools.Mp3Player;

/**
 * @author BlindingDark 切歌线程
 */
public class DelectThread implements Runnable {
	MusicList nowPlayList;
	Scanner sc = new Scanner(System.in);
	Mp3Player mp3Player;
	int index;

	public DelectThread(Mp3Player _mp3Player, MusicList nowPlayList) {
		this.nowPlayList = nowPlayList;
		this.mp3Player = _mp3Player;
	}

	/**
	 * 清除当前播放列表
	 */
	public void cleanMusicList() {
		nowPlayList.cleanMusicList();
		mp3Player.cut();

	}

	/**
	 * 切歌
	 */
	public void delectMusic() {
		while (true) {
			ArrayList<Music> nowPlayMusic = nowPlayList.getNowPlayMusic();

			System.out.println("+++++++++++++++");
			nowPlayList.getMusicList();
			System.out.println("+++++++++++++++");
			System.out.println("+++++++++++++++");

			System.out.println("输入要删除的序号:");

			index = sc.nextInt();

			// 输入 负数 或 0 切歌
			if (index <= 0) {
				mp3Player.cut();
				continue;
			}

			// 输入 1024 清空播放列表并切歌
			if (index == 1024) {
				cleanMusicList();
				continue;
			}

			// 其它数字代表要删除指定位置的歌曲
			synchronized (nowPlayMusic) {
				if (index <= nowPlayMusic.size()) {
					nowPlayMusic.remove(index - 1);
				}

			}
		}

	}

	@Override
	public void run() {
		this.delectMusic();
	}

}
