package main;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cloudmusic.GetMusicURLByName;
import core.ConnectServer;
import core.DefaultMusicList;
import core.DelectThread;
import core.MsgBuffer;
import core.Music;
import core.MusicFilter;
import core.MusicList;
import core.MusicListBuffer;
import core.MusicPlayer;
import core.ReceiveMsg;
import tools.Mp3Player;

public class Launcher {

	// 维持一个与弹幕服务器的连接（30s发送一个心跳）
	ConnectServer connect = new ConnectServer(206617, 7178767);
	InputStream is = connect.conn();

	// 弹幕缓冲区
	MsgBuffer msgBuffer = new MsgBuffer();

	ReceiveMsg receiveMsg = new ReceiveMsg(is, msgBuffer);
	Thread receiveThread = new Thread(receiveMsg);

	MusicFilter musicFilter = new MusicFilter();
	// 点歌列表缓冲区
	MusicListBuffer musicListBuffer = new MusicListBuffer();

	// 正在播放
	MusicList nowPlayList = new MusicList(musicListBuffer);
	Mp3Player mp3Player = new Mp3Player();
	MusicPlayer musicPlayer = new MusicPlayer(mp3Player, nowPlayList);

	Thread musicPlayerThread = new Thread(musicPlayer);
	DelectThread delectThread = new DelectThread(mp3Player, nowPlayList);

	Thread musicListController = new Thread(delectThread);

	public void start() {

		// 开启接受弹幕的线程,向弹幕缓冲区写入
		receiveThread.start();
		musicPlayerThread.start();
		musicListController.start();

		String msg;// 弹幕消息
		String musicDM = null;// 点歌信息

		String musicPatternString = "点歌==(.*)";
		Pattern musicPattern = Pattern.compile(musicPatternString);

		String blankMusicPatternString = "点歌 (.*)";
		Pattern blankMusicPattern = Pattern.compile(blankMusicPatternString);

		// 丧心病狂的 JSON 格式点歌
		String patternJSONMusicString = "\\{music:(.*)\\}";
		Pattern JSONMusicPattern = Pattern.compile(patternJSONMusicString);

		Matcher musicMatcher;

		DefaultMusicList defMusicList = new DefaultMusicList();

		while (true) {
			msg = msgBuffer.getDanmuMSG();

			if (!(null == msg)) {
				// System.out.println(msg);
				musicMatcher = blankMusicPattern.matcher(msg);
				if (musicMatcher.find()) {
					musicDM = musicMatcher.group(1);
				}

				musicMatcher = JSONMusicPattern.matcher(msg);
				if (musicMatcher.find()) {
					musicDM = musicMatcher.group(1);
				}

				musicMatcher = musicPattern.matcher(msg);
				if (musicMatcher.find()) {
					musicDM = musicMatcher.group(1);
				}

				// 如果收到了点歌弹幕
				if (musicDM != null) {
					// 如果在播放默认音乐，则删除默认歌单
					if (nowPlayList.isPlayingDefaultMusic()) {
						nowPlayList.setPlayingDefaultMusic(false);
						delectThread.cleanMusicList();
					}
					this.putThisMusic(musicDM);
					musicDM = null;
					
				}
			}

			if ((nowPlayList.getNowMusicCount() == 0) && (!musicPlayer.isPlaying())) {
				nowPlayList.setPlayingDefaultMusic(true);
				this.putMusicList(defMusicList.getDefaultmusiclist());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void putMusicList(List<String> musicList) {
		for (String music : musicList) {
			this.putThisMusic(music);
		}
	}

	public void putThisMusic(String _music) {

		Music music = GetMusicURLByName.haveThisMusic(_music);
		if (!music.isMusic()) {
			System.out.println("+++++++++++++++");
			System.out.println("=找不到" + _music + "这个歌曲=");
			System.out.println("+++++++++++++++");
			return;
		}

		boolean isleagel = musicFilter.filter(music);

		if (isleagel) {
			musicListBuffer.putMusic(music);
			System.out.println("+++++++++++++++");
			nowPlayList.getMusicList();
			System.out.println("+++++++++++++++");
		}
	}

	public static void main(String[] args) {

		new Launcher().start();

	}

}
