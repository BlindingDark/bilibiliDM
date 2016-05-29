package tools;

import java.io.BufferedInputStream;
import java.net.URL;

import core.Music;
import javazoom.jl.player.Player;

public class Mp3Player {
	URL music;
	BufferedInputStream buffer;
	Player player;
	public void cut() {
		player.close();
	}
	
	
	public void play(Music _music) {
		try {
			music = new URL(_music.getMusicURL());
			buffer = new BufferedInputStream(music.openStream());
			player = new Player(buffer);
			player.play();
		} catch (Exception e) {
			//System.out.println(e);
			System.out.println("由于版权问题或网络问题，"+_music.getMusicName()+" 播放失败");
		}
	}
}
