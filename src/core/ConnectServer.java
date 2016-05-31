package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import tools.*;

/**
 * @author BlindingDark
 *	连接b站弹幕服务器
 */
public class ConnectServer {

	int roomid;
	int uid;

	/**
	 * @param _roomid 要连接的直播间的ID（真实ID，短ID无效）
	 * @param _uid 用户ID（随便写一个用户的ID就行了，自己的就行）
	 */
	public ConnectServer(int _roomid, int _uid) {
		this.roomid = _roomid;
		this.uid = _uid;
	}

	String serverURL = "livecmt-2.bilibili.com";

	Heartbeats heartbeats;
	Thread startHeartbeat;

	/**
	 * 获取一个与弹幕服务器维持链接的 InputStream
	 * @return InputStream
	 */
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

			// 发起连接的包主要信息格式
			String send = "{\"roomid\":" + roomid + ",\"uid\":" + uid + "}";
			int msgLength = 16 + send.length();

			byte[] msgLengthBytes = BytesIntSwitch.intToByteArray(msgLength);
			byte[] argBetweenLengthAndAskarg = BytesIntSwitch.intToByteArray(1048577);
			byte[] Askarg = BytesIntSwitch.intToByteArray(7);
			byte[] argbehindAskarg = BytesIntSwitch.intToByteArray(1);

			//组合成发起连接包的头部
			byte[] sendHead = BytesMerger.merger(
					BytesMerger.merger(BytesMerger.merger(msgLengthBytes, argBetweenLengthAndAskarg), Askarg),
					argbehindAskarg);

			//头部和主体拼接
			byte[] sendm = BytesMerger.merger(sendHead, send.getBytes("UTF-8"));

			os.write(sendm);
			os.flush();
			
			//如果第 11 位为 8，则表示成功链接弹幕服务器 
			byte[] reply = new byte[16];
			is.read(reply);
			if (reply[11] == 8) {
				System.out.println("连接服务器成功");
			}

			// 启动心跳发送，每 30 秒发送一个心跳包
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
