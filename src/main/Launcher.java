package main;


import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cloudmusic.GetMusicURLByName;
import core.ConnectServer;
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

	public static void main(String[] args){
		
		// 维持一个与弹幕服务器的连接（30s发送一个心跳）
		ConnectServer connect = new ConnectServer(206617, 7178767);
		InputStream is = connect.conn();

		//弹幕缓冲区
		MsgBuffer msgBuffer = new MsgBuffer();
		
		ReceiveMsg receiveMsg = new ReceiveMsg(is, msgBuffer);
		Thread receiveThread = new Thread(receiveMsg);
		
		//开启接受弹幕的线程,向弹幕缓冲区写入
		receiveThread.start();
		
		//点歌列表缓冲区
		MusicListBuffer musicListBuffer = new MusicListBuffer();
		//正在播放
		MusicList nowPlayList = new MusicList(musicListBuffer);
		
		
		Mp3Player mp3Player =  new Mp3Player();
		
		MusicPlayer musicPlayer = new MusicPlayer(mp3Player,nowPlayList);
		Thread musicPlayerThread = new Thread(musicPlayer);
		musicPlayerThread.start();
		
		
		MusicFilter musicFilter = new MusicFilter();
		String msg;
		String patternString = "点歌==.*";
		Pattern myPattern = Pattern.compile(patternString);
		String[] musicStr = new String[2];
		
		DelectThread delectThread = new DelectThread(mp3Player,nowPlayList);
		Thread musicListController = new Thread(delectThread);
		musicListController.start();
		
		while (true) {
			msg = msgBuffer.getDanmuMSG();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (!(null == msg)) {
				//System.out.println(msg);
				if (myPattern.matcher(msg).find()) {
					
					musicStr = msg.split("==");
					
					Music music = GetMusicURLByName.haveThisMusic(musicStr[1]);
					if (!music.isMusic()) {
						System.out.println("+++++++++++++++");
						System.out.println("=找不到"+musicStr[1]+"这个歌曲=");
						System.out.println("+++++++++++++++");
						continue;
					}
					
					boolean isleagel= musicFilter.filter(music);
					
					if (isleagel) {
						musicListBuffer.putMusic(music);
						System.out.println("+++++++++++++++");
						nowPlayList.getMusicList();
						System.out.println("+++++++++++++++");
						System.out.println("+++++++++++++++");
					}
				}
			}
		}

	}

}
