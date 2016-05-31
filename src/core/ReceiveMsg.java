package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import tools.BytesIntSwitch;

public class ReceiveMsg implements Runnable {

	InputStream is;
	MsgBuffer msgBuffer;
	ConnectServer connect;
	


	public ReceiveMsg(ConnectServer _connect, MsgBuffer _msgBuffer) {
		this.connect = _connect;
		this.msgBuffer = _msgBuffer;
	}

	public void receive(){
		
		this.is = connect.conn();
		byte[] reply = new byte[1024*1024];
		int length;

		try {
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
			
			
		} catch (UnsupportedEncodingException e) {
			System.out.println("出现了意外的编码错误，正在重连……");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.receive();
		} catch (IOException e) {
			System.out.println("出现了意外的连接错误，正在重连……");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.receive();
		}
		
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			this.receive();
			System.out.println("正在重连……");
		}
	}
}
