package cloudmusic;

import tools.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import core.Music;

public class GetMusicURLByName {

	static JSONObject jsonMusicInfo;
	static JSONArray jsonMusicInfoArray;
	static String[] musicInfoBuf;
	static String musicInfo;
	static String musicURLInfo;

	static String musicIdPatternStr = "\"id\":[0-9]*";
	static Pattern musicIdPattern = Pattern.compile(musicIdPatternStr);

	static String musicNamePatternStr = "\"name\":\"(.*?)\"";
	static Pattern musicNamePattern = Pattern.compile(musicNamePatternStr);

	static String musicArtistsPatternStr = "\"artists\":\\[\\{\"id\":[0-9]*,\"name\":\"(.*?)\"";
	static Pattern musicArtistsPattern = Pattern.compile(musicArtistsPatternStr);

	static Matcher matcher;

	public static void main(String[] args) {
		String musicInfo = PostAndGet.sendPost("http://music.163.com/api/search/get/",
				"s=" + "借口" + "&limit=1&type=1&offset=0");

		String musicArtists = getMusicArtists(musicInfo);
		System.out.println(musicArtists);
	}
	
	static String getMusicName(String musicInfo) {
		matcher = musicNamePattern.matcher(musicInfo);
		matcher.find();
		return matcher.group(1);

	}

	static String getMusicId(String musicInfo) {
		matcher = musicIdPattern.matcher(musicInfo);
		if (matcher.find()) {
			musicInfoBuf = matcher.group(0).split(":");
			return musicInfoBuf[1];
		} else {
			return null;
		}
	}

	static String getMusicArtists(String musicInfo) {
		matcher = musicArtistsPattern.matcher(musicInfo);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static Music haveThisMusic(String _music) {
		Music music = new Music();

		String musicInfo = PostAndGet.sendPost("http://music.163.com/api/search/get/",
				"s=" + _music + "&limit=1&type=1&offset=0");

		String musicId = getMusicId(musicInfo);
		if (musicId == null) {
			music.setMusic(false);
			return music;
		} else {
			music.setMusic(true);
			music.setMusicId(musicId);
		}

		String musicName = getMusicName(musicInfo);
		music.setMusicName(musicName);

		String musicArtists = getMusicArtists(musicInfo);
		music.setMusicArtist(musicArtists);
		
		String musicURL = getMusicURL(musicId);
		music.setMusicURL(musicURL);

		return music;
	}

	public static String getMusicURL(String musicId) {

		musicURLInfo = PostAndGet.sendGet("http://app.atime.me/music-api-server/",
				"p=netease&t=songlist&i=" + musicId + "&q=high");

		jsonMusicInfo = new JSONObject(musicURLInfo);
		jsonMusicInfoArray = jsonMusicInfo.getJSONArray("songs");
		jsonMusicInfo = jsonMusicInfoArray.getJSONObject(0);
		return jsonMusicInfo.getString("url");

	}

}
