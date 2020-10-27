package com.sus;

import java.util.Scanner;

class Th extends Thread{

	boolean stop = false;
	boolean sus = false;
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public void setSus(boolean sus) {
		this.sus = sus;
	}
	
	@Override
	public void run() {
		while(true) {
			if(stop == true) {
				System.out.println("Stop Thread...");
				break;
			}			
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(sus !=true) {
				System.out.println("Downloading ...");
			}
		}
		System.out.println("End Thread ...");
	}
	
}
public class Test {

	public static void main(String[] args) {
		Th t = new Th();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Input CMD");
			String cmd = sc.nextLine();
			if(cmd.equals("start")) {
				t.start();
			}else if (cmd.equals("stop")) {
				t.setStop(true);
			}else if (cmd.equals("res")) {
				t.setSus(false);
			}else if (cmd.equals("sus")) {
				t.setSus(true);
			}else if (cmd.equals("q")) {
				t.setStop(true);
			}
		}
	}

}
