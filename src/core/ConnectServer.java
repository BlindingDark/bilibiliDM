package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import tools.*;

public class ConnectServer {

	int roomid;
	int uid;

	public ConnectServer(int _roomid, int _uid) {
		this.roomid = _roomid;
		this.uid = _uid;
	}

	@SuppressWarnings("resource")
	public InputStream conn(){
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;

		try {
			// 1.建立客户端socket连接，指定服务器位置及端口
			socket = new Socket("livecmt-2.bilibili.com", 788);
			// 2.得到socket读写流
			os = socket.getOutputStream();

			// 输入流
			is = socket.getInputStream();

			// 3.利用流按照一定的操作，对socket进行读写操作

			String send = "{\"roomid\":" + roomid + ",\"uid\":" + uid + "}";
			int msgLength = 16 + send.length();

			byte[] msgLengthBytes = BytesIntSwitch.intToByteArray(msgLength);
			byte[] argBetweenLengthAndAskarg = BytesIntSwitch.intToByteArray(1048577);
			byte[] Askarg = BytesIntSwitch.intToByteArray(7);
			byte[] argbehindAskarg = BytesIntSwitch.intToByteArray(1);

			byte[] sendHead = BytesMerger.merger(
					BytesMerger.merger(BytesMerger.merger(msgLengthBytes, argBetweenLengthAndAskarg), Askarg),
					argbehindAskarg);

			byte[] sendm = BytesMerger.merger(sendHead, send.getBytes("UTF-8"));

			byte[] heartMsg1 = BytesIntSwitch.intToByteArray(16);
			byte[] heartMsg2 = BytesIntSwitch.intToByteArray(1048577);
			byte[] heartMsg3 = BytesIntSwitch.intToByteArray(2);
			byte[] heartMsg4 = BytesIntSwitch.intToByteArray(1);

			byte[] heartMsg = BytesMerger
					.merger(BytesMerger.merger(BytesMerger.merger(heartMsg1, heartMsg2), heartMsg3), heartMsg4);

			os.write(sendm);
			os.flush();
			byte[] reply = new byte[16];
			is.read(reply);
			if (reply[11] == 8) {
				System.out.println("连接服务器成功");
			}

			// 启动心跳发送

			Heartbeats heartbeats = new Heartbeats(os, heartMsg);
			Thread startHeartbeat = new Thread(heartbeats);
			startHeartbeat.start();
			// os.write(heartMsg);

			return is;

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			/*
			try {
				is.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 */
		}
	}

}
