package core;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author BlindingDark
 *	音乐缓冲列表
 */
public class MusicListBuffer {
	int FULLSIZE = 100;
	int nowSize;
	Random rand = new java.util.Random();

	ArrayList<Music> MusicListBuff = new ArrayList<>();

	/**
	 * 把音乐塞进缓冲区
	 * @param music
	 */
	public void putMusic(Music music) {

		synchronized (MusicListBuff) {
			if (MusicListBuff.size() >= FULLSIZE) {
				MusicListBuff.remove(0);
			}
			MusicListBuff.add(music);
		}

	}

	/**
	 * 得到下一首歌曲
	 * @return 返回从 FULLSIZE 个点歌弹幕随机抽取的歌曲
	 */
	public Music getNextMusic() {
		synchronized (MusicListBuff) {
			nowSize = MusicListBuff.size();
			if (nowSize != 0) {
				return MusicListBuff.remove(rand.nextInt(nowSize));
			}
		}
		return null;
	}

}
