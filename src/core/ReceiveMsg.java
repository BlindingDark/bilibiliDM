package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import tools.BytesIntSwitch;

public class ReceiveMsg implements Runnable {

	InputStream is;
	MsgBuffer msgBuffer;

	public ReceiveMsg(InputStream _is, MsgBuffer _msgBuffer) {
		this.is = _is;
		this.msgBuffer = _msgBuffer;
	}

	public void receive() throws IOException, UnsupportedEncodingException {
		byte[] reply = new byte[1024*1024];
		int length;

		while ((length = is.read(reply)) != -1) {
			int nowPoint = 0;
			byte[] replyHead = new byte[4];
			int oneDatalength;

			ArrayList<byte[]> datas = new ArrayList<>();

			byte[] oneData;

			while (nowPoint < length) {
				// 计算一个数据的长度
				System.arraycopy(reply, nowPoint, replyHead, 0, 4);
				oneDatalength = BytesIntSwitch.byteArrayToInt(replyHead);

				// 获得一个数据
				oneData = new byte[oneDatalength];
				System.arraycopy(reply, nowPoint, oneData, 0, oneData.length);

				datas.add(oneData);

				nowPoint += oneDatalength;
			}

			for (byte[] one : datas) {
				// 如果是弹幕
				if (one[11] == 5) {
					byte[] out = new byte[one.length - 16];
					System.arraycopy(one, 16, out, 0, out.length);

					String JSON = new String(out, "UTF-8");
					msgBuffer.pushDanmuMSG(JSON);
					//System.out.println(JSON);

				}
			}

		}
		is.close();
	}

	@Override
	public void run() {
		try {
			this.receive();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
