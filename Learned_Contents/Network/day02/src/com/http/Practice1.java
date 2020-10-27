package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Practice1 {

	public static void main(String[] args) {
		String urlstr = "http://192.168.10.100/network/login.jsp";
		URL url = null;
		HttpURLConnection con = null;
		
		try {
			String id = "qqq";
			String pwd = "111";
			url = new URL(urlstr+"?id="+id+"&pwd="+pwd);
			
			con =(HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000);
			con.setRequestMethod("POST");
			con.getInputStream();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			con = (HttpURLConnection) url.openConnection();
			is = con.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String result = "";
			while((result=br.readLine()) != null) {
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
