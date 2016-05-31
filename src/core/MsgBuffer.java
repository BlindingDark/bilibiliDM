package core;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author BlindingDark
 *	弹幕缓冲区
 */
public class MsgBuffer {
	ArrayList<String> msgs = new ArrayList<>();
	JSONObject jsonobj;
	String danmuType;

	/**
	 * 提取具体弹幕信息
	 * @param 接受到的b站弹幕信息的 json 格式
	 */
	public void pushDanmuMSG(String json) {
		jsonobj = new JSONObject(json);
		danmuType = jsonobj.getString("cmd");
		
		//这里只接收弹幕的包，其它包如用户进入房间，请自行增加
		
		if ("DANMU_MSG".equals(danmuType)) {
			// JSONArray jsonarray = jsonobj.getJSONArray("info");
			// String danmu = jsonarray.getString(1);
			synchronized (msgs) {
				msgs.add(jsonobj.getJSONArray("info").getString(1));
			}
		}

	}

	/**
	 * 从缓冲区中得到一条弹幕信息
	 * @return 弹幕字符串
	 */
	public String getDanmuMSG() {
		synchronized (msgs) {
			if (msgs.isEmpty()) {
				return null;
			}
			return msgs.remove(0);
		}
	}

}
