package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DefaultMusicList {
	
	
	ArrayList<String> defaultMusicList;

	
	public ArrayList<String> getDefaultmusiclist() {
		readDefaultMusicFile();
		return defaultMusicList;
	}




	void readDefaultMusicFile() {
        File file = new File("default_music_list");
        BufferedReader reader = null;
        defaultMusicList = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	defaultMusicList.add(tempString);
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
}
