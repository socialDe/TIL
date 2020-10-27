package com.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test2 {

	public static void main(String[] args){
		String file = "/Users/jaeuk/Desktop/Dev/Network/day01/src/test.txt";
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		
		try {
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			fw = new FileWriter("test2.txt");
			bw = new BufferedWriter(fw);
			
			String data = "";
			while((data=br.readLine()) != null) {
				bw.write(data);
				System.out.println(data);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
