package com.tcpip;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	int port;
	String ip;
	
	Socket socket;
	
	OutputStreamWriter ow;
	BufferedWriter bw;
	
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	public void connectServer() {
		try {
			System.out.println("Start Client");
			socket = new Socket(ip, port);
			System.out.println("Client Connected...");
		} catch (Exception e) {
			while(true) {
				try {
					Thread.sleep(2000);
					socket = new Socket(ip, port);
					System.out.println("Client Connected...");
					break;
				} catch (Exception e1) {
					System.out.println("Retry...");
				}
			}
		}
	}
	
	
	public void request() throws IOException {
		//내부에서 Exception이 발생하더라도 socket과 reader를 닫아주기 위해 별도 예외처리
		try {
			ow = new OutputStreamWriter(socket.getOutputStream());
			bw = new BufferedWriter(ow);
			bw.write("안녕?");
			System.out.println("a message is sent to server");
		} catch(Exception e) {
			throw e;
		} finally {
			if(bw != null) {
				bw.close();
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		Client client = null;
		client = new Client("192.168.10.100",9999);
		client.connectServer();
		String msg = "";
		
		try {
			client.request();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("End Client");

	}

}
