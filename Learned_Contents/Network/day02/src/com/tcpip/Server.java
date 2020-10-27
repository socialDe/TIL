package com.tcpip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	int port = 9999;
	ServerSocket serverSocket;
	Socket socket;
	
	InputStreamReader ir;
	BufferedReader br;
	
	public Server() {
		
	}
	public void startServer() throws IOException {
		serverSocket = new ServerSocket(port);
		//내부에서 Exception이 발생하더라도 socket과 reader를 닫아주기 위해 별도 예외처리
		try {
			while(true) {
				System.out.println("Ready Server ...");
				socket = serverSocket.accept();
				System.out.println("Connected ...");
				
				//Reader 생성
				ir = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(ir);
				
				//Client로부터 message 수신하여 출력
				String msg = "";
				msg = br.readLine();
				System.out.println(msg);
			}
		} catch(Exception e){
			throw e;
		} finally {
			if(br != null) {
				br.close();
			}
			if(socket != null) {
				socket.close();
			}
		}
	}

	public static void main(String[] args) {
		Server server = null;
		server = new Server();
		try {
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End Server");
	}
}
