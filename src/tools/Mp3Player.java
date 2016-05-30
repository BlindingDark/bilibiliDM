package tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import core.Music;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Mp3Player {
	Music music;
	BufferedInputStream buffer;
	Player player;
	boolean stop = false;

	public void cut() {
		this.stop = true;
		String command = "taskkill /F /IM wmplayer.exe";

		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException | InterruptedException e1) {
		}
		// player.close();
	}

	public void play(Music _music) {
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

	public static void main(String args[]) {

		String command = "C:/Program Files (x86)/Windows Media Player/wmplayer.exe \"http://p1.music.126.net/qHexK855tbfvuYPumSBgAQ==/7997847580846443.mp3\"";
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * public void play(Music _music) { try { this.music = _music; try { player
	 * = new Player(new URL(music.getMusicURL()).openStream()); } catch
	 * (MalformedURLException e) { } catch (IOException e) { } player.play();
	 * 
	 * } catch (JavaLayerException e) { try { try { new Player(new
	 * URL(music.getMusicURL()).openStream()).play(player.getPosition()); }
	 * catch (MalformedURLException e1) { } catch (IOException e1) { }
	 * player.play();
	 * 
	 * } catch (JavaLayerException e1) { System.out.println("由于版权问题或网络问题，" +
	 * _music.getMusicName() + " 播放失败"); } } }
	 */

}
