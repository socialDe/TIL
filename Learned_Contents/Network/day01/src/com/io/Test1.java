package com.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test1 {

	public static void main(String[] args){
		String file = "/Users/jaeuk/Desktop/Dev/Network/day01/src/test.txt";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		
		try {
			
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream("test2.txt");
			bos = new BufferedOutputStream(fos);
			
			int data = 0;
			while((data=bis.read()) != -1) {
				bos.write(data);
			}
			
			
			System.out.println(fis.available());
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
