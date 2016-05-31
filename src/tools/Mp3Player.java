package tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import core.Music;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author BlindingDark 调用音乐播放器。 默认是 windows media player 。 
 * 确保 C:/Program Files (x86)/Windows Media Player/wmplayer.exe 存在
 *
 * 如果不想使用WMP，请修改 String musicPlayer = "javaPlayer";
 */
public class Mp3Player {
	Music music;
	BufferedInputStream buffer;
	Player player;
	boolean stop = false;

	String musicPlayer = "WMPplayer";
	//String musicPlayer = "javaPlayer";
	
	/**
	 * 切歌
	 */
	public void cut() {

		this.stop = true;
		switch (musicPlayer) {
		case "WMPplayer":
			String command = "taskkill /F /IM wmplayer.exe";
			try {
				Process process = Runtime.getRuntime().exec(command);
				process.waitFor();
			} catch (IOException | InterruptedException e1) {
			}
			break;
		case "javaPlayer":
			player.close();
			break;
		default:
			break;
		}

	}

	/**
	 * 播放
	 * @param _music
	 */
	public void play(Music _music) {
		switch (musicPlayer) {
		case "WMPplayer":
			this.WMPplay(_music);
			break;
		case "javaPlayer":
			this.javaPlay(_music);
			break;
		default:
			break;
		}
	}

	public void WMPplay(Music _music) {
		this.music = _music;

		String musicURL = music.getMusicURL();
		String command = "C:/Program Files (x86)/Windows Media Player/wmplayer.exe \"" + musicURL + "\"";

		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e1) {
		}
		stop = false;
		try {
			int timer = music.getDuration() / 1000 + 2;

			while ((!stop) && (timer > 0)) {

				Thread.sleep(1000);
				timer--;

			}

		} catch (InterruptedException e) {
			// e.printStackTrace();
		}

	}

	/**
	 * 不稳定的纯 java mp3 播放器
	 * 
	 * @param _music
	 */
	public void javaPlay(Music _music) {
		try {
			this.music = _music;

			try {
				player = new Player(new URL(music.getMusicURL()).openStream());
			} catch (MalformedURLException e) {
				System.out.println("由于版权问题或网络问题，" + _music.getMusicName() + " 播放失败");
			} catch (IOException e) {
				System.out.println("由于版权问题或网络问题，" + _music.getMusicName() + " 播放失败");
			} catch (JavaLayerException e) {
				System.out.println("由于版权问题或网络问题，" + _music.getMusicName() + " 播放失败");
			}
			stop = false;
			player.play();
		} catch (JavaLayerException e) {
			System.out.println("由于版权问题或网络问题，" + _music.getMusicName() + " 播放失败");

		} finally {

		}
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		String command = "C:/Program Files (x86)/Windows Media Player/wmplayer.exe \"http://p1.music.126.net/qHexK855tbfvuYPumSBgAQ==/7997847580846443.mp3\"";
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
