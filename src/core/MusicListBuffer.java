package core;

import java.util.ArrayList;
import java.util.Random;

public class MusicListBuffer {
	int FULLSIZE = 100;
	int nowSize;
	Random rand = new java.util.Random();

	ArrayList<Music> MusicListBuff = new ArrayList<>();

	public void putMusic(Music music) {

		synchronized (MusicListBuff) {
			if (MusicListBuff.size() >= FULLSIZE) {
				MusicListBuff.remove(0);
			}
			MusicListBuff.add(music);
		}

	}

	public Music getNextMusic() {
		nowSize = MusicListBuff.size();

		if (nowSize != 0) {
			synchronized (MusicListBuff) {
				return MusicListBuff.remove(rand.nextInt(nowSize));
			}
		}
		return null;
	}

}
