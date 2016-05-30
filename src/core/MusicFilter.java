package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class MusicFilter {
	static final Hashtable<String, Boolean> illegalMusicList = new Hashtable<String, Boolean>();

	public MusicFilter() {
		this.readIllegalMusicFile();
	}

	public void readIllegalMusicFile() {
		File file = new File("illegal_music_list");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				illegalMusicList.put(tempString, Boolean.FALSE);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public boolean filter(Music music) {
		this.readIllegalMusicFile();
		Boolean illegalMusicName = illegalMusicList.get(music.getMusicName());
		if (null != illegalMusicName) {
			return false;
		}
		Boolean illegalMusicId = illegalMusicList.get(music.getMusicId());
		if (null != illegalMusicId) {
			return false;
		}

		return true;
	}

}
