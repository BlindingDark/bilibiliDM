package core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import tools.BytesIntSwitch;
import tools.BytesMerger;

public class Heartbeats implements Runnable {

	OutputStream os;
	byte[] heartMsg1 = BytesIntSwitch.intToByteArray(16);
	byte[] heartMsg2 = BytesIntSwitch.intToByteArray(1048577);
	byte[] heartMsg3 = BytesIntSwitch.intToByteArray(2);
	byte[] heartMsg4 = BytesIntSwitch.intToByteArray(1);

	byte[] heartMsg = BytesMerger
			.merger(BytesMerger.merger(BytesMerger.merger(heartMsg1, heartMsg2), heartMsg3), heartMsg4);

	Socket socket;
	String serverURL;
	
	public Heartbeats(String _serverURL){
		this.serverURL = _serverURL;
		this.getOutputStream(_serverURL);
	}
	

	void getOutputStream(String serverURL) {
		
		// 1.建立客户端socket连接，指定服务器位置及端口
		try {
			this.socket = new Socket(serverURL, 788);
			this.os = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println("向弹幕服务器发送消息失败……正在重试……");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.getOutputStream(serverURL);
		}
	}

	public void send() {
		try {
			os.write(heartMsg);
		} catch (IOException e) {
			this.getOutputStream(serverURL);
			this.send();
		}
	}

	@Override
	public void run() {
		while (true) {
			this.send();
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
