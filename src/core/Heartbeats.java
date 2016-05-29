package core;

import java.io.IOException;
import java.io.OutputStream;

public class Heartbeats implements Runnable {

	OutputStream os;
	byte[] heartMsg;

	public Heartbeats(OutputStream _os, byte[] _heartMsg) {
		this.os = _os;
		this.heartMsg = _heartMsg;
	}

	public void send() {
		try {
			os.write(heartMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			this.send();
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
