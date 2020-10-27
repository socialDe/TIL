 package com.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Test3 {

	public static void main(String[] args) {

		String urlstr = "http://192.168.10.100/network/car.jsp";
		URL url = null;
		HttpURLConnection con = null;
		Random r = new Random();
		
		while(true) {
			try {
				double lat =r.nextFloat()+127;
				double lng =r.nextFloat()+36;
				url = new URL(urlstr+"?lat="+lat+"&lng="+lng);
				con = (HttpURLConnection)url.openConnection();
				con.setReadTimeout(5000);
				con.setRequestMethod("POST");
				con.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con.disconnect();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

}
