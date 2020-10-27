package com.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Test2 {

	public static void main(String[] args) {

		String urlstr = "http://192.168.10.100/network/Writer_Project.zip";
		URL url = null;
		URLConnection con = null;
		InputStream is = null;
		BufferedInputStream bis =null;
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		
		try {
			url = new URL(urlstr);
			con = url.openConnection();
			is = con.getInputStream();
			bis = new BufferedInputStream(is,100000);
			
			fos = new FileOutputStream("network.zip");
			bos = new BufferedOutputStream(fos);
			
			int data =0;
			while((data = bis.read()) != -1) {
				bos.write(data);
				System.out.println(data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
