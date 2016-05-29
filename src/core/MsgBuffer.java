package core;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class MsgBuffer {
	ArrayList<String> msgs = new ArrayList<>();
	JSONObject jsonobj;
	String danmuType;

	public void pushDanmuMSG(String json) {
		jsonobj = new JSONObject(json);
		danmuType = jsonobj.getString("cmd");

		if ("DANMU_MSG".equals(danmuType)) {
			// JSONArray jsonarray = jsonobj.getJSONArray("info");
			// String danmu = jsonarray.getString(1);
			synchronized (msgs) {
				msgs.add(jsonobj.getJSONArray("info").getString(1));
			}
		}

	}

	public String getDanmuMSG() {
		synchronized (msgs) {
			if (msgs.isEmpty()) {
				return null;
			}
			return msgs.remove(0);
		}
	}

}
