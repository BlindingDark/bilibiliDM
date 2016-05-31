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

	static String musicDurationPatternStr = "\"duration\":[0-9]*";
	static Pattern musicDurationPattern = Pattern.compile(musicDurationPatternStr);

	static String musicCanDownloadPatternStr = ".*==/0\\.mp3";
	static Pattern musicCanDownloadPattern = Pattern.compile(musicCanDownloadPatternStr);

	static Matcher matcher;
	static Matcher URLmatcher;

	public static void main(String[] args) {
		String musicInfo = PostAndGet.sendPost("http://music.163.com/api/search/get/",
				"s=" + 1702644 + "&limit=1&type=1&offset=0");
		;
		String musicUrl = getMusicURL("21162523");
		System.out.println(musicCanDownloadPattern.matcher(musicUrl).find());

	}

	static Integer getMusicDuration(String musicInfo) {
		matcher = musicDurationPattern.matcher(musicInfo);
		if (matcher.find()) {
			musicInfoBuf = matcher.group(0).split(":");
			return Integer.valueOf(musicInfoBuf[1]);
		} else {
			return null;
		}

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

		// 查看是否能被下载
		String musicUrl = getMusicURL(musicId);
		if (musicCanDownloadPattern.matcher(musicUrl).find()) {
			music.setMusic(false);
			System.out.println("由于版权原因，无法播放 " + music.getMusicName());
			return music;
		}

		String musicArtists = getMusicArtists(musicInfo);
		music.setMusicArtist(musicArtists);

		Integer duration = getMusicDuration(musicInfo);
		music.setDuration(duration);

		return music;
	}

	static String musicURLPatternStr = "\"url\":\"(.*?)\"";
	static Pattern musicURLPattern = Pattern.compile(musicURLPatternStr);

	public static String getMusicURL(String musicId) {

		musicURLInfo = PostAndGet.sendGet("http://app.atime.me/music-api-server/",
				"p=netease&t=songlist&i=" + musicId + "&q=high");

		URLmatcher = musicURLPattern.matcher(musicURLInfo);

		if ("".equals(musicURLInfo)) {
			return GetMusicURLByName.getMusicURL(musicId);
		}

		if (URLmatcher.find()) {
			return URLmatcher.group(1);
		}else{
			System.out.println("得到的音乐下载连接出现意外");
			return "http://p1.music.126.net/jjViVB6auTiX_pWTbXZFgQ==/2090171604421748.mp3";
		}
		
		

	}

}
