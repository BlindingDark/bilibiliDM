package core;

import java.util.ArrayList;
import java.util.Scanner;

import tools.Mp3Player;

public class DelectThread implements Runnable {
	MusicList nowPlayList;
	Scanner sc = new Scanner(System.in);
	Mp3Player mp3Player;
	int index;

	public DelectThread(Mp3Player _mp3Player, MusicList nowPlayList) {
		this.nowPlayList = nowPlayList;
		this.mp3Player = _mp3Player;
	}

	public void cleanMusicList() {
		nowPlayList.cleanMusicList();
		mp3Player.cut();

	}

	public void delectMusic() {
		while (true) {
			ArrayList<Music> nowPlayMusic = nowPlayList.getNowPlayMusic();

			System.out.println("+++++++++++++++");
			nowPlayList.getMusicList();
			System.out.println("+++++++++++++++");
			System.out.println("+++++++++++++++");

			System.out.println("输入要删除的序号:");

			index = sc.nextInt();
			
			if (index == -1) {
				mp3Player.cut();
				continue;
			}
			
			if (index == -1024) {
				cleanMusicList();
				continue;
			}
			synchronized (nowPlayMusic) {
				nowPlayMusic.remove(index);
			}
		}

	}

	@Override
	public void run() {
		this.delectMusic();
	}

}
