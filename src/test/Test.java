package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.w3c.dom.css.ElementCSSInlineStyle;

import tools.PostAndGet;

public class Test {

	public static void haveThisMusic(String music) {
		String musicInfo = PostAndGet.sendPost("http://music.163.com/api/search/get/",
				"s=" + music + "&limit=1&type=1&offset=0");
		
		String patternString = "\"id\":[0-9]*";
		Pattern myPattern = Pattern.compile(patternString);
		Matcher matcher = myPattern.matcher(musicInfo);
		if (matcher.find()) {
			String[] id = matcher.group(0).split(":");
			System.out.println(id[1]);
			//return true;
		}else{
			//return false;
		}

	}
	
	
	public static void main(String[] args){
		haveThisMusic("威风堂堂");
	}
		
}
