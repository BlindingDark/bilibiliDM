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

	String serverURL = "livecmt-2.bilibili.com";

	Heartbeats heartbeats;
	Thread startHeartbeat;

	@SuppressWarnings("resource")

	public InputStream conn() {
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;

		try {
			if (startHeartbeat != null) {
				startHeartbeat.stop();
			}

			// 1.建立客户端socket连接，指定服务器位置及端口
			socket = new Socket(serverURL, 788);
			heartbeats = new Heartbeats(socket);
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

			os.write(sendm);
			os.flush();
			
			byte[] reply = new byte[16];
			is.read(reply);
			if (reply[11] == 8) {
				System.out.println("连接服务器成功");
			}

			// 启动心跳发送
			startHeartbeat = new Thread(heartbeats);
			startHeartbeat.start();
			// os.write(heartMsg);

			return is;

		} catch (UnknownHostException e) {
			System.out.println("无法连接指定的弹幕服务器……正在重试……");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			startHeartbeat.stop();
			return this.conn();
		} catch (IOException e) {
			System.out.println("弹幕服务器连接异常……正在重试……");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			startHeartbeat.stop();
			return this.conn();
		} finally {
			/*
			 * try { is.close(); os.close(); socket.close(); } catch
			 * (IOException e) { e.printStackTrace(); }
			 */
		}
	}

}
